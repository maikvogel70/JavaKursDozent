package w4t4_Dozent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Farbmischer extends JFrame implements ActionListener, ChangeListener
{
	
	private static final int COLOR_MIN	= 0;
	private static final int COLOR_MAX  = 255;
	private static final int COLOR_INIT = 238;
	

	private JLabel labelRed;
	private JSlider sliderRed;
	
	private JTextField tfRed, tfHexRed;
	
	private JButton btnBeenden;	
	
	private Hashtable<Integer, JLabel> sliderLabelTabelle;
	
	
	public Farbmischer()
	{
		initializeComponents();
		
	}
	
	private void initializeComponents()
	{
		
		this.setTitle("Farbmischer");
		this.setSize(520,  340);
		
		// keine Grössenänderung des Frames
		this.setResizable(false);
		
		// Layout Manager ausschalten
		this.setLayout(null);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		labelRed = new JLabel("Rot");
		labelRed.setBounds(30, 10, 30, 25);
		this.add(labelRed);
		
		sliderRed = new JSlider(JSlider.HORIZONTAL, COLOR_MIN, COLOR_MAX, COLOR_INIT);
		sliderRed.setBounds(120,  10, 300, 50);
		
		// Einstellungen für die Skala des Schiebereglers
		
		// Distanz zwischen des Hauptmarkierungen der Skala
		sliderRed.setMajorTickSpacing(15);
		
		// Distanz zwischen des Zwischenmarkierungen der Skala
		sliderRed.setMinorTickSpacing(5);
		
		// Skala anzeigen
		sliderRed.setPaintTicks(true);
		
		// Beschriftungen für die Hauptmarkierunge anzeigen
		// Zu viele Labels, können nicht vernünftig angezeigt werden.
		//sliderRed.setPaintLabels(true);
		
		// Erstellen einer Label-Tabelle
		sliderLabelTabelle = new Hashtable<>();
		sliderLabelTabelle.put(COLOR_MIN, new JLabel(Integer.toString(COLOR_MIN)));
		sliderLabelTabelle.put(COLOR_MAX, new JLabel(Integer.toString(COLOR_MAX)));
		
		sliderRed.setLabelTable(sliderLabelTabelle);
		sliderRed.setPaintLabels(true);
		
		sliderRed.addChangeListener(this);
		
		this.add(sliderRed);
		
		
		tfHexRed = new JTextField();
		tfHexRed.setBounds(440,  10,  40, 25);
		tfHexRed.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(tfHexRed);
		
		tfRed = new JTextField();
		tfRed.setBounds(440,  35,  40, 25);
		tfRed.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(tfRed);
		
		
		
		
		
	}
	
	
	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);
		
		scrollSlider();
		
	}
	
	private void scrollSlider()
	{
		
		int valRed = sliderRed.getValue();
		
		
		// Jeder einzelne Slider bekommt seinen aktuellen spezifischen
		// Farbanteil als Hintergrundfarbe.

		sliderRed.setBackground(new Color(valRed, 0, 0));
		
		
		tfHexRed.setText(String.format("X'%02X'", valRed));
		tfRed.setText(Integer.toString(valRed));
		
	}
	
	
	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}
	
	
	
	
	public static void main(String[] args)
	{
		
		Farbmischer f = new Farbmischer();
		f.showFrame();

	}

	// ActionListener
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	// ChangeListener
	@Override
	public void stateChanged(ChangeEvent e)
	{
		scrollSlider();
		
	}

}
