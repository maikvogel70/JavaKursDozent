package w1t4_Dozent;

import java.util.Scanner;

public class Schleifen
{

	// Schleifen dienen dazu, Anweisungsfolgen wiederholt auszuführen.
	
	
	public static void main(String[] args)
	{
		
		// Die for-Schleife
		// Man setzt eine for-Schleife zumeist dann ein, wenn bekannt ist, wie
		// oft bestimmte Anweisungen ausgeführt werden müssen.
		
		// Ausdruck1: Initialisierung
		// Ausdruck2: Bedingung
		// Ausdruck3: Modifizierung
		
		// Syntax:
		// for (Ausdruck1; Ausdruck2; Ausdruck3)
		// {
		// 		Anweisungen
		// 		...
		// }
		

		// Gibt die Zahlen 1 bis 10 über die Konsole aus
		
			
		for (int i = 1; i <= 10; i++)
		{
			System.out.println(i);
		}
		
		System.out.println();
		
		
		// Die folgende Anweisung verursacht einen Kompilierfehler
		// Die Zählervariable, die im Schleifenkopf deklariert wird, gilt als
		// lokale Variable der Schleife und ist deshalb auch nur innerhalb des
		// Anweisungsblocks der for-Schleife gültig.
		//System.out.println(i);
		
		
		System.out.println("Vorzeitiges Verlassen einer Schleife");
		
		// Ausgabe aller Zahlen von 0 - 4
		for (int i = 0; i < 10; i++)
		{
			if (i > 4)
				break;
			
			System.out.println(i);
			
		}
		
		System.out.println();
		
		// for-Schleife mit Komma-Operator
		// Im ersten und im letzten Teil einer for-Schleife lässt sich ein Komma
		// setzen.
		// Damit lassen sich entweder mehrere Variablen gleichen Typs
		// deklarieren, oder mehrere Ausdrücke nebeneinander schreiben.
		
		// Ausgabe: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		for (int i = 0, j = 0; i + j < 10; i--, j += 2 )
		{
			System.out.print((i + j) + ", ");
		}
		
		System.out.println("\n");
		
		
		// Variationen der for-Schleife
		// 1. for-Schleife ohne Initialisierungsausdruck
		
		int a = 1;
		
		for (; a <= 10; a++)
		{
			System.out.print(a + ", ");
		}
		
		System.out.println("\n");
		
		// 2. for-Schleife ohne Bedingungsausdruck
		//	  Der Schleifenabbruch muss innerhalb des Schleifenrumpfs angegeben werden.
		
		
		for (int i = 1;;i++)
		{
			if (i > 10)
				break;
			
			System.out.print(i + ", ");
			
		}
		
		System.out.println("\n");
		
		// 3. for-Schleife ohne Modifizierungsausdruck
		//    Inkrementierung erfolgt im Schleifenrumpf.
		
		for (int i = 1; i <= 10;)
		{
//			System.out.print(i + ", ");
//			i++;
			
			// oder
			System.out.print((i++) + ", ");
			
		}
		
		System.out.println("\n");
		
		
		// Die Endlosschleife wird durch den Editor erkannt 
		// weil im Schleifenrumpf keine break-Anweisung angegeben wurde.
//		for (;;)
//		{
//			System.out.println("Immer wieder und wieder ....");
//		}
		
		
		System.out.println();
		
		System.out.println("Bedingtes Auslassen von Anweisungen  mit 'continue'");
		
		// Ausgabe aller geraden Zahlen zwischen 0 und 10
		
		for (int i = 0; i <= 10; i++)
		{
			if (i % 2 == 1)
			{
				continue;
			}
			
			System.out.print(i + ", ");
			
		}
		
		System.out.println("\n");
		
		
		for (int zeile = 1; zeile <= 10; zeile++)
		{
			
			for (int spalte = 1; spalte <= 10; spalte++)
			{
				
				System.out.printf("%4d", zeile * spalte);
				
			}
			
			System.out.println();
		
		}
		
		System.out.println("\n");
		
		// Die 'while'-Schleife.
		// In eine while-Schleife wird dann eingetreten, wenn bestimmte
		// Bedingungen erfüllt sind. Bei der for-Schleife wird diese Bedingung
		// durch den Schleifenzähler festgelegt, bei einer while-Schleife wird die
		// Bedingung hinter dem Schlüsselwort while in runden Klammern angegeben. 
		// Da sich die Anweisungen der Bedingungsprüfung anschließen, spricht man auch von
		// einer kopfgesteuerten Schleife.
		
		// Syntax:
		// while (Bedingung)
		// {
		// 		Anweisungen
		// }
		
		// In der Bedingung handelt es sich um einen booleschen Ausdruck, der
		// aus Vergleichsoperatoren gebildet wird und entweder true oder false
		// liefert.
		
		// Eine while-Schleife wird ausgeführt, solange die Bedingung wahr, also
		// true ist. Die Schleife wird beendet, wenn die Bedingung false ist.
		
		// Ist die Bedingung schon bei der ersten Überprüfung falsch, werden die
		// Anweisungen im Schleifenkörper überhaupt nicht ausgeführt.

		// Da im Gegensatz zur for-Schleife die Bedingung zum Austritt der
		// Schleife nicht automatisch verändert wird, muss innerhalb des
		// Schleifenkörpers eine Anweisung stehen, die es ermöglicht, die Schleife 
		// zu einem vordefinierten Zustand zu verlassen. Wird eine solche Anweisung
		// vergessen, liegt der klassische Fall einer Endlosschleife vor.
		
		// Ausgabe der Werte 1 bis 10 
		
		int i = 1;
		int zahl = 10;
		
		while (i <= zahl)
		{
			//System.out.println("Schleifendurchlauf " + i);
			//i++;
			// oder
			System.out.println("Schleifendurchlauf " + (i++));
		}
		
		System.out.println();
		
		// Endlosschleife
		
//		while(true)
//		{
//			System.out.println("Immer wieder und wieder ....");
//		}
		
		
		// Die 'do'-Schleife.
		// Die do-Schleife unterscheidet sich dahingehend von der
		// while-Schleife, dass die Schleifenbedingung am Ende der Schleife ausgewertet wird.
		// Die do-Schleife ist eine fußgesteuerte Schleife. Die Folge ist,
		// dass die Anweisungen innerhalb des Anweisungsblocks zumindest einmal
		// durchlaufen werden.
		
		// Syntax:
		// do
		// {
		// 	Anweisungen
		// } 
		// while (Bedingung);
		
		i = 1;
		do
		{
			System.out.println("Schleifendurchlauf " + (i++));
		}
		while (i <= 10);
		
		System.out.println();
		
		// Erstellen einer Klasse Scanner mit Übergabe der Standardeingabe
		// (Standard-Eingabestrom) Konsole
		Scanner console = new Scanner(System.in);
		
		// Eingabe eines Wertes über die Konsole, der halbiert wird,
		// solange der Wert >= 11 ist;
		
		double d, wert;
		
		System.out.println("Bitte eine Dezimalzahl eingeben: ");
		
		d = console.nextDouble();
		wert = d;
		
		
		do
		{
			System.out.println(d);
			//d = d / 2;
			
			// oder
			d /= 2;
			
		}
		while (d >= 11);
		
		System.out.println();
		
		
		d = wert;
		
		do
		{
			System.out.println(d);
		}
		while ((d /= 2) >= 11);
		
		System.out.println();
		
		// Schleifenbedingungen und absolute Vergleiche mit == oder !=
		
		// Eine Schleifenabbruchbedingung kann ganz unterschiedlich aussehen.
		// Beim Zählen ist es häufig der Vergleich auf einen Endwert.
		// Oft steckt an dieser Stelle ein absoluter Vergleich mit == oer !=,
		// der aus zwei Gründen problematisch werden kann.
		
		for (int x = 1; x != 11; x++)
		{
			System.out.println(x);
		}
		
		System.out.println();
		
//		int x = 12;
//		
//		for (; x != 11; x++)
//		{
//			System.out.println(x);
//		}
//		
//		System.out.println();
		
		
		byte b = 12;
		
		for (; b != 11; b++)
		{
			System.out.println(b);
		}
		
		System.out.println();
		
		// Bei Erreichen des höchsten positiven Wertes
		// 0111 1111  = 127
		// 1000 0000  = -128
		
		// Daher passt folgendes besser:
		
		for (; b < 11; b++)
		{
			System.out.println(b);
		}
		
		System.out.println();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
