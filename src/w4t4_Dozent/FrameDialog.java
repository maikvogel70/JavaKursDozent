package w4t4_Dozent;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FrameDialog extends JFrame implements ActionListener
{

	private JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	
	
	private JButton btnBeenden, btnDialog;
	
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
		
		
	}

}
