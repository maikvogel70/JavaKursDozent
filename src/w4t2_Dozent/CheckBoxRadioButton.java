package w4t2_Dozent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class CheckBoxRadioButton extends JFrame
{

	private JPanel backGroundPanel;
	private JLabel backGroundLabel;

	private JRadioButton rbHGGruen, rbHGBlau, rbHGRot, rbHGGelb, rbHGGrau;

	private ItemListener hgRadioButtonListener;

	public CheckBoxRadioButton()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{

		this.setTitle("CheckBox und RadioButton");
		this.setSize(480, 280);

		// Keine Gr�ssen�nderung des Frames
		this.setResizable(false);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Layout Manager ausschalten
		this.setLayout(null);

		// Panel f�r die Hintergrundfarbe erstellen
		backGroundPanel = new JPanel();
		backGroundPanel.setBounds(20, 20, 140, 160);
		backGroundPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Opazit�t = Lichtundurchl�ssigkeit;
		// Opaque = Undurchsichtig, nicht transparent...
		// Standardwert = true (Lichtundurchl�ssig, nicht transparent).
		// 'false' bedeutet transparent, die Hintergrundfarbe des Steuerelements
		// auf dem sich die Komponente befindet scheint durch.
		
	
		backGroundPanel.setOpaque(false);
		
		// GridLayout: 6 Zeilen, 1 Spalte
		backGroundPanel.setLayout(new GridLayout(6, 1));

		backGroundLabel = new JLabel("Hintergrund");
		backGroundLabel.setHorizontalAlignment(JLabel.CENTER);
		backGroundPanel.add(backGroundLabel);
		
		// Den ItemListener f�r alle Radiobuttons der Hintergrundfarbe
		// erstellen.

		hgRadioButtonListener = new HGRadioButtonItemListener();

		// ButtonGroup um die RadioButtons f�r die Hintergrundfarbe
		// logisch zu gruppieren.
		ButtonGroup hgButtonGroup = new ButtonGroup();

		rbHGGruen = new JRadioButton("Gr�n");
		// rbHGGruen.setBounds(50, 50, 100, 20);
		hgButtonGroup.add(rbHGGruen);
		rbHGGruen.setOpaque(false);
		rbHGGruen.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzuf�gen
		backGroundPanel.add(rbHGGruen);

		rbHGBlau = new JRadioButton("Blau");
		// rbHGBlau.setBounds(50, 80, 100, 20);
		rbHGBlau.setOpaque(false);
		hgButtonGroup.add(rbHGBlau);
		rbHGBlau.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzuf�gen
		backGroundPanel.add(rbHGBlau);

		rbHGRot = new JRadioButton("Rot");
		// rbHGRot.setBounds(50, 110, 100, 20);
		rbHGRot.setOpaque(false);
		hgButtonGroup.add(rbHGRot);
		rbHGRot.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzuf�gen
		backGroundPanel.add(rbHGRot);

		rbHGGelb = new JRadioButton("Gelb");
		rbHGGelb.setOpaque(false);
		hgButtonGroup.add(rbHGGelb);
		rbHGGelb.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzuf�gen
		backGroundPanel.add(rbHGGelb);

		rbHGGrau = new JRadioButton("Grau");
		rbHGGrau.setOpaque(false);
		hgButtonGroup.add(rbHGGrau);
		rbHGGrau.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzuf�gen
		backGroundPanel.add(rbHGGrau);

		// Background Panel zum Frame hinzuf�gen
		this.add(backGroundPanel);

	}

	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);

		// this.setLocationByPlatform(true);

		// // Die Gr�ssen�nderung f�r setExtendedState()
		// // muss erlaubt sein.
		// this.setResizable(true);
		// this.setExtendedState(MAXIMIZED_BOTH);

		// // Ermittlung der Fenstergr�sse des Desktops
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// System.out.println("Bildschirmaufl�sung");
		// System.out.println("Breite: " + dim.width);
		// System.out.println("H�he : " + dim.height);

		rbHGGruen.setSelected(true);

	}

	
	private void setBackColor()
	{
		
		Color backColor = Color.LIGHT_GRAY;
		
		if (rbHGGruen.isSelected())
		{
			backColor = Color.GREEN;
		}
		else if (rbHGBlau.isSelected())
		{
			backColor = Color.BLUE;
		}
		else if (rbHGRot.isSelected())
		{
			backColor = Color.RED;
		}
		else if (rbHGGelb.isSelected())
		{
			backColor = Color.YELLOW;
		}
		
		this.getContentPane().setBackground(backColor);
		
		
		
		
	}
	
	
	
	public void showFrame()
	{

		initFrame();
		this.setVisible(true);

	}

	public static void main(String[] args)
	{

		CheckBoxRadioButton f = new CheckBoxRadioButton();
		f.showFrame();

	}

	private class HGRadioButtonItemListener implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e)
		{

			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				setBackColor();

			}

		}

	}

}
