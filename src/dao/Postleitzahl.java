package dao;

public class Postleitzahl implements IPostleitzahl
{
	
	private long primaryKey;
	private String plz;
	private String ort;
	private String timeStamp;
	

	@Override
	public long getPrimaryKey()
	{
		return primaryKey;
	}

	@Override
	public void setPrimaryKey(long value)
	{
		primaryKey = value;
		
	}

	@Override
	public String getPLZ()
	{
		return plz;
	}

	@Override
	public void setPLZ(String value)
	{
		plz = value;
		
	}

	@Override
	public String getOrt()
	{
		return ort;
	}

	@Override
	public void setOrt(String value)
	{
		ort = value;
		
	}

	@Override
	public String getTimeStamp()
	{
		return timeStamp;
	}

	@Override
	public void setTimeStamp(String value)
	{
		timeStamp = value;
		
	}

	@Override
	public boolean equals(Object that)
	{
		
		boolean retValue = false;
		
		// Wenn auf das gleiche Objekt verwiesen wird
		if (this == that)
		{
			retValue = true;
		}
		// Wenn der Argumenttyp ungültig (kein Vergleich von Äpfeln mit Birnen)
		else if (that instanceof Postleitzahl)
		{
			retValue = this.getPrimaryKey() == ((Postleitzahl)that).getPrimaryKey();
		}
		
		return retValue;
	}

	@Override
	public int hashCode()
	{
		return Long.hashCode(getPrimaryKey());
	}

	@Override
	public String toString()
	{
		return getPLZ() + " - " + getOrt();
	}
	
	
	
	
	
	

	
}
