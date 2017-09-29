package w8t2_Dozent;

import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import util.Crypto;

public class DBConnection
{
	private static Connection dbConn = null;
	private static String connectionString;
	
	public static final String MSSQL_DEFAULTPORT = "1433";
	public static final String MYSQL_DEFAULTPORT = "3306";
	public static final String MSSQL_CLASSFORNAME="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String MYSQL_CLASSFORNAME="org.mariadb.jdbc.Driver";
	public static final String MSACCESS_CLASSFORNAME="sun.jdbc.odbc.JdbcOdbcDriver";
	
	static private DatabaseProvider dbProvider  = DatabaseProvider.UNKNOWN;
	static private Authentication serverAuth  = Authentication.WINDOWS;
	static private String ServerName;
	static private String Database;
	static private String ClassForName;
	static private String DBConnectionPort;
	static private String UserID;
	static private String Password;
	
	public static enum DatabaseProvider
	{
		UNKNOWN, MYSQL, MSSQL, MSACCESS
	}
	
	public static enum Authentication
	{
		WINDOWS, SQLSERVER
	}
	
	 // Top-Level-Klassen können nicht statisch sein!
	 // 
	 // Privater Standardkonstruktor.
	 // Alle Methoden dieser Klasse sind statisch. Durch die Deklaration eines eigenen
	 // Standardkonstruktors wird verhindert, dass Java einen Standardkonstruktor erstellt. 
	 // Die Änderung des Zugriffsmodifizierers von 'public' in 'private' verhindert, dass
	 // eine Instanz dieser Klasse erstellt werden kann.
	
	 private DBConnection() {throw  new AssertionError();}	 

	 static public void setDBProvider(DatabaseProvider dbProvider)
	 {
		 DBConnection.dbProvider = dbProvider;
	 }
	 
	 static public DatabaseProvider getDBProvider()
	 {
		 return dbProvider;
	 }
	 
	 static public void setServer(String ServerName)
	 {
		 DBConnection.ServerName = ServerName;
	 }
	 
	 static public String getServer()
	 {
		 return ServerName;
	 }
	 
	 static public void setDatabase(String Database)
	 {
		 DBConnection.Database = Database;
	 }
	 
	 static public String getDatabase()
	 {
		 return Database;
	 }
	 
	 static public void setClassForName(String ClassForName)
	 {
		 DBConnection.ClassForName = ClassForName;
	 }
	 
	 static public String getClassForName()
	 {
		 return ClassForName;
	 }
	 
	 static public void setUserID(String UserID)
	 {
		 DBConnection.UserID = UserID;
	 }
	 
	 static public String getUserID()
	 {
		 return UserID;
	 }
	 
	 static public void setPassword(String Password)
	 {
		 DBConnection.Password = Password;
	 }
	 
	 static public void setDecryptedPassword(String Password)
	 {
		 try
      {
	      DBConnection.Password = Crypto.decrypt(Password);
      } 
		 catch (GeneralSecurityException e)     {  }
	 }
	 
	 static public String getPassword()
	 {
		 return Password;
	 }
	 
	 static public String getEncryptedPassword()
	 {
		 String Password = "";
		 
		 try
      {
	      Password = Crypto.encrypt(DBConnection.Password);
      } 
		 catch (GeneralSecurityException e)   {  }
		 
		 return Password;
	 }
	 
	 static public void setDBConnectionPort(String DBConnectionPort)
	 {
		 DBConnection.DBConnectionPort = DBConnectionPort;
	 }
	 
	 static public String getDBConnectionPort()
	 {
		 return DBConnectionPort;
	 }
	 
	 static public void setDatabaseProvider(DatabaseProvider dbProvider)
	 {
		 DBConnection.dbProvider = dbProvider;
	 }
	 
	 static public DatabaseProvider getDatabaseProvider()
	 {
		 return dbProvider;
	 }
	 
