package w4t5_Dozent;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameDialog extends JFrame implements ActionListener
{

	private JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	
	
	private JButton btnBeenden, btnDialog, btnDatei;
	
	private File fcFile = new File("C:\\");
	//private File fcFile = new File(System.getProperty("user.dir")); 		// Aktuelles Arbeitsverzeichnis
	//private File fcFile = new File(System.getProperty("user.home")); 		// Home Verzeichnis des Benutzers
	//private File fcFile = new File(System.getProperty("user.name")); 		// Name des angemeldetete Benutzers
	//private File fcFile = new File(System.getProperty("java.io.tmpdir"));	// Pfad für temporäre Dateien
	
	
	public FrameDialog()
	{
		initializeComponents();
	}
	
	
	private void initializeComponents()
	{
		
		this.setTitle("Hauptfenster des Programms");
		this.setSize(760,  420);
		
		// Keine Grössenänderung des Frames
		this.setResizable(false);
		
		// Layout Manager ausschalten
		this.setLayout(null);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		textArea = new JTextArea();
		
		// Automatischer Zeilenumbruch
		textArea.setLineWrap(true);
		
		
		// Automatischer Umbruch auf Wortgrenze
		// Wenn autmo. Zeilenumbruch gesetzt wurde.
		textArea.setWrapStyleWord(true);
		
		// Freien Bereich zwischen Rahmen und Text definieren
		textArea.setMargin(new Insets(10, 10, 10, 10));
		
		
		textAreaScrollPane = new JScrollPane(textArea);
		textAreaScrollPane.setBounds(5, 5, 580, 360);
		this.add(textAreaScrollPane);
		
		btnDatei = new JButton("Datei öffnen...");
		btnDatei.setBounds(595,  265, 150, 30);
		btnDatei.addActionListener(this);
		this.add(btnDatei);
		
		btnDialog = new JButton("Dialog anzeigen");
		btnDialog.setBounds(595,  300, 150, 30);
		btnDialog.addActionListener(this);
		this.add(btnDialog);
		
		btnBeenden = new JButton("Beenden");
		btnBeenden.setBounds(595,  335, 150, 30);
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
		
		
		
		
	}
	
	
	private void anzeigeDialog()
	{
		
		FrameDialogModal dlg = new FrameDialogModal();
		dlg.showDialog(this);
		
		//System.out.println("Ende von anzeigeDialog()");
		
		
	}
	
	private void dateiLesen()
	{
	
				
		JFileChooser fc = new JFileChooser();
		//fc.setFileFilter(new FileNameExtensionFilter("Textdokument", "txt", "bat", "csv"));
		
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
		
		dateiLesen(fc.getSelectedFile().toString());
		
		
		
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
			
			while(in.hasNextLine())
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
		
		
		JOptionPane.showMessageDialog(this, "Dauer: " + NumberFormat.getInstance().format(ende - start) + " Millisekunden.", "Lesen zeilenweise mit Scanner", JOptionPane.INFORMATION_MESSAGE);
		
		
		textArea.requestFocusInWindow();
		
	
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
		
		//System.out.println("Ende von main()");
		

	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource() == btnBeenden)
			// Schließt den Frame
			this.dispose();
		else if (e.getSource() == btnDialog)
			anzeigeDialog();
		else if (e.getSource() == btnDatei)
			dateiLesen();
		
		
	}

}
