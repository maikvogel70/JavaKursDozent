package w2t3_Dozent;

import java.util.Scanner;

/*
* 1.	Schreiben sie eine Methode 'digitCount', die einen String
*		übergeben bekommt und als 'int'-Wert zurückliefert, wie viele Ziffern
*		im String enthalten sind.
*
* 2.	Schreiben sie eine Methode	'stringReverse' die einen String übergeben
*		bekommt, und ihn umdreht. Aus "umdrehen" wird "neherdmu".
*    
* 3.   Schreiben Sie eine Methode 'lTrim' die alle führenden Leerzeichen einer Zeichenkette entfernt.
*
* 4.   Schreiben Sie eine Methode 'rTrim' die alle abschliessenden Leerzeichen aus einer Zeichenkette
*      entfernt.
*
* 5.   Schreiben Sie eine Methode 'trim' die alle führenden und alle abschliessenden Leerzeichen einer
*      Zeichenkette entfernt.
*
* 6.   Erweitern sie die Trim-Methoden (lTrim, rTrim, trim) soweit, dass sie nicht nur Leerzeichen,
*      sondern auch jedes andere Zeichen aus der Zeichenkette entfernen können.   
*
*      Übungen mit Hilfe von String-Methoden:
*
* 7.   Schreiben sie eine Methode subStringCount, welche die Anzahl von Vorkommen einer angegebenen Zeichenfolge
*      zurückliefert. 
*   
*      Beispiel: "viel vor viel dahinter, noch viel viel mehr vor, aber viel viel weniger dahinter" enthält 
*                 6 mal die Zeichenfolge "viel".
*
*/	

public class StringMethoden
{

	public static void main(String[] args)
	{
		String eingabe, zeichen;
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("Methode digitCount(String)");
		System.out.println("Text eingeben:");
		
		eingabe = console.nextLine();
		System.out.println("Anzahl Ziffern: " + digitCount(eingabe));
		System.out.println();
		
		System.out.println("Methode stringReverse(String)");
		System.out.println("Text eingeben:");
		
		eingabe = console.nextLine();
		System.out.println("Ergebnis: " + stringReverse(eingabe));
		System.out.println();
		
		System.out.println("Methoden lTrim(String), rTrim(String), trim(String");
		System.out.println("Text eingeben:");
		
		eingabe = console.nextLine();
		System.out.println("Ergebnis lTrim(String): '" + lTrim(eingabe) + "'");
		System.out.println("Ergebnis rTrim(String): '" + rTrim(eingabe) + "'");
		System.out.println("Ergebnis trim(String): '" + trim(eingabe) + "'");
		System.out.println();
		
		System.out.println("Methoden lTrim(String, char), rTrim(String, char), trim(String, char");
		System.out.println("Text eingeben:");
		eingabe = console.nextLine();
		
		System.out.println("Zu entfernendes Zeichen:");
		zeichen = console.nextLine();
				
		System.out.println("Ergebnis lTrim(String, char): '" + lTrim(eingabe, zeichen.charAt(0)) + "'");
		System.out.println("Ergebnis rTrim(String, char): '" + rTrim(eingabe, zeichen.charAt(0)) + "'");
		System.out.println("Ergebnis trim(String, char): '" + trim(eingabe, zeichen.charAt(0)) + "'");
		System.out.println();
		
		System.out.println("Methode subStringCount(String, String)");
		System.out.println("Text eingeben:");
		eingabe = console.nextLine();
		
		System.out.println("Zu suchende Zeichenfolge eingeben");
		zeichen = console.nextLine();
		
		System.out.println("Ergebnis: " + subStringCount(eingabe, zeichen));
		
			
		console.close();

	}
	
	
	private static int digitCount(String s)
	{
		
		int retValue = 0;
		int length;
		String zahlen = "0123456789";
		
		int j;
		
		if (s == null)
			return -1;
		
		length = s.length();
		
//		for (int i = 0; i < length; i++)
//		{
//			if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
//				retValue++;
//			
//		}
		
		// oder
//		for (int i = 0; i < length; i++)
//		{
//			if (Character.isDigit(s.charAt(i)))
//				retValue++;
//			
//		}
		
		// oder		
//		for (int i = 0; i < length; i++)
//		{
//			if (zahlen.indexOf(s.charAt(i)) >= 0)
//				retValue++;
//		}

		
		// oder
		for (int i = 0; i < length; i++)
		{
			try
			{
				
				Integer.parseInt(String.valueOf(s.charAt(i)));
				retValue++;
				
			}
			catch (Exception ex)
			{
				
			}
			
		}
		
		return retValue;
		
	}
	
	
	private static String stringReverse(String s)
	{
		
//		String retValue = "";
//		
		if (s == null)
			return null;
//		
//		for (int i = s.length() - 1; i >= 0; i--)
//		{
//			retValue += s.charAt(i);
//		}
//		
//		
//		return retValue;
		
		// oder
		StringBuilder sb = new StringBuilder(s);
		return sb.reverse().toString();
		
	}
	
