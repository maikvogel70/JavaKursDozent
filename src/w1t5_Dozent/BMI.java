package w1t5_Dozent;

import java.util.Scanner;

public class BMI
{

	public static void main(String[] args)
	{
		double bmi, gewicht, groesse;
		

		Scanner console = new Scanner(System.in);
		
		System.out.println("Berechnung des BMI (Body Mass Index)");
		System.out.print("Bitte geben Sie ihr Gewicht in Kilogramm ein: ");
		
		gewicht = console.nextDouble();
		
		System.out.print("\nBitte geben Sie ihre Grösse in Meter ein: ");
		groesse = console.nextDouble();
		
		console.close();
		
		//bmi = gewicht / (groesse * groesse);
		// oder
		bmi = gewicht / Math.pow(groesse, 2);
		
		System.out.printf("\nBerechneter BMI: %1.2f\n", bmi);
		
		if (bmi < 10 || bmi >= 50)
			System.out.println("Bitte Überprüfen Sie ihre Eingabewerte.\n");
		else if (bmi < 15)
			System.out.println("Magersucht\n");
		else if (bmi < 20)
			System.out.println("Untergewicht\n");
		else if (bmi < 25)
			System.out.println("Normalgewicht\n");
		else if (bmi < 30)
			System.out.println("Übergewicht\n");
		else if (bmi < 40)
			System.out.println("Fettsucht\n");
		else
			System.out.println("Morbide Fettsucht\n");
		
	}

}
