package w6t5_Dozent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

import dao.DBConnection;
import dao.PostleitzahlenDAO;
import util.StatusBar;
import util.SwingUtil;

//Der Name MySQL setzt sich zusammen aus dem Vornamen My, den die Tochter des MySQL AB Mitbegr�nders Michael Widenius tr�gt, und SQL.
//Da Oracle die Markenrechte an MySQL h�lt, musste ein neuer Namen f�r das Datenbanksystem gefunden werden. 
//Der Name MariaDB geht auf Widenius� j�ngere Tochter zur�ck, seine andere Tochter My war bereits die Namensgeberin f�r MySQL.

//Verwenden von MySQL/MariaDB als Datenbank:
//MariaDB ist eine Abspaltung von MySQL, dessen einstiges Kernteam von Oracle abgesprungen ist. 
//Tr�ger der Entwicklung ist inzwischen die finnische Firma SkySQL, w�hrend eine MariaDB Foundation 
//�ber den Erhalt des Open-Source-Erbes wacht. 
//Bis Version 5.5 lief die MariaDB-Versionierung parallel zu MySQL, um Kompatibilit�t zu unterstreichen. 
//Doch w�hrend Oracle MySQL 5.6 herausgebracht hat, macht MariaDB jetzt einen Sprung auf Version 10 - 
//um Unterschiede hervorzuheben.
//Die wichtigsten Verbesserungen von MariaDB 10 beziehen sich auf den Durchsatz und die NoSQL-F�higkeiten. 
//Laut Hersteller ist die Performance bis zu zehnfach besser. Nachdem der Code der Datenbank bei rund einer 
//Million Codezeilen "kernsaniert" und modernisiert wurde, lie�en sich Neuerungen einf�hren.
//Mit der Kernsanierung des Programm-Codes ist MariaDB in der neuen Version 10.0 nicht mehr 100-prozentig MySQL-kompatibel. 
//
//1. 	Herunterladen des mySQL-Datenbank-Treibers (Connector/J) von
//		http://www.mysql.de/downloads/connector/j/
//		Plattformunabh�ngiges ZIP-Archiv (mysql-connector-java-5.1.xx.zip)  
//		Nach bet�tigen des Download-Buttons keine Eingabe von Zugangsdaten
//		sondern Auswahl von [� No thanks, just take me to the downloads!]
//
//		Spezifischer MariaDB-Treiber:
//		https://downloads.mariadb.org/connector-java/
//
//2. 	Innerhalb des Java Projekts ein Verzeichnis 'lib' anlegen und
//		den Connector in dieses Verzeichnis kopieren.
//
//3. 	In Eclipse das Men� Project->Properties aufrufen. In den Properties 'Java Build Path'
//		ausw�hlen und dort die Registerkarte 'Libraries'. �ber den Button
//		[Add External JARs...] das Java-Archiv im Verzeichnis 'lib' hinzuf�gen.     
//		Alternativ:
//		Verzeichnis 'lib' im Package Explorer �ffnen, mit rechter Maustaste auf den Connector
//		klicken und  anschliessend 'Build Path' -> Add to Build Path' ausw�hlen. 

//Anlegen einer Datenbank/Tabelle mit XAMPP.
//1. 	Download vom XAMPP mit Installer -> http://www.apachefriends.org/de/xampp-windows.html
//2. 	Installation direkt in C:\XAMPP (sicherheitshalber wg. Zugriffsberechtigungen etc.)
//3. 	Starten des Programmes C:\xampp\xampp\xampp-control.exe.
//4. 	Starten des Apache-Servers.
//5. 	Starten der Datenbank. 
//6. 	Aufruf von XAMPP F�r Windows �ber den Browser (Firefox) mit localhost.
//7. 	Auswahl von phpAdmin in der obersten Zeile. 

//Verwenden von Microsoft SQL Server als Datenbank
//
//1. 	Herunterladen Microsoft JDBC Driver 4.0 f�r SQL Server von
//		http://www.microsoft.com/de-de/download/details.aspx?id=11774
//2. 	Ausf�hren der Exe-Datei.
//3. 	Das Java Archiv sqljdbc4.jar in das Verzeichnis 'lib' des
//		Workspaces kopieren.
//4. 	F�r die Anmeldung mit Windows Authentifizierung die Bibliothek 'sqljdbc_auth.dll'
//		aus dem Verzeichnis 'Microsoft JDBC Driver 4.0 for SQL Server\sqljdbc_4.0\deu\auth\x64'   
//		in das Verzeichnis 'Windows\System32 kopieren.


