package w3t3_Dozent;

public class Datum
{

	private int tag, monat, jahr;

	private static int DEFAULT_TAG = 1;
	private static int DEFAULT_MONAT = 1;
	private static int DEFAULT_JAHR = 2000;

	private static int[] maxMonatstage = new int[]
	{ 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static String[] arrMonatsname = new String[]
	{ "", "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" };

	private static String[] arrWochentag = new String[] { "Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag" };
	
	public Datum()
	{
		tag = DEFAULT_TAG;
		monat = DEFAULT_MONAT;
		jahr = DEFAULT_JAHR;

	}

	public Datum(int tag, int monat, int jahr)
	{
		// Aufruf des Standardkonstruktors
		this();

		setDatum(tag, monat, jahr);

	}

	// Copy-Kontruktor
	public Datum(Datum datum)
	{

		tag = datum.getTag();
		monat = datum.getMonat();
		jahr = datum.getJahr();

	}

	public boolean setDatum(int tag, int monat, int jahr)
	{
		boolean retValue = false;

		if (checkDatum(tag, monat, jahr))
		{
			this.tag = tag;
			this.monat = monat;
			this.jahr = jahr;

			retValue = true;
		}

		return retValue;
	}

	private boolean checkDatum(int tag, int monat, int jahr)
	{

		return checkJahr(jahr) && checkMonat(monat) && checkTag(tag, monat, jahr);

	}

	private boolean checkJahr(int jahr)
	{

		return (jahr > 1582);
	}

	private boolean checkMonat(int monat)
	{

		return (monat > 0 && monat <= 12);
	}

	private boolean checkTag(int tag, int monat, int jahr)
	{

		return (tag > 0 && tag <= maxMonatstage(monat, jahr));

	}

	private int maxMonatstage(int monat, int jahr)
	{

		int retValue = maxMonatstage[monat];

		if (monat == 2)
			retValue += (istSchaltjahr(jahr) ? 1 : 0);

		return retValue;

	}

	public static boolean istSchaltjahr(int jahr)
	{
		boolean retValue = false;

		if ((jahr % 4 == 0 && jahr % 100 != 0) || (jahr % 400 == 0))
			retValue = true;

		return retValue;
	}

	public int getTag()
	{
		return tag;
	}

	public boolean setTag(int value)
	{
		return setDatum(value, monat, jahr);

	}

	public int getMonat()
	{
		return monat;
	}

	public boolean setMonat(int value)
	{
		return setDatum(tag, value, jahr);
	}

	public int getJahr()
	{
		return jahr;
	}

	public boolean setJahr(int value)
	{
		return setDatum(tag, monat, value);
	}

	public String toLongString()
	{
		return String.format("%s, %02d. %s %04d", getWochentag(tag,  monat, jahr), tag, arrMonatsname[monat], jahr);
	}

	@Override
	public String toString()
	{
		return String.format("%02d.%02d.%04d", tag, monat, jahr);
	}

	public boolean addiereJahre(int anzahl)
	{

		boolean retValue = true;

		// Auf ein gültiges Jahr innerhalb des Gregorianischen Kalenders prüfen
		if (checkJahr(jahr + anzahl))
		{
			jahr += anzahl;

			if (!checkDatum(tag, monat, jahr))
				setDatum(maxMonatstage(monat, jahr), monat, jahr);

		}
		else
			retValue = false;

		return retValue;

	}

	public boolean addiereMonate(int anzahl)
	{

		boolean retValue = true;

		// Aktuelle Werte des Datums sichern
		int aktTag = tag;
		int aktMonat = monat;
		int aktJahr = jahr;

		// Prüfen ob die Azahl der Monate >= 1 Jahr ist
		int jahre = anzahl / 12;
		int monate = anzahl % 12;

		if (!addiereJahre(jahre))
		{
			setDatum(aktTag, aktMonat, aktJahr);
			return false;
		}

		// Restliche Monate addieren bzw. subtrahieren
		monat += monate;

		if (monat > 12)
		{
			jahr++;
			monat = Math.abs(12 - monat);
		}
		else if (monat < 1)
		{
			jahr--;
			monat = 12 + monat;
		}

		if (!checkJahr(jahr))
		{

			setDatum(aktTag, aktMonat, aktJahr);
			retValue = false;
		}
		else if (!checkDatum(tag, monat, jahr))
		{
			setDatum(maxMonatstage(monat, jahr), monat, jahr);

		}

		return retValue;
	}

	public boolean addiereTage(int anzahl)
	{

		boolean retValue = true;

		// Aktuelle Werte des Datums sichern
		int aktTag = tag;
		int aktMonat = monat;
		int aktJahr = jahr;

		while (anzahl > 0)
		{

			if (tag + anzahl > maxMonatstage(monat, jahr))
			{
				anzahl -= (maxMonatstage(monat, jahr) - tag) + 1;
				addiereMonate(1);
				tag = 1;

			}
			else
			{
				tag += anzahl;
				anzahl = 0;
			}
		}

		while (anzahl < 0)
		{
			if (tag + anzahl < 1)
			{

				anzahl += tag;
				if (!addiereMonate(-1))
				{
					retValue = false;
					break;
				}

				tag = maxMonatstage(monat, jahr);

			}
			else
			{
				tag += anzahl;
				anzahl = 0;
			}

		}

		if (!retValue)
			setDatum(aktTag, aktMonat, aktJahr);

		return retValue;
	}
	
	/*
	 * Gauß hat eine Formel entwickelt, mit der man zu einem Datum bestehend aus
	 * Tag, Monat und Jahr den zugehörigen Wochentag ermitteln kann. Damit die
	 * untenstehende Formel richtige Ergebnisse liefert, muß das Datum zwischen
	 * dem 01.01.1582 und dem 31.12.3000 liegen. Die Berechnung erfolgt mit der
	 * folgenden von Gauß stammenden sogenannten Kalenderformel : Das zu
	 * behandelnde Datum sei in den Variablen tag, monat, jahr abgelegt.
	 *
	 * Damit werden zwei Hilfsgrößen h und k wie folgt berechnet :
	 * 
	 * Ist der Wert von monat <= 2, so ist h = monat + 12 und k = jahr - 1. Ist
	 * der Wert von monat > 2, so ist h = monat und k = jahr. Der Wochentag
	 * ergibt sich nun durch : wochentag = [ tag + 2*h + (3*h + 3) div 5 + k + k
	 * div 4 - k div 100 + k div 400 +1] mod 7
	 * 
	 * Dadurch erhält die Variable wochentag einen Wert zwischen 0 und 6 . Dabei
	 * bedeutet 0 = Sonntag , 1 = Montag , usw.
	 */
	
	public static int getTagDerWoche(int tag, int monat, int jahr)
	{

		int h = monat;
		int k = jahr;

		if (monat <= 2)
		{
			h = monat + 12;
			k = jahr - 1;
		}

		return (tag + 2 * h + (3 * h + 3) / 5 + k + k / 4 - k / 100 + k / 400 + 1) % 7;

	}
	
	public static String getWochentag(int tag, int monat, int jahr)
	{

		return arrWochentag[getTagDerWoche(tag, monat, jahr)];

	}
	

	// Berechnung des Ostersonntags nach Gauß
	public static Datum getOstersonntag(int jahr)
	{
		Datum Ostersonntag;

		int K, M, S, D, R, A, OG, SZ, OE, OSI;

		// Berechnung der Säkulärzahl
		K = jahr / 100;
		// Säkuläre Mondschaltung
		M = 15 + ((3 * K + 3) / 4) - ((8 * K + 13) / 25);
		// Säkuläre Sonnenschaltung
		S = 2 - ((3 * K + 3) / 4);
		// Mondparameter
		A = jahr % 19;
		// Keim für den ersten Vollmond im Frühling
		D = (19 * A + M) % 30;
		// Kalendarische Korrekturgröße
		R = (D / 29) + ((D / 28) - (D / 29)) * (A / 11);
		// Ostergrenze
		OG = 21 + D - R;
		// Erster Sonntag im März
		SZ = 7 - (jahr + (jahr / 4) + S) % 7;
		// Entfernung des Ostersonntags von der Ostergrenze (Osterentfernung in
		// Tagen)
		OE = 7 - (OG - SZ) % 7;
		// Datum des Ostersonntags als Märzdatum (32. März = 1. April usw.)
		OSI = ((OG + OE) - 1);

		Ostersonntag = new Datum(1, 3, jahr);
		Ostersonntag.addiereTage(OSI);

		return Ostersonntag;

	}
	
	// Für die Berechnung des Buß- und Bettag gilt folgendes:
	// Dieser Tag ist immer der vorletzte Mittwoch vor dem 1. Advent und kann
	// demnach nur
	// in der Zeit vom 16. - 22.11. liegen. Zur Bestimmung des 1. Advent muss
	// der 4. Advent ermittelt werden.
	// Der 4. Advent ist nun der letzte Sonntag vor dem 1. Weihnachtstag
	// (25.12.) und kann demnach nur in der Zeit vom 18. - 24.12 liegen.
	// Um diesen Sonntag zu bestimmen, muss die Tageszahl des 24.12. ermittelt
	// werden.
	// Für die Ermittlung der Tageszahl eines bestimmten Datum gibt es eine
	// Formel, die jedoch nur für die Tage vom 1.1.1901 bis zum 31.12.2099 korrekt ist. Da
	// aber in der Zeit zwischen Ostern und Weihnachten keine Schalttage liegen können,
	// kann man den 4. Advent trotzdem korrekt berechnen.
	// Da der 30.4. auf den gleichen Wochentag fällt wie der 24.12., kann
	// folgende Formel zur Berechnung der Tageszahl herangezogen werden:
	//
	// Liegt der Ostersonntag im März:
	// Tageszahl = (33 - Tag des Ostersonntags) mod 7
	// Liegt der Ostersonntag im April:
	// Tageszahl = (30 - Tag des Ostersonntags) mod 7

	// Jetzt gilt für den Buß- und Bettag:
	// Zwischen Buss und Bettag und dem 4. Advent liegen 32 Tage.
	// Zwischen dem 4. Advent und dem 24.12. liegt die Anzahl Tage, die durch
	// die Tageszahl ausgedrückt wird.
	// Deshalb: Der 24.12. - 32 Tage (ergibt den 22.11.) - Tageszahl = Buß- und
	// Bettag

	public static Datum getBussUndBetTag(int jahr)
	{

		Datum Ostern = getOstersonntag(jahr);
		int Tageszahl = 0;
		Datum BussUndBetTag;

		if (Ostern.getMonat() == 3)
			Tageszahl = (33 - Ostern.getTag()) % 7;
		else if (Ostern.getMonat() == 4)
			Tageszahl = (30 - Ostern.getTag()) % 7;

		BussUndBetTag = new Datum(24, 12, jahr);
		BussUndBetTag.addiereTage(-32 - Tageszahl);

		return BussUndBetTag;

	}

	
	
}
