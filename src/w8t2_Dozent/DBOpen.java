package w8t2_Dozent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.ListItem;

public class DBOpen extends JDialog implements WindowListener, ActionListener
{

	private JComboBox<ListItem>    cboDatenbankTyp, cboAuth;
	private JLabel lblDatenbanktyp, lblServername, lblDatenbank, lblAuth, lblBenutzername, lblKennwort; 
	private JTextField tfServername, tfDatenbank, tfBenutzer;
	private JPasswordField tfKennwort;
	private Vector<ListItem> cboDatenbankTypListModel, cboAuthListModel;
	private JButton btnDBDialog, btnOK, btnAbbrechen;
	
	private DBConnection.DatabaseProvider dbProvider = DBConnection.DatabaseProvider.UNKNOWN; 
	private DBConnection.Authentication serverAuth  = DBConnection.Authentication.WINDOWS;
	
	public DBOpen()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{
		this.setTitle("Datenbank öffnen");
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addWindowListener(this);
		this.setSize(new Dimension(380, 300));
		
		lblDatenbanktyp = new JLabel("Datenbank Typ:");
		lblDatenbanktyp.setBounds(10, 10, 100, 20);
		this.add(lblDatenbanktyp);
		
		cboDatenbankTypListModel = new Vector<ListItem>();
		cboDatenbankTyp = new JComboBox<ListItem>(cboDatenbankTypListModel);
		cboDatenbankTyp.setBounds(150, 10,  180, 20);
		cboDatenbankTyp.setBackground(this.getBackground());
		cboDatenbankTyp.addActionListener(this);
		this.add(cboDatenbankTyp);

		
		lblServername = new JLabel("Servername:");
		lblServername.setBounds(10, 40, 100, 20);
		this.add(lblServername);
		
		tfServername = new JTextField();
		tfServername.setBounds(150, 40, 180, 20);
		this.add(tfServername);
		
		lblDatenbank = new JLabel("Datenbank:");
		lblDatenbank.setBounds(10, 70, 100, 20);
		this.add(lblDatenbank);
		
		tfDatenbank = new JTextField();
		tfDatenbank.setBounds(150, 70, 180, 20);
		this.add(tfDatenbank);
		
		btnDBDialog = new JButton("...");
		btnDBDialog.setBounds(332, 70, 30, 20);
		btnDBDialog.addActionListener(this);
		this.add(btnDBDialog);
		
		lblAuth = new JLabel("Authentifizierung:");
		lblAuth.setBounds(10, 100, 100, 20);
		this.add(lblAuth);
		
		cboAuthListModel = new Vector<ListItem>();
		cboAuth = new JComboBox<ListItem>(cboAuthListModel);
		cboAuth.setBounds(150, 100,  180, 20);
		cboAuth.setBackground(this.getBackground());
		cboAuth.addActionListener(this);
		this.add(cboAuth);
		
		lblBenutzername = new JLabel("Benutzername:");
		lblBenutzername.setBounds(10, 130, 100, 20);
		this.add(lblBenutzername);
		
		tfBenutzer = new JTextField();
		tfBenutzer.setBounds(150, 130, 180, 20);
		this.add(tfBenutzer);
		
		lblKennwort = new JLabel("Kennwort:");
		lblKennwort.setBounds(10, 160, 100, 20);
		this.add(lblKennwort);
		
		tfKennwort = new JPasswordField();
		tfKennwort.setBounds(150, 160, 180, 20);
		this.add(tfKennwort);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(60, 220, 120, 25);
		btnOK.addActionListener(this);
		this.add(btnOK);
		
		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setBounds(200, 220, 120, 25);
		btnAbbrechen.addActionListener(this);
		this.add(btnAbbrechen);
		
	}
	
	public void showDialog()
	{
		initDialog();
		Globals.centerOnDesktop(this);
		
		this.setModal(true);
		this.setVisible(true);
		
	}
	
	private void initDialog()
	{
		populateDatenbankTypComboBox();
		cboDatenbankTyp.setSelectedIndex(0);
		
		populateAuthentifizierungComboBox();
		cboAuth.setSelectedIndex(0);
		
	}
	
	private void populateDatenbankTypComboBox()
	{
		cboDatenbankTypListModel.add(new ListItem(DBConnection.DatabaseProvider.UNKNOWN, "-- Auswählen --"));
		cboDatenbankTypListModel.add(new ListItem(DBConnection.DatabaseProvider.MYSQL, "MySQL"));
		cboDatenbankTypListModel.add(new ListItem(DBConnection.DatabaseProvider.MSSQL, "Microsoft SQL Server"));
		cboDatenbankTypListModel.add(new ListItem(DBConnection.DatabaseProvider.MSACCESS, "Microsoft Access"));
		
	}

