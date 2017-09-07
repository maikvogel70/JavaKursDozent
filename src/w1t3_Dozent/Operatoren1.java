package w1t3_Dozent;

/*
 *   Operatoren -> dienen zur Verknüpfung von Daten
 */

/*
*   Mathematische Operatoren:     <Zahl> operator <Zahl> => Zahl
*   Vergleichsoperatoren:         <Zahl> operator <Zahl> => logischer Wert
*   Logische Operatoren:          <log. Wert> operator <log. Wert> => logischer Wert
*   Bitweise logische Operatoren: <Bitmuster> operator <Bitmuster> => Bitmuster
*/

public class Operatoren1
{

	public static void main(String[] args)
	{
		 /*
	      * Mathematische Operatoren:
	      *
	      *     (a) + (Addition)
	      *     (b) - (Substraktion) -> sowohl binär (a = b - c) als auch unär (-a)
	      *     (c) * (Multiplikation)
	      *     (d) / (Division)
	      *     (e) % (Modulo, Rest der Ganzzahldivision)
	      *
	      */
		
		
		// Beispiel:
		int ganzzahlig = 17;
		double gleitkomma = 0.16;
		
		System.out.println("Ganzzahlwert: " + ganzzahlig);
		System.out.println("Gleitkommawert: " + gleitkomma);
		System.out.println("Multiplikation: " + ganzzahlig * gleitkomma);
		// oder:
		System.out.println("Multiplikation: " + (ganzzahlig * gleitkomma));
		
		int wert = 17, teiler = 5;
		int restwert, quotient;
		
		quotient = wert / teiler;
		System.out.println("Quotient: " + quotient);
		
		restwert = wert % teiler;
		System.out.println("Restwert: " + restwert);
		
		
		double rechnung = 3 * 6.60 + 2 * 1.90 + 3.10;
		System.out.println("Die Rechnung beträgt: "  + rechnung);
		
		rechnung = (3 * 6.60) + (2 * 1.90) + 3.10;
		System.out.println("Die Rechnung beträgt: "  + rechnung);
		System.out.println();

		// Benzinverbrauch auf 345 Kilometer bei einem Verbrauch von 7,5 Liter/100 Km
		
		double benzinverbrauch = (345 / 100) * 7.5;    // (345 / 100) = 3 * 7,5 = 22,5 (falsch)
		System.out.println("Benzinverbrauch auf 345 Kilometer: " + benzinverbrauch);
		
		benzinverbrauch = (345.0 / 100) * 7.5;         // (345.0 / 100) = 3,45 * 7,5 = 25,875 (richtig)
		System.out.println("Benzinverbrauch auf 345 Kilometer: " + benzinverbrauch);
		
		int km = 345;
		benzinverbrauch = ((double)km / 100) * 7.5;   
		System.out.println("Benzinverbrauch auf 345 Kilometer: " + benzinverbrauch);
		System.out.println();
		
	     /*
	       * Inkrement- und Dekrement-Operatoren (unäre Operatoren)
	       * sowohl in Postfix- als auch in Präfix-Notation.
	       *
	       *		(a) Inkrement: ++ (erhöht den Wert einer Variablen um 1)  .
	       *		(b) Dekrement: -- (verringert den Wert einer Variablen um 1).
	       *
	       * Wenn die Präfix-Notation gewählt wird, so erfolgt das Inkrementieren
	       * bzw. Dekrementieren vor allen anderen Operationen innerhalb einer
	       * geschachtelten Anweisung, ansonsten danach.
	       *
	       */
		
		int a = 0, b = 0;
		System.out.println("a: " + a + ", b: " + b);             		// a = 0, b = 0
		
		// Postfix
		a++;
		
		// Präfix
		--b;
		System.out.println("a: " + a + ", b: " + b);					// a = 1, b = -1
		
		// a wird erst nach der Zuweisung inkrementiert
		b = a++;
		System.out.println("a: " + a + ", b: " + b);				    // a = 2, b = 1	
		
		b = --a;
		System.out.println("a: " + a + ", b: " + b);	                // a = 1, b = 1
		
		// a wird erst nach der Ausgabe inkrementiert
		// b wird vor der Ausgabe dekrementiert
		System.out.println("a: " + a++ + ", b: " + --b);				// a = 1, b = 0
		
		// Überprüfung
		System.out.println("a: " + a + ", b: " + b);                    // a = 2, b = 0
		
		

	}

}
