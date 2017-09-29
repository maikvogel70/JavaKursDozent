package w8t1_Dozent;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXSearchField;

import util.StatusBar;
import util.SwingUtil;

@SuppressWarnings("serial")
public class PLZTable extends JDialog implements ActionListener, WindowListener, ListSelectionListener, KeyListener, MouseListener, AWTEventListener
{

	private JMenuBar menuBar;
	private JMenu menuDatei, menuBearbeiten, menuFilter;
	private JMenuItem miXML, miNeu, miAendern, miLoeschen, miSchliessen, miDrucken;
	private JMenuItem miFilterPLZ, miFilterOrt, miFilterLoeschen;
	private JTable Tabelle;
	private JScrollPane jspTabelle;

	private StatusBar statusBar;
	private Component owner;

	private JToolBar filterToolbar;
	private JLabel lblFilter;
	private JButton btnWeitersuchen;

	// private JTextField tfFilter;
	private JXSearchField tfFilter, tfSuche;

	private ArrayList<String> SQLColumns;
	private ArrayList<String> SQLTables;

	private File fcFile;

	private enum PLZTableFilter
	{
		KEIN_FILTER, PLZ, ORT
	}

	private PLZTableFilter currFilter = PLZTableFilter.KEIN_FILTER;

	public PLZTable()
	{
		InitializeComponent();
	}

	private void InitializeComponent()
	{
		this.setTitle("Postleitzahlen");
		this.setBounds(10, 10, 800, 480);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.addWindowListener(this);

		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);

		menuBar = new JMenuBar();

		menuBar = new JMenuBar();

