package w8t1_Dozent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.StatusBar;
import util.SwingUtil;

//Verwenden von MySQL/MariaDB als Datenbank:
//MariaDB ist eine Abspaltung von MySQL, dessen einstiges Kernteam von Oracle abgesprungen ist. 
//Träger der Entwicklung ist inzwischen die finnische Firma SkySQL, während eine MariaDB Foundation 
//über den Erhalt des Open-Source-Erbes wacht. 
//Bis Version 5.5 lief die MariaDB-Versionierung parallel zu MySQL, um Kompatibilität zu unterstreichen. 
//Doch während Oracle MySQL 5.6 herausgebracht hat, macht MariaDB jetzt einen Sprung auf Version 10 - 
//um Unterschiede hervorzuheben.
//Die wichtigsten Verbesserungen von MariaDB 10 beziehen sich auf den Durchsatz und die NoSQL-Fähigkeiten. 
//Laut Hersteller ist die Performance bis zu zehnfach besser. Nachdem der Code der Datenbank bei rund einer 
//Million Codezeilen "kernsaniert" und modernisiert wurde, ließen sich Neuerungen einführen.
//Mit der Kernsanierung des Programm-Codes ist MariaDB in der neuen Version 10.0 nicht mehr 100-prozentig MySQL-kompatibel. 
//
//1. 	Herunterladen des mySQL-Datenbank-Treibers (Connector/J) von
//		http://www.mysql.de/downloads/connector/j/
//		Plattformunabhängiges ZIP-Archiv (mysql-connector-java-5.1.xx.zip)  
//		Nach betätigen des Download-Buttons keine Eingabe von Zugangsdaten
//		sondern Auswahl von [» No thanks, just take me to the downloads!]
//
//		Spezifischer MariaDB-Treiber:
//		https://downloads.mariadb.org/connector-java/
//
//2. 	Innerhalb des Eclipse Workspaces ein Verzeichnis 'lib' anlegen und
//		das Java-Archiv mysql-connector-java-5.1.xx-bin.jar aus dem heruntergeladenen
//		Verzeichnis in dieses Verzeichnis kopieren.
//3. 	In Eclipse das Menü Project->Properties aufrufen. In den Properties 'Java Build Path'
//		auswählen und dort die Registerkarte 'Libraries'. Über den Button
//		[Add External JARs...] das Java-Archiv im Verzeichnis 'lib' hinzufügen.     
//		Alternativ:
//		Verzeichnis 'lib' im Package Explorer öffnen, mit rechter Maustaste auf den Connector
//		klicken und  anschliessend 'Build Path' -> Add to Build Path' auswählen. 

//Anlegen einer Datenbank/Tabelle mit XAMPP.
//1. 	Download vom XAMPP mit Installer -> http://www.apachefriends.org/de/xampp-windows.html
//2. 	Installation direkt in C:\XAMPP (sicherheitshalber wg. Zugriffsberechtigungen etc.)
//3. 	Starten des Programmes C:\xampp\xampp\xampp-control.exe.
//4. 	Starten des Apache-Servers.
//5. 	Starten der Datenbank. 
//6. 	Aufruf von XAMPP Für Windows über den Browser (Firefox) mit localhost.
//7. 	Auswahl von Tools/phpAdmin auf der linken Seite.  

//Verwenden von Microsoft SQL Server als Datenbank
//1. 	Herunterladen Microsoft JDBC Driver 4.0 für SQL Server von
//	http://www.microsoft.com/de-de/download/details.aspx?id=11774
//2. 	Ausführen der Exe-Datei.
//3. 	Das Java Archiv sqljdbc4.jar in das Verzeichnis 'lib' des
//	Workspaces kopieren.
//4. 	Für die Anmeldung mit Windows Authentifizierung die Bibliothek 'sqljdbc_auth.dll'
//	aus dem Verzeichnis 'Microsoft JDBC Driver 4.0 for SQL Server\sqljdbc_4.0\deu\auth\x64'   
//	in das Verzeichnis 'Windows\System32 kopieren.

