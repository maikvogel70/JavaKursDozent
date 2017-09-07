package w2t4_Dozent;

import java.util.Random;

public class Lottozahlen2
{
	private static final int ANZAHL_LOTTOZAHLEN = 6;

	public static void main(String[] args)
	{
		
		boolean[] lotto = new boolean[50];
		
		// Initialisierung des Zufallsalgorithmus
		Random zufall = new Random();
		
		int index = 0;
		int anzahl = 0;
		
		while (anzahl < ANZAHL_LOTTOZAHLEN)
		{
			
			index = zufall.nextInt(49) + 1;
			
			if (!lotto[index])
			{
				lotto[index] = true;
				anzahl++;
			}
		}
		
		System.out.println("Lottozahlen");
		
		for (int i = 1; i < lotto.length; i++)
		{
			if (lotto[i])
				System.out.printf("%3d", i);
		}
		
		System.out.println();
		System.out.println("Superzahl:  \t" + zufall.nextInt(10));  

	}

}
