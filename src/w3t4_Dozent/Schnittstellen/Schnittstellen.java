package w3t4_Dozent.Schnittstellen;

// Bei Interfaces, oft auch direkt als Schnittstellen bezeichnet, handelt es sich um eine Abart der abstrakten Klassendeklaration.
// Sie enthält neben diversen Datenelementen lediglich abstrakte Methoden.
// Eine Schnittstelle dient in der objektorientierten Programmierung der Vereinbarung gemeinsamer Signaturen von Methoden, 
// die in unterschiedlichen Klassen implementiert werden um die Kompatibilität der Klassen zu gewährleisten. Die Schnittstelle 
// gibt dabei an, welche Methoden vorhanden sind oder vorhanden sein müssen.
// Eine Schnittstelle ist also eine Menge von Anforderungen an Klassen, die mit der Schnittstelle konform gehen wollen.
// Schnittstellen sind keine Klassen. Eine Schnittstelle kann man nicht mit dem Operator 'new' instanziieren.
//
// Schnittstellen werden auch für die Mehrfachvererbung eingesetzt, denn Klassen können beliebig viele dieser Schnittstellen
// implementieren. Als Ersatz für Mehrfachvererbung eignen sich Schnittstellen allerdings nicht, da sie lediglich Methoden 
// und deren Parameter definieren und keine Vererbung von Funktionalität ermöglichen.

// Alle in der Schnittstelle vorhandenen abstrakten Methoden müssen in der Klasse implementiert werden!

// Eine Schnittstelle enthält keine Implementierungen, sondern deklariert nur den Kopf einer Methode - also Modifizierer, den Rückgabetyp und
// die Signatur - ohne Rumpf.

public class Schnittstellen
{

	public static void main(String[] args)
	{
		
		Mitarbeiter m = new Mitarbeiter("Max Mustermann", 2000);
		System.out.println(m.getName());
		m.print();
		m.printerSetup();
		m.preview();
		System.out.println(m.varPrint);
		
		System.out.println();
		
		
		
		
		
		
		
		
		
		

	}

}
