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
	
	
	
}
