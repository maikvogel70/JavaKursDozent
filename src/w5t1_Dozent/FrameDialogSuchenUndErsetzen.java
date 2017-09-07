package w5t1_Dozent;

import java.awt.Component;
import java.awt.Font;

import javax.swing.*;

public class FrameDialogSuchenUndErsetzen extends JDialog
{

	private JLabel lbl1, lbl2;
	private JTextField tfSuche, tfErsetze;
	private JCheckBox checkGrossKlein;
	private JButton btnAbbrechen, btnWeitersuchen, btnErsetzen, btnAlleErsetzen;
	
	// Wir bekommen vom aufrufenden Fenster eine Referenz auf seine TextArea übergeben
	private JTextArea textArea;
	
	private Component owner;
	
	public FrameDialogSuchenUndErsetzen(JTextArea textArea)
	{
		initializeComponents();
		this.textArea = textArea;
		
	}
	
	private void initializeComponents()
	{
		this.setTitle("Suchen und Ersetzen");
		this.setSize(480,  155);
		
		// Layout Manager ausschalten
		this.setLayout(null);
		
		// Keine Grössenänerung des Dialogs
		this.setResizable(false);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		lbl1 = new JLabel("Suchen nach:");
		lbl1.setBounds(5, 5, 90, 25);
		this.add(lbl1);
		
		tfSuche = new JTextField();
		tfSuche.setBounds(110, 5, 240, 25);
		this.add(tfSuche);
		
		lbl2 = new JLabel("Ersetzen durch:");
		lbl2.setBounds(5, 35, 90, 25);
		this.add(lbl2);
		
		tfErsetze = new JTextField();
		tfErsetze.setBounds(110, 35, 240, 25);
		this.add(tfErsetze);
		
		checkGrossKlein = new JCheckBox("Groß-/Kleinschreibung beachten");
		checkGrossKlein.setBounds(5, 90, 300, 25);
		this.add(checkGrossKlein);
		
		btnWeitersuchen = new JButton("Weitersuchen");
		btnWeitersuchen.setFont(btnWeitersuchen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnWeitersuchen.setBounds(350, 5, 120, 25);
		this.add(btnWeitersuchen);
		
		btnErsetzen = new JButton("Ersetzen");
		btnErsetzen.setFont(btnErsetzen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnErsetzen.setBounds(350, 35, 120, 25);
		this.add(btnErsetzen);
		
		btnAlleErsetzen = new JButton("Alle Ersetzen");
		btnAlleErsetzen.setFont(btnAlleErsetzen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnAlleErsetzen.setBounds(350, 65, 120, 25);
		this.add(btnAlleErsetzen);
		
		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setFont(btnAbbrechen.getFont().deriveFont(Font.PLAIN, 11.0f));
		btnAbbrechen.setBounds(350, 95, 120, 25);
		this.add(btnAbbrechen);
		
		
	}
	
	
	private void initDialog()
	{
		this.setModal(true);
		
		this.setLocationRelativeTo(owner);
		
		
		if (textArea.getSelectedText() != null)
		{
			tfSuche.setText(textArea.getSelectedText());
		}
		
		
		tfSuche.selectAll();
		
		// Wichtig!
		// Durch den Fokusverlust ist der markierte Text im Hauptfenster
		// nicht mehr sichtbar (gilt nur für modalen Dialog).
		// Durch das erneute Setzen des Eingabefokus wird die Markierung wieder
		// angezeigt.
		textArea.requestFocusInWindow();
		
		// Beispiel für die Selektion eines Textes
//		textArea.setSelectionStart(12);
//		textArea.setSelectionEnd(20);
		
		
		textArea.select(12, 20);
		
		
		
	}
	
	
	public void showDialog()
	{
		initDialog();
		this.setVisible(true);
	}
	
	public void showDialog(Component owner)
	{
		this.owner = owner;
		showDialog();
	}
	
	
}