//Einstellungen der Collation Order (Sortierreihenfolge) der Datenbanken.

//Wichtig für die Unterscheidung von 'ß' und "ss":
//1. 	mySQL : latin1_general_ci
//2. 	Microsoft SQL Server: Latin1_General_CI_AS.
//	Zur Unterscheidung von Orten die einmal mit 'ß' und einmal mit 'ss' geschrieben werden
//		29229 - Celle - Garßen
//		52224 - Stolberg - Süßendell
//		99444 - Blankenhain - Keßlar
//		Latin1_General_BIN (nur für das Feld 'ORT' in der Tabelle 'Postleitzahlen'.
//3. 	Microsoft Access: Allgemein (Standardeinstellung)


//Hinweis:
//Sollte der Apache-Server nicht gestartet werden können weil der Port 80 bereits von einem
//anderen Programm benutzt wird (kommt manchmal vor), folgende Änderungen vornehmen:
//Editieren der Datei C:\XAMPP\apache\conf\httpd.config:
//Dort gibt es einen Eintrag Listen 80.
//Dem Listener einen Port vergeben: z.B. Listen 8080.
//Danach muss allerdings der Aufruf von XAMPP für Windows (siehe oben Punkt 6.) mit
//localhost:8080 erfolgen.
//

//Sollte der Apache-Server nicht gestartet werden können weil der Port 443 bereits von einem
//anderen Programm benutzt wird (kommt manchmal vor), folgende Änderungen vornehmen:
//Editieren der Datei C:\XAMPP\apache\conf\extra\httpd-ssl.conf:
//Dort gibt es einen Eintrag Listen 443.
//Dem Listener einen neuen Port vergeben: z.B. Listen 444.

//Layout für die Tabelle Postleitzahlen

//Feldname			Typ			
//PRIMARYKEY		bigint								(Primärschlüssel)
//PLZ				varchar(10)	                        (Nicht eindeutiger Index)
//ORT				varchar(100)                        (Eindeutiger Index PLZ, ORT )
//TIMESTAMP         timestamp


public class DatenbankZugriffe extends JFrame implements ActionListener, WindowListener
{

	private JMenuBar	            menuBar;
	private JMenu	                menuDatei, menuStammdaten,  menuExtras, menuLAF;
	private JMenuItem            	miÖffnen, miSchliessen, miBeenden, miPostleitzahlen, miPostleitzahlenImportieren;
	
	private StatusBar	            statusBar;
	private JProgressBar		    progressBar;

	
	// Beginnt den Datei-Dialog mit dem Programmverzeichnis
	private File fcFile = new File(System.getProperty("user.dir"));
		
	public DatenbankZugriffe()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{
		
		this.setTitle("Datenbankzugriffe");
		this.setBounds(10, 10, 800, 480);
		
		// Das Schließen des Frames wird mit dem WindowListener überwacht. 
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);

		this.setLayout(new BorderLayout());
		
		menuBar = new JMenuBar();

		menuBar = new JMenuBar();

		menuDatei = SwingUtil.createMenu(menuBar, "Datei", null, 'D');
		miBeenden = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Beenden", null, 'B', null);

		menuStammdaten = SwingUtil.createMenu(menuBar, "Stammdaten", null, 'S');
		miPostleitzahlen = SwingUtil.createMenuItem(menuStammdaten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Postleitzahlen", null, 'P', null);

		menuExtras = SwingUtil.createMenu(menuBar, "Extras", null, 'x');
		miPostleitzahlenImportieren = SwingUtil.createMenuItem(menuExtras, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, "Postleitzahlen importieren...", null, 'I', null);

		createLAFMenu(menuExtras);
		
		this.setJMenuBar(menuBar);

		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(0, 25));
		statusBar.setStatusLabelFont(statusBar.getFont().deriveFont(Font.PLAIN));
		// Symbol zur Statusbar hinzufügen
		statusBar.setIcon(new ImageIcon(this.getClass().getResource("/images/Server.png")));
		this.add(statusBar, BorderLayout.PAGE_END);		

