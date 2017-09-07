package w1t3_Dozent;

// Bedingte Anweisungen und Fallunterscheidungen (Java ist auch eine Insel, Seite 164 ff.)

public class Kontrollstrukturen
{

	public static void main(String[] args)
	{

		// Die if-Anweisung.
		// Die if-Anweisung bietet sich an, wenn bestimmte Programmteile nur
		// beim Auftreten einer bestimmten Bedingung ausgeführt werden sollen.
		// Die zu prüfende Bedingung hinter dem Schlüsselwort 'if' liefert
		// grundsätzlich immer einen booleschen Wert, also 'true' oder 'false'
		// zurück.

		int a = 14;
		int b = 22;

		// Ein relationaler Vergleich, ob der Inhalt einer Variable a einen bestimmten
		// Wert enthält:

		if (a == 14)
			System.out.println("Der Inhalt der Variablen a ist 14.");

		// Die if-Anweisung kann mit dem sogenannten else-Zweig erweitert
		// werden. Dieser ist jedoch optional (dangling else).
		// Die Anweisungen im else-Zweig werden dann ausgeführt, wenn der
		// boolesche Ausdruck der if-Anweisung falsch (false) liefert.

		if (a == 14)
			System.out.println("Der Inhalt der Variablen a ist 14.");
		else
			System.out.println("Der Inhalt der Variablen a ist nicht 14.");

		System.out.println();

		if (a < b)
		{
			System.out.println("a ist kleiner als b");
			System.out.println("Die Differenz beträgt: " + (b - a));
		}
		else
		{

			System.out.println("a ist größer oder gleich b");
			System.out.println("Die Differenz beträgt: " + (a - b));
		}

		System.out.println();

		// Mehrfachverzweigungen bzw. geschachtelte Alternativen
		//

		int monat = 9;
		int tage = 0;

		// Die geschachtelte if-Anweisung soll uns helfen, die Anzahl der Tage
		// für einen angegebenen Monat zu ermitteln.

		if (monat == 1)
			tage = 31;
		else if (monat == 2)
			tage = 28;
		else if (monat == 3)
			tage = 31;
		else if (monat == 4)
			tage = 30;
		else if (monat == 5)
			tage = 31;
		else if (monat == 6)
			tage = 30;
		else if (monat == 7)
			tage = 31;
		else if (monat == 8)
			tage = 31;
		else if (monat == 9)
			tage = 30;
		else if (monat == 10)
			tage = 31;
		else if (monat == 11)
			tage = 30;
		else if (monat == 12)
			tage = 31;
		else
			System.out.println("Der Monat " + monat + " ist ungültig.");

		if (tage > 0)
			System.out.println("Der Monat " + monat + " hat " + tage + " Tage.");

		System.out.println();

		tage = 0;

		// oder
		if (monat > 0 && monat <= 12)
		{

			if (monat == 4 || monat == 6 || monat == 9 || monat == 11)
			{
				tage = 30;
			}
			else if (monat == 2)
			{
				tage = 28;
			}
			else
			{
				tage = 31;
			}
		}
		else
		{
			System.out.println("Der Monat " + monat + " ist ungültig.");
		}

		if (tage > 0)
			System.out.println("Der Monat " + monat + " hat " + tage + " Tage.");

		System.out.println();

		// Der Bedingungsoperator
		// Ternärer Operator, besteht aus drei Teilen.

		int max = (a > b) ? a : b;
		System.out.println("Max: " + max);

		// Der Ausdruck entspricht folgender if-Anweisung
		if (a > b)
		{
			max = a;
		}
		else
		{
			max = b;
		}

		System.out.println("Max: " + max);

		System.out.println("Max: " + ((a > b) ? a : b));

		System.out.println();

		// Die switch-Anweisung
		// Die switch-Anweisung erlaubt die Auswahl von
		// - Ganzzahlen,
		// - Aufzählungen (enum)
		// - einzelnen Zeichen (char)
		// - Zeichenketten (Java 7)

		a = 5;

		switch (a)
		{

		case 1:
			System.out.println("Eins");
			break;

		case 2:
			System.out.println("Zwei");
			break;

		case 3:
			System.out.println("Drei");
			break;

		default:
			System.out.println("Alle anderen Zahlen");
			break;

		}

		System.out.println();

		tage = 0;

		switch (monat)
		{

		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			tage = 31;
			break;

		case 4:
		case 6:
		case 9:
		case 11:
			tage = 30;
			break;

		case 2:
			tage = 28;
			break;

		default:
			System.out.println("Der Monat " + monat + " ist ungültig.");
			break;

		}

		if (tage > 0)
			System.out.println("Der Monat " + monat + " hat " + tage + " Tage.");

		System.out.println();

		char geschlecht = 'W';

		switch (geschlecht)
		{

		case 'W':
		case 'w':
			System.out.println("Geschlecht: weiblich");
			break;

		case 'M':
		case 'm':
			System.out.println("Geschlecht: männlich");
			break;

		default:
			System.out.println("Geschlecht: keine Ahnung");
			break;

		}

		String tier = "Maus";

		switch (tier.substring(0, 1).toLowerCase())
		{
		case "m":
			System.out.println("Tier mit 'm'");
			break;

		case "w":
			System.out.println("Tier mit 'w'");
			break;

		default:
			System.out.println("Tier ohne 'm' und ohne 'w'");
			break;

		}

	}

}
