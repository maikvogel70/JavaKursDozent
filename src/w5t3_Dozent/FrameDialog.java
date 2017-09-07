package w5t3_Dozent;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameDialog extends JFrame implements ActionListener, ItemListener, WindowListener, DocumentListener
{

	private JTextArea textArea;
	private JScrollPane textAreaScrollPane;

	private JCheckBox checkLineWrap, checkWordWrap;

	private JButton btnBeenden, btnDialog, btnDatei, btnSuchenUndErsetzen;
	
	//private File fcFile; 
	
	private File fcFile = new File("C:\\");
	// private File fcFile = new File(System.getProperty("user.dir")); // Aktuelles Arbeitsverzeichnis
	// private File fcFile = new File(System.getProperty("user.home")); // Home Verzeichnis des Benutzers
	// private File fcFile = new File(System.getProperty("user.name")); // Name des angemeldetete Benutzers
	// private File fcFile = new File(System.getProperty("java.io.tmpdir")); // Pfad für temporäre Dateien

	
	private FrameDialogSuchenUndErsetzen dlgSuchenUndErsetzen;
	
	private boolean hasChanged;
	
	private int hashCode;
	
	public FrameDialog()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{

		Icon icon;
		
		
		this.setTitle("Hauptfenster des Programms");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/Editor.png")));
		
		this.setSize(760, 420);

		// Keine Grössenänderung des Frames
		this.setResizable(false);

		// Layout Manager ausschalten
		this.setLayout(null);

		// Das Schließen des Frames wird mit dem WindowListener überwacht.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);

		textArea = new JTextArea();

		textArea.getDocument().addDocumentListener(this);
		
		// Automatischer Zeilenumbruch
		// textArea.setLineWrap(true);

		// Automatischer Umbruch auf Wortgrenze
		// Wenn autmo. Zeilenumbruch gesetzt wurde.
		// textArea.setWrapStyleWord(true);

		// Freien Bereich zwischen Rahmen und Text definieren
		textArea.setMargin(new Insets(10, 10, 10, 10));

		textAreaScrollPane = new JScrollPane(textArea);
		textAreaScrollPane.setBounds(5, 5, 580, 360);
		this.add(textAreaScrollPane);

		checkLineWrap = new JCheckBox("Automatischer Zeilenumbruch");
		checkLineWrap.setFont(checkLineWrap.getFont().deriveFont(Font.PLAIN, 9F));
		checkLineWrap.setBounds(590, 5, 200, 20);
		checkLineWrap.addItemListener(this);
		this.add(checkLineWrap);

		checkWordWrap = new JCheckBox("Auf Wortgrenze");
		checkWordWrap.setFont(checkWordWrap.getFont().deriveFont(Font.PLAIN, 9F));
		checkWordWrap.setBounds(590, 25, 200, 20);
		checkWordWrap.addItemListener(this);
		this.add(checkWordWrap);

		btnDatei = new JButton("Datei öffnen...");
		btnDatei.setFont(btnDatei.getFont().deriveFont(Font.PLAIN, 11F));
		btnDatei.setBounds(595, 230, 150, 30);
		btnDatei.addActionListener(this);
		this.add(btnDatei);

		btnDialog = new JButton("Dialog anzeigen");
		btnDialog.setFont(btnDialog.getFont().deriveFont(Font.PLAIN, 11F));
		btnDialog.setBounds(595, 265, 150, 30);
		btnDialog.addActionListener(this);
		this.add(btnDialog);

		
		// Suchen und Ersetzen Button mit Image
		icon = new ImageIcon(this.getClass().getResource("/images/Search.png"));
		
		btnSuchenUndErsetzen = new JButton("Suchen/Ersetzen");
		btnSuchenUndErsetzen.setIcon(icon);
				
		btnSuchenUndErsetzen.setFont(btnSuchenUndErsetzen.getFont().deriveFont(Font.PLAIN, 11F));
		btnSuchenUndErsetzen.setBounds(595, 300, 150, 30);
		btnSuchenUndErsetzen.addActionListener(this);
		this.add(btnSuchenUndErsetzen);
		
		btnBeenden = new JButton("Beenden");
		btnBeenden.setFont(btnBeenden.getFont().deriveFont(Font.PLAIN, 11F));
		btnBeenden.setBounds(595, 335, 150, 30);
		btnBeenden.addActionListener(this);
		this.add(btnBeenden);

	}

	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);

		textArea.setText("Mit dem JTextArea-Steuerelement kann der Benutzer Text in einer Anwendung eingeben. "
				+ "Dieses Steuerelement bietet eine Funktionalität, die über das Standard-JTextField-Steuerelement von Java hinausgeht. "
				+ "Dazu gehören Mehrzeilenbearbeitung und Zeichenmaskierung für Kennwörter. "
				+ "Normalerweise wird ein JTextField-Steuerelement für die Anzeige oder Eingabe einer einzelnen Textzeile verwendet.");

		
		hasChanged = false;
		
		hashCode = textArea.getText().hashCode();
		
		
		// Autom. Zeilenschaltung auf Wortgrenze
		checkWordWrap.setSelected(true);
		
		
	}

	private void anzeigeDialog()
	{

		FrameDialogModal dlg = new FrameDialogModal();
		dlg.showDialog(this);

		// System.out.println("Ende von anzeigeDialog()");

	}

	private void dateiLesen()
	{

		JFileChooser fc = new JFileChooser();
		// fc.setFileFilter(new FileNameExtensionFilter("Textdokument", "txt", "bat", "csv"));

		fc.setFileFilter(new FileNameExtensionFilter("Textdokument", "txt"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("CSV-Datei", "csv"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Stapeldatei", "bat"));

		// Anfangsverzeich setzen
		fc.setCurrentDirectory(fcFile);

		fc.setDialogTitle("Textdokument öffnen");

		// Alle Dateien (*.*) als Dateifilter wird nicht angeboten.
		// Standard = true.
		fc.setAcceptAllFileFilterUsed(false);

		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		// Ausgewählte Dateinamen merken
		fcFile = fc.getSelectedFile();

		// dateiLesen(fc.getSelectedFile().toString());

		// dateiLesenStringBuilder(fc.getSelectedFile().toString());

		// dateiLesenPuffer(fc.getSelectedFile().toString());

		// dateiLesenPuffer1(fc.getSelectedFile().toString());

		//dateiLesenFileChannel(fc.getSelectedFile().toString());

		dateiLesenReadAllBytes(fc.getSelectedFile().toString());
		
		//dateiLesenTextAreaRead(fc.getSelectedFile().toString());
		
		
		hasChanged = false;
		
		hashCode = textArea.getText().hashCode();
		
		
	}

	private void dateiLesen(String Dateiname)
	{

		long start = 0, ende = 0;
		Scanner in = null;

		// Inhalt der TextArea löschen
		textArea.setText(null);

		try
		{

			in = new Scanner(new FileInputStream(Dateiname));

			start = System.currentTimeMillis();

			while (in.hasNextLine())
			{
				textArea.append(in.nextLine() + "\n");
			}

			ende = System.currentTimeMillis();

		}
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei: " + ex.getMessage(), "Lesefehler", JOptionPane.ERROR_MESSAGE);
		}

		if (in != null)
			in.close();

		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.", "Lesen zeilenweise mit Scanner",
				JOptionPane.INFORMATION_MESSAGE);

		textArea.requestFocusInWindow();

	}

	private void dateiLesenStringBuilder(String Dateiname)
	{

		StringBuilder sb = new StringBuilder();

		long start = 0, ende = 0;
		Scanner in = null;

		// Inhalt der TextArea löschen
		textArea.setText(null);

		try
		{

			in = new Scanner(new FileInputStream(Dateiname));

			start = System.currentTimeMillis();

			while (in.hasNextLine())
			{
				sb.append(in.nextLine()).append("\n");
			}

			textArea.setText(sb.toString());

			ende = System.currentTimeMillis();

		}
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei: " + ex.getMessage(), "Lesefehler", JOptionPane.ERROR_MESSAGE);
		}

		if (in != null)
			in.close();

		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.",
				"Lesen zeilenweise mit StringBuilder", JOptionPane.INFORMATION_MESSAGE);

		textArea.requestFocusInWindow();

	}

	private void dateiLesenPuffer(String Dateiname)
	{
		StringBuilder sb = new StringBuilder();
		long start = 0, ende = 0;

		char[] chars = new char[8192];
		int length = 0;

		// Inhalt der TextArea löschen
		textArea.setText(null);

		try (BufferedReader br = new BufferedReader(new FileReader(Dateiname)))
		{
			start = System.currentTimeMillis();

			while (true)
			{

				length = br.read(chars, 0, chars.length);
				sb.append(new String(chars, 0, length));
				if (length < chars.length)
					break;
			}

			textArea.setText(sb.toString());
			ende = System.currentTimeMillis();

		}
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei: " + ex.getMessage(), "Lesefehler", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.", "Lesen mit Puffer",
				JOptionPane.INFORMATION_MESSAGE);

		textArea.requestFocusInWindow();

	}

	private void dateiLesenPuffer1(String Dateiname)
	{

		long start = 0, ende = 0;

		File file = new File(Dateiname);

		if (file.length() > Integer.MAX_VALUE)
		{
			// JOptionPane.showMessageDialog(this, "Die Datei ist zu groß um mit dieser Methode eingelesen werden zu können", "E/A Fehler",
			// JOptionPane.ERROR_MESSAGE);
			// return;

			// oder
			dateiLesenPuffer(Dateiname);
			return;

		}

		// Inhalt der TextArea löschen
		textArea.setText(null);

		try (FileReader in = new FileReader(Dateiname))
		{

			start = System.currentTimeMillis();

			char[] chars = new char[(int) file.length()];

			in.read(chars);

			textArea.setText(new String(chars));

			ende = System.currentTimeMillis();

		}
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei: " + ex.getMessage(), "Lesefehler", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.", "Lesen mit Puffer1",
				JOptionPane.INFORMATION_MESSAGE);

		textArea.requestFocusInWindow();

	}

	private void dateiLesenFileChannel(String Dateiname)
	{

		long start = 0, ende = 0;

		File file = new File(Dateiname);

		if (file.length() > Integer.MAX_VALUE)
		{
			dateiLesenPuffer(Dateiname);
			return;
		}

		// Inhalt der TextArea löschen
		textArea.setText(null);

		try (FileChannel in = FileChannel.open(file.toPath()))
		{
			start = System.currentTimeMillis();

			ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
			in.read(buffer);

			textArea.setText(new String(buffer.array()));

			ende = System.currentTimeMillis();

		}
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei: " + ex.getMessage(), "Lesefehler", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.", "Lesen mit FileChannel",
				JOptionPane.INFORMATION_MESSAGE);

		textArea.requestFocusInWindow();

	}

	private void dateiLesenReadAllBytes(String Dateiname)
	{

		long start = 0, ende = 0;

		// Inhalt der TextArea löschen
		textArea.setText(null);

		
		try
		{
			start = System.currentTimeMillis();
			
			byte[] byteArray = Files.readAllBytes(new File(Dateiname).toPath());
			textArea.setText(new String(byteArray));

			ende = System.currentTimeMillis();
			
		}
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei: " + ex.getMessage(), "Lesefehler", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.", "Lesen mit ReadAllBytes",
				JOptionPane.INFORMATION_MESSAGE);

		textArea.requestFocusInWindow();
		
	}

	private void dateiLesenTextAreaRead(String Dateiname)
	{
		long start = 0, ende = 0;
		
		// Inhalt der TextArea löschen
		textArea.setText(null);
				
		try (FileReader fr = new FileReader(Dateiname))
		{
			start = System.currentTimeMillis();
			
			textArea.read(fr, null);
			
			ende = System.currentTimeMillis();
			
		}
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(this, "Fehler beim Einlesen der Datei: " + ex.getMessage(), "Lesefehler", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.", "Lesen mit TextAreaRead",
				JOptionPane.INFORMATION_MESSAGE);

		textArea.requestFocusInWindow();
		
	}
	
	// Gibt 'true' zurück, wenn das Programm beendet werden soll
	private boolean queryExit()
	{
		boolean retValue = true;
		
//		// Benutzerdefinierte Buttontexte
//		String[] options = {"Ja", "Nein"};
//		
//		if (JOptionPane.showOptionDialog(this, "Soll das Programm wirklich beendet werden", "Programm beenden", 
//				                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == JOptionPane.YES_OPTION)
//		{
//			
//			retValue = true;
//		}
//		
		
		
//		if (hasChanged)
//		{
//			
//			retValue = saveFile();
//			
//		}
//		
		
		if (hashCode != textArea.getText().hashCode())
		{
			retValue = saveFile();
		}
		
		

		return retValue;
		
	}
	
	
	private boolean saveFile()
	{
		
		boolean retValue = false;
		
		int optionValue;
		
		// Benutzerdefinierte Button Texte
		String[] options = { "Ja", "Nein", "Abbrechen" };
		
		optionValue = JOptionPane.showOptionDialog(this, "Daten wurden geändert.\nSollen die Daten vorher gespeichert werden", "Achtung",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		
		if (optionValue == JOptionPane.NO_OPTION)
			// Nein - nicht speichern, Programm beenden
			retValue = true;
		else if (optionValue == JOptionPane.YES_OPTION)
		{
			retValue = saveFile(fcFile);
		}
		
		return retValue;
		
		
	}
	
	private boolean saveFileAs()
	{
		
		boolean retValue = false;
		int optionValue;
		
		String Dateiname;
		
		// Benutzerdefinierte Button Texte
		String[] options = { "Ja", "Nein" };
				
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Textdokument (*.txt)", "txt"));
		
		
		if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return retValue;
			
			
		
		Dateiname = fc.getSelectedFile().toString();
		
		// Dateierweiterung ermitteln.
		// Nicht jede Plattform benutzt Dateierweiterungen wie Windows.
		// Deshalb gibt es auch keine Methode um die Dateierweiterung zu
		// ermitteln.
		if (Dateiname.lastIndexOf('.') < 0)
			Dateiname += ".txt";
				
		// Überprüfen, ob die Datei bereits vorhanden ist.
		File f = new File(Dateiname);
		
		if(f.exists() && f.isFile())
		{
			optionValue = JOptionPane.showOptionDialog(this, "Die Datei '" + Dateiname + "' ist bereits vorhanden.\nÜberschreiben", "Datei speichern",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			
			if (optionValue == JOptionPane.YES_OPTION)
				retValue = saveFile(f);
			
		}
		else
			retValue = saveFile(f);
		
		
		return retValue;
		
	}
	
	
	private boolean saveFile(File fcFile)
	{
		
		boolean retValue = false;
		
		
		if (fcFile == null || fcFile.isDirectory())
			return saveFileAs();
		
		
		try (FileWriter fw = new FileWriter(fcFile.toString()))
		{
			
			textArea.write(fw);
			
			retValue = true;
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Fehler beim Speichern der Datei", "E/A Fehler", JOptionPane.ERROR_MESSAGE);
			
		}
		
		return retValue;
	}
	
	
	
	
	private void anzeigeSuchenUndErsetzen()
	{
		
//		if (dlgSuchenUndErsetzen == null)
//			dlgSuchenUndErsetzen = new FrameDialogSuchenUndErsetzen(textArea);
//		
//		dlgSuchenUndErsetzen.showDialog(this);
	
		// oder (Übergabe des eigenen WindowListeners and den Dialog)
//		dlgSuchenUndErsetzen =  new FrameDialogSuchenUndErsetzen(textArea);
//		dlgSuchenUndErsetzen.addWindowListener(this);
//		// Deaktivieren des Buttons für den Aufruf des Dialogs
//		btnSuchenUndErsetzen.setEnabled(false);
//		dlgSuchenUndErsetzen.showDialog(this);
		
		// oder mit einer Singleton Klasse 
		// Nur eine Instanz dieser Klasse kann erstellt werden.
		dlgSuchenUndErsetzen =  FrameDialogSuchenUndErsetzen.getInstance(textArea);
		dlgSuchenUndErsetzen.addWindowListener(this);
		dlgSuchenUndErsetzen.showDialog(this);
		
		
		
		
	}
	
	
	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		FrameDialog f = new FrameDialog();
		f.showFrame();

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource() == btnBeenden)
		{
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (e.getSource() == btnDialog)
		{
			anzeigeDialog();
		}
		else if (e.getSource() == btnDatei)
		{
			dateiLesen();
		}
		else if (e.getSource() == btnSuchenUndErsetzen)
		{
			anzeigeSuchenUndErsetzen();
		}


	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{

		if (e.getSource() == checkLineWrap)
		{

			textArea.setLineWrap(checkLineWrap.isSelected());

			if (!checkLineWrap.isSelected())
			{
				checkWordWrap.setSelected(false);
			}
		}
		else if (e.getSource() == checkWordWrap)
		{
			textArea.setWrapStyleWord(checkWordWrap.isSelected());

			if (checkWordWrap.isSelected())
			{
				checkLineWrap.setSelected(true);
			}
		}
		
	}

	// WindowListener
	
	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
//		if (dlgSuchenUndErsetzen != null)
//			dlgSuchenUndErsetzen.dispose();
		
		// oder (wenn der eigene WindowListener auch vom SuchenUndErsetzenDialog verwendet wird) 
		if (e.getSource() == dlgSuchenUndErsetzen)
		{
			btnSuchenUndErsetzen.setEnabled(true);
			dlgSuchenUndErsetzen = null;
		}
		else if (e.getSource() == this)
		{
			if (dlgSuchenUndErsetzen != null)
				dlgSuchenUndErsetzen.dispose();
		}
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		
		if (queryExit())
			// Ruft windowClosed()
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
	public void changedUpdate(DocumentEvent arg0)
	{
		// Nur wenn Attribute der Komponente verändert wurden, nicht der Inhalt.
		
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		hasChanged = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		hasChanged = true;
		
	}

}
