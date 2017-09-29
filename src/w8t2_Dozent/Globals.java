package w8t2_Dozent;

import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Globals
{

	static private String appConfigPath;


	// Top-Level-Klassen können nicht statisch sein!
	//
	// Privater Standardkonstruktor.
	// Alle Methoden dieser Klasse sind statisch. Durch die Deklaration eines
	// eigenen
	// Standardkonstruktors wird verhindert, dass Java einen Standardkonstruktor
	// erstellt.
	// Die Änderung des Zugriffsmodifizierers von 'public' in 'private'
	// verhindert, dass
	// eine Instanz dieser Klasse erstellt werden kann.

	private Globals()
	{
		throw new AssertionError();
	}

	static public void setApplicationConfigPath(String Filename)
	{
		appConfigPath = Filename;
	}

	static public String getApplicationConfigPath()
	{
		return appConfigPath;
	}

	public static void centerOnDesktop(Window w)
	{
		w.setLocationRelativeTo(null);
	}

	public static void centerOnParent(Window c, Window parent)
	{
		c.setLocationRelativeTo(parent);
	}

	public static long getNextKey()
	{
		long retValue = -1;

		String SQL = "SELECT MAX(PRIMARYKEY) FROM POSTLEITZAHLEN";
		Object obj = DBConnection.executeScalar(SQL);
		if (obj != null)
			retValue = Long.parseLong(obj.toString());

		return ++retValue;

	}

	public static String quote(String value)
	{
		return "'" + value.replaceAll("'", "''") + "'";
	}

	public static boolean istPLZOrtVorhanden(String PLZ, String Ort)
	{
		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN";
		SQL += " WHERE PLZ = " + Globals.quote(PLZ);
		SQL += " AND ORT = " + Globals.quote(Ort);

		Object obj = DBConnection.executeScalar(SQL);

		return Long.parseLong(obj.toString()) > 0;

	}

}
