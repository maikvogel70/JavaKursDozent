package w8t1_Dozent;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXBusyLabel;

public class PLZForm extends JDialog implements ActionListener, KeyListener, FocusListener, WindowListener, DocumentListener
{

	private JLabel	     jlabel1, jlabel2;
	private JTextField	tfPLZ, tfOrt;
	private JButton	  btnOK, btnAbbrechen;
	
	private long mKey = -1;
	private boolean hasChanged; 
	private Component owner;
	
	private JXBusyLabel busyLabel;
	
	
	public PLZForm()
	{
		initializeComponents();
	}

	public PLZForm(long Key)
	{
		this();
		this.mKey = Key;
		
	}
	
	private void initializeComponents()
	{
		this.setTitle("Eintrag");
		this.setLayout(null);
		this.setModal(true);
		this.setSize(480, 160);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);

		jlabel1 = new JLabel("Postleitzahl");
		jlabel1.setBounds(10, 10, 100, 25);
		this.add(jlabel1);

		tfPLZ = new JTextField();
		tfPLZ.setBounds(10, 35, 80, 25);
		tfPLZ.addKeyListener(this);
		tfPLZ.addFocusListener(this);
		// Wird nur benötigt, um Änderungen im Textfeld zu überwachen
		tfPLZ.getDocument().addDocumentListener(this);
		this.add(tfPLZ);

		jlabel2 = new JLabel("Ort");
		jlabel2.setBounds(100, 10, 100, 25);
		this.add(jlabel2);

		tfOrt = new JTextField();
		tfOrt.setBounds(100, 35, 350, 25);
		tfOrt.addKeyListener(this);
		tfOrt.addFocusListener(this);
		// Wird nur benötigt, um Änderungen im Textfeld zu überwachen
		tfOrt.getDocument().addDocumentListener(this);
		this.add(tfOrt);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(100, 80, 120, 25);
		btnOK.addActionListener(this);
		this.add(btnOK);

		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setBounds(240, 80, 120, 25);
		btnAbbrechen.addActionListener(this);

		this.add(btnAbbrechen);
			
		
	}
	
	public void showDialog()
	{
		initDialog();
		this.setLocationRelativeTo(owner);
		this.setVisible(true);
	}
	
	public void showDialog(Component owner)
	{
		this.owner = owner;
		showDialog();
	}
	
	public long getPrimaryKey()
	{
		return this.mKey;
	}
	
	private void initDialog()
	{
		if (mKey > -1) readEntry(mKey);
	}
	
	private void readEntry(long key)
	{
		String SQL = "SELECT PLZ, ORT FROM POSTLEITZAHLEN";
		SQL += " WHERE PRIMARYKEY = " + Long.toString(key);
		
		ResultSet rSet = DBConnection.executeQuery(SQL);
		if (rSet == null) return;
		
		try
		{
			if (rSet.next())
			{
				tfPLZ.setText(rSet.getString("PLZ"));
				tfOrt.setText(rSet.getString("ORT"));
				rSet.close();
			}
		}
		catch (Exception ex) {}
	}
	
	
	private boolean eingabeOK()
	{
		if (tfPLZ.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Eingabe ungültig oder fehlt", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfPLZ.requestFocus();
			return false;
		}
		
		if (tfPLZ.getText().length() != 5)
		{
			JOptionPane.showMessageDialog(this, "Eingabe ungültig", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfPLZ.requestFocus();
			return false;
		}
		
		if (tfOrt.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Eingabe ungültig oder fehlt", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfOrt.requestFocus();
			return false;
		}
		
		return true;
		
	}
	
	
	private boolean saveEntry()
	{
		boolean retValue = false;
		
		if (!eingabeOK()) return false;
		
		if (Globals.istPLZOrtVorhanden(tfPLZ.getText(), tfOrt.getText().replaceAll("'", "''")))
		{
			JOptionPane.showMessageDialog(this, "Ein Eintrag mit dieser Postleitzahl und diesem Ort ist bereits vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	
		if (mKey > -1)
			retValue = updateEntry();
		else
			retValue = insertEntry();
				
		hasChanged = !retValue;
		
		return retValue;
		
	}
	
	private boolean updateEntry()
	{
		String SQL = "UPDATE POSTLEITZAHLEN";
		SQL += " SET PLZ = " + Globals.quote(tfPLZ.getText()) + ", ";
		SQL += " ORT = " + Globals.quote(tfOrt.getText().replaceAll("'", "''"));
		SQL += " WHERE PRIMARYKEY = " + Long.toString(mKey);
		
		return  DBConnection.executeNonQuery(SQL) > 0;		
		
	}
	
	private boolean insertEntry()
	{
		
		this.mKey = Globals.getNextKey();
		
		String SQL = "INSERT INTO POSTLEITZAHLEN";
		SQL += " (PRIMARYKEY, PLZ, ORT)";
		SQL += " VALUES(";
		SQL +=  Long.toString(this.mKey) + ", ";
		SQL +=  Globals.quote(tfPLZ.getText()) + ", ";
		SQL +=   Globals.quote(tfOrt.getText().replaceAll("'", "''")) + ")";
		
		return  DBConnection.executeNonQuery(SQL) > 0;		
	}
	
	
	// Gibt 'true' zurück, wenn der Diaoog geschlossen werden soll.
	private boolean queryExit()
	{
			
		// Benutzerdefinierten Button Text
		Object[] options = {"Ja", "Nein", "Abbrechen"};
		
		if (!hasChanged) return true;
		
		int retValue = JOptionPane.showOptionDialog(this, "Daten wurden geändert.\nÄnderungen speichern", "Frage", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[2]);
		
		// Abbrechen ?
		if (retValue == JOptionPane.CANCEL_OPTION) return false;
		
		// Ja - Speichern ?
		if (retValue == JOptionPane.YES_OPTION)
			return saveEntry();
		
		// Nein - Nicht speichern
		return true;
				
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
			this.dispose();
		else
			tfPLZ.requestFocus();
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
		if (e.getSource().equals(tfPLZ))
			tfPLZ.selectAll();
				
	}

	@Override
	public void focusLost(FocusEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if (e.getSource().equals(tfPLZ) && tfPLZ.getText().length() != 5)
				return;
			if (e.getSource().equals(tfOrt) && tfOrt.getText().length() == 0)
				return;
		
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}	
		
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

		// Steuertasten ignorieren
		if (Character.isISOControl(e.getKeyChar()))
			return;
				
		if (e.getSource().equals(tfPLZ))
		{
					// Zuerst die markierten Zeichen löschen, um das
					// gleiche Verhalten wie bei der TextBox von Windows zu erreichen.	
					((JTextField)e.getSource()).replaceSelection("");
					
					if (((JTextField)e.getSource()).getText().length() >= 5)
					{
							Toolkit.getDefaultToolkit().beep();
							e.consume();
							return;
					}
				
				if (!Character.isDigit(e.getKeyChar()))
				{
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}

		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(btnAbbrechen))
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if  (e.getSource().equals(btnOK))
		{
			if (hasChanged && saveEntry())
					windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));			
		}
	}

	@Override
   public void changedUpdate(DocumentEvent arg0)
   {
	   // TODO Automatisch generierter Methodenstub
	   
   }

	@Override
   public void insertUpdate(DocumentEvent arg0)
   {
		hasChanged = true;
	   
   }

	@Override
   public void removeUpdate(DocumentEvent arg0)
   {
		hasChanged = true;
	   
   }

}
