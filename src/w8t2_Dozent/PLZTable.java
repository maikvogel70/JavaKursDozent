package w8t2_Dozent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import util.StatusBar;
import util.SwingUtil;

@SuppressWarnings("serial")
public class PLZTable extends JDialog implements ActionListener, WindowListener, ListSelectionListener, KeyListener, MouseListener
{
	
	private JMenuBar	              menuBar;
	private JMenu	                       menuDatei, menuBearbeiten, menuSuchen;
	private JMenuItem	           		miXML, miNeu, miAendern, miLoeschen, miSchliessen;
	private JMenuItem                miSuchePLZ, miSucheOrt;
	private   JTable         				  Tabelle;
	private   JScrollPane    		   jspTabelle;
	
	private StatusBar	               	statusBar;
	
	private int colPLZWidth;
	
	private long primaryKey;
		
	public PLZTable()
	{
		InitializeComponent();
	}

	private void InitializeComponent()
	{
		this.setTitle("Postleitzahlen");
		this.setModal(true);
		this.setBounds(10, 10, 800, 480);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.addWindowListener(this);
		
		menuBar = new JMenuBar();

		menuDatei = new JMenu("Datei");
		menuDatei.setMnemonic('D');
		menuBar.add(menuDatei);
		
		miXML = SwingUtil.createMenuItem(menuDatei, null,  SwingUtil.MenuItemType.ITEM_PLAIN, this, "Als XML exportieren", null, 'X', null);
				
		menuDatei.addSeparator();
		
		miSchliessen = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Schließen", null, 'S', null);
		
		menuBearbeiten = new JMenu("Bearbeiten");
		menuBearbeiten.setMnemonic('B');
		menuBar.add(menuBearbeiten);
		
		miNeu = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Neu", null, 'N', null);	
		miAendern = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Ändern", null, 'Ä', null);
		miLoeschen = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Löschen", null, 'L', null);
				
		menuSuchen = new JMenu("Suchen");
		menuSuchen.setMnemonic('u');
		menuBar.add(menuSuchen);
		
		miSuchePLZ = SwingUtil.createMenuItem(menuSuchen, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Postleitzahlen", null, 'P', null);
		miSucheOrt = SwingUtil.createMenuItem(menuSuchen, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Ort", null, 'O', null);
				
		this.setJMenuBar(menuBar);
		
		Tabelle = new JTable(); 
		// Keine Mehrfach-Auswahl zulassen
		Tabelle.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		Tabelle.addKeyListener(this);
		Tabelle.addMouseListener(this);
		 jspTabelle = new JScrollPane(Tabelle);
		// Listener zur Anzeige der aktuellen Zeilennummer
		 Tabelle.getSelectionModel().addListSelectionListener(this);
		 
		 
		this.add(jspTabelle, BorderLayout.CENTER);	
			
		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(0, 25));
		statusBar.setStatusLabelFont(statusBar.getFont().deriveFont(Font.PLAIN));
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(statusBar, BorderLayout.PAGE_END);
  		
	}
	
	private void initDialog()
	{
		readProperties(Globals.getApplicationConfigPath() + "\\" +  this.getClass().getName()+ ".config");
		statusBar.setText("Datensätze werden gelesen ..."); 
		showDataThread();	
	}
	
	
	private void showDataThread()
	{
		 // Thread zum Einlesen der Postleitzahlen verwenden.
		//  Die Form wird sofort sichtbar und das Lesen der
		//  Datensätze erfolgt im Hintergrund.
		
		 Thread t  = new Thread(new ShowData());
		 t.start();
		
	}
	
	private class ShowData  implements Runnable
	 {

		@Override
		public void run()
		{
			Tabelle.setModel(new PLZTableModel());
			
			// Überschriften der Tabelle grösser und fett
			Font font = Tabelle.getTableHeader().getFont().deriveFont(Font.BOLD, 14.0f);
			Tabelle.getTableHeader().setFont(font);
			
			// Erste Spalte (PRIMARYKEY) unsichtbar machen
			setTableColumnInvisible(Tabelle, 0);     

			// Spaltenbreite setzen
			setTableColumnWidth(Tabelle, 1, 100);     
			
			if (Tabelle.getRowCount() > 0)
				setSelectedRow(1);
			else
				statusBar.setText(null);		
			
		}
	 }
	
	private class PLZTableModel extends AbstractTableModel
	{

		 private int               anzahlZeilen;
	     private int               anzahlSpalten;
	     private ArrayList<String> ColumnNames;
	     private Object[][]  data;
			     
		String SQL = "SELECT PRIMARYKEY, PLZ as Postleitzahl, ORT as Wohnort FROM POSTLEITZAHLEN  ORDER BY PLZ ASC, ORT ASC";
		
		public PLZTableModel()
		{
				
			// Ausführen der SQL-Anweisung zum Lesen aller Datensätzen 
			ResultSet rSet = DBConnection.executeQuery(SQL);
			
			// Lesen der Metadaten: Anzahl, Datentypen und Eigenschaften der Spalten
	   		// aus der SQL Anweisung
			ResultSetMetaData rsMetaData = getMetaData(rSet);
			
			// Anzahl der Spalten aus ResultSet ermitteln
			anzahlSpalten = getColumnCount(rsMetaData);
			
			// Anzahl der Zeilen anhand des ResultSets ermitteln kann nur mit MySQL
			// verwendet werden.
			//anzahlZeilen = getRowCount(rSet); 
			
			// Allgemeine Lösung zur Ermittlung der Anzahl der Zeilen
			anzahlZeilen = getRowCountBySQL();
					
			// Überschriften der Spalten aus den Metadaten erstellen
			SetHeader(rsMetaData);
			
			// Liest alle Datensätze aus dem Resultset in das zweidimensionale Objekt-Array 'data'
			getData(rSet);
			
		}
		
		private ResultSetMetaData getMetaData(ResultSet rs)
  		 {
  			 ResultSetMetaData rsMD = null;
  			 
  			 try
  			 {
  				 rsMD = rs.getMetaData();
  			 }
  			 catch (Exception ex)
  			 {
  				 JOptionPane.showMessageDialog(null, "getMetaData: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
  			 }
  			 
  			 return rsMD; 
  		 }
		
		private int getRowCount(ResultSet rs)
	   	 {
			int retValue = 0;
			
			try
			{
				rs.last();
				retValue = rs.getRow();
				rs.beforeFirst();
			}
			 catch(Exception ex)
	   		 {
	   			 JOptionPane.showMessageDialog(null, "getRowCount: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);		
	   		 }
			
			return retValue;
	   	 }
		
	   	 private int getRowCountBySQL()
	   	 {
	   		int retValue = 0;
	   		
	   		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN";
	   		
	   		Object obj = DBConnection.executeScalar(SQL);
	   			   		
	   		return Integer.parseInt(obj.toString());
	   	 }
	   	 
		 private int getColumnCount(ResultSetMetaData rsMD)
	   	 {
	   		 int retValue = 0;
	   		 
	   		 try
	   		 {
	   			 retValue = rsMD.getColumnCount();
	   		 }
	   		 catch(Exception ex)
	   		 {
	   			 JOptionPane.showMessageDialog(null, "getColumnCount: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
	   		 }
	   		 
	   		 return retValue;
	   		 
	   	 }
		 
		 private void SetHeader(ResultSetMetaData rsMD)
	   	 {
	   		 ColumnNames = new ArrayList<String>();
	           for (int i = 1; i <= anzahlSpalten; i++)
	         	  ColumnNames.add(getColumnLabel(rsMD, i));
	   	 }
		 
		 // Den Spaltennamen der SELECT-Anweisung zurückliefern 
	   	 private String getColumnName(ResultSetMetaData rsMD, int colIndex)
	   	 {
	   		 String colName =  "";
	   		 
	   		 try
	   		 {
	   			 colName = rsMD.getColumnName(colIndex);
	   		 }
	   		 catch(Exception ex)
	   		 {
	   			 JOptionPane.showMessageDialog(null, "getColumnName: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
	   		 }
	   		 
	   		 return colName;
	   	 }
		 
	   	 private String getColumnLabel(ResultSetMetaData rsMD, int colIndex)
	   	 {
	   		 String colName =  "";
	   		 
	   		 try
	   		 {
	   			 colName = rsMD.getColumnLabel(colIndex);
	   		 }
	   		 catch(Exception ex)
	   		 {
	   			 JOptionPane.showMessageDialog(null, "getColumnLabel: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
	   		 }
	   		 
	   		 return colName;
	   	 }
	   	 
	   	 // Spaltenindex für den angegeben Spaltennamen ermitteln
	   	 private int getColumnIndex(String colName)
	   	 {
	   		 int retValue = -1;
	   		 int i = 0;
	   		 
	   		 for (String s : ColumnNames)
	   		 {
	   			if (s.equals(colName))
	   			{
	   				retValue = i;
	   				break;
	   			}
	   			i++;
	   		 }
	   		 
	   		 return retValue;
	   	 }
	   	 
	   	 private void getData(ResultSet rs)
	   	 {
	   		 data = new Object[anzahlZeilen][anzahlSpalten];
	   		 
	   		 try
	   		 {
	   			 for (int zeile = 1; zeile <= anzahlZeilen; zeile++)
	   			 {
	   				 rs.next();
	   				 for (int spalte = 1; spalte <= anzahlSpalten; spalte++)
	   					 data[zeile - 1][spalte - 1] = rs.getString(spalte);
	   			 }
	   			 
	   		 }
	   		catch(Exception ex)
	   		   {
	   		   	 JOptionPane.showMessageDialog(null, "getData: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);	
	   		   }
	   	 }
		 
		public int findEntry(String colName, Object value)
		{
			int retValue = -1;
			String searchValue;
			String leftPattern = "";
			String rightPattern = "";
			
			int colIndex = getColumnIndex(colName);
			if (colIndex == -1)
				return retValue;

			// Das Muster mit dem regulären Ausdruck erstellen::
			// Findet alle Suchbegriff innerhalb einer
			// Vergleichszeichenkette
			// unabhängig von Gross- oder Kleinschreibung (?i)
			
			// Exakter Vergleich
			// ^Suchbegriff$
			searchValue = "^" + value.toString() + "$";
			
			if (value.toString().startsWith("*"))
			{
				// Endet mit Suchbegriff$
				rightPattern = "$";
				searchValue = value.toString().substring(1, value.toString().length());
				value = searchValue;
			}
			
			if (value.toString().endsWith("*"))
			{
				// Beginnt mit ^Suchbegriff
				leftPattern = "^";
				searchValue = value.toString().substring(0, value.toString().length() -1 );
			}
						
			Pattern p = Pattern.compile("(?i)" + leftPattern +  searchValue  + rightPattern);
			
			for (int zeile = 0; zeile < getRowCount(); zeile++)
			{
				// Einen Matcher mit dem Vergleichszeichenkette erstellen
				Matcher m = p.matcher(data[zeile][colIndex].toString());
				if (m.find())
				{
					retValue = zeile;
					break;
				}
				
//				if (data[zeile][colIndex].toString().equals(value.toString()))
//				{
//					retValue = zeile;
//					break;
//				}
			}

			return retValue;

		}
	   	 
		public void convertToXML(String Filename)
		{
			PrintWriter xml = null;
			File file;
			
			try
			{
			    xml = new PrintWriter(new FileWriter(Filename));
			    
			   xml.println("<?xml version=\"1.0\"  encoding=\"UTF-8\"?>");
			    
			    for (int i = 0; i < getRowCount(); i++)
			    {
			    	xml.println("<PLZTable>");
			    	for (int j = 0; j < getColumnCount(); j++)
			    	  		  xml.println("     <" + getColumnName(j) + ">"+ getValueAt(i, j) + "</"+ getColumnName(j) + ">");
			    	  xml.println("</PLZTable>");		    		
			    }
			    
			    xml.close();
			}
			catch(Exception ex)
			{
				 JOptionPane.showMessageDialog(null, "convertToXML: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);	
			}
		}
	   	 
	   	@Override
	   	 public String getColumnName(int colIndex)
	       {
	          return ColumnNames.get(colIndex);
	       }
	   	 
		@Override
		public int getColumnCount()
		{
			
			return anzahlSpalten;
		}

		@Override
		public int getRowCount()
		{
			
			return anzahlZeilen;
		}

		@Override
		public Object getValueAt(int rowIndex, int colIndex)
		{
			return data[rowIndex][colIndex];
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int colIndex)
		{
			data[rowIndex][colIndex] = value;
			fireTableCellUpdated(rowIndex, colIndex);
		}
		
	}
	
	public void setTableColumnInvisible(JTable jTable, int col) 
	{
		jTable.getColumnModel().getColumn(col).setWidth(0);
		jTable.getColumnModel().getColumn(col).setMaxWidth(0);
		jTable.getColumnModel().getColumn(col).setMinWidth(0);
		jTable.getColumnModel().getColumn(col).setPreferredWidth(0);
		jTable.getColumnModel().getColumn(col).setResizable(false);
		jTable.getTableHeader().resizeAndRepaint();
	} 
	
	private void setTableColumnWidth(JTable jTable, int col, int width)
	{
		jTable.getColumnModel().getColumn(col).setWidth(width);
		// setMaxWidth() ist erforderlich um die Spaltenbreite zu setzen,
		// durch den Multiplikator wird aber die Möglichkeit gegeben
		// die Spalte nachträglich um das n-fache zu vergrössern. 
		jTable.getColumnModel().getColumn(col).setMaxWidth(width * 3);
		// Ohne Angabe von MinWidht() kann die Spalte vollständig minimiert werden.
		//jTable.getColumnModel().getColumn(col).setMinWidth(width);
		jTable.getColumnModel().getColumn(col).setPreferredWidth(width);
		jTable.getTableHeader().resizeAndRepaint();		
		
	}
	
	private void setSelectedRow(int row)
	{
		 Tabelle.changeSelection(row - 1, 0, false,true );
	}
	
	public void ShowDialog()
	{
		initDialog();
		//Globals.CenterOnDesktop(this);
		this.setVisible(true);
	}
	
	private void detailFormat(long Key)
	{
		
		int selectedRow = Tabelle.getSelectedRow();
		
		PLZForm f = new PLZForm(this, Key);
		f.showDialog();
		
		if (Key > -1)
			updateTableRow(selectedRow);		
		else
		{
			statusBar.setText("Datensätze werden gelesen ...");
			// Aufruf von ShowData() ohne Thread weil hier
			// gewartet werden muss, bis die Daten vollständig
			// gelesen wurden, um anschliessend auf die
			// Zeile positionieren bzw. einen bestimmten Eintrag 
			// finden zu können.
			
			ShowData showData = new ShowData();
			showData.run();
			
			selectRowByValue("PRIMARYKEY", this.primaryKey);		
		}
	}
	
	private void selectRowByValue(String colName, Object value)
	{
		int rowIndex = ((PLZTableModel)Tabelle.getModel()).findEntry(colName, value);
		setSelectedRow(++rowIndex);
		
	}

	private void updateTableRow(int row)
	{
		
		String plz, ort;
		
		String SQL = "SELECT PLZ, ORT FROM POSTLEITZAHLEN";
		SQL += " WHERE PRIMARYKEY = " + Tabelle.getValueAt(row, 0).toString();
		
		ResultSet rSet = DBConnection.executeQuery(SQL);
		if (rSet == null) return;

		try
		{
			if (rSet.next())
			{
				plz = rSet.getString("PLZ");
				ort	= rSet.getString("ORT");
				rSet.close();
				
				Tabelle.setValueAt(plz,  row, 1);
				Tabelle.setValueAt(ort,  row, 2);
				
			}
			
		}
		catch (Exception ex)	{	}
		
	}

	public void setNewEntryKey(long Key)
	{
		this.primaryKey = Key;
	}
	
	private void deleteEntry(long Key)
	{
	
		Object[] options = { "Ja", "Nein"};
		
		String SQL = "DELETE FROM POSTLEITZAHLEN";
		SQL += " WHERE PRIMARYKEY = " + Long.toString(Key);
		
		int retValue = JOptionPane.showOptionDialog(this, "Datensatz löschen", "Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		
		// Nein - Nicht Löschen
		if (retValue !=  0) return;
		
		// Aktuelle Zeile merken
		int selectedRow =  Tabelle.getSelectedRow();
		
		DBConnection.executeNonQuery(SQL);
		
		statusBar.setText("Datensätze werden gelesen ...");
		ShowData showData = new ShowData();
		showData.run();

		// Die die vorherige  Zeile positionieren 
		setSelectedRow(selectedRow);
		
		
	}
	
	private void writeXML()
	{
		((PLZTableModel)Tabelle.getModel()).convertToXML("c:\\Temp\\PLZTable.xml");
	}
	
	private void readProperties(String Dateiname)
	{

		int x = 0;
		int y = 0;
		int width = -1;
		int height = -1;
		String property;
		
		Reader reader = null;

		try
		{
			reader = new FileReader(Dateiname);
			Properties prop = new Properties();
			prop.load(reader);

			property = prop.getProperty("Location.X");
			if (property != null) x = Integer.parseInt(property);
			property = prop.getProperty("Location.Y");
			if (property != null) y = Integer.parseInt(property);
			property = prop.getProperty("Width");
			if (property != null) width = Integer.parseInt(property);
			property = prop.getProperty("Height");
			if (property != null) height  = Integer.parseInt(property);
			property = prop.getProperty("colPLZ.Width");	
			if (property != null) colPLZWidth  = Integer.parseInt(property);

		}
		catch (IOException ex) 	{}
		finally
		{
			// Die close_Methode wirft eine Ausnahmebedingung.
			// Deshalb muss sie in einer try-catch-Anweisung formuliert
			// werden.
			try
			{
				reader.close();
			}
			catch (Exception ex) {	}
		}
		
		if (width == -1) width = this.getWidth();
		if (height == -1) height = this.getHeight();
		
		this.setBounds(x, y, width, height);

	}
	
	private void writeProperties(String Dateiname)
	{
		Writer writer = null;
		
		File f = new File(Dateiname);
		f = new File(f.getParent());
		
		if (!f.exists())
			f.mkdirs();
		
		Properties prop = new Properties();
		prop.setProperty("Location.X", String.valueOf(this.getLocation().x));
		prop.setProperty("Location.Y", String.valueOf(this.getLocation().y));
		prop.setProperty("Width", String.valueOf(this.getWidth()));
		prop.setProperty("Height", String.valueOf(this.getHeight()));
		prop.setProperty("colPLZ.Width", new Integer(Tabelle.getColumnModel().getColumn(1).getWidth()).toString());
		
		try
		{
			writer = new FileWriter(Dateiname);
			prop.store(writer, null);
		}
		catch (IOException ex) 	{}
		finally
		{
			// Die close_Methode wirft eine Ausnahmebedingung.
			// Deshalb muss sie in einer try-catch-Anweisung formuliert
			// werden.
			try
			{
				writer.close();
			}
			catch (Exception ex) {	}
		}
	}
	
	private void suchePLZ()
	{
		String str = JOptionPane.showInputDialog(this, "Postleitzahl eingeben (vollständig oder teilweise ( *Zahl, Zahl*): ", "Suchen", JOptionPane.PLAIN_MESSAGE);
		if (str == null) return;
		
		selectRowByValue("Postleitzahl", str);
		
	}

	private void sucheOrt()
	{
		String str = JOptionPane.showInputDialog(this, "Ort eingeben (vollständig oder teilweise ( *Name, Name*): ", "Suchen", JOptionPane.PLAIN_MESSAGE);
		if (str == null) return;
		
		selectRowByValue("Wohnort", str);	
	}
 
	@Override
	public void mouseClicked(MouseEvent e)
	{
		int selectedRow;
		
		// Doppelklick erkennen
		if (e.getClickCount() == 2)
		{
			selectedRow = Tabelle.getSelectedRow();
			if (selectedRow != -1)
				detailFormat(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

		int selectedRow = Tabelle.getSelectedRow();
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			e.consume();
			if (selectedRow != -1)
					detailFormat(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
		} 
		else if (e.getKeyCode() == KeyEvent.VK_HOME)
			setSelectedRow(1);
		else if (e.getKeyCode() == KeyEvent.VK_END)
			setSelectedRow(Tabelle.getRowCount());
		else if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			if (selectedRow != -1)
				deleteEntry(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
	       statusBar.setText(String.format("Datensatz %d von %d", Tabelle.getSelectedRow() + 1, Tabelle.getRowCount())); 		
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		writeProperties(Globals.getApplicationConfigPath() + "\\" +  this.getClass().getName()+ ".config");
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		int selectedRow = Tabelle.getSelectedRow();
		
		if (e.getSource().equals(miXML))
			writeXML();
		else if (e.getSource().equals(miNeu))
			detailFormat(-1);
		else if (e.getSource().equals(miAendern))
		{
			if (selectedRow != -1)
			 	   detailFormat(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
		}
		else if (e.getSource().equals(miLoeschen))
		{
			if (selectedRow != -1)
				deleteEntry(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
		}
		else if (e.getSource().equals(miSuchePLZ))
			suchePLZ();			
		else if (e.getSource().equals(miSucheOrt))
			sucheOrt();
	}

}
