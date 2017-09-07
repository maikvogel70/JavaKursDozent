package w1t3_Dozent;

/*
 *  In Java gibt es zwei Arten von primitiven (eingebauten) Datentypen:
 * 		-  arithmetische Typen (ganze Zahlen, auch integrale Typen genannt, Fließkommazahlen und Unicode-Zeichen)
 * 		-  Wahrheitswerte für die Zustände wahr oder falsch.
 * 
 *  Zeichenketten (Strings)  sind invariante (unveränderliche) Objekte!
 * 
 */

/* Die Speicherbereiche Stack und Heap.
*
* Alle elementaren Datentypen werden auf dem Stack abgelegt, alle Objekte auf dem Heap.
* Ausnahme C++: Objekte können auch auf dem Stack erstellt werden.
*
* Stack (deutsch: Stapel)
* Der Stapelspeicher ist ein Speicherbereich für lokale Variablen eines Moduls (statische Speicherverwaltung). 
* Beim Verlassen eines Gültigkeitsbereichs werden diese Bereiche automatisch zerstört.
* Für Stackspeicher gilt immer, was zuletzt angefordert wurde muss auch als Erstes wieder freigegeben werden (LIFO: Last In – First Out). 
* Wenn Sie innerhalb eines logischen Blocks also Variablen anlegen, werden diese auf dem Stack angelegt. 
* Am Ende des Blocks verliert die Variable ihre Gültigkeit und der Speicher wird wieder freigegeben.
* Wenn eine Methode aufgerufen wird, wird die aktuelle Programmadresse (also die Stelle im Programm, an der die Methode aufgerufen wurde)
* auf dem Stack abgelegt. Innerhalb der Methode werden möglicherweise Variablen angelegt, die wiederum auf dem Stack landen. 
* Am Ende der Methode werden die Speicherbereiche der Variablen wieder freigegeben und das Programm springt zu der Programmadresse, 
* die oben auf dem Stack liegt. Somit befindet es sich jetzt wieder an der Stelle, an der die Funktion aufgerufen wurde.
* Der Stack ist in seiner Grösse begrenzt (1 MegaByte).
*
* Heap (deutsch: Halde)
* Objekte können dynamisch und permanent bis zum Ende der Laufzeit des Moduls erstellt werden. Dies erfolgt im sog. Heap.
* Zu Erstellung eines Objekts verwendet man den Operator new. Objekte, für die es keine Referenz mehr in ein aktives Programm gibt,
* werden vom Garbage Collector automatisch entfernt.
* Der Heap hat den eklatanten Vorteil, dass die Grenzen des zuteilbaren Speichers nur vom Betriebssystem und der physikalischer Speichermenge
* gezogen werden und nicht von Compiler- und Linkereinstellungen. Ein weiterer Vorteil ist, dass alle Elemente einer Klasse dann auch auf dem 
* Heap liegen. 
*/

/*
 *	Datentyp   		Größe		Wertebereich 											Beschreibung
 *------------    -------	    -----------------										-----------------
 *	boolean 	     8 Bit	    true / false 											Boolescher Wahrheitswert
 *  char          	16 Bit 		0 ... 65.535											Unicode-Zeichen
 *  byte			 8 Bit		-128 ... 127											Zweierkomplement-Wert
 *  short 		   	16 Bit		-32.768 ... 32.767 										Zweierkomplement-Wert
 *  int 			32 Bit		-2.147.483.648 ... 2.147.483.647						Zweierkomplement-Wert
 *  long 			64 Bit      -9.223.372.036.854.775.808 ...             				Zweierkomplement-Wert
 *                              9.223.372.036.854.775.807
 *  float 			32 Bit 		+/-1,4E-45 ... +/-3,4E+38 								Gleitkommazahl
 *  double 	   		64 Bit 		+/-4,9E-324 ... +/-1,7E+308                   			Gleitkommawert mit doppelter Genauigkeit
 */

/*
 * Zwei wesentliche Punkte zeichnen die primitive Datentypen aus:
 * 
 * 1. Alle Datentypen haben eine festgesetzte Länge, die sich unter keinen Umständen ändert.
 * 2. Die numerischen Datentypen byte, short, int und long sind vorzeichenbehaftet, Fließkommazahlen
 * 	  sowieso. Das ist leider nicht immer praktisch, aber man muss stets daran denken.
 */


public class Datentypen
{