	private void populateAuthentifizierungComboBox()
	{
		cboAuthListModel.add(new ListItem(DBConnection.Authentication.WINDOWS, "Windows Authentifizierung"));
		cboAuthListModel.add(new ListItem(DBConnection.Authentication.SQLSERVER, "SQL Server Authentifizierung"));
		
	}

	private boolean connectToDatabase()
	{
		boolean retValue = false;

		String connectionString = "";
		String ClassForName = "";
		String Servername = tfServername.getText();
		String DBConnectionPort = "";
		String Datenbank = tfDatenbank.getText();
		String UserID = null;
		String Password = null;
		
		if (tfBenutzer.getText().length() > 0)
			UserID = tfBenutzer.getText();
		
		// tfKennwort ist ein JPassword.
		// Der Inhalt eines JPassword wird als Array gespeichert:
		// Umwandlung eines Arrays in einen String.
		char[] pw = tfKennwort.getPassword();
		if (pw.length > 0)
		 	Password =String.valueOf(pw);		
		
		if (dbProvider == DBConnection.DatabaseProvider.MSSQL && serverAuth == DBConnection.Authentication.WINDOWS)
		{
			UserID = null;
			Password = null;
		}		
		
		connectionString = DBConnection.createConnectionString(dbProvider, Servername, Datenbank, UserID, Password);
		ClassForName = DBConnection.createClassForName(dbProvider);
		retValue = DBConnection.connectToDatabase(ClassForName, connectionString, UserID != null ? UserID : null, Password != null ? Password : null);

		if (retValue)
		{
			DBConnection.setDatabaseProvider(dbProvider);
			DBConnection.setClassForName(ClassForName);
			DBConnection.setServer(Servername);
			DBConnection.setDatabase(Datenbank);
			DBConnection.setDBConnectionPort(DBConnectionPort);
			DBConnection.setUserID(UserID);
			DBConnection.setPassword(Password);
		}

		return retValue;

	}
	
	private void setDatabaseType()
	{
		ListItem li = (ListItem) cboDatenbankTyp.getSelectedItem();
		dbProvider = (DBConnection.DatabaseProvider) li.getValueMember();
				
		switch (dbProvider)
		{
		case MYSQL:
			tfDatenbank.setEnabled(true);
			btnDBDialog.setEnabled(false);
			tfServername.setEnabled(true);
			cboAuth.setEnabled(false);
			tfServername.requestFocus();
			tfBenutzer.setEnabled(true);
			tfKennwort.setEnabled(true);
			break;
		case MSSQL:
			tfDatenbank.setEnabled(true);
			btnDBDialog.setEnabled(false);
			tfServername.setEnabled(true);
			cboAuth.setEnabled(true);
			tfServername.requestFocus();
			break;
		case MSACCESS:
			tfDatenbank.setEnabled(true);
			btnDBDialog.setEnabled(true);
			cboAuth.setEnabled(false);
			tfServername.setEnabled(false);
			tfDatenbank.requestFocus();
			tfBenutzer.setEnabled(true);
			tfKennwort.setEnabled(true);
			break;
		default:
			tfDatenbank.setEnabled(false);
			btnDBDialog.setEnabled(false);
			tfServername.setEnabled(false);
			cboAuth.setEnabled(false);
			break;
		}
				
	}
	
	private void openDatabaseDialog()
	{
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Datenbank öffnen");
		fc.setFileFilter(new  FileNameExtensionFilter("Microsoft Office Access-Datenbank", "*.accdb", "accdb"));

		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
		
		tfDatenbank.setText(fc.getSelectedFile().toString());
			
	}
	
	private void setDatabaseAuth()
	{

		ListItem li = (ListItem) cboAuth.getSelectedItem();
		serverAuth = (DBConnection.Authentication) li.getValueMember();

		switch (serverAuth)
		{
		case WINDOWS:
			tfBenutzer.setEnabled(false);
			tfKennwort.setEnabled(false);
			break;
		case SQLSERVER:
			tfBenutzer.setEnabled(true);
			tfKennwort.setEnabled(true);
			tfBenutzer.requestFocus();
			break;

		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(btnAbbrechen))
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if  (e.getSource().equals(btnOK))
		{
				if (connectToDatabase())
						windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (e.getSource().equals(cboDatenbankTyp))
			setDatabaseType();
		else if (e.getSource().equals(btnDBDialog))
			openDatabaseDialog();
		else if (e.getSource().equals(cboAuth))
			setDatabaseAuth();

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
	  this.dispose();
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

}
