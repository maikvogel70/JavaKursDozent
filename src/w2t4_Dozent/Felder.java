package w2t4_Dozent;

import java.util.Arrays;

/*
 * Ein Array (deutsch: Feld) ist eine Zusammenfassung mehrerer Variablen desselben
 * Typs zu einer Einheit. Innerhalb einer solchen Einheit werden die einzelnen
 * Elemente �ber Indizes (ganze Zahlen von 0 an aufw�rts) angesprochen.
 * 
 * Arrays sind in der Gr��e festgelegt, d.h. sie sind nicht dynamisch erweiterbar.
 * Arrays k�nnen (auch deshalb) intern besonders schnell verarbeitet werden.
 * Es gibt ein- und mehrdimensionale Arrays (d.h. ein Array von Arrays).
 * 
 * Jedes Array beinhaltet nur Werte eines bestimmten Datentyps.
 * Dies k�nnen sein:
 * 			- elementare Datentypen (int, byte, long, double...)
 * 			- Referenztypen
 * 			- Referenztypen anderer Arrays, um mehrdimensionale Arrays zu realisieren.
 */

/*
 * Die Verwendung von Arrays bietet sich speziell dann an, wenn man mit
 * gro�en Datenmengen vom gleichen Datentyp arbeitet, oder/und die
 * Reihenfolge der Daten eine wichtige Rolle spielt - wenn man �ber den
 * Index ein Element ansprechen k�nnen soll.
 * 
 * Arrays sind Objekte und m�ssen instanziert werden. Die Arraygr��e wird
 * in eckige Klammern [] geschrieben. G�ltige Werte f�r die Gr��e eines
 * Arrays sind positive, ganzzahlige Zahlen. Der Arrayindex beginnt mit 0.
 * F�r ein Array der Gr��e n ist somit der Endindex (n-1).
 */

/*
 * Bei der Deklaration eines Arrays wird der eigentliche Container
 * erzeugt. Das bedeutet, dass das Array zun�chst exakt in seiner Gr��e
 * und Typ bestimmt wird. M�glich ist die Verwendung aller elementaren und
 * benutzerdefinierten Datentypen. Die Deklaration wird mit dem Datentyp
 * eingeleitet, gefolgt vom Namen des Arrays. Wichtig ist die
 * Kennzeichnung des Arrays mit den eckigen Klammeroperatoren, welche eine
 * Array - Variable ausweisen. Dabei spielt es keine Rolle, ob diese nach
 * dem Typ oder dem Namen erscheinen. Zum Abschlu� wird das Array noch mit
 * dem new - Operator auf dem Speicher instanziiert, da es sich um eine
 * Objektinstanz handelt.
 */


public class Felder
{

