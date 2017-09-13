package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PostleitzahlenDAO
{

	public static boolean insertPLZOrt(long primaryKey, String PLZ, String Ort)
	{
			
		String SQL = "INSERT INTO POSTLEITZAHLEN ";
		SQL += "(PRIMARYKEY, PLZ, ORT) ";
		SQL += "VALUES (";
		SQL += Long.toString(primaryKey) + ", ";
		SQL += DBConnection.dbString(PLZ) + ", ";
		SQL += DBConnection.dbString(Ort) + ")";
		
		return DBConnection.executeNonQuery(SQL) > 0;
		
	}
	
	
	public static long getNextKey()
	{
		long retValue = 0;
		
		String SQL = "SELECT MAX(PRIMARYKEY) FROM POSTLEITZAHLEN";
		
		Object obj = DBConnection.executeScalar(SQL);
		if (obj != null)
			retValue = (long)obj;
		
		
		return ++retValue;
	}
	
	
	public static boolean istPLZOrtVorhanden(String PLZ, String Ort)
	{
		
		boolean retValue = false;
		
		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN ";
		SQL += "WHERE PLZ = " + DBConnection.dbString(PLZ);
		SQL += " AND ORT = " + DBConnection.dbString(Ort);
		
		Object obj = DBConnection.executeScalar(SQL);
		
		if (obj != null)
			retValue = ((Number)obj).longValue() > 0;
			
		return retValue;
	}
	
	public static long getRecordCount()
	{
		
		long retValue = 0;
		
		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN";
		
		Object obj = DBConnection.executeScalar(SQL);
		
		if (obj != null)
			retValue = ((Number)obj).longValue();
			
		return retValue;
	}
	
	
	public static void deleteTable()
	{
		
		String SQL = "TRUNCATE TABLE POSTLEITZAHLEN";
		DBConnection.executeNonQuery(SQL);
		
	}
	
	
	public static List<Postleitzahl> getPostleitzahlen()
	{
		
		List<Postleitzahl> list  = new ArrayList<>();
		
		
		String SQL = "SELECT PRIMARYKEY, PLZ, ORT, TIMESTAMP FROM POSTLEITZAHLEN ORDER BY PLZ, ORT";
		
		ResultSet rSet = DBConnection.executeQuery(SQL);
		
		if (rSet == null)
			return list;
		
		
		try
		{
			while(rSet.next())
			{
				
				Postleitzahl plz = new Postleitzahl();
				plz.setPrimaryKey(rSet.getLong("PRIMARYKEY"));
				plz.setPLZ(rSet.getString("PLZ"));
				plz.setOrt(rSet.getString("ORT"));
				plz.setTimeStamp(rSet.getString("TIMESTAMP"));
				list.add(plz);

			}
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Lesen der Postleitzahlentabelle: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return list;
	}
	
	
	
}
