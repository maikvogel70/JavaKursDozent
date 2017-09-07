package w4t1_Dozent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Grafik1
{

	private JFrame frame;
	private JLabel lbl1, lbl2;
	private JTextField tf1, tf2;
	private JButton btnBeenden;
	
	
	
	public Grafik1()
	{
		
		initializeComponents();
		
		
	}
	
	
	private void initializeComponents()
	{
		
		frame = new JFrame("Erstes Grafikprogramm");
		
		// Muss angegeben werden, da sonst das Fenster nur geschlossen wird
		// (es wird unsichtbar), die Klasse selbst bleibt jedoch aktiv!
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Position des Frames in Pixel
		//frame.setLocation(200,  100);
		
		// Gr�sse des Frames in Pixel
		//frame.setSize(350, 150);
		
		// Position und Gr�sse des Frames festlegen.
		frame.setBounds(200, 100, 350, 150);
		
		// Keine Gr�ssen�nderung des Frames
		frame.setResizable(false);
		
		
		// Layout Manager ausschalten
		frame.setLayout(null);
		
		// Einen Label erstellen
		lbl1 = new JLabel("Eingabe");
		//lbl1.setBorder(LineBorder.createBlackLineBorder());
		
		lbl1.setBounds(10,  10, 90, 20);
		
		// Den Label zum Frame hinzuf�gen
		//frame.getContentPane().add(lbl1);
		
		// oder
		frame.add(lbl1);
		
		
		// Ein Textfeld erstellen
		tf1 = new JTextField();
		tf1.setBounds(100, 10, 200, 20);
		
		// Das Texfeld dem Frame hinzuf�gen
		frame.add(tf1);
		
		// Einen weiteren Label erstellen und dem Frame zuweisen
		lbl2 = new JLabel("Kopie");
		lbl2.setBounds(10, 40, 90, 20);
		frame.add(lbl2);
		
		
		// Ein neues Textfeld erstellen und dem Frame zuweisen
		tf2 = new JTextField("Kopie");
		tf2.setBounds(100, 40, 200, 20);
		
		// Nicht editierbar, keine Eingabe m�glich.
		// Der Hintergrund des Textfeldes bleibt unver�ndert, die Schriftfarbe
		// wird grau.
		// Die Komponente kann auch den Eingabefokus nicht erhalten.
		//tf2.setEnabled(false);
		
		// Der Hintergrund wird grau, die Schriftfarbe bleibt schwarz.
		// Die Komponente kann jedoch immer noch den Eingabefokus erhalten.
		//tf2.setEditable(false);
		
		// Hintergund- und Schriftfarbe bleiben unver�ndert, eine Eingabe ist
		// nicht m�glich.
		// Die Komponente kann auch den Eingabefokus nicht erhalten.
		tf2.setFocusable(false);
		
		frame.add(tf2);
		
		
		// Einen Button zum Frame hinzuf�gen
		btnBeenden = new JButton("Beenden");
		btnBeenden.setBounds(120, 80, 100, 25);
		btnBeenden.addActionListener(new ButtonBeendenActionListener());
		
		frame.add(btnBeenden);
		
		
		
	}
	
	
	public void  showFrame()
	{
		
		frame.setVisible(true);
		
	}
	
	
	private class ButtonBeendenActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.out.println(((JButton)e.getSource()).getText() + ": Button geklickt."); 
			System.exit(0);
			
		}
		
		
		
	}
	
	
	
	
}