		menuDatei = SwingUtil.createMenu(menuBar, "Datei", null, 'D');
		miXML = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "XML", null, 'X', null);
		menuDatei.addSeparator();
		miDrucken = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Drucken...", null, 'D', null);
		menuDatei.addSeparator();
		miSchliessen = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Schließen", null, 'S', null);

		menuBearbeiten = SwingUtil.createMenu(menuBar, "Bearbeiten", null, 'B');
		miNeu = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Neu", null, 'N', null);
		miAendern = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Ändern", null, 'Ä', null);
		miLoeschen = SwingUtil.createMenuItem(menuBearbeiten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Löschen", null, 'L', null);

		menuFilter = SwingUtil.createMenu(menuBar, "Filter", null, 'F');
		miFilterPLZ = SwingUtil.createMenuItem(menuFilter, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Postleitzahlen", null, 'P', null);
		miFilterOrt = SwingUtil.createMenuItem(menuFilter, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Ort", null, 'O', null);
		menuFilter.addSeparator();
		miFilterLoeschen = SwingUtil.createMenuItem(menuFilter, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Löschen", null, 'L', null);

		// Abstand zwischen Menüeinträgen und Suchfeld
		// Die Breite des Suchfeldes soo elwa 400 Pixel betragen.
		menuBar.add(Box.createHorizontalStrut(this.getWidth() - 400));

		// Suchfeld auf der rechten Seite des Menüs hinzufügen
		menuBar.add(Box.createHorizontalGlue());

		tfSuche = new JXSearchField();
		// Die Schriftfarbe für den Prompt-Text etwas deutlicher hervorgheben
		tfSuche.setPromptForeground(Color.GRAY);
		tfSuche.setPrompt("  Tabelle durchsuchen");
		// Initial unsichtbar
		tfSuche.setVisible(false);
		tfSuche.addKeyListener(this);
		menuBar.add(tfSuche);

		this.setJMenuBar(menuBar);

		Tabelle = new JTable();
		// Keine Mehrfach-Auswahl zulassen
		Tabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Tabelle.addKeyListener(this);
		Tabelle.addMouseListener(this);
		jspTabelle = new JScrollPane(Tabelle);
		// Listener zur Anzeige der aktuellen Zeilennummer
		Tabelle.getSelectionModel().addListSelectionListener(this);

		this.add(jspTabelle, BorderLayout.CENTER);

		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(200, 25));
		statusBar.setHorizontalAlignment(JLabel.CENTER);
		this.add(statusBar, BorderLayout.PAGE_END);

		// Filter Toolbar
		filterToolbar = new JToolBar();
		filterToolbar.setPreferredSize(new Dimension(300, 30));
		// setName() setzt die Titlezeile der Toolbar.
		// Diese wird angezeigt, wenn die Toolbar auf das Fenster gezogen wird.
		filterToolbar.setName("Filter");
		// Initial unsichtbar
		filterToolbar.setVisible(false);

		lblFilter = new JLabel();
		filterToolbar.add(lblFilter);
		filterToolbar.addSeparator();
		tfFilter = new JXSearchField();
		// Die Schriftfarbe für den Prompt-Text etwas deutlicher hervorgheben
		tfFilter.setPromptForeground(Color.GRAY);
		tfFilter.setPrompt("  Vollständigen oder teilweisen Suchbegriff eingeben: Suchtext*, *Suchtext, *Suchtext*");

		tfFilter.addKeyListener(this);
		filterToolbar.add(tfFilter);

		btnWeitersuchen = new JButton("Weitersuchen");
		btnWeitersuchen.setFont(btnWeitersuchen.getFont().deriveFont(Font.PLAIN, 10.0f));
		btnWeitersuchen.setPreferredSize(new Dimension(80, 20));
		btnWeitersuchen.addActionListener(this);
		// Initial unsichtbar
		btnWeitersuchen.setVisible(false);
		filterToolbar.add(btnWeitersuchen);

		this.add(filterToolbar, BorderLayout.PAGE_START);

	}

	private void initDialog()
	{
		SQLColumns = new ArrayList<String>();
		SQLColumns.add("PRIMARYKEY");
		SQLColumns.add("PLZ as Postleitzahl");
		SQLColumns.add("ORT as Wohnort");

		SQLTables = new ArrayList<String>();
		SQLTables.add("POSTLEITZAHLEN");

		this.setModal(true);
		this.setLocationRelativeTo(owner);

		// Filter und Suche
		miFilterLoeschen.setEnabled(false);

		showDataThread(SQLColumns, SQLTables, null, "PLZ, ORT");
	}

	private void showDataThread(ArrayList<String> SQLColumns, ArrayList<String> SQLTables, String Where, String OrderBy)
	{
		statusBar.setText("Datensätze werden gelesen ...");
		// Thread zum Einlesen der Postleitzahlen verwenden.
		// Die Form wird sofort sichtbar und das Lesen der
		// Datensätze erfolgt im Hintergrund.

		Thread t = new Thread(new ShowData(SQLColumns, SQLTables, Where, OrderBy));
		t.start();

	}

	private class ShowData implements Runnable
	{

		ArrayList<String> SQLColumns;
		ArrayList<String> SQLTables;
		String Where;
		String OrderBy;

		public ShowData(ArrayList<String> SQLColumns, ArrayList<String> SQLTables, String Where, String OrderBy)
		{
			this.SQLColumns = SQLColumns;
			this.SQLTables = SQLTables;
			this.Where = Where;
			this.OrderBy = OrderBy;
		}

		@Override
		public void run()
		{

			Tabelle.setModel(new PLZTableModel(Tabelle, SQLColumns, SQLTables, Where, OrderBy));

			// Überschriften der Tabelle grösser und fett
			Font font = Tabelle.getTableHeader().getFont().deriveFont(Font.BOLD, 14.0f);
			Tabelle.getTableHeader().setFont(font);

			// Erste Spalte (PRIMARYKEY) unsichtbar machen
			setTableColumnInvisible(Tabelle, 0);

			// Breite für zweite Spalte (PLZ) setzen
			setTableColumnWidth(Tabelle, 1, 100);

			// Weitere Zellenformatierungen
			Tabelle.setRowHeight(21);
			Tabelle.setIntercellSpacing(new Dimension(5, 2));
			Tabelle.setDefaultRenderer(Object.class, new PLZTableCellRenderer());

			// Alle Spaltenüberschriften linksbündig ausrichten
			DefaultTableCellRenderer tableHeaderRenderer = (DefaultTableCellRenderer) Tabelle.getTableHeader().getDefaultRenderer();
			tableHeaderRenderer.setHorizontalAlignment(SwingConstants.LEFT);
			Tabelle.getTableHeader().setDefaultRenderer(tableHeaderRenderer);

			if (Tabelle.getRowCount() > 0)
			{
				setSelectedRow(0);
				tfSuche.setVisible(true);
				Tabelle.requestFocusInWindow();
			}
			else
			{
				statusBar.setText(null);
				tfSuche.setVisible(false);
			}

		}
	}

	private class PLZTableModel extends AbstractTableModel
	{

		private JTable owner;
		private int anzahlZeilen;
		private int anzahlSpalten;
		private ArrayList<String> ColumnNames;
		private Object[][] data;
		private ArrayList<String> SQLColumns;
		private ArrayList<String> SQLTables;
		private String Where;
		private String OrderBy;

		private String SQL;

		public PLZTableModel(JTable owner, ArrayList<String> SQLColumns, ArrayList<String> SQLTables, String Where, String OrderBy)
		{

			this.owner = owner;
			this.SQLColumns = SQLColumns;
			this.SQLTables = SQLTables;
			this.Where = Where;
			this.OrderBy = OrderBy;

			SQL = "SELECT ";
			for (String s : SQLColumns)
				SQL += s + ", ";

			// Letztes Komma mit Leerstelle entfernen
			SQL = SQL.substring(0, SQL.length() - 2);

			SQL += " FROM ";

			for (String s : SQLTables)
				SQL += s + ", ";

			// Letztes Komma mit Leerstelle entfernen
			SQL = SQL.substring(0, SQL.length() - 2);

			// Filterbedingung festlegen, wenn vorhanden
			if (Where != null && Where.length() > 0)
				this.Where = Where;
			else
				this.Where = "1 = 1";

			SQL += " WHERE " + this.Where;

			// Sortierung festlegen, wenn vorhanden
			if (OrderBy != null && OrderBy.length() > 0)
				SQL += " ORDER BY " + OrderBy;

			// Allgemeine Lösung zur Ermittlung der Anzahl der Zeilen
			anzahlZeilen = getRowCountBySQL();

			// Ausführen der SQL-Anweisung zum Lesen aller Datensätzen
			ResultSet rSet = DBConnection.executeQuery(SQL);

			// Lesen der Metadaten: Anzahl, Datentypen und Eigenschaften der
			// Spalten
			// aus der SQL Anweisung
			ResultSetMetaData rsMetaData = getMetaData(rSet);

			// Anzahl der Spalten aus ResultSet ermitteln
			anzahlSpalten = getColumnCount(rsMetaData);

			// Anzahl der Zeilen anhand des ResultSets ermitteln kann nur mit
			// MySQL
			// verwendet werden.
			// anzahlZeilen = getRowCount(rSet);

			// Überschriften der Spalten aus den Metadaten erstellen
			setHeader(rsMetaData);

			// Liest alle Datensätze aus dem Resultset in das zweidimensionale
			// Objekt-Array 'data'
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
				JOptionPane.showMessageDialog(null, "getMetaData: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
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
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "getRowCount: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			}

			return retValue;
		}

		private int getRowCountBySQL()
		{
			int retValue = 0;

			String SQL = "SELECT COUNT(*) FROM ";

			for (String s : SQLTables)
				SQL += s + ", ";

			// Letztes Komma mit Leerstelle entfernen
			SQL = SQL.substring(0, SQL.length() - 2);

			// Filterbedingung setzen. Standard WHERE 1 = 1
			SQL += " WHERE " + this.Where;

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
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "getColumnCount: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			}

			return retValue;

		}

		private void setHeader(ResultSetMetaData rsMD)
		{
			ColumnNames = new ArrayList<String>();
			for (int i = 1; i <= anzahlSpalten; i++)
				ColumnNames.add(getColumnLabel(rsMD, i));
		}

		// Den Spaltennamen der SELECT-Anweisung zurückliefern
		private String getColumnName(ResultSetMetaData rsMD, int colIndex)
		{
			String colName = "";

			try
			{
				colName = rsMD.getColumnName(colIndex);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "getColumnName: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			}

			return colName;
		}

		private String getColumnLabel(ResultSetMetaData rsMD, int colIndex)
		{
			String colName = "";

			try
			{
				colName = rsMD.getColumnLabel(colIndex);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "getColumnLabel: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
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
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "getData: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}

		// Alle Spalten der Zeilen durchsuchen, beginnend mit wowIndex.
		public int findEntry(Object value, int rowIndex)
		{
			return findEntry(null, value, rowIndex);
		}

		// Alle Zeilen durchsuchen, beginnend ab rowIndex.
		public int findEntry(String colName, Object value, int rowIndex)
		{
			int retValue = -1;
			String searchValue;
			String leftPattern = "";
			String rightPattern = "";
			int colIndex = -1;

			// Wenn ein Spaltenname angegeben wurde, muss er auch existieren.
			// Ansonsten werden alle Spalten durchsucht.
			if (colName != null)
			{
				colIndex = getColumnIndex(colName);
				if (colIndex == -1)
					return retValue;
			}

			// Das Muster mit dem regulären Ausdruck erstellen::
			// Findet alle Suchbegriff (auch teilweise mit *) innerhalb einer
			// Vergleichszeichenkette
			// unabhängig von Gross- oder Kleinschreibung (?i)

			// Beispiele:
			// Eingabe Regex Beschreibung
			// ------------------------ ----------------
			// ---------------------------------
			// Suchbegriff* (?i)^Suchbegriff Beginnt mit Suchbegriff
			// *Suchbegriff (?i)Suchbegriff$ Endet mit Suchbegriff
			// *Suchbegriff* (?i)Suchbegriff Der Suchbegriff kann überall
			// vorkommen
			// Suchbegriff (?i)^Suchbegriff$ Exakter Vergleich

			// Übernahme des Suchbegriffs ohne Wildcards
			searchValue = value.toString().replace("*", "");

			if (value.toString().contains("*"))
			{
				if (value.toString().startsWith("*") && !value.toString().endsWith("*"))
				{
					// Endet mit Suchbegriff$
					rightPattern = "$";
				}
				else if (value.toString().endsWith("*") && !value.toString().startsWith("*"))
				{
					// Beginnt mit ^Suchbegriff
					leftPattern = "^";
				}

				// Suchbegriff beginnt mit * und endet mit *
				// leftPattern und rightPattern bleibt leer.
				// Der Suchbegriff kann irgendwo innerhalb des Textes vorkommen.
			}
			else
			{
				// Exakter Vergleich
				// ^Suchbegriff$
				leftPattern = "^";
				rightPattern = "$";
			}

			Pattern p = Pattern.compile("(?i)" + leftPattern + searchValue + rightPattern);

			for (int zeile = rowIndex; zeile < getRowCount(); zeile++)
			{

				// Einen Matcher mit dem Vergleichszeichenkette erstellen

				// Wenn 'colIndex' > -1 ist, wird nur die Spalte mit dem
				// angegebenen
				// Index geprüft.
				if (colIndex > -1)
				{
					Matcher m = p.matcher(data[zeile][colIndex].toString());
					if (m.find())
					{
						retValue = zeile;
						break;
					}
				}
				else
				{
					// Alle Spalten der Zeile werden geprüft.
					for (int column = 0; column < getColumnCount(); column++)
					{
						// Spalten mit einer Breite von 0 (unsichtbar) werden
						// nicht berücksichtigt
						if (owner.getTableHeader().getColumnModel().getColumn(column).getWidth() == 0)
							continue;
						Matcher m = p.matcher(data[zeile][column].toString());
						if (m.find())
						{
							retValue = zeile;
							break;
						}
					}

					if (retValue > -1)
						break;
				}

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

				//
				xml.println("<?xml version=\"1.0\"  encoding=\"UTF-8\"?>");

				for (int i = 0; i < getRowCount(); i++)
				{
					xml.println("<PLZTable>");
					for (int j = 0; j < getColumnCount(); j++)
						xml.println("     <" + getColumnName(j) + ">" + getValueAt(i, j) + "</" + getColumnName(j) + ">");
					xml.println("</PLZTable>");
				}

				xml.close();
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "convertToXML: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
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
			this.fireTableCellUpdated(rowIndex, colIndex);
		}

	}

	// Damit die Darstellung der JTable angepasst werden kann, muss ein
	// TableCellRenderer
	// eingesetzt werden.
	// Die Klasse DefaultTableCellRenderer implementiert die Schnittstelle
	// TableCellRenderer
	// die nur die Methode getTableCellRendererComponent(..) vorschreibt.
	// Diese Methode muss überschrieben werden, um die gewünschten Anpassungen
	// vorzunehmen.
	private class PLZTableCellRenderer extends DefaultTableCellRenderer
	{
		private Color defaultColor = Color.WHITE;
		private Color alternateColor = new Color(217, 230, 253);
		private Color selectedColor = new Color(67, 134, 224);
		private Color backColor, foreColor;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column)
		{
			// Aufruf der Methode der Basisklasse
			// Hinter jeder Spalte in der Tabelle verbirgt sich ein Label.
			JLabel itemLabel = (JLabel) super.getTableCellRendererComponent(table, value, selected, focused, row, column);

			// Alternierende Hintergrund-Farbe für jede 2.Zeile setzen bzw.
			// Selektionsfarbe
			if (!selected)
				backColor = (row % 2 == 0 ? alternateColor : defaultColor);
			else
				backColor = selectedColor;

			itemLabel.setBackground(backColor);

			// Schriftfarbe ändern, wenn selektiert
			if (selected)
				foreColor = Color.white;
			else
				foreColor = Color.black;

			itemLabel.setForeground(foreColor);

			return this;
		}
	}

	public void setTableColumnInvisible(JTable jTable, int col)
	{
		jTable.getColumnModel().getColumn(col).setWidth(0);
		jTable.getColumnModel().getColumn(col).setMaxWidth(0);
		jTable.getColumnModel().getColumn(col).setMinWidth(0);
		jTable.getColumnModel().getColumn(col).setPreferredWidth(0);
		jTable.getColumnModel().getColumn(col).setResizable(false);

	}

	private void setTableColumnWidth(JTable jTable, int col, int width)
	{
		jTable.getColumnModel().getColumn(col).setWidth(width);
		// setMaxWidth() ist erforderlich um die Spaltenbreite zu setzen,
		// durch den Multiplikator wird aber die Möglichkeit gegeben
		// die Spalte nachträglich um das n-fache zu vergrössern.
		jTable.getColumnModel().getColumn(col).setMaxWidth(width * 3);
		// Ohne Angabe von MinWidht() kann die Spalte vollständig minimiert
		// werden.
		// jTable.getColumnModel().getColumn(col).setMinWidth(width);
		jTable.getColumnModel().getColumn(col).setPreferredWidth(width);
		// jTable.getTableHeader().resizeAndRepaint();

	}

	private void setSelectedRow(int rowIndex)
	{
		Tabelle.changeSelection(rowIndex, 0, false, true);
	}

	private void filter(PLZTableFilter filter)
	{

		// Wenn sich das Filterkriterium geändert hat zuerst neu lesen und damit
		// vorherigen Filter
		// entfernen.
		if (currFilter != PLZTableFilter.KEIN_FILTER & currFilter != filter)
		{
			showDataThread(SQLColumns, SQLTables, null, "PLZ, ORT");
			// ShowData showData = new ShowData(SQLColumns, SQLTables, null,
			// "PLZ, ORT");
			// showData.run();
		}

		currFilter = filter;

		switch (filter)
		{
		case PLZ:
			lblFilter.setText("Postleitzahl");
			break;

		case ORT:
			lblFilter.setText("Wohnort");
			break;

		}

		tfFilter.setText(null);
		btnWeitersuchen.setVisible(false);

		filterToolbar.setVisible(true);
		menuFilter.setIcon(null);
		menuFilter.setToolTipText(null);
		miFilterLoeschen.setEnabled(true);
		// tfFilter.requestFocusInWindow();

	}

	private void filterPLZ(String text)
	{
		
		String Where = "";

		if (text.contains("*"))
			Where = "PLZ LIKE " + Globals.quote(text.replace('*', '%'));
		else
			Where = "PLZ = " + Globals.quote(text);

		showDataThread(SQLColumns, SQLTables, Where, "PLZ, ORT");
		// ShowData showData = new ShowData(SQLColumns, SQLTables, Where,
		// "PLZ, ORT");
		// showData.run();

	}

	private void filterOrt(String text)
	{
		String Where = "";

		if (text.contains("*"))
			Where = "ORT LIKE " + Globals.quote(text.replace('*', '%'));
		else
			Where = "ORT = " + Globals.quote(text);

		showDataThread(SQLColumns, SQLTables, Where, "PLZ, ORT");
		// ShowData showData = new ShowData(SQLColumns, SQLTables, Where,
		// "PLZ, ORT");
		// showData.run();

	}

	private void removeFilter()
	{
		filterToolbar.setVisible(false);
		miFilterLoeschen.setEnabled(false);
		menuFilter.setIcon(null);
		menuFilter.setToolTipText(null);
		showDataThread(SQLColumns, SQLTables, null, "PLZ, ORT");
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

	private void detailFormat(long Key)
	{

		int selectedRow = Tabelle.getSelectedRow();

		PLZForm dlg = new PLZForm(Key);
		dlg.showDialog(this);

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

			ShowData showData = new ShowData(SQLColumns, SQLTables, null, "PLZ, ORT");
			showData.run();

			selectRowByValue("PRIMARYKEY", dlg.getPrimaryKey(), 0);
		}
	}


	// Inkrementelles Suchen
	private void selectRowByValue(String colName, Object value, int rowIndex)
	{

		do
		{
			rowIndex = ((PLZTableModel) Tabelle.getModel()).findEntry(colName, value, rowIndex);
			if (rowIndex == -1)
			{
				int retValue = showEndOfTableMessage();
				if (retValue != JOptionPane.YES_OPTION)
					break;
				else
					rowIndex = 0;
			}
			else
				break;
		}
		while (true);
		
		setSelectedRow(rowIndex);

	}

	private int showEndOfTableMessage()
	{
		String msg = "Das Ende der Tabelle ist erreicht.\nDie Suche am Anfang der Tabelle neu beginnen";
		// Benutzerdefinierte Button Texte
		Object[] options =
		{ "OK", "Abbrechen" };

		return JOptionPane.showOptionDialog(this, msg, "Frage", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

	}

	private void updateTableRow(int row)
	{

		String plz, ort;

		String SQL = "SELECT PLZ, ORT FROM POSTLEITZAHLEN";
		SQL += " WHERE PRIMARYKEY = " + Tabelle.getValueAt(row, 0).toString();

		ResultSet rSet = DBConnection.executeQuery(SQL);
		if (rSet == null)
			return;

		try
		{
			if (rSet.next())
			{
				plz = rSet.getString("PLZ");
				ort = rSet.getString("ORT");
				rSet.close();

				Tabelle.setValueAt(plz, row, 1);
				Tabelle.setValueAt(ort, row, 2);

			}

		}
		catch (Exception ex)
		{
		}

	}

	private void deleteEntry(long Key)
	{

		Object[] options =
		{ "Ja", "Nein" };

		String SQL = "DELETE FROM POSTLEITZAHLEN";
		SQL += " WHERE PRIMARYKEY = " + Long.toString(Key);

		int retValue = JOptionPane.showOptionDialog(this, "Datensatz löschen", "Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);

		// Nein - Nicht Löschen
		if (retValue != 0)
			return;

		// Aktuelle Zeile merken
		int selectedRow = Tabelle.getSelectedRow();

		DBConnection.executeNonQuery(SQL);

		ShowData showData = new ShowData(SQLColumns, SQLTables, null, "PLZ, ORT");
		showData.run();

		// Die die vorherige Zeile positionieren
		setSelectedRow(--selectedRow);

	}

	private void writeXML()
	{
		String Dateiname;

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("XML Datei erstellen");
		fc.setCurrentDirectory(fcFile);
		fc.setFileFilter(new FileNameExtensionFilter("XML Dokument", "xml"));
		fc.setAcceptAllFileFilterUsed(false);

		if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		// Datei merken
		fcFile = fc.getSelectedFile();

		Dateiname = fcFile.toString();
		if (!Dateiname.endsWith(".xml"))
			Dateiname += ".xml";

		((PLZTableModel) Tabelle.getModel()).convertToXML(Dateiname);
		JOptionPane.showMessageDialog(this, "XML Datei wurde erstellt", "XML Datei erstellen", JOptionPane.INFORMATION_MESSAGE, null);

	}

	private void printTable()
	{
		try
		{

			MessageFormat headerFormat = new MessageFormat("Postleitzahlen");
			MessageFormat footerFormat = new MessageFormat("- Seite {0} -");

			HashPrintRequestAttributeSet printAttr = new HashPrintRequestAttributeSet();
			
			// Seitenränder festlegen
			float leftMargin = 10;
			float rightMargin = 10;
			float topMargin = 20;
			float bottomMargin = 20;

			// Festlegen der  Seiten grösse DIN A4
			printAttr.add(MediaSizeName.ISO_A4);
			
			// Ermittlung der Seitengrösse in Millimeter
			MediaSize mediaSize = MediaSize.ISO.A4;
			float mediaWidth = mediaSize.getX(Size2DSyntax.MM);
			float mediaHeight = mediaSize.getY(Size2DSyntax.MM);

			// Festlegen des Druckbereichs abzüglich der Ränder
			// (der rechte und der untere Rand wird über die Parameter 3 und 4 errechnet).
			printAttr.add(new MediaPrintableArea(leftMargin, topMargin, (mediaWidth - leftMargin - rightMargin), (mediaHeight - topMargin - bottomMargin),
					Size2DSyntax.MM));
			
			// Seitenaususrichtung festlegen (Hochformat)
			printAttr.add(OrientationRequested.PORTRAIT);

			// Parameter 1: JTable.PrintMode.FIT_WIDTH verkleinert die JTable, wenn nötig, so daß alle Spalten auf eine Seite passen.
			// Parameter 2, 3: Header und Footer Text kann hinzugefügt werden (ansonsten einfach null angeben); 
			//                 im MessageFormat kann man als ArgumentIndex 0 für die Seitennummer angegeben: new MessageFormat("Seite {0}")
			// Parameter 4: Zeigt einen Drucker-Auswahl Dialog an wenn 'true'
			// Parameter 5: Ermöglicht die direkte Angabe von Druckattributen: Seitengrösse, Seitenausrichtung, usw.
			// Parameter 6: Es wird während des Druckvorgangs ein modaler Progressdialog angezeigt, mit Abbruchmöglichkeit, wenn 'true'.
			if (Tabelle.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat, true, printAttr, true))
			{
				JOptionPane.showMessageDialog(this, "Tabelle wurde gedruckt", "Drucken", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Drucke der Tabelle wurde abgebrochen", "Drucken", JOptionPane.INFORMATION_MESSAGE);
			}

		}
		catch (PrinterException ex)
		{
			JOptionPane.showMessageDialog(this, "Druck der Tabelle fehlgeschlagen: /n" + ex.getMessage(), "Fehler:", JOptionPane.ERROR_MESSAGE);
		}
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

		int selectedRow = -1;

		if (e.getSource() == Tabelle)
		{
			selectedRow = Tabelle.getSelectedRow();
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				e.consume();
				if (selectedRow != -1)
					detailFormat(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
			}
			else if (e.getKeyCode() == KeyEvent.VK_HOME)
				setSelectedRow(0);
			else if (e.getKeyCode() == KeyEvent.VK_END)
				setSelectedRow(Tabelle.getRowCount() - 1);
			else if (e.getKeyCode() == KeyEvent.VK_DELETE)
			{
				if (selectedRow != -1)
					deleteEntry(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
			}
		}
		else if (e.getSource() == tfFilter && e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if (currFilter == PLZTableFilter.PLZ)
				filterPLZ(tfFilter.getText());
			else if (currFilter == PLZTableFilter.ORT)
				filterOrt(tfFilter.getText());
		}
		else if (e.getSource() == tfSuche && e.getKeyCode() == KeyEvent.VK_ENTER)
			selectRowByValue(null, tfSuche.getText(), Tabelle.getSelectedRow() + 1);

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

		// Steuertasten ignorieren
		if (Character.isISOControl(e.getKeyChar()))
			return;

		if (e.getSource() == tfFilter)
		{
			if (currFilter == PLZTableFilter.PLZ)
			{
				if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '*')
				{
					Toolkit.getDefaultToolkit().beep();
					e.consume();
					return;
				}
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
			statusBar.setText(String.format("Datensatz %s von %s", NumberFormat.getInstance().format(Tabelle.getSelectedRow() + 1), NumberFormat.getInstance()
				.format(Tabelle.getRowCount())));
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{

	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		this.dispose();
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

		if (e.getSource() == miXML)
			writeXML();
		else if (e.getSource() == miDrucken)
			printTable();
		else if (e.getSource() == miSchliessen)
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if (e.getSource() == miNeu)
			detailFormat(-1);
		else if (e.getSource() == miAendern)
		{
			if (selectedRow != -1)
				detailFormat(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
		}
		else if (e.getSource() == miLoeschen)
		{
			if (selectedRow != -1)
				deleteEntry(Long.parseLong(Tabelle.getValueAt(selectedRow, 0).toString()));
		}
		else if (e.getSource() == miFilterPLZ)
			filter(PLZTableFilter.PLZ);
		else if (e.getSource() == miFilterOrt)
			filter(PLZTableFilter.ORT);
		else if (e.getSource() == miFilterLoeschen)
			removeFilter();

	}

	@Override
	public void eventDispatched(AWTEvent event)
	{
		// Nur wenn es sich bei dem AWTEvent um ein KeyEvent handelt
		if (event instanceof KeyEvent)
		{
			KeyEvent e = (KeyEvent) event;
			if (e.getKeyCode() == KeyEvent.VK_F3 && e.getID() == KeyEvent.KEY_RELEASED)
				selectRowByValue(null, tfSuche.getText(), Tabelle.getSelectedRow() + 1);

		}

	}

}