	private static String lTrim(String s)
	{
		
		int i;
		
		if (s == null)
			return null;
		
//		// Alle zu entfernenden Zeichen am Anfang der Zeichenkette zählen 
//		for (i = 0; i < s.length(); i++)
//		{
////			if (s.charAt(i) != ' ')
////				break;
//			
//			// oder
//			// Alle Leerzeichen, Tabulatoren, Zeilenschaltungen ... werden entfernt.
//			if (!Character.isWhitespace(s.charAt(i)))
//				break;
//			
//		}
//		
//		// Die Anzahl der ermittelten Zeichen am Anfang der Zeichenkette überspringen
//		// und den verbleibenden Rest der Zeichen zurückgeben.
//		return s.substring(i);
				
		
		// oder
		
		while(s.startsWith(" "))
		{
			
			s = s.substring(1);
			
		}
		
		return s;
		
		
	}
	
	private static String lTrim(String s, char c)
	{
		
		int i;
		
		if (s == null)
			return null;
		
//		// Alle zu entfernenden Zeichen am Anfang der Zeichenkette zählen 
//		for (i = 0; i < s.length(); i++)
//		{
////			if (s.charAt(i) != c)
////				break;
			
//			
//		}
//		
//		// Die Anzahl der ermittelten Zeichen am Anfang der Zeichenkette überspringen
//		// und den verbleibenden Rest der Zeichen zurückgeben.
//		return s.substring(i);
				
		
		// oder
		
		while(s.startsWith(Character.toString(c)))
		{
			
			s = s.substring(1);
			
		}
		
		return s;
		
		
	}
	
	private static String rTrim(String s)
	{
		
		int i;
		
		if (s == null)
			return null;
		
		// Alle zu entfernenden Zeichen am Ende der Zeichenkette zählen
		
//		for (i = s.length() - 1; i >= 0; i--)
//		{
//			if (s.charAt(i) != ' ')
//				break;
//		}
//		
//		return s.substring(0, i + 1);
		
		// oder
		
//		while(s.endsWith(" "))
//		{
//			
//			s = s.substring(0, s.length() - 1);
//			
//		}
//		
//		return s;
		
		
		// oder
		return stringReverse(lTrim(stringReverse(s)));
		
		
		
	}
	
	private static String rTrim(String s, char c)
	{
		
		int i;
		
		if (s == null)
			return null;
		
		// Alle zu entfernenden Zeichen am Ende der Zeichenkette zählen
		
//		for (i = s.length() - 1; i >= 0; i--)
//		{
//			if (s.charAt(i) != c)
//				break;
//		}
//		
//		return s.substring(0, i + 1);
		
		// oder
		
		while(s.endsWith(Character.toString(c)))
		{
			
			s = s.substring(0, s.length() - 1);
			
		}
		
		return s;
		
		
		// oder
//		return stringReverse(lTrim(stringReverse(s), c));
		
		
		
	}
	
	private static String trim(String s)
	{
		
		if (s == null)
			return null;
		
		// Die Methode trim() ist in der Klasse String vorhanden.
		// Sie entfernt allerdings nicht nur Leerzeichen sondern 'Whitespaces' (Leerzeichen, Tabulatoren, Zeilenschaltung...) 
		//return s.trim();
		
		// oder
		return lTrim(rTrim(s));
		
	}
	
	private static String trim(String s, char c)
	{
		
		return lTrim(rTrim(s, c), c);
		
	}
	
	private static int subStringCount(String s, String teil)
	{
		
//		int retValue = 0;
//		
//		if (s == null || teil == null)
//			return -1;
//		
//		if (s.isEmpty() || teil.isEmpty())
//			return retValue;
//		
//		
//		for (int i = 0; (i = s.indexOf(teil, i)) != -1; retValue++)
//		{
//			i += teil.length();
//		}
//		
//		return retValue;
		
		
		// oder
		return (s.length() - s.replace(teil, "").length()) / teil.length();
		
		
		
		
	}
	
}