//Einstellungen der Collation Order (Sortierreihenfolge) der Datenbanken.

//Wichtig f�r die Unterscheidung von '�' und "ss":
//1. 	mySQL/MarioDB : latin1_general_ci
//2. 	Microsoft SQL Server: Latin1_General_CI_AS.
//		zur Unterscheidung von Orten die einmal mit '�' und einmal mit 'ss' geschrieben werden
//		29229 - Celle - Gar�en
//		52224 - Stolberg - S��endell
//		99444 - Blankenhain - Ke�lar

//Layout f�r die Tabelle Postleitzahlen

//	Feldname				Typ		
//  ------------------------------------------------------------------------------------------------- 
//	PRIMARYKEY			bigint								(Prim�rschl�ssel)
//	PLZ					varchar(10)	                        (Nicht eindeutiger Index)
//	ORT					varchar(100)                        (Eindeutiger Index PLZ, ORT )
//	TIMESTAMP         	timestamp





public class Datenbankzugriffe extends JFrame implements WindowListener, ActionListener
{

	private JMenuBar menuBar;
	private JMenu    menuDatei, menuStammdaten, menuExtras, menuLAF;
	private JMenuItem miBeenden, miPostleitzahlen, miPostleitzahlenImportieren;
	
	private StatusBar statusBar;
	private JProgressBar progressBar;
	
	private File fcFile;
	
	public Datenbankzugriffe()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		this.setTitle("Datenbankzugriffe");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(("/images/Server.png"))));
		this.setSize(800,  480);
		
		// Das Schlie�en des Programms wird vom WindowListener �berwacht
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		// Das Standard-Layout ist das BorderLayout
		
		// Ein Men� erstellen
		menuBar = new JMenuBar();
				
		menuDatei = SwingUtil.createMenu(menuBar, "Datei", null, 'D');		
		miBeenden = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, 
				                             "Beenden", null, 'e', "Programm beenden");
		
		
		menuStammdaten = SwingUtil.createMenu(menuBar, "Stammdaten", null, 'S');
		miPostleitzahlen = SwingUtil.createMenuItem(menuStammdaten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, 
                "Postleitzahlen", new ImageIcon(this.getClass().getResource("/images/Server.png")), 'p', null);
		
		menuExtras = SwingUtil.createMenu(menuBar, "Extras", null, 'x');
		miPostleitzahlenImportieren = SwingUtil.createMenuItem(menuExtras, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, 
                "Postleitzahlen importieren...", null, 'i', null);
		
		
		createLAFMenu(menuExtras);
		
		// Die Men�bar zum Frame hinzuf�gen
		this.setJMenuBar(menuBar);
		
		
		// Statusleiste
		statusBar = new StatusBar();
		// Symbol zur Statusbar hinzuf�gen
		statusBar.setIcon(new ImageIcon(this.getClass().getResource("/images/Server.png")));
		statusBar.setPreferredSize(new Dimension(0, 28));
		statusBar.setStatusLabelFont(statusBar.getStatusLabelFont().deriveFont(Font.PLAIN));
		this.add(statusBar, BorderLayout.PAGE_END);
		
		// Fortschrittsanzeige innerhalb der Statusleiste erzeugen
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressBar.setBorderPainted(true);
		progressBar.setPreferredSize(new Dimension(300, statusBar.getHeight()));
		// Farbe der Fortschrittsanzeige
		progressBar.setForeground(Color.GREEN);
		
		
		progressBar.setUI(new JProgressBarUI());
			
		// oder mit einer anonymen Klasse
