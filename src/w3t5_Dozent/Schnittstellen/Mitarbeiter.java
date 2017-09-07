package w3t5_Dozent.Schnittstellen;

import java.util.Arrays;

// Schnittstelle Comparable: Damit können sich die Objekte selbst mit anderen Objekten vergleichen. 
// Sollen Objekte verglichen werden, muss es ein Vergleichskriterium für diese Objekte geben: Name, Grösse, Menge, Gehalt...

// Die Schnittstelle Comparable kommt aus dem java.lang-Paket und deklariert eine Methode:compareTo().
// Die Methode 'sort' der Klasse 'Arrays' verspricht, ein Array von Objekten zu sortieren. Allerdings
// unter der Bedingung dass die Objekte zu Klassen gehören, die die Schnittstelle 'Comparable' implementieren.

public class Mitarbeiter extends AbstractMitarbeiter implements IPrintable, IPrintSetup,
                                 Comparable<Mitarbeiter> 
{
	
	
	/*
	 * Mit Enumerations (Enums) wurde in Java 1.5 ein lang vermisstes Element
	 * der Programmiersprache nachgereicht, welches in anderen Sprachen schon
	 * lange vorhanden ist. Durch Enums ist es möglich auf einfachste und
	 * sichere Art und Weise Aufzählungen zu realisieren, die früher über
	 * statische Konstante implementiert werden mussten.
	 */
	
	// Mit dem Schlüsselwort enum können Aufzählungen eingeleitet werden.
	// Intern verfügen enum-Konstanten über eine Ganzzahl als
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
		System.out.println("Dialog für Druckereinstellungen anzeigen.");
		
	}


	@Override
	public void pageSetup()
	{
		System.out.println("Dialog für Seiteneinstellungen anzeigen.");
		
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

	// compareTo() gibt einen int-Wert zurück der signalisiert, ob das
	// Argument kleiner oder größer als das Mitarbeiter-Objekt
	// ist bzw. mit diesem übereinstimmt.
	
    // Rückgabewerte: > 0 = Der eigene Wert ist größer als der Argumentwert, 
    //                < 0 = der eigene Wert ist kleiner als der Argumentwert, 
    //				    0 = die Werte stimmen überein.
	
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


	// Wenn equals() nicht sinnvoll überschrieben wird verhält es sich wie ==
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
		// Wenn der Argumenttyp gültig ist (kein Vergleich von Äpfeln mit Birnen)
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


	// Eine Klasse die 'equals() überschreibt muss auch die Methode
	// hashCode() überschreiben.
	// Das Überschreiben der Methode 'equals()' bedeuted, dass ein bestimmtes
	// Kriterium für den Vergleich des Objekts verwendet wird.
	// Dieses Kriterium sollte auch für Methode 'hashCode()' verwendet werden.

	// Die Methode hashCode() soll zu jedem Objekt eine möglichst eindeutige
	// Integerzahl (sowohl positiv als auch negativ)
	// liefern, die das Objekt identifiziert. Die Ganzzahl heißt Hashcode
	// beziehungsweise Hash-Wert, und hashCode() ist die
	// Implementierung einer Hash-Funktion. Nötig sind Hashcodes, wenn die
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
		
		
		// Beispiel für das Errechnen eines HashCodes
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
