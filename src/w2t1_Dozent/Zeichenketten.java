package w2t1_Dozent;

import javax.sound.midi.Synthesizer;

/*
 * Ein String ist eine Sammlung von Zeichen (Datentyp char), die die Laufzeit-
 * umgebung geordnet im Speicher ablegt.
 * F�r einen Zugriff auf einzelne Zeichen innerhalb eines Strings ist deswegen zu beachten, 
 * dass auch hier, ebenso wie bei Arrays, das erste Zeichen den Index 0 hat. Daher entspricht 
 * der Index des letzten Elements nicht der L�nge der Zeichenkette, sondern der L�nge-1. 
 * Ansonsten kann man auch hier eine java.lang.ArrayIndexOutOfBoundsException bekommen.
 * 
 * 
 * String ist einer der am meisten verwendeten Datentypen in Java. In einer Variablen 
 * mit dem Datentyp String werden Zeichenketten gespeichert. Zeichenketten werden in Java
 * immer in doppelte Anf�hrungszeichen gesetzt. Die L�nge einer Zeichenkette ist variabel. 
 * Ein String-Objekt kann mit dem new-Operator erzeugt werden. Der new-Operator ist aber 
 * nicht zwingend erforderlich, da er bei der ersten Zuweisung ansonsten implizit aufgerufen wird.
 * 
 */

/* Java sieht drei Klassen vor, die Zeichenfolgen verwalten: String, StringBuilder und StringBuffer.
 * Sie unterscheiden sich in folgenden Punkten:
 * 		-  Sind die Zeichenfolgen unver�nderbar (invariant, immutable) oder
 *         nicht.
 *      -  Sind die Operationen auf den Zeichenketten gegen nebenl�ufige Zugriffe
 *         aus mehreren Threads abgesichert.
 */

/* Die Klasse String repr�sentiert eine nicht �nderbare Zeichenkette. Daher ist ein 
 * String immer threadsicher.
 * Mit Methoden der Klasse String kann man nach Zeichen oder Teilzeichenketten suchen
 * und ein String l�sst sich mit einem anderen String vergleichen. Aber Zeichen im
 * String k�nnen nicht ver�ndert werden.
 * Es gibt einige Methoden, die scheinbar Ver�nderungen an Strings vornehmen, aber
 * sie erzeugen in Wahrheit neue String-Objekte, die die ver�nderten Zeichenketten
 * repr�sentieren.      
 *        
 */




public class Zeichenketten
{

