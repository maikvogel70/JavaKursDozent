package dao;

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
	
	
	
}
