package w4t5_Dozent;

import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Farbmischer extends JFrame implements ActionListener, ChangeListener, KeyListener, FocusListener
{
	
	private static final int COLOR_MIN	= 0;
	private static final int COLOR_MAX  = 255;
	private static final int COLOR_INIT = 238;
	private static final int TF_MAX_LENGTH = 3;
	

	private JLabel labelRed, labelGreen, labelBlue;
	private JSlider sliderRed, sliderGreen, sliderBlue;
	
	private JTextField tfRed, tfHexRed, tfGreen, tfHexGreen, tfBlue, tfHexBlue;
	
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
		tfHexRed.setFocusable(false);
		this.add(tfHexRed);
		
		tfRed = new JTextField();
		tfRed.setBounds(440,  35,  40, 25);
		tfRed.setHorizontalAlignment(SwingConstants.CENTER);
		tfRed.setName("ROT");
		
		tfRed.addKeyListener(this);
		tfRed.addFocusListener(this);
		this.add(tfRed);
		
		labelGreen = new JLabel("Grün");
		labelGreen.setBounds(30, 60, 30, 25);
		this.add(labelGreen);
		
		sliderGreen = new JSlider(JSlider.HORIZONTAL, COLOR_MIN, COLOR_MAX, COLOR_INIT);
		sliderGreen.setBounds(120,  60, 300, 50);
		sliderGreen.setMajorTickSpacing(15);
		sliderGreen.setMinorTickSpacing(5);
		sliderGreen.setPaintTicks(true);
		
		sliderGreen.setLabelTable(sliderLabelTabelle);
		sliderGreen.setPaintLabels(true);
		
		sliderGreen.addChangeListener(this);
		this.add(sliderGreen);
		
		tfHexGreen = new JTextField();
		tfHexGreen.setBounds(440, 60, 40, 25);
		tfHexGreen.setHorizontalAlignment(JTextField.CENTER);
		tfHexGreen.setFocusable(false);
		this.add(tfHexGreen);

		tfGreen = new JTextField();
		tfGreen.setBounds(440, 85, 40, 25);
		tfGreen.setHorizontalAlignment(JTextField.CENTER);
		tfGreen.setName("GRÜN");
		tfGreen.addKeyListener(this);
		tfGreen.addFocusListener(this);
		this.add(tfGreen);

		labelBlue = new JLabel("Blau");
		labelBlue.setBounds(30, 110, 30, 25);
		this.add(labelBlue);
		
		sliderBlue = new JSlider(JSlider.HORIZONTAL, COLOR_MIN, COLOR_MAX, COLOR_INIT);
		sliderBlue.setBounds(120, 110, 300, 50);
		sliderBlue.setMajorTickSpacing(15);
		sliderBlue.setMinorTickSpacing(5);
		sliderBlue.setPaintTicks(true);

		// Wenn die Labels angezeigt werden sollen:
		sliderBlue.setLabelTable(sliderLabelTabelle);
		sliderBlue.setPaintLabels(true);

		sliderBlue.addChangeListener(this);
		this.add(sliderBlue);
		
		tfHexBlue = new JTextField();
		tfHexBlue.setBounds(440, 110, 40, 25);
		tfHexBlue.setHorizontalAlignment(JTextField.CENTER);
		tfHexBlue.setFocusable(false);
		this.add(tfHexBlue);

		tfBlue = new JTextField();
		tfBlue.setBounds(440, 135, 40, 25);
		tfBlue.setHorizontalAlignment(JTextField.CENTER);
		tfBlue.setName("BLAU");
		tfBlue.addKeyListener(this);
		tfBlue.addFocusListener(this);
		this.add(tfBlue);
		
		btnBeenden = new JButton("Beenden");
		btnBeenden.setMnemonic('e');
		btnBeenden.setBounds(180, 260, 140, 30);
		btnBeenden.addActionListener(this);
		this.add(btnBeenden);
		
	}
	
	
	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);
		
		scrollSlider();
		
	}
	
	private void scrollSlider()
	{
		
		Color labelForeGroundColor = Color.BLACK;
		
		int valRed = sliderRed.getValue();
		int valGreen = sliderGreen.getValue();
		int valBlue = sliderBlue.getValue();
		
		
		// Jeder einzelne Slider bekommt seinen aktuellen spezifischen
		// Farbanteil als Hintergrundfarbe.

		sliderRed.setBackground(new Color(valRed, 0, 0));
		sliderGreen.setBackground(new Color(0, valGreen, 0));
		sliderBlue.setBackground(new Color(0, 0, valBlue));
		
		
		// Die Frame-Hintergrundfarbe soll sich aus den
		// Farbwerten der drei Slider zusammensetzen
		this.getContentPane().setBackground(new Color(valRed, valGreen, valBlue));
		
		
		tfHexRed.setText(String.format("X'%02X'", valRed));
		tfRed.setText(Integer.toString(valRed));
		
		tfHexGreen.setText(String.format("X'%02X'", valGreen));
		tfGreen.setText(Integer.toString(valGreen));
		
		tfHexBlue.setText(String.format("X'%02X'", valBlue));
		tfBlue.setText(Integer.toString(valBlue));
		
		// Die Vordergrundfarbe (Schriftfarbe) der Label so einstellen,
		// dass sie immer lesbar sind.
		
		// 1.
//		if (valRed + valGreen + valBlue < 300)
//		{
//			labelForeGroundColor = Color.WHITE;
//		}
//		else
//		{
//			labelForeGroundColor = Color.BLACK;
//		}
		
		// 2.
		labelForeGroundColor = new Color(valRed ^ 0x80, valGreen ^ 0x80, valBlue ^ 0x80);
		
		// 3.
		//labelForeGroundColor = new Color((valRed + 128) % 256, (valGreen + 128) % 256, (valBlue + 128) % 256);
		
		// Schriftfarbe für alle Label setzen
		for (Component c : this.getContentPane().getComponents())
		{
			if (c instanceof JLabel)
				c.setForeground(labelForeGroundColor);
		}
		
		// Schriftfarbe der Labels pauschal auf Weiss setzen
		sliderLabelTabelle.get(COLOR_MIN).setForeground(Color.WHITE);
		sliderLabelTabelle.get(COLOR_MAX).setForeground(Color.WHITE);
		
		// Farbe der Skala über setForeGround() pauschal auf Weiss setzen
		sliderRed.setForeground(Color.WHITE);
		sliderGreen.setForeground(Color.WHITE);
		sliderBlue.setForeground(Color.WHITE);
		
		
		
	}
	
	
	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}
	
	private int convertTextFieldToValue(JTextField tf, KeyEvent e)
	{
		
		// Position, an der das Zeichen eingefügt wurde ermitteln
		int charPos = tf.getCaretPosition();
		String tmpText = tf.getText().substring(0, charPos)+ e.getKeyChar() + tf.getText().substring(charPos);
		
		return Integer.parseInt(tmpText);
		
	}
	
	private void setSliderValue(JTextField tf)
	{
		int value = 0;
		
		JSlider slider = sliderRed;
		
		
		if (tf == tfGreen)
		{
			
			slider = sliderGreen;
			
		}
		else if (tf == tfBlue)
		{
			slider = sliderBlue;
		}

		try
		{
			value = Integer.parseInt("0" + tf.getText());
			
		}
		catch (Exception ex)
		{
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Ungültige Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE);
			tf.setText(Integer.toString(slider.getValue()));
			value = slider.getValue();
		}
		
		slider.setValue(value);
		

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
		if (e.getSource() == btnBeenden)
			this.dispose();
		
		
	}

	// ChangeListener
	@Override
	public void stateChanged(ChangeEvent e)
	{
		scrollSlider();
		
	}

	// FocusListener
	
	@Override
	public void focusGained(FocusEvent e)
	{
		if (e.getSource() instanceof JTextField)
			((JTextField) e.getSource()).selectAll();
		
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		
		if (e.getSource() instanceof JTextField)
			setSliderValue((JTextField)e.getSource());
		
		
	}

	// KeyListener
	
	@Override
	public void keyPressed(KeyEvent e)
	{

		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
		JTextField tf = null;
		
		if (e.getSource() instanceof JTextField)
			tf = (JTextField) e.getSource();
		else
			return;
		
		//System.out.println(tf.getName());
		
		// Steuertasten ignorieren
		if (Character.isISOControl(e.getKeyChar()))
			return;
		
		// Überprüfung auf Ziffer 0 - 9
		if (!Character.isDigit(e.getKeyChar()))
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}
		
		tf.replaceSelection("");
		
		if (tf.getText().length() >= TF_MAX_LENGTH)
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}
		
		if (convertTextFieldToValue(tf, e) > COLOR_MAX)
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
		}
		
		
		
		
		
		
	}

}