	public static void main(String[] args)
	{

		
//		// Variablendeklaration
//		
//		String name;
//		int alter;
//		double gehalt;
//		char geschlecht;
//		boolean istKanzler;
//		
//		
//		name = "Angela Merkel";
//		alter = 61;
//		gehalt = 24575.50;
//		geschlecht = 'w';
//		istKanzler = true;
		
		
		
		String name = "Angela Merkel";
		int alter = 61;
		double gehalt = 245875.50;
		char geschlecht = 'w';
		boolean istKanzler = true;
		
		System.out.println(name);
		
		
		// Deklaration mehrerer Variablen in einem Schritt
		int a, b , c, d;
		
		// Mehrere Zuweisungen in einem Schritt
		a = b = c = 0;
		
		System.out.println(a);
		a = 5;
		System.out.println(a);
		System.out.println(b);
		
		System.out.println();
		
		
		// Fehler!
		// Ohne vorherige Initialisierung/Zuweisung darf auf eine Variable nicht
		// zugegriffen werden: Die lokale Varibale wurde noch nicht initialisiert.
		//System.out.println(d);
		
		/*
		 * char 
		 * Ein char besteht in Java aus zwei Bytes. Das ist notwendig, da
		 * ein char nicht wie z.B. in C nur einen Wertebereich von 0-255
		 * abdeckt, sondern ein Unicode-Zeichen aufnehmen muss, welches aus zwei
		 * Bytes bestehen kann. Diese Art der Zeichendarstellung wird auch
		 * "UTF-16" genannt, weil 2 Bytes = 16 Bit entsprechen. Mit dieser
		 * Datenbreite ist es möglich, auch mit asiatischen Zeichensätzen in
		 * Java zu arbeiten.
		 */
		
		char buchstabe1 = 'A';
		char buchstabe2 = 65;
		
		System.out.println(buchstabe1);
		System.out.println(buchstabe2);
		System.out.println();
		
		buchstabe1 = (char)(buchstabe1 + 32);
		buchstabe2 = (char)(buchstabe2 + 2);
		System.out.println(buchstabe1);
		System.out.println(buchstabe2);
		System.out.println();
		
		
		System.out.print(buchstabe1);
		System.out.print(buchstabe2);
		System.out.println();
		System.out.println();
		
		char buchstabe3 = 'ß';
		System.out.println(buchstabe3);
		System.out.println((int)buchstabe3);
		
		System.out.println((int)buchstabe1);
		System.out.println((int)buchstabe2);
		System.out.println();
		
		// Zeichenketten: Datentyp String
		// Strings werden in doppelte Anführungszeichen gesetzt.
		// Beispiele für Strings sind "alfatraining", "Java ist toll", "@%#&$",
		// "x" sowie auch "".
		// Die Länge eines Strings ist die Anzahl der Zeichen, aus denen er
		// besteht.
		// So hat beispielsweise der String "alfatraining" die Länge 12.
		// Der leere String "" hat die Länge 0, da er 0 Zeichen enthält.
		// Die einzelnen Zeichen eines Strings der Länge n werden von 0 bis n-1
		// nummeriert.
		
		// Zeichenketten sind Objekte.
		// Der Datentyp String ist in Java kein einfacher Datentyp wie etwa int
		// oder double oder boolean.
		// Eine Variable vom Typ String enthält nicht den String selbst, sondern
		// sie enthält einen Verweis auf ein Objekt vom Typ String. 
		// Dies ist deswegen nötig, weil die Zeichenketten unterschiedliche Längen
		// annehmen können.
		// Obwohl Strings Objekte sind, sieht die Wertzuweisung syntaktisch
		// genauso aus wie bei den einfachen Datentypen:
		
		String s = "Guten Tag im Java Kurs.";
		System.out.println(s);
		
		// Verkettung von Zeichenketten
		// Noch in einer anderen Hinsicht werden Strings wie einfache Datentypen
		// behandelt.
		// Strings lassen sich nämlich mit dem Operator + verketten.
		
		String s1, s2, s3;
		
		
		s1 = "Java ";
		s2 = "ist ";
		s3 = "toll.";
		
		s = s1 + s2 + s3;                              // Verkettung von Zeichenketten
		System.out.println(s);
		System.out.println(s.length());                // Anzahl Zeichen der Zeichenkette ausgeben 
		System.out.println();
		
		// Zeichenketten lassen sich auch mit Werten anderer Datentypen
		// verketten; hierbei werden diese automatisch in Zeichenketten umgewandelt.
		
		int k = 3;
		
		s = "Die Zahl heißt " + k;						// Verkettung einer Zeichenkete mit einer Zahl
		System.out.println(s);                          // Die Zahl heißt 3
		
		
		s = "Die Zahl heißt " + k + 1;                 // Zweimal Verkettung
		System.out.println(s);						   // Die Zahl heißt 31	
		
		
		s = "Die Zahl heißt " + (k + 1);              // Einmal Addition und einmal Verkettung
		System.out.println(s);						  // Die Zahl heißt 4
		
		s = k + 1 + " heißt die Zahl";                // Einmal Addition und einmal Verkettung
		System.out.println(s);                        // 4 heißt die Zahl
		System.out.println();
		
		/*
		 * Escape-Sequenzen.
		 * Um spezielle Zeichen, etwa Zeilenumbruch oder
		 * Tabulator, in einer Zeichenkette setzen zu können, stehen
		 * Escape-Sequenzen zur Verfügung:
		 *  
		 * 	\n Zeilenschaltung (New line) 
		 * 	\t horizontaler Tabulator 
		 * 	\b Rückschritt (Backspace) 
		 * 	\r Wagenrücklauf (Carriage return) 
		 * 	\f Seitenumbruch (Formfeed) 
		 * 	\\ Backslash 
		 * 	\' einfaches Anführungszeichen 
		 * 	\" doppeltes Anführungszeichen
		 * 
		 */
		
		// Beispiele
		System.out.println("Java ist auch eine 'Insel'");
		System.out.println("Java ist auch eine \"Insel\"");
		
		System.out.println("c:\\temp\\textdatei.txt");
		// oder
		System.out.println("c:/temp/textdatei.txt");
		
		System.out.print("Guten ");
		System.out.print("Tag ");
		System.out.print("im ");
		System.out.print("Java - ");
		System.out.print("Kurs.\n");
		System.out.println();
		
		System.err.println("Das ist eine Fehlermeldung.");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
