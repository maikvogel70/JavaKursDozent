package w5t4_Dozent;

import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.*;

import util.MyFocusTraversalPolicy;

public class FrameDialogSuchenUndErsetzen extends JDialog implements ActionListener, KeyListener
{

	private static FrameDialogSuchenUndErsetzen myInstance;
	
	private JLabel lbl1, lbl2;
	private JTextField tfSuche, tfErsetze;
	private JCheckBox checkGrossKlein;
	private JButton btnAbbrechen, btnWeitersuchen, btnErsetzen, btnAlleErsetzen;

	// Wir bekommen vom aufrufenden Fenster eine Referenz auf seine TextArea übergeben
	private JTextArea textArea;

	private Component owner;

	private int foundPos;
	private int searchCounter, replaceCounter;

	private boolean replaceAll;
	
	private MyFocusTraversalPolicy newPolicy;
	
	private boolean newDialog = true;

	private FrameDialogSuchenUndErsetzen(JTextArea textArea)
	{
		initializeComponents();
		this.textArea = textArea;

	}

	public static FrameDialogSuchenUndErsetzen getInstance(JTextArea textArea)
	{
		if (FrameDialogSuchenUndErsetzen.myInstance == null)
			FrameDialogSuchenUndErsetzen.myInstance = new FrameDialogSuchenUndErsetzen(textArea);
		
		return FrameDialogSuchenUndErsetzen.myInstance;
	}
	
	
	private void initializeComponents()
	{
		this.setTitle("Suchen und Ersetzen");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/Search.png")));
		this.setSize(480, 155);

		// Layout Manager ausschalten
		this.setLayout(null);

		// Keine Grössenänderung des Dialogs
		this.setResizable(false);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		lbl1 = new JLabel("Suchen nach:");
		lbl1.setBounds(5, 5, 90, 25);
		this.add(lbl1);

		tfSuche = new JTextField();
		tfSuche.setBounds(110, 5, 240, 25);
		tfSuche.addKeyListener(this);
		this.add(tfSuche);

		lbl2 = new JLabel("Ersetzen durch:");
		lbl2.setBounds(5, 35, 90, 25);
		this.add(lbl2);

		tfErsetze = new JTextField();
		tfErsetze.setBounds(110, 35, 240, 25);
		tfErsetze.addKeyListener(this);
		this.add(tfErsetze);

		checkGrossKlein = new JCheckBox("Groß-/Kleinschreibung beachten");
		checkGrossKlein.setBounds(5, 90, 300, 25);
		this.add(checkGrossKlein);

		btnWeitersuchen = new JButton("Weitersuchen");
		btnWeitersuchen.setFont(btnWeitersuchen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnWeitersuchen.setBounds(350, 5, 120, 25);
		btnWeitersuchen.addActionListener(this);
		this.add(btnWeitersuchen);

		btnErsetzen = new JButton("Ersetzen");
		btnErsetzen.setFont(btnErsetzen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnErsetzen.setBounds(350, 35, 120, 25);
		btnErsetzen.addActionListener(this);
		this.add(btnErsetzen);

		btnAlleErsetzen = new JButton("Alle Ersetzen");
		btnAlleErsetzen.setFont(btnAlleErsetzen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnAlleErsetzen.setBounds(350, 65, 120, 25);
		btnAlleErsetzen.addActionListener(this);
		this.add(btnAlleErsetzen);

		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setFont(btnAbbrechen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnAbbrechen.setBounds(350, 95, 120, 25);
		btnAbbrechen.addActionListener(this);
		this.add(btnAbbrechen);
		
		
		// ----------------------------------------------
		
		// Verwenden eines Vectors um die Fokus-Reihenfolge der Komponenten,
		// abweichend von der Standardreihenfolge, zu definieren.
		Vector<Component> components = new Vector<>();
		
		// Hinzufügen der Komponenten in der gewünschten Reihenfolge.
		components.add(tfSuche);
		components.add(tfErsetze);
		components.add(checkGrossKlein);
		components.add(btnWeitersuchen);
		components.add(btnErsetzen);
		components.add(btnAlleErsetzen);
		components.add(btnAbbrechen);
		
		// Neue FocusTraversalPolicy erstellen
		newPolicy = new MyFocusTraversalPolicy(components);
		this.setFocusTraversalPolicy(newPolicy);
		
		
		

	}

	private void initDialog()
	{
		//this.setModal(true);

		if (newDialog)
		{
			this.setLocationRelativeTo(owner);
			newDialog = false;
		}

		if (textArea.getSelectedText() != null)
		{
			tfSuche.setText(textArea.getSelectedText());
			searchCounter++;
		}

		tfSuche.selectAll();

		// Wichtig!
		// Durch den Fokusverlust ist der markierte Text im Hauptfenster
		// nicht mehr sichtbar (gilt nur für modalen Dialog).
		// Durch das erneute Setzen des Eingabefokus wird die Markierung wieder
		// angezeigt.
		textArea.requestFocusInWindow();

		enableButtons();
		
		
		// Test der Focus Reihenfolge
		//tfErsetze.setEditable(false);
		
		//newPolicy.enableAllComponents(false);
		
	
	}

	public void showDialog()
	{
		initDialog();
		this.setVisible(true);
	}

	private void enableButtons()
	{
		// Button zum Weitersuchen aktivieren, wenn im Suchtext
		// ein Suchbegriff eingegeben wurde.
		btnWeitersuchen.setEnabled(tfSuche.getText().length() > 0);

		// Button zum Ersetzen deaktivieren, wenn kein Suchtext vorhanden
		// ist.
		btnErsetzen.setEnabled(btnWeitersuchen.isEnabled());

		// Button zum Ersetzen aller Texte ist nur aktiv, wenn auch der
		// Button zum Ersetzen aktiviert ist.
		btnAlleErsetzen.setEnabled(btnErsetzen.isEnabled());

	}

	private void suche()
	{

		int dlgValue;

		if (checkGrossKlein.isSelected())
		{
			// Groß-/Kleinschreibung beachten
			foundPos = textArea.getText().indexOf(tfSuche.getText(), textArea.getCaretPosition());
		}
		else
		{
			// Groß-/Kleinschreibung nicht beachten
			foundPos = textArea.getText().toLowerCase().indexOf(tfSuche.getText().toLowerCase(), textArea.getCaretPosition());
		}

		if (foundPos > -1)
		{
			// Text in der TextArea merkieren
			textArea.select(foundPos, foundPos + tfSuche.getText().length());
			searchCounter++;
		}
		else
		{
			if (replaceAll)
			{
				replaceAll = false;
			}
			else
			{

				// Das Textmuster wurde nicht oder nicht mehr gefunden
				if (searchCounter > 0)
				{
					dlgValue = showEndOfTextMessage();

					if (dlgValue == JOptionPane.OK_OPTION)
					{

						searchCounter = 0;
						replaceCounter = 0;
						// Den Cursor an den Anfang des textes positionieren
						textArea.setCaretPosition(0);
						suche();
					}
					else
					{
						tfSuche.requestFocusInWindow();
						tfSuche.selectAll();
					}

				}
				else
					showTextNotFoundMessage();
			}

		}

	}

	private int showEndOfTextMessage()
	{

		String msg = "Die Suche hat das Ende des Textes erreicht.\nVom Anfang des Textes an weitersuchen";

		// Benutzerdefinierte Button Texte
		String[] options =
		{ "OK", "Abbrechen" };

		return JOptionPane.showOptionDialog(this, msg, "Frage", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

	}

	private void showTextNotFoundMessage()
	{
		String msg = "Die angegebene Zeichenfolge wurde nicht gefunden";
		JOptionPane.showMessageDialog(this, msg, "Hinweis", JOptionPane.INFORMATION_MESSAGE);

	}

	private void ersetze()
	{

		if (textArea.getSelectedText() != null)
		{

			if (checkGrossKlein.isSelected() && textArea.getSelectedText().equals(tfSuche.getText()))
			{
				textArea.replaceSelection(tfErsetze.getText());
				replaceCounter++;
			}
			else if (!checkGrossKlein.isSelected() && textArea.getSelectedText().equalsIgnoreCase(tfSuche.getText()))
			{
				textArea.replaceSelection(tfErsetze.getText());
				replaceCounter++;
			}

		}

		suche();

	}

	private void ersetzeAlle()
	{

		replaceCounter = 0;
		searchCounter = 0;

		textArea.setCaretPosition(0);

		replaceAll = true;

//		do
//		{
//			ersetze();
//		}
//		while (foundPos > -1);
//
//		if (replaceCounter > 0)
//		{
//
//			JOptionPane.showMessageDialog(this, String.format("Es wurden %s Textstellen ersetzt", NumberFormat.getInstance().format(replaceCounter)), "Hinweis",
//					JOptionPane.INFORMATION_MESSAGE);
//
//		}

		
		String regEx = "";
		
		if (!checkGrossKlein.isSelected())
			regEx = "(?i)";
		
		replaceCounter = countMatches(textArea.getText(), tfSuche.getText());
		if (replaceCounter > 0)
		{
			textArea.setText(textArea.getText().replaceAll(regEx + Pattern.quote(tfSuche.getText()), tfErsetze.getText()));
			JOptionPane.showMessageDialog(this, String.format("Es wurden %s Textstellen ersetzt", NumberFormat.getInstance().format(replaceCounter)), "Hinweis",
					JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		replaceCounter = 0;
		searchCounter = 0;

		// Cursor an den Textanfang zurücksetzen
		textArea.setCaretPosition(0);

	}
	
	
	private int countMatches(String s, String searchPattern)
	{
		
		if (!checkGrossKlein.isSelected())
		{
			s = s.toLowerCase();
			searchPattern = searchPattern.toLowerCase();
		}
		
		return (s.length() - s.replace(searchPattern, "").length()) / searchPattern.length();
		
		
		
		
	}
	

	public void showDialog(Component owner)
	{
		this.owner = owner;
		showDialog();
	}

	// ActionListener
	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource() == btnAbbrechen)
			this.dispose();
		else if (e.getSource() == btnWeitersuchen)
			suche();
		else if (e.getSource() == btnErsetzen)
			ersetze();
		else if (e.getSource() == btnAlleErsetzen)
			ersetzeAlle();

	}

	// KeyListener
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e)
	{

		if (e.getSource() == tfSuche)
		{
			enableButtons();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
