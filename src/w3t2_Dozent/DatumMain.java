package w3t2_Dozent;

public class DatumMain
{

	public static void main(String[] args)
	{
		
		Datum d1 = new Datum();
		System.out.println(d1.toDateString());
		System.out.println(d1.toLongDateString());
		System.out.println();
		
		
		Datum d2 = new Datum(29, 2, 2016);
		System.out.println(d2.toDateString());
		System.out.println(d2.toLongDateString());
		System.out.println();
		
		d1.setDatum(29,  2, 2017);
		System.out.println(d1.toDateString());
		System.out.println(d1.toLongDateString());
		System.out.println();
		
		System.out.println("Verwenden des Copy-Konstruktors");
		
		Datum d3 = new Datum(d2);
		System.out.println(d2.toDateString());
		System.out.println(d3.toDateString());
		System.out.println();
		
		d3.setDatum(22, 8, 2017);
		System.out.println(d2.toDateString());
		System.out.println(d3.toDateString());
		System.out.println();
		
		
		
		
		
		if (Datum.istSchaltjahr(2012))
			System.out.println("Das Jahr 2012 ist ein Schaltjahr");
		else
			System.out.println("Das Jahr 2012 ist kein Schaltjahr");
		
		System.out.println();
		
		// Test auf Schaltjahre
		
		for (int i = 1900; i <= 2100; i++)
		{
			System.out.print("Jahr " + i);
			if (Datum.istSchaltjahr(i))
				System.out.print(" (Schaltjahr)\n");
			else
				System.out.println();
			
			
		}
		
		System.out.println();
		
		System.out.println(d3.toString());
		
		// Die Methode println() ruft bei Objektvariablen immer die Methode toSTring() auf.
		System.out.println(d3);
		
		
		
		
		

	}

}
