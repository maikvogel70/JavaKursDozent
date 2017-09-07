package w1t5_Dozent;

// In der Programmierung heißt Rekursion, dass eine Methode sich selbst aufruft. 

// Der rekursive Aufruf von Methoden birgt allerdings die Gefahr, dass bei einer
// grossen Anzahl von rekursiven Aufrufen der Stack nicht ausreicht, um alle
// Rücksprungadressen und Variablen abszuspeichern.
// Dann kommt es zu einem Stack Overflow!

public class Rekursion
{

	public static void main(String[] args)
	{
		
		//r1();
		//down(10);

		down1(10);
		System.out.println();
		down2(10);
		
		System.out.println();		
		
		System.out.println("Fakultät von 20: " + fakultaet(20));
		
		
	}

	// Keine Abbruchbedingung. Absturz mit StackOverflow.
	private static void r1()
	{
		System.out.println("r1()");
		r1();

	}
	
	// Endlos-Rekursion: keine Abbruchbedingung.
	// Absturz mit StackOverflow.
	private static void down(int i)
	{
		System.out.println(i);
		down(i - 1);
		
	}
	
	
	private static void down1(int i)
	{
		
		if (i <= 0)               // Rekursionsende (Abbruchbedingung)
			return;
		
		System.out.print(i + ", ");
		down1(i - 1);
		
	}
	
	private static void down2(int i)
	{
		
		if (i <= 0)               // Rekursionsende (Abbruchbedingung)
			return;
		
		down2(i - 1);
		System.out.print(i + ", ");
		
	}
	
	// Die Fakultät (Faktorielle) ist in der Mathematik eine Funktion, die einer natürlichen Zahl das Produkt aller
    // natürlichen Zahlen kleiner oder gleich dieser Zahl zuordnet.
    // Sie wird durch ein dem Argument nachgestelltes Ausrufezeichen („!“) abgekürzt. 

    // Zahl            Fakultät
    //------------------------
    // 0                      1   
    // 1                      1   
    // 2                      2   (1 * 2) =    						   2
    // 3                      6   (1 * 2 * 3) =    					   6 
    // 4                     24   (1 * 2 * 3 * 4) =   				  24
    // 5                    120   (1 * 2 * 3 * 4 * 5) =  			 120
    // 6                    720   (1 * 2 * 3 * 4 * 5 * 6) =  		 720 
    // 7                   5040   (1 * 2 * 3 * 4 * 5 * 6 * 7) = 	5040
    // ...                 ....
	
	
	private static long fakultaet(int zahl)
	{
		
		long retValue = -1;
		
		System.out.println();
		
		if (zahl >= 0)
		{
			retValue = 1;
			
			for (int i = 2; i <= zahl; i++)
			{
				//retValue = retValue * i;
				// oder
				retValue *= i;
				System.out.println("    " + i + "!\t - " + retValue);
				
				
			}
		}
		
		System.out.println();
		
		return retValue;
	}
	
	
	
}
