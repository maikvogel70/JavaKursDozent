package w3t5_Dozent.Schnittstellen;

import java.util.Arrays;

// Schnittstelle Comparable: Damit k�nnen sich die Objekte selbst mit anderen Objekten vergleichen. 
// Sollen Objekte verglichen werden, muss es ein Vergleichskriterium f�r diese Objekte geben: Name, Gr�sse, Menge, Gehalt...

// Die Schnittstelle Comparable kommt aus dem java.lang-Paket und deklariert eine Methode:compareTo().
// Die Methode 'sort' der Klasse 'Arrays' verspricht, ein Array von Objekten zu sortieren. Allerdings
// unter der Bedingung dass die Objekte zu Klassen geh�ren, die die Schnittstelle 'Comparable' implementieren.

public class Mitarbeiter extends AbstractMitarbeiter implements IPrintable, IPrintSetup,
                                 Comparable<Mitarbeiter> 
{
	
	
	/*
	 * Mit Enumerations (Enums) wurde in Java 1.5 ein lang vermisstes Element
	 * der Programmiersprache nachgereicht, welches in anderen Sprachen schon
	 * lange vorhanden ist. Durch Enums ist es m�glich auf einfachste und
	 * sichere Art und Weise Aufz�hlungen zu realisieren, die fr�her �ber
	 * statische Konstante implementiert werden mussten.
	 */
	
	// Mit dem Schl�sselwort enum k�nnen Aufz�hlungen eingeleitet werden.
	// Intern verf�gen enum-Konstanten �ber eine Ganzzahl als
	// Identifizierer.
	
	public static enum SortOrder
	{
		ASCENDING, DESCENDING
	}
	
	public static enum SortField
	{
		NAME, GEHALT
	}
	
	
	private static SortOrder sortOrder = SortOrder.ASCENDING;
	private static SortField sortField = SortField.NAME;
	
	private double gehalt;
	
	public Mitarbeiter(String name, double gehalt)
	{
		
		super(name);
		
		this.gehalt = gehalt;
	}


	@Override
	public double getGehalt()
	{
		return gehalt;
	}


	@Override
	public void print()
	{
		
		System.out.println("Druckerauswahl-Dialog anzeigen.");
		
	}


	@Override
	public void immediatePrint()
	{
		
		System.out.println("Sofortige Druckausgabe auf dem Standarddrucker.");
		
	}


	@Override
	public void preview()
	{
		
		System.out.println("Seitenansicht anzeigen.");
		
	}


	@Override
	public void printerSetup()
	{
		System.out.println("Dialog f�r Druckereinstellungen anzeigen.");
		
	}


	@Override
	public void pageSetup()
	{
		System.out.println("Dialog f�r Seiteneinstellungen anzeigen.");
		
	}


	@Override
	public void printTest()
	{
		System.out.println("Testdruck.");
		
	}


	@Override
	public boolean pause()
	{
		return IPrintable.super.pause();
	}


	@Override
	public String toString()
	{
		return getName();
	}

	// compareTo() gibt einen int-Wert zur�ck der signalisiert, ob das
	// Argument kleiner oder gr��er als das Mitarbeiter-Objekt
	// ist bzw. mit diesem �bereinstimmt.
	
    // R�ckgabewerte: > 0 = Der eigene Wert ist gr��er als der Argumentwert, 
    //                < 0 = der eigene Wert ist kleiner als der Argumentwert, 
    //				    0 = die Werte stimmen �berein.
	
	@Override
	public int compareTo(Mitarbeiter that)
	{
		
		int retValue = 0;
		
		// Einfache aufsteigende Sortierung nach Name des Mitarbeiters
		//return this.getName().compareTo(that.getName());
		
		// Einfach absteigende Sortierung nach Name.
		//return this.getName().compareTo(that.getName()) * -1;
		//return that.getName().compareTo(this.getName());
		//return -this.getName().compareTo(that.getName());
	
		
		int mult = 1;
		
		if (sortOrder == SortOrder.DESCENDING)
			mult = -1;
		
		
		switch(sortField)
		{
			case NAME:
				retValue = this.getName().compareTo(that.getName());
				break;
				
			case GEHALT:
				if (this.getGehalt() < that.getGehalt())
					retValue = -1;
				else if (this.getGehalt() > that.getGehalt())
					retValue = 1;
				else
					retValue = 0;
				
				break;
		
		}
		
		
		
		return retValue * mult;
		
		
	}


	// Wenn equals() nicht sinnvoll �berschrieben wird verh�lt es sich wie ==
	// also werden Referenzen verglichen und keine Werte.
	
	@Override
	public boolean equals(Object that)
	{
		
		boolean retValue = false;
		
		// Wenn auf das dasselbe Objekt verwiesen wird.
		if (this == that)
		{
			return true;
		}
		// Wenn der Argumenttyp g�ltig ist (kein Vergleich von �pfeln mit Birnen)
		else if (!(that instanceof Mitarbeiter))
		{
			return false;
		}
		
		
		switch (sortField)
		{
		case NAME:
			retValue = 	this.getName().equals(((Mitarbeiter)that).getName());
			break;
			
		case GEHALT:
			retValue = this.getGehalt() == ((Mitarbeiter)that).getGehalt();	
			break;
			
		}
		

		return retValue;
	}


	// Eine Klasse die 'equals() �berschreibt muss auch die Methode
	// hashCode() �berschreiben.
	// Das �berschreiben der Methode 'equals()' bedeuted, dass ein bestimmtes
	// Kriterium f�r den Vergleich des Objekts verwendet wird.
	// Dieses Kriterium sollte auch f�r Methode 'hashCode()' verwendet werden.

	// Die Methode hashCode() soll zu jedem Objekt eine m�glichst eindeutige
	// Integerzahl (sowohl positiv als auch negativ)
	// liefern, die das Objekt identifiziert. Die Ganzzahl hei�t Hashcode
	// beziehungsweise Hash-Wert, und hashCode() ist die
	// Implementierung einer Hash-Funktion. N�tig sind Hashcodes, wenn die
	// Objekte in speziellen Datenstrukturen untergebracht werden,
	// die nach dem Hashing-Verfahren arbeiten (Assoziativspeicher HashMap,
	// TreeMap).
	// Datenstrukturen mit Hashing-Algorithmen bieten effizienten Zugriff auf
	// ihre Elemente.
	
	@Override
	public int hashCode()
	{
		
		int retValue = 0;
		
		switch(sortField)
		{
		case NAME:
			if (this.getName() == null)
				retValue =  0;
			else
				retValue = this.getName().hashCode();
			break;
		
		case GEHALT:
			retValue = Double.hashCode(this.getGehalt());
			break;

		
		}
		
		return retValue;
		
		
		// Beispiel f�r das Errechnen eines HashCodes
		// final int prime = 31;
		// long result = 1;
		//
		// result = prime * result + Wert
		// result = prime * result + Wert
		// result = prime * result + wert;
		// result = prime * result + wert;
		// result = prime * result + wert;
		// result = prime * result + wert;
		// return Long.hashCode(result);
		
		
	}
	
	public static void sort(Mitarbeiter[] a, SortField sortField, SortOrder sortOrder)
	{
		
		Mitarbeiter.sortOrder = sortOrder;
		Mitarbeiter.sortField = sortField;
		
		Arrays.sort(a);
		
	}

	
	


	
	
}
