package w1t5_Dozent;

/*
 * Methoden
 * 
 * Bestandteil einer Methode
 * Eine Methode setzt sich aus mehreren Bestandteilen zusammen. Dazu gehören der
 * Methodenkopf und der Methodenrumpf. Der Kopf besteht aus einem Rückgabetyp,
 * auch Ergebnistyp genannt, dem Methodennnamen und einer optionalen
 * Parameterliste.
 * 
 * Die Signatur einer Methode
 * Der Methodennname und die Parameterliste bestimmen die Signatur einer Methode;
 * der Rückgabetyp gehört nicht dazu. Die Parameterliste ist durch die Anzahl,
 * die Reihenfolge und die Typen der Parameter beschrieben. Pro Klasse darf es
 * nur eine Methode mit derselben Signatur geben.
 * 
 * Aufruf einer Methode
 * Da eine Methode immer einer Klasse oder einem Objekt zugeordnet ist, muss
 * der Eigentümer beim Aufruf angegeben werden. Nur wenn die Methode innerhalb
 * der eigenen Klasse verwendet wird kann der Eigentümer weggelassen werden.
 * Die aufgerufene Methode wird mit ihrem Namen genannt. Die Parameterliste
 * wird durch ein Klammerpaar umschlossen. Diese Klammern müssen auch dann
 * gesetzt werden, wenn die Methode keine Parameter enthält.
 * 
 * Statische Methoden (Klassenmethoden)
 * Statische Methoden müssen explizit mit dem Schlüsselwort 'static' kenntlich
 * gemacht werden. Fehlt der Modifizierer 'static', so wird damit eine Objektmethode
 * deklariert die nur aufgerufen werden kann, wenn mann vorher ein Objekt an-
 * gelegt (instanziiert) hat. 
 * Das Besondere an statischen Methoden ist, dass sie nicht an einem Objekt 
 * hängen und daher immer ohne explizit erzeugtes Objekt aufgerufen werden 
 * können.
 * 
 * 
 */

public class Modularisierung
{

	private static int p1 = 42;
	
	
	public static void main(String[] args)
	{
		System.out.println("Aufruf der Methode neueZeile()");
		neueZeile();
		System.out.println("Ende der Methode neueZeile()");
		
		System.out.println("Aufruf der Methode neueZeile(int)");
		neueZeile(5);
		System.out.println("Ende der Methode neueZeile(int)");
		
		neueZeile();
		
		
		int p1 = 12;
		m1(p1);
		// Die Änderung der Variable 'p1' in der Methode m1(int)
		// hat keine Auswirkungen auf die Variable 'p1'
		// der Methode main().
		System.out.println(p1);
		
		neueZeile();
		
		// Aufruf der Methode m1(), welche die globale Variable p1 verwendet.
		m1();
		System.out.println(Modularisierung.p1);
		System.out.println(p1);
		
		neueZeile();
		
		int result = mult(3, 4);
		System.out.println(result);
		
		neueZeile();
		
		System.out.println(mult(3, 4));
		System.out.println(mult(mult(3, 4), mult(8, 8)));
		
		neueZeile();
		
		optionaleParameter(1, 2 , 3, 4, 5, 6, 7, 8, 9, 10, p1, Modularisierung.p1);
		neueZeile();
		optionaleParameter(1, 2 , 3);
		neueZeile();
		optionaleParameter(1);
		neueZeile();
		optionaleParameter();
		
		neueZeile();
		optionaleParameter("alfatraining", 1, 2, 3);
		
		neueZeile();
		optionaleParameter(101, 102);
		
		
		neueZeile();
		
		// Beispiel short-circuit-evaluation
		
		if (methode1() == 1 && methode2() == 2)
			System.out.println("Die Bedingung ist wahr.");
		else
			System.out.println("Die Bedingung ist nicht wahr.");
		
		neueZeile();
		
		if (methode1() == 1 || methode2() == 2)
			System.out.println("Die Bedingung ist wahr.");
		else
			System.out.println("Die Bedingung ist nicht wahr.");
		
		neueZeile();
		
		if (methode1() == 2 && methode2() == 2)
			System.out.println("Die Bedingung ist wahr.");
		else
			System.out.println("Die Bedingung ist nicht wahr.");
			
	}

	
	private static void neueZeile()
	{
		System.out.println();
	}
	
	private static void neueZeile(int anzahl)
	{
//		for (int i = 1; i <= anzahl; i++)
//			System.out.println();
		
		// oder:
//		while (anzahl-- > 0)
//			System.out.println();
		
		// oder
		while (anzahl-- > 0)
			neueZeile();
		
	}
	
	
	private static void m1(int p1)
	{
		System.out.println("Dies ist die Methode m1(int)");
		System.out.println("Übergebener Parameter: " + p1);
		
		p1 = 100;
		
	}
	
	private static void m1()
	{
		
		System.out.println("Dies ist die Methode m1()");
		System.out.println("Globale Variable p1: " + p1);
		
		p1 = 100;
		
	}
	
	
	private static int mult(int p1, int p2)
	{
		
		System.out.println("Dies ist die Methode mult(int, int)");
		System.out.println("Übergebene Parameter: " + p1 + ", " + p2);
		
//		int retValue = p1 * p2;
//		
//		return retValue;
		
		return (p1 * p2);
		
		
	}
	
	// Methoden mit variabler Parameterliste.
	// Technisch entspricht die Deklaration der eines Arrays-Parameters, und so
	// wird auch auf die Elemente zugegriffen.
	// Dabei ist zu beachten, dass lediglich der letzte Parameter variabel sein
	// darf. Andernfalls könnte der Compiler
	// nicht mehr unterscheiden, welches aktuelle Argument zu welchem formalen
	// Parameter gehört.
	// Praktischen Nutzen haben die variablen Parameterlisten bei Anwendungen, in
	// denen nicht von vorneherein klar ist,
	// wieviele Argumente benötigt werden. Tatsächlich wurde ihre Entwicklung
	// durch den Wunsch motiviert, flexible Ausgabemethoden
	// definieren zu können, wie in anderen Programmierspachen auch (z.B.
	// System.out.printf()).
	
	
	private static void optionaleParameter(int ... parm)
	{
		for (int i = 0; i < parm.length; i++)
		{
			System.out.println(parm[i]);
		}
	}
	
	
	private static void optionaleParameter(String s, int ... parm)
	{
		
		System.out.println(s);
		
		for (int i = 0; i < parm.length; i++)
		{
			System.out.println(parm[i]);
		}
	}
	
	private static void optionaleParameter(int a, int b)
	{
		
		System.out.println("a: " + a + ", b: " + b);
		
	
	}
	
	
	private static int methode1()
	{
		System.out.println("Die Methode methode1() wird ausgeführt.");
		return 1;
	}
	
	private static int methode2()
	{
		System.out.println("Die Methode methode2() wird ausgeführt.");
		return 2;
	}
	
	
}
