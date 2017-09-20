package w6t5_Dozent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import dao.Postleitzahl;
import dao.PostleitzahlenDAO;
import util.StatusBar;
import util.SwingUtil;

public class PLZTable extends JDialog implements ActionListener, ListSelectionListener, KeyListener, MouseListener
{

	private JMenuBar	  menuBar;
	private JMenu	      menuDatei, menuBearbeiten;
	private JMenuItem	  miNeu, miAendern, miLoeschen, miSchliessen;
	
	// Popup Menü verwenden
	private JPopupMenu 	  popupMenu;
	private JMenuItem     pmiNeu, pmiAendern, pmiLoeschen; 
	
	
	private JTable        Tabelle;
	private JScrollPane   jspTabelle;
	
	private StatusBar 	  statusBar;
	
	private Component	  owner;
	
	public PLZTable()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		
		this.setTitle("Postleitzahlen");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(("/images/Server.png"))));
		this.setSize(800,  480);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		// Das Standard-Layout ist das BorderLayout
		
		menuBar = new JMenuBar();
		
		menuDatei = SwingUtil.createMenu(menuBar, "Datei", null, 'D');
		miSchliessen = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Schließen", null, 'S', null);
		
		menuBearbeiten = SwingUtil.createMenu(menuBar, "Bearbeiten", null, 'B');
		miNeu = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Neu", 
				new ImageIcon(this.getClass().getResource("/images/New.png")), 'N', null);
		miAendern = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Ändern", 
				new ImageIcon(this.getClass().getResource("/images/Edit.png")), 'Ä', null);
		miLoeschen = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Löschen", 
				new ImageIcon(this.getClass().getResource("/images/Delete.png")), 'L', null);
		
		this.setJMenuBar(menuBar);
		
		// Tabelle zur Anzeige der Datensätze
		Tabelle = new JTable();
		// Keine Mehrfachauswahl zulassen
		Tabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Listener zur Anzeige der aktuellen Zeilennummer
		Tabelle.getSelectionModel().addListSelectionListener(this);
		
		// KeyListener für POS1/ENDE-Taste
		Tabelle.addKeyListener(this);
		
		// MouseListener für Doppelklick
		Tabelle.addMouseListener(this);

		
		createPopupMenu();
		
		// Der Tabelle das PopupMenü zuweisen
		Tabelle.setComponentPopupMenu(popupMenu);
		
		// Zum Blättern der Tabelle
		jspTabelle = new JScrollPane(Tabelle);
		
		this.add(jspTabelle, BorderLayout.CENTER);
		
		
		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		statusBar.setPreferredSize(new Dimension(0, 28));
		statusBar.setStatusLabelFont(statusBar.getStatusLabelFont().deriveFont(Font.PLAIN));
		this.add(statusBar, BorderLayout.PAGE_END);
		
	}

	private void initDialog()
	{
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		
		showDataThread();
		
			
	}
	
	private void createPopupMenu()
	{
		popupMenu = new JPopupMenu();

		pmiNeu = SwingUtil.createPopUpMenuItem(popupMenu, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Neu", 
				new ImageIcon(this.getClass().getResource("/images/New.png")), 'N', null);
				
		pmiAendern = SwingUtil.createPopUpMenuItem(popupMenu, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Ändern", 
				new ImageIcon(this.getClass().getResource("/images/Edit.png")), 'Ä', null);
				
		pmiLoeschen = SwingUtil.createPopUpMenuItem(popupMenu, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Löschen", 
				new ImageIcon(this.getClass().getResource("/images/Delete.png")), 'L', null);
		
	}
	
	
	public void showDialog()
	{
		initDialog();
		this.setVisible(true);
	}
	
	
	public void showDialog(Component owner)
	{
		this.owner = owner;
		showDialog();
	}
	
	private void showDataThread()
	{
		
		statusBar.setText("Datensätze werden gelesen ...");
		
		Thread t = new Thread(new ShowData());
		t.start();
		
	}
	
	
	private void showData()
	{
		
		//Thread t = new Thread(new ShowData());
		//t.run();
		
		// oder
		
		ShowData showData = new ShowData();
		showData.run();
	
	}
	
	
	private void setTableColumnWidth(JTable jTable, int col, int width)
	{
		
		jTable.getColumnModel().getColumn(col).setPreferredWidth(width);
		jTable.getColumnModel().getColumn(col).setMaxWidth(width * 3);

		
	}
	
	
	private void setTableColumnInvisible(JTable jTable, int col)
	{
		
		// Wichtig ist die Reihenfolge der Aufrufe
		jTable.getColumnModel().getColumn(col).setWidth(0);
		jTable.getColumnModel().getColumn(col).setMaxWidth(0);
		jTable.getColumnModel().getColumn(col).setMinWidth(0);
		jTable.getColumnModel().getColumn(col).setPreferredWidth(0);
		jTable.getColumnModel().getColumn(col).setResizable(false);
		
		
	}
	
	
	private void setSelectedRow(int rowIndex)
	{
		
		Tabelle.changeSelection(rowIndex, 0, false, true);
		
		
	}
	
	
	private void showPLZDialog(long Key)
	{
		
		PLZForm dlg = new PLZForm(Key);
		dlg.showDialog(this);
		
		if (Key > -1)
		{
			// Änderungsmodus
			updateTableRow(Tabelle.getSelectedRow());
			
		}
		else
		{
			if (dlg.getPrimaryKey() > -1)
			{
				showData();
				selectRowByValue("PK", dlg.getPrimaryKey());
			}
		}
		
	}
	
	
	private int selectRowByValue(String colName, Object value)
	{
		
		int rowIndex = ((PLZTableModel)Tabelle.getModel()).findEntry(colName, value);
		setSelectedRow(rowIndex);
		
		return rowIndex;
	}
	
	
	private void updateTableRow(int row)
	{
		
		Postleitzahl plz = PostleitzahlenDAO.getPostleitzahl((long)Tabelle.getValueAt(row, 0));
		
		if (plz == null)
			return;
		
		Tabelle.setValueAt(plz.getPLZ(), row, 1);
		Tabelle.setValueAt(plz.getOrt(), row, 2);
		
	}
	
	
	
	private void deleteEntry(long Key)
	{
		
		// Benutzerdefinierte Buttontexte
		String[] options = { "Ja", "Nein" };
		
		int retValue = JOptionPane.showOptionDialog(this, "Datensatz löschen", "Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		
		if (retValue == JOptionPane.NO_OPTION)
			return;
		
		
		int selectedRow = Tabelle.getSelectedRow();
		
		if (PostleitzahlenDAO.deleteEntry(Key))
		{
			
			showData();
			
			// Auf die vorherige Zeile positionieren
			setSelectedRow(selectedRow - 1);
			
		}
	}
	
	
	private class ShowData implements Runnable
	{

		@Override
		public void run()
		{
			
			Tabelle.setModel(new PLZTableModel());
			
			// Überschriften der Tabelle größer und fett
			Font font = Tabelle.getTableHeader().getFont().deriveFont(Font.BOLD, 14F);
			Tabelle.getTableHeader().setFont(font);
			
			// Spalte PRIMARYKEY unsichtbar machen
			setTableColumnInvisible(Tabelle, 0);
			
			// Spaltenbreite für die Spalte PLZ setzen
			setTableColumnWidth(Tabelle, 1, 100);
			
			// Weitere Zellenformatierungen
			Tabelle.setRowHeight(21);
			Tabelle.setIntercellSpacing(new Dimension(5, 2));
			
			// Alle Spaltenüberschriften linksbündig ausrichten
			DefaultTableCellRenderer tableHeaderRenderer = (DefaultTableCellRenderer)Tabelle.getTableHeader().getDefaultRenderer();
			tableHeaderRenderer.setHorizontalAlignment(SwingConstants.LEFT);
			Tabelle.getTableHeader().setDefaultRenderer(tableHeaderRenderer);
			
			// Tabelle aktivieren, wenn Datensätze vorhanden
			Tabelle.setEnabled(Tabelle.getRowCount() > 0);
			
			if (Tabelle.isEnabled())
			{
				setSelectedRow(0);
			}
			else
			{
				statusBar.setText(null);
			}
			
			miAendern.setEnabled(Tabelle.isEnabled());
			miLoeschen.setEnabled(Tabelle.isEnabled());
			
			
		}
		
	}
	
	
	private class PLZTableModel extends AbstractTableModel
	{
		
		private int 		anzahlZeilen;
		private int 		anzahlSpalten;
		
		private String[] COLUMN_NAMES = {"PK", "Postleitzahl", "Wohnort" };
		
		private Object[][]  data;
		
		
		public PLZTableModel()
		{
			// Ausführen der SQL-Anweisung zum Lesen aller Datensätzen
			List<Postleitzahl> list = PostleitzahlenDAO.getPostleitzahlen();
			
			anzahlSpalten = COLUMN_NAMES.length;
			
			anzahlZeilen = list.size();
			
			
			getData(list);
			
			
		}
		
		
		private void getData(List<Postleitzahl> list)
		{
			
			data = new Object[anzahlZeilen][anzahlSpalten];
			
			
			for (int zeile = 0; zeile < anzahlZeilen; zeile++)
			{
				
				for (int spalte = 0; spalte < anzahlSpalten; spalte++)
				{
					
					switch(spalte)
					{
						case 0:
							data[zeile][spalte] = list.get(zeile).getPrimaryKey();
							break;
							
						case 1:
							data[zeile][spalte] = list.get(zeile).getPLZ();
							break;
							
						case 2:
							data[zeile][spalte] = list.get(zeile).getOrt();
							break;	
					
					}
					
				}
				
			}
		}
		
		
		// Spaltenindex für den angegebenen Spaltennamen ermitteln
		public int getColumnIndex(String colName)
		{
			
			int retValue = -1;
			
			for (int i =0; i < COLUMN_NAMES.length; i++)
			{
				
				if (COLUMN_NAMES[i].equalsIgnoreCase(colName))
				{
					retValue = i;
					break;
				}
				
			}
			
			return retValue;
		}
		
		public int findEntry(String colName, Object value)
		{
			
			int retValue = -1;
			
			int colIndex = getColumnIndex(colName);
			if (colIndex == -1)
			{
				return retValue;
			}
			
			
			for (int zeile = 0; zeile < getRowCount(); zeile++)
			{
				if (data[zeile][colIndex].toString().equals(value.toString()))
				{
					retValue = zeile;
					break;
				}
				
			}
			
			return retValue;
			

		}
		
		
		
		// Keine abstrakte Methode.
		// Muß manuell überschrieben werden, falls notwendig.
		@Override
		public String getColumnName(int colIndex)
		{
			return COLUMN_NAMES[colIndex];
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
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == miSchliessen)
		{
			this.dispose();
		}
		else if (e.getSource() == miAendern || e.getSource() == pmiAendern)
		{
			showPLZDialog((long)Tabelle.getValueAt(Tabelle.getSelectedRow(), 0));
		}
		else if (e.getSource() == miNeu || e.getSource() == pmiNeu)
		{
			showPLZDialog(-1);
		}
		else if (e.getSource() == miLoeschen || e.getSource() == pmiLoeschen)
			deleteEntry((long)Tabelle.getValueAt(Tabelle.getSelectedRow(), 0));
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		
		if (!e.getValueIsAdjusting())
		{
			statusBar.setText(String.format("Datensatz %s von %s", NumberFormat.getInstance().format(Tabelle.getSelectedRow() + 1),
					                         NumberFormat.getInstance().format(Tabelle.getRowCount())));
		}
		
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
		if (e.getKeyCode() == KeyEvent.VK_HOME)
		{
			setSelectedRow(0);
		}
		else if (e.getKeyCode() == KeyEvent.VK_END)
		{
			setSelectedRow(Tabelle.getRowCount() - 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			e.consume();
			showPLZDialog((long)Tabelle.getValueAt(Tabelle.getSelectedRow(), 0));
		}
		else if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			deleteEntry((long)Tabelle.getValueAt(Tabelle.getSelectedRow(), 0));
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
	public void mouseClicked(MouseEvent e)
	{
		
		// Doppelklick erkennen
		if (e.getClickCount() == 2)
		{
			showPLZDialog((long)Tabelle.getValueAt(Tabelle.getSelectedRow(), 0));
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
	
}
