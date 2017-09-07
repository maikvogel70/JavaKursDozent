package w2t1_Dozent;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Eine Primzahl ist eine natürliche Zahl mit genau zwei natürlichen Zahlen als Teiler, 
// nämlich der Zahl 1 und sich selbst. 
// Die kleinsten Primzahlen sind 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, ...


public class Primzahlen
{
	
	private static final int COLUMNS = 10;
	private static int COLUMN_WIDTH = 8;

	public static void main(String[] args)
	{
	
		int maxValue = -1;
		int anzahl = 0;
		String eingabe;
		
		
		Scanner console = new Scanner(System.in);
		
		do
		{
			
			System.out.println("Bitte einen Wert größer 1 eingeben, bis zu dem die Primzahlen berechnet werden sollen und die Eingabetaste betätigen: ");
			
			// Die try-catch-Anweisung besteht aus einem try-Block, auf den eine
			// oder mehrere catch-Klauseln folgen, welche die die Behandlung verschiedener
			// Ausnahmen ermöglichen.
			// Der try-Block enthält den überwachten Code, der möglicherweise
			// die Ausnahme verursacht.
			// Die catch-Klausel fängt den Ausnahmetyp ab.
			// Die finally-Klausel ist optional. Sie wird immer ausgeführt,
			// unabhängig davon, ob eine Ausnahme verursacht wurde oder nicht.
			
			/*
			 * Syntax der try-catch-Anweisung
			 * 
			 * try 
			 * { 
			 * 		// 	Anweisungen 
			 * } 
			 * catch(Typ Objekt) 
			 * { 
			 * 		// 	Anweisungen 
			 * } 
			 * [finally] 
			 * { 
			 * 		// 	Anweisungen 
			 * }
			 */
			
			/*
			 * 1. 	Wenn eine Ausnahme von einer Anweisung im try{}-Block ausgelöst wird, werden die catch{}-Blöcke 
			 * 		einer nach dem anderen untersucht, beginnend mit dem ersten. 
			 * 2. 	Nur ein catch{}-Block wird ausgewählt. 
			 * 3. 	Wenn kein catch{}-Block mit der Ausnahme übereinstimmt, wird keiner ausgewählt und
			 * 		die Ausführung verläßt die Methode (gerade so als wenn es keinen try{}-Block gegeben hätte.) 
			 * 4. 	Der erste catch{}-Block, der mit dem Typ der Ausnahme übereinstimmt, bekommt die Kontrolle. 
			 * 5. 	Die speziellsten Ausnahmentypen sollten in der Struktur zuerst kommen, gefolgt von
			 * 		allgemeineren Ausnahmetypen. 
			 * 6. 	Die Anweisungen in dem gewählten catch{}-Block werden sequentiell ausgeführt. Nachdem die letzte Anweisung
			 * 		ausgeführt ist, wird die Kontrolle an die erste Anweisung weitergegeben, die der try/catch-Struktur folgt. 
			 * 7. 	Die Kontrolle kehrt nicht zum try-Block zurück.
			 */
			
			try
			{
				//maxValue = console.nextInt();
				
				eingabe = console.nextLine();
				
				maxValue = Integer.parseInt(eingabe);				

			}
			catch (InputMismatchException ex)
			{
				System.out.println("Ungültige Eingabe: " +  ex.getMessage());
			}
			catch (NoSuchElementException ex)
			{
				System.out.println("Keine Eingabe: " +  ex.getMessage());
			}
			catch (IllegalStateException ex)
			{
				System.out.println("Konsole ist bereits geschlossene: " +  ex.getMessage());
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			
		}
		while (maxValue < 2);
		
		console.close();
		
		System.out.println("Primzahlenberechnung bis " + NumberFormat.getInstance().format(maxValue));
		
		// Dnymaische Berechnung der Spaltenbreite über den eingegebenen Maximalwert
		COLUMN_WIDTH = NumberFormat.getInstance().format(maxValue).length() + 2;
		
		for (int i = 2; i <= maxValue; i++)
		{
			if (istPrimzahl(i))
			{
				anzahl++;
				
				System.out.printf("%" + COLUMN_WIDTH + "s", NumberFormat.getInstance().format(i));
				
				if (anzahl % COLUMNS == 0)
					System.out.println();
					

			}			
			
		}
		
		System.out.println("\n\nAnzahl der gefundenen Primzahlen:  " + NumberFormat.getInstance().format(anzahl));
		
		

	}
	
	
	private static boolean istPrimzahl(int zahl)
	{
		
		boolean retValue = true;
		
		// Zum Testen auf Primzahlen
		for (int i = 2; i < zahl; i++)
		{
			if (zahl % i == 0)
			{
				retValue = false;
				break;
			}

		}
				
		return retValue;
	}
	

}
