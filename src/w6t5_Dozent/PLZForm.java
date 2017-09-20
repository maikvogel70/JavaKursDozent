package w6t5_Dozent;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.Postleitzahl;
import dao.PostleitzahlenDAO;

public class PLZForm extends JDialog implements ActionListener, KeyListener, DocumentListener, FocusListener, WindowListener
{
	
	private JLabel jlabel1, jlabel2;
	private JTextField tfPLZ, tfOrt;
	private JButton btnOK, btnAbbrechen;
	
	private Component owner;
	private boolean hasChanged;

	private long mKey = -1;
	
	private KeyboardFocusManager kbFocusManager;
	
	
	// TIMESTAMP zur Überprüfung, ob der Datensatz von einem anderen Benutzer
	// geändert wurde.
	private String timeStamp;
	
	public PLZForm(long Key)
	{
		initializeComponents();
		mKey = Key;
	}
	
	private void initializeComponents()
	{
		this.setTitle("Eintrag");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(("/images/Server.png"))));
	
		this.setLayout(null);
		this.setSize(480, 160);
		this.setResizable(false);
		
		// Der WindowListener überwacht das Schließen des Dialogs.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
			
		jlabel1 = new JLabel("Postleitzahl");
		jlabel1.setBounds(10, 10, 100, 25);
		this.add(jlabel1);
		
		tfPLZ = new JTextField();
		tfPLZ.setBounds(10, 35, 80, 25);
		tfPLZ.addKeyListener(this);
		tfPLZ.addFocusListener(this);
		tfPLZ.getDocument().addDocumentListener(this);
		this.add(tfPLZ);
		
		jlabel2 = new JLabel("Ort");
		jlabel2.setBounds(100, 10, 100, 25);
		this.add(jlabel2);
		
		tfOrt = new JTextField();
		tfOrt.setBounds(100, 35, 350, 25);
		tfOrt.addKeyListener(this);
		tfOrt.addFocusListener(this);
		tfOrt.getDocument().addDocumentListener(this);
		this.add(tfOrt);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(100, 80, 120, 25);
		btnOK.setIcon(new ImageIcon(this.getClass().getResource("/images/OK.png")));
		btnOK.addActionListener(this);
		this.add(btnOK);
		
		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setBounds(240, 80, 120, 25);
		btnAbbrechen.setIcon(new ImageIcon(this.getClass().getResource("/images/Abbruch.png")));
		btnAbbrechen.addActionListener(this);
		this.add(btnAbbrechen);
		
	}
	
	private void initDialog()
	{
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		
		kbFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		
		if (mKey > -1)
			readEntry(mKey);
		
		
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
	
	private void readEntry(long Key)
	{
		
		Postleitzahl plz = PostleitzahlenDAO.getPostleitzahl(Key);
		
		if (plz != null)
		{
			
			tfPLZ.setText(plz.getPLZ());
			tfOrt.setText(plz.getOrt());
			
			// TIMESTAMP des Datensatzes sichern
			timeStamp = plz.getTimeStamp();

		}
		else
		{
			JOptionPane.showMessageDialog(this, "Der Datensatz konnte nicht gefunden werden", "Datensatz lesen", JOptionPane.ERROR_MESSAGE);
			mKey = -1;
		}
		
		hasChanged = false;
		
	}
	
	// Gibt 'true' zurück, wenn der Dialog geschlossen werden soll.
	private boolean queryExit()
	{

		// Benutzerdefinierten Button Text
		String[] options = { "Ja", "Nein", "Abbrechen" };

		if (!hasChanged)
			return true;

		int retValue = JOptionPane.showOptionDialog(this, "Daten wurden geändert.\nÄnderungen speichern", "Frage", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[2]);

		// Nein - nicht speichern ?
		if (retValue == JOptionPane.NO_OPTION)
			return true;

		// Abbruch
		if (retValue != JOptionPane.YES_OPTION)
			return false;

		// Ja - Speichern
		return saveEntry();

	}
	
	public long getPrimaryKey()
	{
		return mKey;
	}
	
	
	
	private boolean saveEntry()
	{
	
		boolean retValue = false;
		
		if (!eingabeOK())
		{
			return retValue;
		}


		if (mKey < 0 && PostleitzahlenDAO.istPLZOrtVorhanden(tfPLZ.getText(), tfOrt.getText()))
		{
			
			JOptionPane.showMessageDialog(this, "Ein Eintrag mit dieser Postleitzahl und diesem Ort ist bereits vorhanden", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			return retValue;
		}
		
		
		if (mKey > -1)
		{
			retValue = updateEntry();
		}
		else
		{
			retValue = insertEntry();
		}
		
		
		hasChanged = !retValue;		
		
		return retValue;
		
	}
	
	private boolean updateEntry()
	{
		
		// Vor dem Update prüfen, ob der TIMESTAMP des Datensatzes
		// in der Zwischenzeit durch einen anderen Benutzer geändert wurde.
		
		Postleitzahl plz = PostleitzahlenDAO.getPostleitzahl(mKey);
		if (!timeStamp.equals(plz.getTimeStamp()))
		{
			
			JOptionPane.showMessageDialog(this, "Dieser Eintrag wurde zwischenzeitlich von einem anderen Benutzer geändert.\nIhre Änderung wird verworfen.",
					"Fehler", JOptionPane.WARNING_MESSAGE);
			
			return false;
		}
		
		return PostleitzahlenDAO.updatePLZOrt(mKey, tfPLZ.getText(), tfOrt.getText());
		
	}
	
	private boolean insertEntry()
	{
		
		mKey = PostleitzahlenDAO.getNextKey();
		return PostleitzahlenDAO.insertPLZOrt(mKey, tfPLZ.getText(), tfOrt.getText());
		
	}
	
	
	
	private boolean eingabeOK()
	{
		boolean retValue = false;

		if (tfPLZ.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Eingabe fehlt", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfPLZ.requestFocusInWindow();
		}
		else if (tfPLZ.getText().length() != 5)
		{
			JOptionPane.showMessageDialog(this, "Eingabe ungültig", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfPLZ.requestFocusInWindow();
		}
		else if (tfOrt.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Eingabe fehlt", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfOrt.requestFocusInWindow();
		}
		else if (tfOrt.getText().length() < 2 )
		{
			JOptionPane.showMessageDialog(this, "Eingabe ungültig", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfOrt.requestFocusInWindow();
		}
		else
			retValue = true;

		return retValue;

	}
	
	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// Überprüfen, ob Änderungen noch gespeichert werden müssen
		if (queryExit())
		{
			this.dispose();
		}
		else
		{
			tfPLZ.requestFocusInWindow();
		}
		
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
	public void focusGained(FocusEvent e)
	{
		
		if (e.getSource() == tfPLZ)
			tfPLZ.selectAll();
		
		
	}

	@Override
	public void focusLost(FocusEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changedUpdate(DocumentEvent arg0)
	{
		// TODO Auto-generated method stub
		
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

	@Override
	public void keyPressed(KeyEvent e)
	{
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if (e.getSource() == tfPLZ && tfPLZ.getText().length() == 5)
				kbFocusManager.focusNextComponent();
			else if (e.getSource() == tfOrt && tfOrt.getText().length() >= 2)
				kbFocusManager.focusNextComponent();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
		if (e.getSource() == tfPLZ)
		{
			
			if (Character.isISOControl(e.getKeyChar()))
				return;
			
			if (!Character.isDigit(e.getKeyChar()))
			{
				Toolkit.getDefaultToolkit().beep();
				e.consume();
				return;
			}
			
			// Zuerst die markierten Zeichen löschen.
			tfPLZ.replaceSelection("");

			if (tfPLZ.getText().length() >= 5)
			{
				Toolkit.getDefaultToolkit().beep();
				e.consume();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnAbbrechen)
		{
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (e.getSource() == btnOK)
		{
			
			if (hasChanged && saveEntry())
			{
				windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
			
		}
		
	}

}
