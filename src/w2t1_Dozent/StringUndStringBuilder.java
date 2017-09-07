package w2t1_Dozent;

import java.text.NumberFormat;
import java.util.Scanner;

public class StringUndStringBuilder
{

	public static void main(String[] args)
	{
		
		Scanner console = new Scanner(System.in);

		long startZeit, endeZeit;
		int maxLoop = 50000;
		
		String str;
		
		
		System.out.println("Eingabetaste bestätigen, zum Starten der String-Methode...");
		console.nextLine();
		
		startZeit = System.currentTimeMillis();
		str = stringMethode(maxLoop);
		endeZeit = System.currentTimeMillis();
		
		System.out.println("\nFertig mit String: Dauer " + NumberFormat.getInstance().format(endeZeit - startZeit) + " Millisekunden");
		System.out.println(str.substring(0,  200));
		System.out.println("Länge: " + NumberFormat.getInstance().format(str.length()));
		System.out.println();
		
		System.out.println("Eingabetaste bestätigen, zum Starten der StringBuilder-Methode...");
		console.nextLine();
		
		startZeit = System.currentTimeMillis();
		str = stringBuilderMethode(maxLoop);
		endeZeit = System.currentTimeMillis();
		
		System.out.println("\nFertig mit StringBuilder: Dauer " + NumberFormat.getInstance().format(endeZeit - startZeit) + " Millisekunden");
		System.out.println(str.substring(0,  200));
		System.out.println("Länge: " + NumberFormat.getInstance().format(str.length()));
		System.out.println();

	}
	
	private static String stringMethode(int maxLoop)
	{
		String str = "";
		
		for (int i = 1; i <= maxLoop; i++)
		{
			str = str + "Ausgabe: " + i + ", ";
		}
		
		return str;
		
	}
	
	
	private static String stringBuilderMethode(int maxLoop)
	{
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 1; i <= maxLoop; i++)
		{
			sb.append("Ausgabe: ").append(i).append(", ");
			
		}
		
		return sb.toString();
		
	}
	
	
	

}
