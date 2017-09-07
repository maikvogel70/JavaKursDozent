package w1t5_Dozent;

import java.util.Random;
import java.util.Scanner;

public class Zahlenraten
{

	private static final int MAX_VERSUCHE = 7;
	
	
	public static void main(String[] args)
	{
		int zahl, geraten;
		int versuch = 0;
		
		Scanner console = new Scanner(System.in);

		System.out.println("Zahlen raten");
		System.out.println("Raten Sie die durch das System ermittelte Zufallszahl.");
		System.out.println("Der Wert kann zwischen 1 und 100 liegen.");
		System.out.println("Sie haben maximal 7 Versuche.");
		System.out.println("-------------------------------------------------------------------");

		// Initialisierung des Zufallsalgorithmus
		Random zufall = new Random();
		zahl = zufall.nextInt(100) + 1;		
			
		do
		{

			geraten = console.nextInt();
			versuch++;
			
			if (geraten < zahl)
				System.out.println("" + geraten + " ist zu klein. Versuch: " + versuch);
			else if (geraten > zahl)
				System.out.println("" + geraten + " ist zu gross. Versuch: " + versuch);
			else
				System.out.println("" + geraten + " ist richtig! Versuch: " + versuch);

			if (versuch >= MAX_VERSUCHE)
				break;

		}
		while (geraten != zahl );
		
		if (geraten != zahl)
		{
			System.out.println("\nSie haben leider verloren.");
			System.out.println("Die gesuchte Zahl war " + zahl);
		}
		
		console.close();
		
		

	}

}
