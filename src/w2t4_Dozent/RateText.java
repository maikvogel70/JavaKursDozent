package w2t4_Dozent;

import java.util.Random;
import java.util.Scanner;

public class RateText
{
	private static String alphabet = "abcdefghijklmnopqrstuvwxyzäöüß";
	private static String gerateneZeichen = "";
	
	private static Scanner eingabe;
	
	public static void main(String[] args)
	{
		String geratenerText = "";

		String[] arrText = {"Das Krokodil am Nilufer.", 
						    "Java ist nicht nur eine Insel.", 
						    "Wissen ist Macht, nichts wissen macht nichts.",
							"Der Klügere gibt nach und der Dummme bekommt was er will.",
						    "Iss keinen gelben Schnee!",
						    "Rettet den Wald, esst mehr Spechte!"};
		
		String suchText = arrText[0];
		
		int maxVersuche = 10;
		int Versuch = 1;
		char c;
		Random zufall =  new Random();
		int i;
		
		
		// Falls der Text als Parameter übergeben wurde.
		if (args.length > 0)
			suchText = args[0];
		else
		{
			i = zufall.nextInt(arrText.length);
			suchText = arrText[i];
		}
		
		// Maximale Anzahl von Versuchen wird aus Textlänge dividiert durch 3 ermittelt;
		maxVersuche = suchText.length() / 3;
		
		eingabe = new Scanner(System.in);

		System.out.println("Errate Sie den untenstehenden Text.");
		System.out.println("Sie haben maximal " + maxVersuche + " Versuche.");
		System.out.println();
		
		// Initialisieren des Suchtextes
		geratenerText = formatText(suchText, gerateneZeichen);
		
		do
		{

			System.out.printf("Runde %d von %d. Bisher geraten: %s\n", Versuch, maxVersuche, geratenerText);
			
			if (Versuch >= maxVersuche)
			{
				System.out.printf("Nach %d Versuchen ist jetzt Schluss!\n", maxVersuche);
				break;
			}

			
			System.out.println("Sie können entweder ein Zeichen eingeben oder ein '?' wenn ein Zufallszeichen ermittelt werden soll oder ein '!' wenn Sie auflösen möchten");
			
			
			if (Versuch == maxVersuche - 1)
				System.out.println("\nLetzter Versuch! ");

			// Ein Zeichen einlesen
			c = eingabe.nextLine().charAt(0);

			if (c == '!')
			{
				geratenerText = lösung();
				if (!suchText.equalsIgnoreCase(geratenerText))
					System.out.println("Das war leider nicht die richtige Lösung.\n");
				
				break;
			}
			
			if (c == '?')
				c = wheelOfFortune(zufall);
			
			
			// Prüfen, ob das Zeichen, unabhängig von Groß-/Kleinschreibung bereits einmal eingegeben wurde.
			// Falls ja, wird dies nicht als Versuch gewertet.
			if (gerateneZeichen.toLowerCase().indexOf(Character.toString(c).toLowerCase()) >= 0)
			{
				System.out.printf("Das Zeichen %c wurde bereits eingegeben!\n", c);
				continue;
			}

			gerateneZeichen += c;
			Versuch++;

			if (suchText.toLowerCase().indexOf(Character.toString(c).toLowerCase()) >= 0)
			{
				geratenerText = formatText(suchText, gerateneZeichen);
				if (geratenerText.contains("_"))
					System.out.printf("Gut geraten, Das Zeichen '%c' kommt im Text vor.\n", c);
			}
			else
				System.out.printf("Pech gehabt, '%c' kommt im Text nicht vor.\n", c);

		}
		while (!suchText.equals(geratenerText));

		if (!suchText.equalsIgnoreCase(geratenerText))
		{
			System.out.print("Gesuchten Text anzeigen? (j/n) ");
			c = eingabe.next().charAt(0);
			if (Character.toString(c).toLowerCase().equals("j"))
				System.out.printf("'%s'\n", suchText);
			
		}
		else
			System.out.printf("Gratulation, Sie haben den Text '%s' erraten.", suchText);
			
		eingabe.close();

	}

	private static String formatText(String suchText, String gerateneZeichen)
	{
		String formatText = "";
		String s = "";

		for (int i = 0; i < suchText.length(); i++)
		{

			// Nur Buchstaben und Ziffern formatieren. Satzzeichen und Leerzeichen werden direkt ausgegegeben.
			if (Character.isLetterOrDigit(suchText.charAt(i)))
			{
				s = "";
				s += suchText.charAt(i);

				formatText += gerateneZeichen.toLowerCase().indexOf(s.toLowerCase()) >= 0 ? suchText.charAt(i) : "_";
			}
			else
				// Satzzeichen oder Leerzeichen
				formatText += suchText.charAt(i);

		}

		return formatText;

	}
	
	private static char wheelOfFortune(Random zufall)
	{
		char c;
		int i;
		
		
		do
		{
			i = zufall.nextInt(alphabet.length());
			c = alphabet.charAt(i);
		}
		while(gerateneZeichen.toLowerCase().indexOf(c) >= 0);
		
		return c;
		
	}
	
	
	private static String lösung()
	{
		
		System.out.println("\n\nBitte Lösungstext mit allen Satzzeichen eingeben. Groß-/Kleinschreibung ist nicht relevant.\n");
		return eingabe.nextLine();
		
	}

}