	public static boolean connectToDatabase(String classForName, String connectionString, String userID, String passWord)
	{
		boolean retValue = false;
		
		try
		{
			Class.forName(classForName).newInstance();
			
			// Für den Zugriff auf statische Variablen (Methoden) muss der Name der Klasse verwendet werden
			// und nicht die Instanz.
			DBConnection.dbConn = DriverManager.getConnection(connectionString, userID, passWord);
			DBConnection.connectionString = connectionString;
			
			retValue = true;
		}
		catch (Exception ex)
		{
			DBConnection.dbConn = null;
			DBConnection.connectionString = null;
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
		}
		
		return retValue;
	}
	
	public static Connection getConnection()
	{
		return dbConn;
	}
	
	public static void setConnectionString(String connectionString)
	{
		DBConnection.connectionString = connectionString;
	}
	
	public static String getConnectionString()
	{
		return connectionString;
	}
	
	public static String createConnectionString(DatabaseProvider dbProvider, String Server, String Database, String UserID, String Password)
	{
		String connectionString = "";
		
		switch (dbProvider)
		{
		case MYSQL:
			connectionString = "jdbc:mysql://" + Server + ":" + MYSQL_DEFAULTPORT + "/";
			connectionString += Database;
			break;
			
		case MSSQL:
			connectionString = "jdbc:sqlserver://" + Server + ";";
			connectionString += "DatabaseName=" + Database + ";";
			if (UserID == null)
				connectionString += "integratedSecurity=true;";
			break;
			
		case MSACCESS:
			connectionString = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
			connectionString += Database + ";";
			break;
		}
		
		return connectionString;
		
	}
	
	public static String createClassForName(DatabaseProvider dbProvider)
	{
		String retValue = "";
		
		switch (dbProvider)
		{
		case MYSQL:
			retValue = DBConnection.MYSQL_CLASSFORNAME;
			break;

		case MSSQL:
			retValue = DBConnection.MSSQL_CLASSFORNAME;
			break;

		case MSACCESS:
			retValue = DBConnection.MSACCESS_CLASSFORNAME;
			break;
		}
		
		return retValue;
	}
	
	
	public static void closeConnection()
	{
		if (DBConnection.dbConn == null) return;
		
		try
		{
			DBConnection.dbConn.close();
		}
		catch(Exception ex) {}
		
		DBConnection.dbConn = null;
		DBConnection.connectionString = null;
	}
	
	public static String getCatalog()
	{
		String retValue = "";
		
		if (DBConnection.dbConn == null) return retValue;
		
		try
		{
			retValue = DBConnection.dbConn.getCatalog();
		}
		catch(Exception ex) {}
		
		return retValue;
		
	}
	
	public static void beginnTransaction()
	{
		try
		{
			dbConn.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
	}

	public static void commitTransaction()
	{
		try
		{
			// Keine TRansaktionsschleife geöffnet
			if (dbConn.getAutoCommit()) return;

			dbConn.commit();
			dbConn.setAutoCommit(true);

		}
		catch (SQLException e)
		{
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

	}

	public static void rollbackTransaction()
	{
		try
		{
			// Keine TRansaktionsschleife geöffnet
			if (dbConn.getAutoCommit()) return;

			dbConn.rollback();
			dbConn.setAutoCommit(true);

		}
		catch (SQLException e)
		{
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
	}
	
	public static int  executeNonQuery(String SQL)
	{
		Statement stmt;
		int retValue = 0;
		
		try
		{
			stmt = dbConn.createStatement();
			retValue = stmt.executeUpdate(SQL);
			stmt.close();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
		}
		
		return retValue;
		
	}
	

	public static Object  executeScalar(String SQL)
	{
		Statement stmt;
		Object retValue = null;
		
		try
		{
			stmt = dbConn.createStatement();
			ResultSet rSet  = stmt.executeQuery(SQL);
			rSet.next();
			retValue = rSet.getObject(1);
			rSet.close();
			stmt.close();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
		}
		
		return retValue;
		
	}
	
	public static ResultSet  executeQuery(String SQL)
	{
		Statement stmt;
		ResultSet rSet = null;
		
		try
		{
			stmt = dbConn.createStatement();
			rSet  = stmt.executeQuery(SQL);
			
			// Statement darf hier nicht geschlossen werden,
			// da sonst der ResultSet ebenfalls nicht mehr verfügbar ist.
			
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler",  JOptionPane.ERROR_MESSAGE);			
		}
		
		return rSet;	
	}
	
}