//		progressBar.setUI(new BasicProgressBarUI()
//				{
//
//					// Schriftfarbe au�erhalb des Fortschrittbalkens
//					@Override
//					protected Color getSelectionBackground()
//					{
//						return Color.DARK_GRAY;
//					}
//
//					// Schriftfarbe innerhalb des Fortschrittbalkens
//					@Override
//					protected Color getSelectionForeground()
//					{
//						return Color.DARK_GRAY;
//					}
//				});
		
	
		
		// Prozentsatz anzeigen
		progressBar.setStringPainted(true);
		// Initial unsichtbar
		progressBar.setVisible(false);
		statusBar.add(progressBar, BorderLayout.LINE_END);
		
	}
	
	
	private void createLAFMenu(JMenu menu)
	{
		String currentLAFName;
		
		int i = Globals.getLookAndFeels();
		if (i == 0)
			return;
		
		Object[] array = Globals.getLAFTable().keySet().toArray();
		
		// Aufsteigende Sortierung
		Arrays.sort(array);
		
		menu.addSeparator();
		
		menuLAF = SwingUtil.createSubMenu(menu, "Look and Feel", null, 'L');
		
		// LookAndFeel-Men� mit RadioButtons erstellen
		
		// Den akuellen LookAndFeel-Name des Systems ermitteln.
		currentLAFName = Globals.getLookAndFeelName();
				
		ButtonGroup bg = new ButtonGroup();
		
		for (Object obj : array)
		{
			
			JMenuItem mi = SwingUtil.createMenuItem(menuLAF, null, SwingUtil.MenuItemType.ITEM_RADIO, this, obj.toString(), null, 0, null);
			bg.add(mi);
			
			// Den aktuellen LookAndFeel-Namen im Menu ausw�hlen
			if (obj.toString().equals(currentLAFName))
				mi.setSelected(true);
			
		}
		
		
	}
	
	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);
		
		//openMariaDB();
		
		openMicrosoftSQLDatabase();
	}
	
	
	private void openMariaDB()
	{
		
		String connectionString, classForName;
		String server = "localhost";
		String dataBase = "alfatraining";
		
		
		classForName = "org.mariadb.jdbc.Driver";
		
		connectionString = "jdbc:mariadb://" + server + ":3306/";
		connectionString += dataBase;
		
		dbEnabled(DBConnection.connectToDatabase(classForName, connectionString, "root", null));
		
		
	}
	

	private void openMicrosoftSQLDatabase()
	{
		String connectionString, classForName;
		String server = "localhost";
		String dataBase = "alfatraining";
		
		classForName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		
		connectionString = "jdbc:sqlserver://" + server + ";DatabaseName=" + dataBase + ";";
		
		dbEnabled(DBConnection.connectToDatabase(classForName, connectionString, "alfa-sql", "alfa"));
		
		
	}
	
	
	private void dbEnabled(boolean enabled)
	{
		
		menuStammdaten.setEnabled(enabled);
		menuExtras.setEnabled(enabled);
		
		if (!enabled)
			statusBar.setText("Datenbank: (keine)");
		else
			statusBar.setText("Datenbank: " + DBConnection.getCatalog());
		
	
	}
	
	private void openFileDialog()
	{
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(fcFile);
		fc.setFileFilter(new FileNameExtensionFilter("Textdokumente (*.txt)", "txt"));
		
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		// Speichern der zuletzt ausgew�hlten Datei
		fcFile = fc.getSelectedFile();
		
		//readFile(fcFile.toString());

		readFileAsThread(fcFile.toString());
		

	}
	
	
	private void readFileAsThread(String Dateiname)
	{
		
		Thread t = new Thread(new ReadFileIntoDatabase(Dateiname));
		t.start();
	
	}
	
	private int getRecordCount(String Dateiname)
	{
		
		int retValue = 0 ;
		
		try (Scanner scanner = new Scanner(new FileInputStream(Dateiname)))
		{
			while(scanner.hasNextLine())
			{
				scanner.nextLine();
				retValue++;
			}
		}
		catch (Exception ex)
		{
			
		}
		

		return retValue;
		
	}
	
	
	private void readFile(String Dateiname)
	{
		
		String zeile = null;
		int readCounter = 0;
		int insertCounter = 0;
		String tempString;
		
		String[] split;
		long lngKey, recordCount;
		
		int dlgValue;
		
		// Benutzerdefinierte Button Texte
		String[] options = { "Ja", "Nein" };
				
		recordCount = PostleitzahlenDAO.getRecordCount();
		
		if (recordCount > 0)
		{
			
			dlgValue = JOptionPane.showOptionDialog(this, "Sollen die Eintr�ge der vorhandenen Tabelle der Postleitzahlen vorher gel�scht werden", "Frage",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			
			if (dlgValue == JOptionPane.YES_OPTION)
			{
				PostleitzahlenDAO.deleteTable();
			}
				
		}
		
		
		// Inhalt der der Statusanzeige sichern
		tempString = statusBar.getText();
		
		
		lngKey = PostleitzahlenDAO.getNextKey();
		
		
		// Fortschrittsanzeige vorbereiten und sichtbar machen
		long fileLength = new File(Dateiname).length();
		
		if (fileLength > Integer.MAX_VALUE)
		{
			progressBar.setMaximum(getRecordCount(Dateiname));
			fileLength = -1;
			
		}
		else
		{
			progressBar.setMaximum((int)fileLength);
		}
		
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setVisible(true);
		
		
		
		try (Scanner scanner = new Scanner(new FileInputStream(Dateiname)))
		{
		
			while(scanner.hasNextLine())
			{
				zeile = scanner.nextLine();
				readCounter++;
			
				statusBar.setText(String.format("Datens�tze werden gelesen ...   [%s]", NumberFormat.getInstance().format(readCounter)));
				
				
				if (fileLength < 0)
				{
					progressBar.setValue(readCounter);
				}
				else
				{
					
					progressBar.setValue(progressBar.getValue() + zeile.length() + System.lineSeparator().length());
					
				}
				
				split = zeile.split(";", 2);
				
				if (split.length == 2)
				{
					
					if (PostleitzahlenDAO.istPLZOrtVorhanden(split[0], split[1]))
						continue;
					
					
					if (PostleitzahlenDAO.insertPLZOrt(lngKey, split[0], split[1]))
					{
						lngKey++;
						insertCounter++;
					}
					
				}

			}
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei " + Dateiname + ": " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		// Inhalt der Statusanzeige wiederherstellen
		statusBar.setText(tempString);

		// Fortschrittsanzeige wieder unsichtbar machen
		progressBar.setVisible(false);
		
		JOptionPane.showMessageDialog(this, String.format("Es wurden %s Datens�tze erfolgreich eingelesen", NumberFormat.getInstance().format(insertCounter)));
				
	
	}
	
	
	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		Datenbankzugriffe f = new Datenbankzugriffe();
		f.showFrame();
		

	}

	
	private void showPLZTable()
	{
		PLZTable dlg = new PLZTable();
		dlg.showDialog(this);
	}
	
	
	private class ReadFileIntoDatabase implements Runnable
	{

		private String Dateiname; 
		
		public ReadFileIntoDatabase(String Dateiname)
		{
			
			this.Dateiname = Dateiname;
			
		}
	
		
		@Override
		public void run()
		{
			
			// Verhindern dass w�hrend des Imports Benutzermen�-Funktionen
			// aufgerufen werden k�nnen.
			for (int i = 0; i < menuBar.getMenuCount(); i++)
			{
				menuBar.getMenu(i).setEnabled(false);
			}
			
			readFile(Dateiname);
			
			// Benutzermen� wieder aktivieren.
			for (int i = 0; i < menuBar.getMenuCount(); i++)
			{
				menuBar.getMenu(i).setEnabled(true);
			}
			
		}
		
			
	}
	
	private class JProgressBarUI extends BasicProgressBarUI
	{
		
		
		
		// Schriftfarbe au�erhalb des Fortschrittbalkens
		@Override
		protected Color getSelectionBackground()
		{
			return Color.DARK_GRAY;
		}

		// Schriftfarbe innerhalb des Fortschrittbalkens
		@Override
		protected Color getSelectionForeground()
		{
			return Color.DARK_GRAY;
		}
	
		
	}
	
	
	
	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		
		DBConnection.closeConnection();
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (menuDatei.isEnabled())
		{
			this.dispose();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Das Programm kann wegen aktiver Prozesse momentan nicht geschlossen werden", "Importieren Postleitzahlen",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
		
		if (e.getSource() == miBeenden)
		{
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (e.getSource() == miPostleitzahlenImportieren)
			openFileDialog();
		else if (e.getSource() == miPostleitzahlen)
			showPLZTable();
		// LookAndFeel
		else if (e.getActionCommand() != null)
		{
			Globals.setLookAndFeel(e.getActionCommand(), this);
		}
	}

}
