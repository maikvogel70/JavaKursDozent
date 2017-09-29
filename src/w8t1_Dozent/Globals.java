package w8t1_Dozent;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Globals
{

	static private String appConfigPath;
	static private Hashtable<String, String> htLAF = new Hashtable<String, String>();

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

	public static long getNextKey()
	{
		long retValue = -1;

		String SQL = "SELECT MAX(PRIMARYKEY) FROM POSTLEITZAHLEN";
		Object obj = DBConnection.executeScalar(SQL);
		
		// Obwohl das Feld PRIMARYKEY liefert die Aggregatfunktion MAX(PRIMARYKEY)
		// einen Integer-Wert zurück. Deswegen muss auch hier eine Konvertierung
		// über die Unterklasse 'Number' vorgenommen werden.
		if (obj != null)
			retValue = ((Number)obj).longValue();

		return ++retValue;

	}

	public static String quote(String value)
	{
		return "'" + value.replaceAll("'", "''") + "'";
	}

	public static boolean istPLZOrtVorhanden(String PLZ, String Ort)
	{
		boolean retValue = false;

		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN";
		SQL += " WHERE PLZ = " + Globals.quote(PLZ);
		SQL += " AND ORT = " + Globals.quote(Ort);

		Object obj = DBConnection.executeScalar(SQL);

		// Die Aggregatfunktion COUNT() gibt für eine MySQL Datenbank einen Long-Wert zurück,
		// MS Access nur einen Integer-Wert.
		// Ein Objekt welches einen Integer-Wert enthält kann nicht direkt in einen Long-Wert umgewandelt werden.
				
		// 1. Variante
		// Abfrage nach der Instanz und dann entsprechende Umwandlung
//		if (obj instanceof Integer)
//			retValue = (int)obj > 0;
//		else if (obj instanceof Long) 
//			retValue = (long)obj > 0;
//			
//		return retValue;
		
		// 2. Variante
		// Number ist eine Unterklasse aller Zahlenwerte und bietet Methoden zum expliziten Konvertieren in byte, double, floeat, int, long und short.
		return ((Number)obj).longValue() > 0;
		
		
	}

	public static int getLookAndFeels()
	{
		LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();

		for (LookAndFeelInfo laf : lafInfo)
			htLAF.put(laf.getName(), laf.getClassName());

		return htLAF.size();
	}

	public static Hashtable<String, String> getLAFTable()
	{
		return htLAF;
	}

	public static void setLookAndFeel(String lafKey, Component component)
	{

		if (!htLAF.containsKey(lafKey))
			return;

		String lafName = htLAF.get(lafKey);

		try
		{
			UIManager.setLookAndFeel(lafName);
			SwingUtilities.updateComponentTreeUI(component);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getSystemLookAndFeelName()
	{

		return UIManager.getLookAndFeel().getName();

	}

}
