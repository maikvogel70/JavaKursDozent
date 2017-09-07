package w1t1_Dozent;

/*
 * Blockkommentar
 * Weitere Zeile.
 * Noch eine Zeile.
 * 
 */

/*  Im folgenden Programm kommen drei Modifizierer vor:
 *  Der Modifizierer 'public' ist ein Sichtbarkeitsmodifizierer. Er bestimmt, ob die Klasse bzw. die Methode
 *  für anderere sichtbar ist oder nicht.
 *  Der Modifizierer 'static' zwingt den Programmierer nicht dazu, vor dem Methodenaufruf ein Objekt
 *  der Klasse zu bilden. Anders gesagt, die Eigenschaft, ob sich eine Methode nur über ein konkretes
 *  Objekt aufrufen lässt oder eine Eigenschaft der Klasse ist, sodass für den Aufruf kein Objekt der
 *  Klasse nötig wird, wird durch diesen Modifizierer bestimmt.
 *  Wir arbeiten in den ersten Tagen nur mit statischen Methoden. Später werden dann auch nicht-statische
 *  Methoden eingeführt. 
 */





public class HalloWelt
{

	// Die Methode main() muss vorhanden sein und ist der Programmeinstiegspunkt.
	// Innerhalb der Methode main() stehen die Programmanweisungen die
	// ausgeführt werden, wenn das Programm gestartet wird.
	
	// Vor der Methode wird der Datentyp deklariert, der als Rückgabewert
	// zurückgeliefert wird.
	// 'void' (leer) bedeuted kein Rückgabewert.
	
	// Der Methodenrumpf oder auch logischer Block ist der Teil der Methode, in
	// dem die Anweisungen stehen.
	// Er wird mit geschweiften Klammern {} gekennzeichnet.
	
	// Jede Anweisung muss mit einem Semikolon (;) abgeschlossen werden.
	
	public static void main(String[] args)
	{
		// Zeilenkommentar

		System.out.println("Hallo Welt");
		
		// Eingabe von 'syso' anschliessend STRG + Leertaste
		// ergibt
		// System.out.println();
		
		System.out.println("Guten Tag im Java-Kurs.");
		
	}

}
