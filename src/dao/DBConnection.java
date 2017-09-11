package dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection
{
	
	private static Connection dbConn;
	private static String connectionString;
	

	/**
	 * Stellt eine Verbindung zur Datenbank her. Diese Methode registriert den
	 * Treiber beim JDBC-Treibermanager und wird für Treiber verwendet, die dies
	 * nicht automatisch tun.
	 * 
	 * @param classForName
	 *            Name des zu registrierenden Treibers.
	 * @param connectionString
	 *            Verbindungszeichenfolge.
	 * @param userID
	 *            Benutzername
	 * @param passWord
	 *            Kennwort
	 * @return
	 */
	public static boolean connectToDatabase(String classForName, String connectionString, String userID, String passWord)
	{
		boolean retValue = false;
		
		try
		{
			
			// Dynamisches Laden und registrieren einer Klasse beim JDBC-Treibermanager 
			// (statische Initialisierung).
			Class.forName(classForName).newInstance();
			
			dbConn = DriverManager.getConnection(connectionString, userID, passWord);
			
			DBConnection.connectionString = connectionString;
			
			retValue = true;
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			dbConn = null;
			DBConnection.connectionString = null;
		}
		
		
		return retValue;
		
	}
	
	
	public static void closeConnection()
	{
		
		if (dbConn == null)
			return;
		
		try
		{
			dbConn.close();
		}
		catch (Exception ex)
		{
			
		}
		
		dbConn = null;
		connectionString = null;
		
	}
	
	
	
	
	
}
