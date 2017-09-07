package w2t1_Dozent;

import java.util.Scanner;

// Schreiben Sie eine Methode Maximum der zwei int-Werte übergeben werden und die
// den größten der beiden Werte zurückliefert.

// Schreiben Sie eine Methode Maximum der drei int-Werte übergeben werden und die
// den größten der drei Werte zurückliefert.
// Versuchen Sie zuerst eine Lösung mit if (und else). 
// Anschließend versuchen Sie eine Lösung nur mit Hilfe des
// ?:-Operators.

// Versuchen Sie eine weitere Methode Maximum mit drei int-Werten zu programmieren indem
// in der Sie zu Lösung nur die erste Maximum-Funktion (mit 2 Argumenten) verwenden.

public class Methoden
{

	public static void main(String[] args)
	{
		int zahl1, zahl2, zahl3;
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("Bitte Zahl 1 eingeben:");
		zahl1 = console.nextInt();
		
		System.out.println("Bitte Zahl 2 eingeben:");
		zahl2 = console.nextInt();

		System.out.println("Bitte Zahl 3 eingeben:");
		zahl3 = console.nextInt();
		
		System.out.println("Maximum(" + zahl1 + ", " + zahl2 + ") -> " + maximum(zahl1, zahl2));
		System.out.println("Maximum(" + zahl1 + ", " + zahl2 + ", " + zahl3 + ") -> " + maximum(zahl1, zahl2, zahl3));
		System.out.println("Maximum1(" + zahl1 + ", " + zahl2 + ", " + zahl3 + ") -> " + maximum1(zahl1, zahl2, zahl3));
		System.out.println("Maximum2(" + zahl1 + ", " + zahl2 + ", " + zahl3 + ") -> " + maximum2(zahl1, zahl2, zahl3));

		console.close();
		
		System.out.println();
		
		System.out.println(Math.max(10, 10));
		System.out.println(Math.min(10, 10));

	}
	
	private static int maximum(int a, int b)
	{
		
//		int retValue = a;
//		
//		if (b > a)
//			retValue = b;
//		
//		return retValue;
		
		// oder
		return (a > b) ? a : b;
		
		
	}
	
	// Mit if-else
	private static int maximum(int a, int b, int c)
	{
		
		int retValue = c;
		
		if (a >= b && a >= c)
			retValue = a;
		else if (b >= a && b >= c)
			retValue = b;
	
		return retValue;
	}
	
	// Mit Bedingungsoperator
	private static int maximum1(int a, int b, int c)
	{
		
		return (a >= b && a >= c) ? a : (b >= a && b >= c) ? b : c;
		
	}
	
	private static int maximum2(int a, int b, int c)
	{
		return maximum(a, maximum(b, c));
	}
	
}
