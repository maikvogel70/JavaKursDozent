package w4t1_Dozent;

public class DatumZeit extends Datum 
{

	private int stunde, minute, sekunde;
	
	
	
	public DatumZeit()
	{
		// Automatischer Aufruf des Standardkonstruktors der Superklasse 'Datum'.
		
	}
	
	public DatumZeit(int tag, int monat, int jahr)
	{
		
		super(tag, monat, jahr);
	}
	
	
	public DatumZeit(int tag, int monat, int jahr, int stunde, int minute, int sekunde)
	{
		// Automatischer Aufruf des Standardkonstruktors der Superklasse 'Datum'.
		
		setDatumZeit(tag, monat, jahr, stunde, minute, sekunde);
		
		
	}
	
	public DatumZeit(DatumZeit dz)
	{
		
		// Automatischer Aufruf des Standardkonstruktors der Superklasse 'Datum'.
		
		setTag(dz.getTag());
		setMonat(dz.getMonat());
		setJahr(dz.getJahr());
		
		stunde = dz.getStunde();
		minute = dz.getMinute();
		sekunde = dz.getSekunde();
	
	}
	
	
	
	
	public boolean setDatumZeit(int tag, int monat, int jahr, int stunde, int minute, int sekunde)
	{
		boolean retValue = false;
		
		
		if (checkZeit(stunde, minute, sekunde))
		{
			
			if (setDatum(tag, monat, jahr))
				retValue = setZeit(stunde, minute, sekunde);
			
		}
		
		return retValue;
	}
	
	
	public boolean setZeit(int stunde, int minute, int sekunde)
	{
		boolean retValue = false;
		
		if (checkZeit(stunde, minute, sekunde))
		{
			this.stunde = stunde;
			this.minute = minute;
			this.sekunde = sekunde;
			
			retValue = true;
		}
		
		return retValue;

	}
	
	
	private boolean checkZeit(int stunde, int minute, int sekunde)
	{
		boolean retValue = true;
		
		if (stunde < 0 || stunde > 23)
			retValue = false;
		else if (minute < 0 || minute > 59)
			retValue = false;
		else if (sekunde < 0 || sekunde > 59)
			retValue = false;
		
		return retValue;
	}
	
	
	
	private String toTimeString()
	{
		return String.format("%02d:%02d:%02d Uhr", stunde, minute, sekunde);
	}


	public int getStunde()
	{
		return stunde;
	}

	public boolean setStunde(int value)
	{
		return setZeit(value, minute, sekunde);
		
	}

	public int getMinute()
	{
		return minute;
	}

	public boolean setMinute(int value)
	{
		return setZeit(stunde, value, sekunde);
	}

	public int getSekunde()
	{
		return sekunde;
	}

	public boolean setSekunde(int value)
	{
		return setZeit(stunde, minute, value);
	}

	@Override
	public String toLongString()
	{
		return super.toLongString() + "  " + toTimeString();
	}

	
	@Override
	public String toString()
	{
		return super.toString() + " " + toTimeString();
	}

	
	public DatumZeit addiereStunden(int anzahl)
	{
		
		boolean success = true;
		
		
		// Aktuelle Werte des Datums sichern
		int aktTag = getTag();
		int aktMonat = getMonat();
		int aktJahr = getJahr();
		int aktStunde = stunde;
		int aktMinute = minute;
		int aktSekunde = sekunde;
				
		
		// Umrechnen der übergebenen Anzahl der Stunden in Tage.
		int tage = anzahl / 24;
		int stunden = anzahl % 24;
		
		
		addiereTage(tage);
		if (error())
		{
			setDatumZeit(aktTag, aktMonat, aktJahr, aktStunde, aktMinute, aktSekunde);
			return this;
		}
		
		stunde += stunden;
		
		if (stunde > 23)
		{
			addiereTage(1);
			stunde = Math.abs(24 - stunde);
		}
		else if (stunde < 0)
		{
			
			addiereTage(-1);
			stunde = 24 + stunde;
		}
		
		if (error())
			setDatumZeit(aktTag, aktMonat, aktJahr, aktStunde, aktMinute, aktSekunde);
		
		
		return this;
	
		
	}
	
	
	public DatumZeit addiereMinuten(int anzahl)
	{
		
		
		
		// Aktuelle Werte des Datums sichern
		int aktTag = getTag();
		int aktMonat = getMonat();
		int aktJahr = getJahr();
		int aktStunde = stunde;
		int aktMinute = minute;
		int aktSekunde = sekunde;
		
		// Umrechnen der übergebenen Anzahl der Minuten in Stunden.
		
		int stunden = anzahl / 60;
		int minuten = anzahl % 60;
		
		addiereStunden(stunden);
		if (error())
		{
			setDatumZeit(aktTag, aktMonat, aktJahr, aktStunde, aktMinute, aktSekunde);
			return this;
		}
		
		minute += minuten;
		
		if (minute > 59)
		{
			
			addiereStunden(1);
			minute = Math.abs(60 - minute);
			
		}
		else if (minute < 0)
		{
			
			addiereStunden(-1);
			minute = 60 + minute;

		}
		
		if (error())
			setDatumZeit(aktTag, aktMonat, aktJahr, aktStunde, aktMinute, aktSekunde);
		
		
		return this;
		
	}
	
	public DatumZeit addiereSekunden(int anzahl)
	{
		
			
		// Aktuelle Werte des Datums sichern
		int aktTag = getTag();
		int aktMonat = getMonat();
		int aktJahr = getJahr();
		int aktStunde = stunde;
		int aktMinute = minute;
		int aktSekunde = sekunde;
		
		// Umrechnen der übergebenen Anzahl der Sekunden in Minuten.
		
		int minuten = anzahl / 60;
		int sekunden = anzahl % 60;
		
		addiereMinuten(minuten);
		if (error())
		{
			setDatumZeit(aktTag, aktMonat, aktJahr, aktStunde, aktMinute, aktSekunde);
			return this;
		}
		
		sekunde += sekunden;
		
		if (sekunde > 59)
		{
			
			addiereMinuten(1);
			sekunde = Math.abs(60 - sekunde);
			
		}
		else if (sekunde < 0)
		{
			
			addiereMinuten(-1);
			sekunde = 60 + sekunde;

		}
		
		if (error())
			setDatumZeit(aktTag, aktMonat, aktJahr, aktStunde, aktMinute, aktSekunde);
		
		
		return this;
		
	}

	@Override
	public DatumZeit addiereJahre(int anzahl)
	{
		super.addiereJahre(anzahl);
		return this;
		
	}

	@Override
	public DatumZeit addiereMonate(int anzahl)
	{
		super.addiereMonate(anzahl);
		return this;
	}

	@Override
	public DatumZeit addiereTage(int anzahl)
	{
		super.addiereTage(anzahl);
		return this;
	}

	
	protected int convertTimeToNumber()
	{
		// Erstellt eine Ganzzahl im Format HHMMSS
		return (stunde * 10000) + (minute * 100) + sekunde;
		
	}
	
	@Override
	public int compareTo(Datum that)
	{
		return super.compareTo(that);
	}

	@Override
	public boolean equals(Object that)
	{
		return super.equals(that);
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}
	
	
}