	public static void main(String[] args)
	{
		
		// (a) Deklaration und Instanziierung (d.h. Einrichten) eines 
		// leeren Integer-Arrays der Gr�sse 6.
		
		//int[] lotto = new int[6];
		// oder
		
		int lotto[] = new int[6];
		
		// Jetzt haben wir ein Feld 'lotto' vom Typ 'int' mit 6 Werten,
		// die alle mit 0 (vor-)belegt sind, und auf die wir so zugreifen k�nnen.
		System.out.printf("%d, %d, %d, ...\n", lotto[0], lotto[1], lotto[2]);
		
		
		// Zuweisung (explitzite Initialisierung)
		lotto[0] = 2;
		lotto[1] = 5;
		lotto[2] = 18;
		lotto[3] = 25;
		lotto[4] = 32;
		lotto[5] = 42;
		
		ausgabe(lotto);
		
		// Array schnell l�schen
		Arrays.fill(lotto, 0);
		ausgabe(lotto);
		System.out.println();
		
		// (b) Deklaration und Initialisierung ohne L�ngenangabe
		//     in ausf�hrlicher Schreibweise.
		int[] lotto2 = new int[] {3, 9, 10, 31, 33, 45};
		ausgabe(lotto2);
		System.out.println();
		
		int[] lotto3 = {7, 17, 27, 37, 47, 48};
		ausgabe(lotto3);
		System.out.println();

		// Ein Array von Zeichenketten erstellen
		String[] arrText = {"Apfelkuchen", "Pustekuchen", "Sahne"};
		
		// Ausgabe
		for (int i = 0; i < arrText.length; i++)
		{
			System.out.println(arrText[i]);
		}
		
		System.out.println();
		
		// Array schnell l�schen
		Arrays.fill(arrText, "Leer");
				
		// Ausgabe
		for (int i = 0; i < arrText.length; i++)
		{
			System.out.println(arrText[i]);
		}
				
		System.out.println();
	
		
		int[] lotto4 = lotto3;
		
		lotto4[0] = 5;
		
		ausgabe(lotto3);
		ausgabe(lotto4);
		System.out.println();
		
		// Beide Objektvariablen verweisen auf dasselbe Array auf dem Heap.
		System.out.println(lotto3);
		System.out.println(lotto4);
		System.out.println();
		
		System.out.println(arrText);
		
		// Um eine Kopie eines Arrays zu erhalten( mit gleicher Gr�sse und
		// gleichem Elementtyp) so gibt es hierf�r die Objekt-Methode clone().
		
		int[] intClone = lotto4.clone();
		intClone[0] = 42;
		
		ausgabe(lotto4);
		ausgabe(intClone);
		System.out.println();
		
		// Um einzelne Feldinhalte in ein anderes Array zu kopieren gibt es die
		// statische Methode System.arraycopy().
		// Dabei kann auch die Position und die Anzahl der zu kopierenden Elemente
		// angegeben werden.
		
		int[] intCopy = new int[10];
		ausgabe(intCopy);
		
		//System.arraycopy(srcArray, srcPosition, destArray, destPosition, Anzahl der Elelemnte);
		System.arraycopy(intClone, 0, intCopy, 3, 3);
		ausgabe(intCopy);
		System.out.println();
		
		// Kopieren eines Arrays geht ebenfalls �ber die Klasse Arrays.
		// Der R�ckgabewert ist ein Array mit der im Argument 2 angegeben Anzahl von
		// Elementen. 
		// Ist die Anzahl der Elemente kleiner als die des Ziel-Arrays, 
		// werden die �berz�hligen Elemente des Ziel-Arrays entfernt.
		// Ist die Anzahl der Elemente gr��er als die des Ziel-Arrays, 
		// wird das Ziel-Array erweitert.
		
		// 1. copyOf()
		
		int[] intCopy2 = new int[10];
		ausgabe(intCopy2);
		
		intCopy2 = Arrays.copyOf(intClone, intClone.length);
		ausgabe(intCopy2);
		System.out.println();
		
		int[] intCopy3 = new int[2];
		ausgabe(intCopy3);
		intCopy3 = Arrays.copyOf(intClone, intClone.length);
		ausgabe(intCopy3);
		System.out.println();
		
		// 2. copyOfRange()
		// Teilweises Kopieren  eines Arrays, zwischen einer angebenen Position und dem Endindex (exklusive).
		
		int[] intCopy4 = Arrays.copyOfRange(intClone, 3,  intClone.length);
		ausgabe(intCopy4);
		System.out.println();
		
		// Ausgabe eines Arrays �ber die Klasse Arrays
		// Ausgabe im Format [e, e, e, e, e, ..]
		System.out.println(Arrays.toString(intCopy));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	private static void ausgabe(int[] arr)
	{
		
		System.out.println("Ausgabe(int[])");
		
//		for (int i = 0; i < arr.length; i++)
//			System.out.printf("%3d", arr[i]);
		
		
		// Oder mit Hilfe der erweiterten for-Schleife
		
		// Die erweiterte for-Schleife 
		// Weil das komplette Durchlaufen von Feldern h�ufig vorkommt,
		// wurde in Java 5 eine Abk�rzung f�r solche Iterationen in die
		// Sprache eingef�hrt:
		
		// Syntax:
		// for (Typ Bezeichner : Feld)
		// ...
		
		// Die erweiterte for-Schleife l�st sich vom Index und erfragt jedes
		// Element des Feldes.
		
		/*
		 * Umsetzung und Einschr�nkung.
		 * Intern setzt der Compiler die erweiterte for-Schleife ganz klassisch um, 
		 * sodass der Bytecode unter beiden Varianten gleich ist.
		 * 
		 * Nachteile der Variante sind:
		 * 		- Es wird immer das gesamte Feld durchlaufen. Anfang- und Ende-Index k�nnen nicht ausdr�cklich gesetzt
		 * 		  werden.
		 * 		- Die Ordnung ist immer von vorne nach hinten.
		 * 		- Der Index ist nicht sichtbar.
		 * 		- Die Schleife liefert ein Element, kann aber nicht in das Feld schreiben.
		 */
		
		
		for (int n : arr)
			System.out.printf("%3d", n);
		
		
		System.out.println();
	}
	
	

}