		// Da der Frame eine Grössenänderung zulässt wird die
		// Fortschrittsanzeige, abhängig von der aktuellen Breite
		// der Frame, auf der rechten Seite erzeugt.

		// Fortschrittsanzeige innerhalb der Statusleiste erzeugen
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressBar.setBorderPainted(true);
		progressBar.setPreferredSize(new Dimension(300, statusBar.getHeight()));
		// Farbe für die Fortschrittanzeige
		progressBar.setForeground(Color.green);
		// Prozentsatz anzeigen
		progressBar.setStringPainted(true);
		// Initial unsichtbar
		progressBar.setVisible(false);
		// Evtl. das BorderLayout der Statusbar vorher anpassen.
		statusBar.add(progressBar, BorderLayout.LINE_END);
		
	}
	
	private void initFrame()
	{			
		this.setLocationRelativeTo(null);
				
		//openMySQLDatabase();
		openMicrosoftSQLDatabase();
		//openDatabaseDialog();
	}
	

	private void openMySQLDatabase()
	{
		String connectionString;
		
		String server = "localhost";
		String dataBase = "alfatraining";	
		connectionString = "jdbc:mariadb://" + server + ":" + DBConnection.MYSQL_DEFAULTPORT + "/";
		connectionString +=  dataBase;
		dbEnabled(DBConnection.connectToDatabase(DBConnection.MYSQL_CLASSFORNAME, connectionString, "root", null));
				
		
	}
	
	private void openMicrosoftSQLDatabase()
	{
		String connectionString;
		//connectionString = "jdbc:sqlserver://ProSoft-C007;" +
		connectionString = "jdbc:sqlserver://localhost;" +
						   "DatabaseName=alfatraining;";
						   //integratedSecurity=true;";
		
		// Wird nur in Verbindung mit PreparedStatemenst benötigt.
		// Zeichenketten in PreparedStaments werden als Unicode Zeichenketten übergeben.
		// Dadurch sind die Buchstaben 'ß' und 'ss' gleichgestellt.
		// In der Tabelle der Postleitzahlen kommen aber 3 Orte vor, die einmal mit 'ß' und
		// einmal mit 'ss' geschrieben werden. Damit diese als unterschiedliche Einträge
		// eingetragen werden können wird folgende Anweisung benötigt.
		connectionString += "SendStringParametersAsUnicode=false;";
		// Beispielorte:
		//		29229 - Celle - Garßen
		//		52224 - Stolberg - Süßendell
		//		99444 - Blankenhain - Keßlar
		
	
		/*
		 * Um die Anmeldung mit Windows-Authentifizierung verwenden zu können
		 * (integratedSecurity=true) muss die DLL 'sqljdbc_auth.dll' aus dem Microsoft-
		 * Treiber Verzeichnis nach 'c:\Windows\system32' kopiert werden.
		 */
		
		dbEnabled(DBConnection.connectToDatabase(DBConnection.MSSQL_CLASSFORNAME, connectionString, "alfa-sql", "alfa"));
		
	}
	
	
	private void openDatabaseDialog()
	{	
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Datenbank öffnen");
		fc.setCurrentDirectory(fcFile);
		fc.setFileFilter(new  FileNameExtensionFilter("Microsoft Office Access-Datenbanken", "mdb", "accdb"));
		fc.setAcceptAllFileFilterUsed(false);
		
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
		
		// Extrahiere Pfad aus vollständigem Dateiname
	  fcFile = fc.getSelectedFile();
		
	  openMicrosoftAccessDatabase(fc.getSelectedFile().toString());
	  
	}
	
	private void openMicrosoftAccessDatabase(String msAccessDatabase)
	{
		String connectionString;
		connectionString = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + msAccessDatabase + ";";

		dbEnabled(DBConnection.connectToDatabase(DBConnection.MSACCESS_CLASSFORNAME, connectionString, null, null));
		
	}
	
	private void createLAFMenu(JMenu menu)
	{
		String currentLAFName;
		
		int i = Globals.getLookAndFeels();
		if (i == 0) return;
		
//		Set<String> keys = Globals.getLAFTable().keySet();
//		// Key ist der L&F Name
//		Object[] array = keys.toArray();
	
		menu.addSeparator();
		
		menuLAF = SwingUtil.createSubMenu(menu, "Look and Feel", null, 'L');
		
		currentLAFName = Globals.getSystemLookAndFeelName();	
		
		ButtonGroup bg = new ButtonGroup();
		// Key ist der L&F Name
		for (Object obj : Globals.getLAFTable().keySet().toArray())
		{
			JMenuItem mi = SwingUtil.createMenuItem(menuLAF, null, SwingUtil.MenuItemType.ITEM_RADIO, this, obj.toString(), null, 0, null);
			// Für die RadioButtons des L&F Menüs wird ein ActionListener und das ActionCommand verwendet und kein
			// ItemListener.
			// Dadurch wird, wie bei einem normalen MenuItem, nur durch die Auswahl des MenuItems das Ereignis ausgelöst
			// (nicht das Entfernen und das anschliessende Setzen des Wertes wie beim ItemListener).
			// Auch die programmatische Auswahl des MenuItems weiter unten löst kein Ereignis aus!
			mi.setActionCommand(obj.toString());
			bg.add(mi);
						
			if (obj.toString().equals(currentLAFName))
				mi.setSelected(true);
			
		}	
	}
	
	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}
	
	private void dbEnabled(boolean enabled)
	{
		
		menuStammdaten.setEnabled(enabled);
		menuExtras.setEnabled(enabled);
		
		if(!enabled)
		{
		   statusBar.setText("Datenbank: (keine)");
		   statusBar.setToolTipText(null);
		}
		else
		{
			statusBar.setText("Datenbank: " +  DBConnection.getCatalog());
			statusBar.setToolTipText(DBConnection.getDatabaseProvider().toString());
		}
	}	
	
	private void openFileDialog()
	{
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(fcFile);
		fc.setFileFilter(new  FileNameExtensionFilter("Texdateien (*.txt)", "txt"));
		fc.addChoosableFileFilter(new  FileNameExtensionFilter("CSV-Dateien (*.csv)", "csv"));
		
		
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
		
		// Zuletzt ausgewählte Datei merken
		fcFile = fc.getSelectedFile();
		
	  readFile(fcFile.toString());	
		
	}
	
	private void readFile(String dateiName)
	{
		
		// Benutzerdefinierte Button Texte
		Object[] options = { "Ja", "Nein"};
					
		int retValue = JOptionPane.showOptionDialog(this, "Sollen die Einträge der vorhandenen Tabelle der Postleitzahlen vorher gelöscht werden", "Frage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (retValue == 0) deletePLZEntries();
		
		// Thread zum Einlesen der Postleitzahlen verwenden.
		 // Nur so kann die Statusanzeige aktualisiert werden.
		 Thread t  = new Thread(new ReadFileIntoDatabase(dateiName));
		 t.start();
		
	}
	
	private void deletePLZEntries()
	{
		String SQL;
		
		//SQL = "DELETE FROM POSTLEITZAHLEN";
		SQL = "TRUNCATE TABLE POSTLEITZAHLEN";
		
		DBConnection.executeNonQuery(SQL);	
		
	}
	
	private class ReadFileIntoDatabase implements Runnable
	{
		String		   zeile		   = null;
		String[]		   split;
		int		      readCounter	= 0;
		int		      addCounter	= 0;
		long		      lngKey;
		String		   tempString;

		String		   Filename;
		boolean		   errFlag;

		public ReadFileIntoDatabase(String Filename)
		{
			this.Filename = Filename;
		}

		@Override
		public void run()
		{

			// Verhindern dass während des Imports Benutzermenü-Funktionen
			// aufgerufen werden können.
			 for (int i = 0; i < menuBar.getMenuCount(); i++)
			 menuBar.getMenu(i).setEnabled(false);

			tempString = statusBar.getText();

			 // Fortschrittsanzeige vorbereiten und sichtbar machen
			 progressBar.setMaximum((int)new File(Filename).length());
			 progressBar.setMinimum(0);
			 progressBar.setValue(0);
			 progressBar.setVisible(true);


			lngKey = Globals.getNextKey();

			DBConnection.beginnTransaction();

			try (Scanner scanner = new Scanner(new FileInputStream(Filename)))
			{
				while (scanner.hasNextLine())
				{

					zeile = scanner.nextLine().replaceAll("'", "''");
					readCounter++;
					
					progressBar.setValue(progressBar.getValue() + zeile.length() + System.lineSeparator().length());

					// Maximal zwei Elemente im SplitArray zurückliefern.
					split = zeile.split(";", 2);
					if (split.length == 2)
					{
						if (Globals.istPLZOrtVorhanden(split[0], split[1])) continue;

						if (insertPLZ(lngKey, split[0], split[1]))
						{
							lngKey++;
							addCounter++;
						}
						else
						{
							errFlag = true;
							break;
						}
					}

				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Datei " + Filename + ": " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
				errFlag = true;
			}

			// Benutzermenü wieder aktivieren
			for (int i = 0; i < menuBar.getMenuCount(); i++)
			  menuBar.getMenu(i).setEnabled(true);

			// Fortschrittsanzeige wieder unsichtbar machen
			progressBar.setVisible(false);

			if (errFlag)
			{
				DBConnection.rollbackTransaction();
				addCounter = 0;
			}
			else
				DBConnection.commitTransaction();


			statusBar.setText(tempString);

			JOptionPane.showMessageDialog(null, String.format(" Es wurden %s Datensätze erfolgreich eingelesen", NumberFormat.getInstance().format(addCounter)), "Postleitzahlentabelle", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	boolean insertPLZ(long PrimaryKey, String PLZ, String Ort)
	{
		
		if (Globals.istPLZOrtVorhanden(PLZ, Ort)) return false;
		
		String SQL = "INSERT INTO POSTLEITZAHLEN";
		SQL += " (PRIMARYKEY, PLZ, ORT)";
		SQL += " VALUES (";
		SQL += Long.toString(PrimaryKey) + ", ";
		SQL += Globals.quote(PLZ) + ", ";
		SQL += Globals.quote(Ort) + ")";
		
		return DBConnection.executeNonQuery(SQL) > 0;
		
	}
	
	int getRecordCount(String Filename)
	{
		int retValue = 0;
		
		try
		{
			Scanner scanner = new Scanner(new FileInputStream(Filename));
			while (scanner.hasNextLine())
			{
				scanner.nextLine();
				retValue++;
			}

			scanner.close();
			
		} catch (Exception ex) 	{}
		
		return retValue;
	}
	

	private void showPLZTable()
	{
		PLZTable dlg = new PLZTable();
		dlg.showDialog(this);
	}
	
	private boolean queryExit()
	{
		// Benutzerdefinierte Button Texte
		Object[] options = { "Ja", "Nein"};
		int retValue = JOptionPane.showOptionDialog(this, "Programm beenden", "Datenbankzugriffe", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		if (retValue == JOptionPane.YES_OPTION)
			return true;
		else
			return false;

	}
	
 
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		DatenbankZugriffe f = new DatenbankZugriffe();
		f.showFrame();
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		//writeProperties(Globals.getApplicationConfigPath() + "\\" +  this.getClass().getName()+ ".config");
		DBConnection.closeConnection();
		
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		if (queryExit()) this.dispose();
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
		
		
		if (e.getSource().equals(miBeenden))
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if (e.getSource().equals(miSchliessen))
		{
			DBConnection.closeConnection();
			dbEnabled(false);
		}
		else if(e.getSource().equals(miPostleitzahlenImportieren))
			openFileDialog();			
		else if(e.getSource().equals(miPostleitzahlen))
			showPLZTable();
		else 
		{
			// LookAndFeel RadioButtons verwenden den ActionListener und das
			// ActionCommand.
			Globals.setLookAndFeel(e.getActionCommand(), this);	
		}
	}

}
