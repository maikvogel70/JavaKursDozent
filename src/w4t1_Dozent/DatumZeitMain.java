package w4t1_Dozent;

import java.util.Arrays;
import java.util.TreeMap;

public class DatumZeitMain
{

	public static void main(String[] args)
	{
		
		// Sortierung von Datumswerten
		Datum d1 = new Datum(28, 8, 2017);
		Datum d2 = new Datum(1, 1, 2000);
		Datum d3 = new Datum(2, 2, 2000);
		Datum d4 = new Datum(1, 1, 1583);
		Datum d5 = new Datum(31, 12, 2017);
		
		
		Datum[] arrDatum = new Datum[] {d1, d2, d3, d4, d5};
		
		Arrays.sort(arrDatum);
		
		System.out.println("Aufsteigende Sortierung nach Datum");
		System.out.println(Arrays.toString(arrDatum));
		System.out.println();
		
		
		// Sortierung von DatumZeit-Werten
		
		DatumZeit dz1 = new DatumZeit(20, 2, 2014, 12, 30, 15);
		DatumZeit dz2 = new DatumZeit(20, 2, 2014, 8, 30, 0);
		DatumZeit dz3 = new DatumZeit(1, 1, 2014, 12, 30, 15);
		DatumZeit dz4 = new DatumZeit(1, 1, 2014, 8, 30, 0);
		DatumZeit dz5 = new DatumZeit(1, 1, 1583, 12, 30, 15);
		DatumZeit dz6 = new DatumZeit(1, 1, 1583, 8, 30, 0);
		DatumZeit dz7 = new DatumZeit(31, 12, 2014, 12, 30, 15);
		DatumZeit dz8 = new DatumZeit(31, 12, 2014, 8, 30, 0);
		
		DatumZeit[] arrDatumZeit = new DatumZeit[] { dz3, dz1, dz2, dz4, dz7, dz8, dz5, dz6};
		
		Arrays.sort(arrDatumZeit);
		System.out.println("Aufsteigende Sortierung nach Datum und Zeit");
		System.out.println(Arrays.toString(arrDatumZeit));
		System.out.println();
		
		
		arrDatum = new Datum[] {dz3, dz1, dz2, dz4, dz7, dz8, dz5, dz6, d1, d2, d3, d4, d5};
		Arrays.sort(arrDatum);
		System.out.println("Aufsteigende Sortierung nach Datum und DatumZeit");
		System.out.println(Arrays.toString(arrDatum));
		System.out.println();
		
		
		// Speichern von Datum und DatumZeit Werten in einer TreeMap
		TreeMap<Datum, String> tmDatumZeit = new TreeMap<>();
		for (Datum d : arrDatum)
		{
			tmDatumZeit.put(d, d.toString());
		}
		
		// Ausgabe über die implizit aufsteigend sortiter TreeMap
		for (Datum d : tmDatumZeit.keySet())
		{
			System.out.println(tmDatumZeit.get(d));
		}
		
		System.out.println();
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
