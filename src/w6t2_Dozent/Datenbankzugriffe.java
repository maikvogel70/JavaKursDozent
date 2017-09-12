package w6t2_Dozent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.DBConnection;
import dao.PostleitzahlenDAO;
import util.StatusBar;
import util.SwingUtil;

//Der Name MySQL setzt sich zusammen aus dem Vornamen My, den die Tochter des MySQL AB Mitbegründers Michael Widenius trägt, und SQL.
//Da Oracle die Markenrechte an MySQL hält, musste ein neuer Namen für das Datenbanksystem gefunden werden. 
//Der Name MariaDB geht auf Widenius’ jüngere Tochter zurück, seine andere Tochter My war bereits die Namensgeberin für MySQL.

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
//2. 	Innerhalb des Java Projekts ein Verzeichnis 'lib' anlegen und
//		den Connector in dieses Verzeichnis kopieren.
//
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
//7. 	Auswahl von phpAdmin in der obersten Zeile. 


public class Datenbankzugriffe extends JFrame implements WindowListener, ActionListener
{

	private JMenuBar menuBar;
	private JMenu    menuDatei, menuStammdaten, menuExtras;
	private JMenuItem miBeenden, miPostleitzahlen, miPostleitzahlenImportieren;
	
	private StatusBar statusBar;
	
	private File fcFile;
	
	public Datenbankzugriffe()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		
		this.setTitle("Datenbankzugriffe");
		this.setSize(800,  480);
		
		// Das Schließen des Programms wird vom WindowListener überwacht
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		// Das Standard-Layout ist das BorderLayout
		
		// Ein Menü erstellen
		menuBar = new JMenuBar();
				
		menuDatei = SwingUtil.createMenu(menuBar, "Datei", null, 'D');		
		miBeenden = SwingUtil.createMenuItem(menuDatei, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, 
				                             "Beenden", null, 'e', "Programm beenden");
		
		
		menuStammdaten = SwingUtil.createMenu(menuBar, "Stammdaten", null, 'S');
		miPostleitzahlen = SwingUtil.createMenuItem(menuStammdaten, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, 
                "Postleitzahlen", null, 'p', null);
		
		menuExtras = SwingUtil.createMenu(menuBar, "Extras", null, 'x');
		miPostleitzahlenImportieren = SwingUtil.createMenuItem(menuExtras, null, SwingUtil.MenuItemType.ITEM_PLAIN, this, 
                "Postleitzahlen importieren...", null, 'i', null);
		
		// Die Menübar zum Frame hinzufügen
		this.setJMenuBar(menuBar);
		
		
		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(0, 28));
		statusBar.setStatusLabelFont(statusBar.getStatusLabelFont().deriveFont(Font.PLAIN));
		this.add(statusBar, BorderLayout.PAGE_END);
		
		
		
	}
	
	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);
		
		openMariaDB();
		
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
		
		// Speichern der zuletzt ausgewählten Datei
		fcFile = fc.getSelectedFile();
		
		//readFile(fcFile.toString());

		readFileAsThread(fcFile.toString());
		

	}
	
	
	private void readFileAsThread(String Dateiname)
	{
		
		Thread t = new Thread(new ReadFileIntoDatabase(Dateiname));
		t.start();
	
	}
	
	
	private void readFile(String Dateiname)
	{
		
		String zeile = null;
		int readCounter = 0;
		int insertCounter = 0;
		String tempString;
		
		String[] split;
		long lngKey;
		
		
		// Inhalt der der Statusanzeige sichern
		tempString = statusBar.getText();
		
		
		lngKey = PostleitzahlenDAO.getNextKey();
		
		try (Scanner scanner = new Scanner(new FileInputStream(Dateiname)))
		{
		
			while(scanner.hasNextLine())
			{
				zeile = scanner.nextLine();
				readCounter++;
			
				statusBar.setText(String.format("Datensätze werden gelesen ...   [%s]", NumberFormat.getInstance().format(readCounter)));
				
				// Aktualisiert den angebenen Bereich
				// Muss angegeben werden, wenn der Inhalt eines Steuerlements in sehr kurzen Abständen vom eigenen Prozess geändert werden soll.
				// Funktioniert aber nicht mehr, wenn z.B. während der Laufzeit auch Grössenänderungen am Frame vorgenommen werden und sich
				// damit auch die Grösse des zu aktualisierenden Steuerlements ändert. 
				//statusBar.paintImmediately(statusBar.getVisibleRect());
				
				split = zeile.split(";", 2);
				
				if (split.length == 2)
				{
					
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

		
		JOptionPane.showMessageDialog(this, String.format("Es wurden %s Datensätze erfolgreich eingelesen", NumberFormat.getInstance().format(insertCounter)));
				
	
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
			readFile(Dateiname);
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
		this.dispose();
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
		
		
	}

}
