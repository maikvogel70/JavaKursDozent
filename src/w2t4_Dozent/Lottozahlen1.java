package w2t4_Dozent;

import java.util.Arrays;
import java.util.Random;

public class Lottozahlen1
{

	private static final int ANZAHL_LOTTOZAHLEN = 6;

	public static void main(String[] args)
	{
		int[] lotto = new int[ANZAHL_LOTTOZAHLEN];

		// Initialisierung des Zufallsalgorithmus
		Random zufall = new Random();

		int anzZiehung = 0;

		for (int i = 0; i < lotto.length; i++)
		{
			lotto[i] = zufall.nextInt(49) + 1;

			for (int j = 0; j < i; j++)
			{

				if (lotto[j] == lotto[i])
				{
					i--;
					break;
				}
			}

			anzZiehung++;
		}

		// Unsortierte Ausgabe ohne Superzahl
		System.out.println("\nAnzahl Ziehungen (" + anzZiehung + ")");
		System.out.println("Lottozahlen:");
		for (int i = 0; i < (lotto.length); i++)
		{
			System.out.printf("%3d", lotto[i]);
		}

		System.out.println();

		Arrays.sort(lotto);

		// Sortierte Ausgabe ohne Superzahl
		System.out.println("Lottozahlen:");
		for (int i = 0; i < (lotto.length); i++)
		{
			System.out.printf("%3d", lotto[i]);
		}

		System.out.println();
	    System.out.println("Superzahl:  \t" + zufall.nextInt(10));  
	      
	}

}