	public static void main(String[] args)
	{
		
		String s = "Java";
		s += " ist";
		s += " toll.";
		
		System.out.printf("Die L�nge der Zeichenkette '%s' betr�gt %d\n", s, s.length());
		
		// Umwandlung der Zeichenkette in Gro�buchstaben
		s = s.toUpperCase();
		System.out.println("Umwandlung der Zeichenkette in Grossbuchstaben: " + s);
		
		// Umwandlung der Zeichenkette in Kleinbuchstaben..
        s = s.toLowerCase();
        System.out.println("Umwandlung der Zeichenkette in Kleinbuchstaben: " + s);
        System.out.println();
        
        // Nat�rlich werden bei der Umwandlung nur solche Buchstaben ber�cksichtigt,
        // f�r die es Gro�- und Kleinschreibung gibt.
        
		s = "76133 Karlsruhe, Kriegsstra�e 100";
		s = s.toUpperCase().toLowerCase().replace('a', '@');
		System.out.println(s);

		System.out.println();
		

		// Nach enthaltenen Zeichen und Zeichenfolgen suchen
		
		s = "Java ist auch eine Insel.";
		char c = 'a';
		
		int i = s.indexOf(c);
		if (i >= 0)
			System.out.printf("Die Zeichenkette enh�lt das Zeichen '" + c + "' an der Indexposition %d\n", i);
		else
			System.out.println("Das Zeichen '" + c + "' kommt in der Zeichenkette nicht vor.");		
		
		
		String suchString = "Insel";
		
		i = s.indexOf(suchString);
		if (i >= 0)
			System.out.printf("Die Zeichenkette enh�lt die Zeichenfolge '" + suchString + "' an der Indexposition %d\n", i);
		else
			System.out.println("Das Zeichenfolge '" + suchString + "' kommt in der Zeichenkette nicht vor.");		
		
		System.out.println();
		
		
		// Alle Zeichen einer Zeichenkette ausgeben
		
		for (i = 0; i < s.length(); i++)
		{
			System.out.print(s.charAt(i));
		}
		
		System.out.println("\n");
		
		// Die Zeichenkette "Java ist toll." wird nur ein einziges Mal angelegt
        // und verweist deshalb auf das gleiche Objekt (Speicheradresse).
		
	    // Der Vergleichsoperator (==) vergleicht lediglich Speicheradressen: 
        // bei Objekten unbrauchbar!
        // Hier werden die beiden Objekte s1 und s2 verglichen und nicht die
        // Zeichenkette selbst
		
//		String s1 = "Java ist toll.";
//		String s2 = "Java ist toll.";
		
		// Erzwingen, dass 2 String-Objekte erstellt werden:
		
		String s1 = new String("Java ist toll.");
		String s2 = new String("Java ist toll.");
		
		if (s1 == s2)
			System.out.println("Die Zeichenketten stimmen �berein.");
		else
			System.out.println("Die Zeichenketten stimmen nicht �berein.");
		
		
		System.out.println();
		
	    // equals() achtet auf absolute �bereinstimung.
        // Die Methode gibt true zur�ck, falls die Zeichenketten gleich lang
        // sind und Zeichen f�r Zeichen �bereinstimmen.
		
		if (s1.equals(s2))
			System.out.println("Die Zeichenketten stimmen �berein.");
		else
			System.out.println("Die Zeichenketten stimmen nicht �berein.");
			
		s1 = s1.toLowerCase();
		
		// Absoluter Vergleich ohne Ber�cksichtigung von Gro�- oder Kleinschreibung 
		if (s1.equalsIgnoreCase(s2))
			System.out.println("Die Zeichenketten stimmen �berein.");
		else
			System.out.println("Die Zeichenketten stimmen nicht �berein.");
		
		System.out.println();
		
		// Lexikografische Vergleiche
	    // compareTo() gibt einen int-Wert zur�ck der signalisiert, ob das
        // Argument lexikografisch kleiner oder gr��er als das String-Objekt ist
        // bzw. mit diesem �bereinstimmt.
        // R�ckgabewerte: 1 = Basiszeichenkette ist gr��er als die Vergleichszeichenkette, 
        //              < 0 = Basiszeichenkette ist kleiner als die Vergleichszeichenkette, 
        //				  0 = Zeichenketten stimmen �berein.
		
		
		s1 = "112";
		s2 = "12";
		
		System.out.printf("Lexikografischer Vergleich der Zeichenketten '%s' und '%s'\n", s1, s2);

		if (s1.compareTo(s2) < 0)
			System.out.println(s1 + " < " + s2);
		
		if (s1.compareTo(s2) >= 0)
			System.out.println(s1 + " >= " + s2);
			
		System.out.println();
		
		// Mit welchen Zeichen endet (Suffix) oder beginnt (Pr�fix) die
        // Zeichenkette.
		
		boolean istTextdatei = "Aufgaben.txt".endsWith(".txt");
		System.out.println("Ist Textdatei: " + istTextdatei);
		
		
		s1 = "Java ist toll.";
		
		String ss = s1.substring(0, 10);
		System.out.println(ss);
				
		System.out.println();
		
		
		// Umwandlung eines numerischen Wertes in eine Zeichenkette
		
		i = 42;
				
		// Ist nicht im Sinne des Erfinders.
		String text = "" + i;
		System.out.println(text);
		
		// Eleganter ist die Verwendung einer Wrapper-Methode toString().
		text = Integer.toString(i);
		System.out.println(text);
		
		//  Oder �ber Klasse String mit der Methode valueOf()
		text = String.valueOf(i);
		System.out.println(text);
		
		
		
		
		
		
		
		
		
				
				
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
