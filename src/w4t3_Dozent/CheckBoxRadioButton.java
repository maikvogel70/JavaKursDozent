package w4t3_Dozent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class CheckBoxRadioButton extends JFrame
{

	private JPanel backGroundPanel, foreGroundPanel, fontPanel;
	private JLabel backGroundLabel, foreGroundLabel, fontLabel, fontGroesseLabel;

	private JRadioButton rbHGGruen, rbHGBlau, rbHGRot, rbHGGelb, rbHGGrau;
	private JRadioButton rbVGBlau, rbVGRot, rbVGGelb, rbVGRosa, rbVGWeiss;

	private JCheckBox 	checkFett, checkKursiv;
	private JTextField  tfGroesse;
	
	private JButton btnBeenden;
	
	private ItemListener hgRadioButtonListener, vgRadioButtonListener, checkBoxListener;

	private Font font;
	
	private final int	     MIN_FONTSIZE	= 5;
	private final int	     MAX_FONTSIZE	= 20;
	private final int		 TF_MAX_LENGTH  = 2;	
	
	public CheckBoxRadioButton()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{

		this.setTitle("CheckBox und RadioButton");
		this.setSize(480, 280);

		// Keine Grössenänderung des Frames
		this.setResizable(false);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Layout Manager ausschalten
		this.setLayout(null);

		// Panel für die Hintergrundfarbe erstellen
		backGroundPanel = new JPanel();
		backGroundPanel.setBounds(20, 20, 140, 160);
		backGroundPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Opazität = Lichtundurchlässigkeit;
		// Opaque = Undurchsichtig, nicht transparent...
		// Standardwert = true (Lichtundurchlässig, nicht transparent).
		// 'false' bedeutet transparent, die Hintergrundfarbe des Steuerelements
		// auf dem sich die Komponente befindet scheint durch.
		
	
		backGroundPanel.setOpaque(false);
		
		// GridLayout: 6 Zeilen, 1 Spalte
		backGroundPanel.setLayout(new GridLayout(6, 1));

		backGroundLabel = new JLabel("Hintergrund");
		backGroundLabel.setHorizontalAlignment(JLabel.CENTER);
		backGroundPanel.add(backGroundLabel);
		
		// Den ItemListener für alle Radiobuttons der Hintergrundfarbe
		// erstellen.

		hgRadioButtonListener = new HGRadioButtonItemListener();

		// ButtonGroup um die RadioButtons für die Hintergrundfarbe
		// logisch zu gruppieren.
		ButtonGroup hgButtonGroup = new ButtonGroup();

		rbHGGruen = new JRadioButton("Grün");
		// rbHGGruen.setBounds(50, 50, 100, 20);
		hgButtonGroup.add(rbHGGruen);
		rbHGGruen.setOpaque(false);
		rbHGGruen.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzufügen
		backGroundPanel.add(rbHGGruen);

		rbHGBlau = new JRadioButton("Blau");
		// rbHGBlau.setBounds(50, 80, 100, 20);
		rbHGBlau.setOpaque(false);
		hgButtonGroup.add(rbHGBlau);
		rbHGBlau.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzufügen
		backGroundPanel.add(rbHGBlau);

		rbHGRot = new JRadioButton("Rot");
		// rbHGRot.setBounds(50, 110, 100, 20);
		rbHGRot.setOpaque(false);
		hgButtonGroup.add(rbHGRot);
		rbHGRot.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzufügen
		backGroundPanel.add(rbHGRot);

		rbHGGelb = new JRadioButton("Gelb");
		rbHGGelb.setOpaque(false);
		hgButtonGroup.add(rbHGGelb);
		rbHGGelb.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzufügen
		backGroundPanel.add(rbHGGelb);

		rbHGGrau = new JRadioButton("Grau");
		rbHGGrau.setOpaque(false);
		hgButtonGroup.add(rbHGGrau);
		rbHGGrau.addItemListener(hgRadioButtonListener);
		// Den RadioButton zum Panel hinzufügen
		backGroundPanel.add(rbHGGrau);

		// Background Panel zum Frame hinzufügen
		this.add(backGroundPanel);
		
		
		// Panel für die Vordergrundfarbe erstellen
		foreGroundPanel = new JPanel();
		foreGroundPanel.setBounds(170, 20, 140, 160);
		foreGroundPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		foreGroundPanel.setOpaque(false);
		
		// GridLayout: 6 Zeilen, 1 Spalte
		foreGroundPanel.setLayout(new GridLayout(6, 1));

		foreGroundLabel = new JLabel("Vordergrund");
		foreGroundLabel.setHorizontalAlignment(JLabel.CENTER);
		foreGroundPanel.add(foreGroundLabel);
		
		// Den ItemListener für alle Radiobuttons der Vordergrundfarbe
		// erstellen.

		vgRadioButtonListener = new VGRadioButtonItemListener();

		// ButtonGroup um die RadioButtons für die Hintergrundfarbe
		// logisch zu gruppieren.
		ButtonGroup vgButtonGroup = new ButtonGroup();
		
		rbVGBlau = new JRadioButton("Blau");
		rbVGBlau.addItemListener(vgRadioButtonListener);
		rbVGBlau.setOpaque(false);
		vgButtonGroup.add(rbVGBlau);
		foreGroundPanel.add(rbVGBlau);
		
		rbVGRot = new JRadioButton("Rot");
		rbVGRot.addItemListener(vgRadioButtonListener);
		rbVGRot.setOpaque(false);
		vgButtonGroup.add(rbVGRot);
		foreGroundPanel.add(rbVGRot);
		
		rbVGGelb = new JRadioButton("Gelb");
		rbVGGelb.addItemListener(vgRadioButtonListener);
		rbVGGelb.setOpaque(false);
		vgButtonGroup.add(rbVGGelb);
		foreGroundPanel.add(rbVGGelb);

		rbVGRosa = new JRadioButton("Rosa");
		rbVGRosa.addItemListener(vgRadioButtonListener);
		rbVGRosa.setOpaque(false);
		vgButtonGroup.add(rbVGRosa);
		foreGroundPanel.add(rbVGRosa);
		
		rbVGWeiss = new JRadioButton("Weiss");
		rbVGWeiss.addItemListener(vgRadioButtonListener);
		rbVGWeiss.setOpaque(false);
		vgButtonGroup.add(rbVGWeiss);
		foreGroundPanel.add(rbVGWeiss);
		
		// Vordergrund Panel zum Frame hinzufügen
		this.add(foreGroundPanel);
		
		
		
		// Schriftart Panel
		fontPanel = new JPanel();
		// Layout Manager für das Panel ausschalten.
		fontPanel.setLayout(null);
		
		fontPanel.setBounds(320,  20, 140,  160);
		fontPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fontPanel.setOpaque(false);
		
		
		// Listener für die CheckBoxen erstellen
		checkBoxListener = new CheckBoxItemListener();
		
		fontLabel = new JLabel("Schriftart");
		fontLabel.setHorizontalAlignment(JLabel.CENTER);
		fontLabel.setBounds(2,  2,  138, 25);
		fontPanel.add(fontLabel);
		
		checkFett = new JCheckBox("Fett");
		checkFett.setBounds(10,  30,  100, 25);
		checkFett.addItemListener(checkBoxListener);
		checkFett.setOpaque(false);
		fontPanel.add(checkFett);
		
		checkKursiv = new JCheckBox("Kursiv");
		checkKursiv.setBounds(10,  55,  100, 25);
		checkKursiv.addItemListener(checkBoxListener);
		checkKursiv.setOpaque(false);
		fontPanel.add(checkKursiv);
		
		fontGroesseLabel = new JLabel("Grösse");
		fontGroesseLabel.setBounds(10,  80,  100, 25);
		fontPanel.add(fontGroesseLabel);
		
		tfGroesse = new JTextField();
		tfGroesse.setBounds(10,  110, 60, 25);
		tfGroesse.setHorizontalAlignment(JTextField.CENTER);
		tfGroesse.addKeyListener(new TextFieldKeyListener());
		tfGroesse.addFocusListener(new TextFieldFocusListener());
		fontPanel.add(tfGroesse);
		
		this.add(fontPanel);
		
		
		// Beenden Button
		btnBeenden = new JButton("Beenden");
		btnBeenden.setBounds(170, 210, 140, 30);
		
		// Tooltip
		btnBeenden.setToolTipText("Programm beenden");
		
		// Tastaturkürzel
		btnBeenden.setMnemonic('e');		
				
		// Der ActionListener wird für den Button benötigt.
		btnBeenden.addActionListener(new ButtonBeendenActionListener());
		this.add(btnBeenden);
	}

	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);

		// this.setLocationByPlatform(true);

		// // Die Grössenänderung für setExtendedState()
		// // muss erlaubt sein.
		// this.setResizable(true);
		// this.setExtendedState(MAXIMIZED_BOTH);

		// // Ermittlung der Fenstergrösse des Desktops
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// System.out.println("Bildschirmauflösung");
		// System.out.println("Breite: " + dim.width);
		// System.out.println("Höhe : " + dim.height);

		
		// Eine Schriftart für alle Kompoenten der Panels erstellen
		font = new Font("Tahoma", Font.PLAIN, 13);
		
		setFont(font, backGroundPanel);
		setFont(font, foreGroundPanel);
		setFont(font, fontPanel);
		
		rbHGGrau.setSelected(true);
		rbVGWeiss.setSelected(true);
	
		checkFett.setSelected(font.isBold());
		checkKursiv.setSelected(font.isItalic());
		
		// Abfrage der Fontgröße
		tfGroesse.setText(Integer.toString(font.getSize()));
		
		
		
		
		
		
		
		
	}

	
	private void setBackColor()
	{
		
		Color backColor = Color.LIGHT_GRAY;
		
		// Alle Komponenten des Vordergrund Panels aktivieren
		for (int i = 0; i < foreGroundPanel.getComponentCount(); i++)
		{
			foreGroundPanel.getComponent(i).setEnabled(true);
		}
		
		
		
		if (rbHGGruen.isSelected())
		{
			backColor = Color.GREEN;
		}
		else if (rbHGBlau.isSelected())
		{
			backColor = Color.BLUE;
			rbVGBlau.setEnabled(false);
		}
		else if (rbHGRot.isSelected())
		{
			backColor = Color.RED;
			rbVGRot.setEnabled(false);
		}
		else if (rbHGGelb.isSelected())
		{
			backColor = Color.YELLOW;
			rbVGGelb.setEnabled(false);
			rbVGWeiss.setEnabled(false);
		}
		
		this.getContentPane().setBackground(backColor);
		
	}
	
	private void setForeColor()
	{
		
		Color foreColor = Color.BLACK;
		
		// Alle Komponenten des Hintergrund Panels aktivieren
		for (int i = 0; i < backGroundPanel.getComponentCount(); i++)
		{
			backGroundPanel.getComponent(i).setEnabled(true);
		}
		
		if (rbVGBlau.isSelected())
		{
			foreColor = Color.BLUE;
			rbHGBlau.setEnabled(false);
		}
		else if (rbVGRot.isSelected())
		{
			foreColor = Color.RED;
			rbHGRot.setEnabled(false);
		}
		else if (rbVGGelb.isSelected())
		{
			foreColor = Color.YELLOW;
		}
		else if (rbVGRosa.isSelected())
		{
			foreColor = Color.PINK;
		}
		else if (rbVGWeiss.isSelected())
		{
			foreColor = Color.WHITE;
		}
		
		if (foreColor == Color.YELLOW || foreColor == Color.WHITE)
		{
			rbHGGelb.setEnabled(false);
		}
		
		// Vordergrundfarbe für alle Komponenten des Hintergrund Panels setzen
		// inklusive des Panel Rahmens.
		backGroundPanel.setBorder(BorderFactory.createLineBorder(foreColor));
		for (int i = 0; i < backGroundPanel.getComponentCount(); i++)
		{
			backGroundPanel.getComponent(i).setForeground(foreColor);
		}
		
		// Vordergrundfarbe für alle Komponenten des Vordergrund Panels setzen
		// inklusive des Panel Rahmens.
		foreGroundPanel.setBorder(BorderFactory.createLineBorder(foreColor));
		for (int i = 0; i < foreGroundPanel.getComponentCount(); i++)
		{
			foreGroundPanel.getComponent(i).setForeground(foreColor);
		}
		
		// Vordergrundfarbe für alle Komponenten des Schriftart Panels setzen
		// inklusive des Panel Rahmens.
		fontPanel.setBorder(BorderFactory.createLineBorder(foreColor));
		
		Component c;
		for (int i = 0; i < fontPanel.getComponentCount(); i++)
		{
			
			c = fontPanel.getComponent(i);
			if (c instanceof JTextField)
				continue;
			
			c.setForeground(foreColor);
		}
		
	}
	
	private void setFont(Font font, JPanel panel)
	{
		
		for (int i = 0; i < panel.getComponentCount(); i++)
		{
			panel.getComponent(i).setFont(font);
		}
		
	}
	
	
	private void setFontStyle()
	{
		
		String fontFamily = font.getFamily();
		int fontSize = font.getSize();
		int fontStyle = Font.PLAIN;
		
		// Definition der Standard-Schriftstile
		// 0000 0000 			PLAIN			0
		// 0000 0001            BOLD            1
		// 0000 0010            ITALIC          2
		// 0000 0011            BOLD/ITALIC     3
		
		
//		if (checkFett.isSelected())
//			fontStyle |= Font.BOLD;
//		
//		if (checkKursiv.isSelected())
//			fontStyle |= Font.ITALIC;
		
		// oder
		if (checkFett.isSelected())
			fontStyle += Font.BOLD;
		
		if (checkKursiv.isSelected())
			fontStyle += Font.ITALIC;
		
		font = new Font(fontFamily, fontStyle, fontSize);
		
		// Schriftart für alle Komponenten der Panels setzen
		setFont(font, backGroundPanel);
		setFont(font, foreGroundPanel);
		setFont(font, fontPanel);
		
		
	}
	
	
	private void setFontSize()
	{
		
		int fontSize = font.getSize();
		int newSize = fontSize;
		
		
		// Prüfen, ob der Inhalt des Textfeldes in eine Integer-Wert umgewandelt werden konnte.
		try
		{
			newSize = Integer.parseInt(tfGroesse.getText());
		}
		catch (Exception ex)
		{
			
			JOptionPane.showMessageDialog(this, "Die Eingabe der Fontgröße ist ungültig: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			tfGroesse.setText(Integer.toString(fontSize));
		}
		
		
		if (newSize == fontSize)
			return;
		
		if (newSize < MIN_FONTSIZE)
		{
			JOptionPane.showMessageDialog(this, "Die Mindestgrösse der Schriftart beträgt " + MIN_FONTSIZE, "Fehler", JOptionPane.ERROR_MESSAGE);
			tfGroesse.setText(Integer.toString(fontSize));
		}
		else if (newSize > MAX_FONTSIZE)
		{
			JOptionPane.showMessageDialog(this, "Die maximale Größe der Schriftart beträgt " + MAX_FONTSIZE, "Fehler", JOptionPane.ERROR_MESSAGE);
			tfGroesse.setText(Integer.toString(fontSize));
		}
		else
			fontSize = newSize;
		
		
		font = new Font(font.getFamily(), font.getStyle(), fontSize);
		
		// Schriftart für alle Komponenten der Panels setzen
		setFont(font, backGroundPanel);
		setFont(font, foreGroundPanel);
		setFont(font, fontPanel);
				
		
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
	
	private class VGRadioButtonItemListener implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e)
		{

			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				setForeColor();
			}

		}

	}
	
	private class ButtonBeendenActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);	
		}
		
		

	}
	
	private class CheckBoxItemListener implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			setFontStyle();

		}

	}
	
	private class TextFieldKeyListener implements KeyListener
	{

		// KeyPressed Ereignis: zum Prüfen auf Kommando- oder Steuertasten.
		// Eingaben, die kein darstellbares Zeichen an das Steuerelement
		// übergeben.
		@Override
		public void keyPressed(KeyEvent e)
		{
			
			//System.out.println("Key pressed: " + e.getKeyChar());
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				//setFontSize();
				//btnBeenden.requestFocusInWindow();
				
				// Mit dem Focus-Manager auf das nächste Steuerelement positionieren
				KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
				
				
				
			}
			
			
		}

		// KeyReleased Ereignis: wenn eine beliebige Taste losgelassen wird.
		@Override
		public void keyReleased(KeyEvent e)
		{
			//System.out.println("Key released: " + e.getKeyChar());
			
		}

		// KeyTyped Ereignis: zum Prüfen auf Zeichen für das Steuerelement.
		@Override
		public void keyTyped(KeyEvent e)
		{
			//System.out.println("Key typed: " + e.getKeyChar());
			
				
			// Steuertasten ignorieren
			if (Character.isISOControl(e.getKeyChar()))
			{
				return;
			}
			
		
			// Nur die Ziffern 0 - 9 zulassen
			if (!Character.isDigit(e.getKeyChar()))
			{
				Toolkit.getDefaultToolkit().beep();
				e.consume();
				return;
			}
			
			// Evtl. markierte Zeichen im Textfeld zuerst löschen
			tfGroesse.replaceSelection(null);
			
			// Nicht mehr als 2 Ziffern zulassen
			if (tfGroesse.getText().length() >= TF_MAX_LENGTH)
			{
				Toolkit.getDefaultToolkit().beep();
				e.consume();
			}
			
			
			
		}
		
		
	}
	
	
	private class TextFieldFocusListener implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e)
		{
			
			if (e.getSource() instanceof JTextField)
			{
				((JTextField)e.getSource()).selectAll();
			}
			
			
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			setFontSize();
			
		}
		
	}
	
	

}
