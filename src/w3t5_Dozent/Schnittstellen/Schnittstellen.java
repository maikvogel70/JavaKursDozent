package w3t5_Dozent.Schnittstellen;

import java.util.Arrays;

// Bei Interfaces, oft auch direkt als Schnittstellen bezeichnet, handelt es sich um eine Abart der abstrakten Klassendeklaration.
// Sie enth�lt neben diversen Datenelementen lediglich abstrakte Methoden.
// Eine Schnittstelle dient in der objektorientierten Programmierung der Vereinbarung gemeinsamer Signaturen von Methoden, 
// die in unterschiedlichen Klassen implementiert werden um die Kompatibilit�t der Klassen zu gew�hrleisten. Die Schnittstelle 
// gibt dabei an, welche Methoden vorhanden sind oder vorhanden sein m�ssen.
// Eine Schnittstelle ist also eine Menge von Anforderungen an Klassen, die mit der Schnittstelle konform gehen wollen.
// Schnittstellen sind keine Klassen. Eine Schnittstelle kann man nicht mit dem Operator 'new' instanziieren.
//
// Schnittstellen werden auch f�r die Mehrfachvererbung eingesetzt, denn Klassen k�nnen beliebig viele dieser Schnittstellen
// implementieren. Als Ersatz f�r Mehrfachvererbung eignen sich Schnittstellen allerdings nicht, da sie lediglich Methoden 
// und deren Parameter definieren und keine Vererbung von Funktionalit�t erm�glichen.

// Alle in der Schnittstelle vorhandenen abstrakten Methoden m�ssen in der Klasse implementiert werden!

// Eine Schnittstelle enth�lt keine Implementierungen, sondern deklariert nur den Kopf einer Methode - also Modifizierer, den R�ckgabetyp und
// die Signatur - ohne Rumpf.

// Mit Java 8 wurden jedoch Methoden eingef�hrt, die nicht zwingend
// implementiert werden m�ssen und deshalb auch keine nachtr�gliches �ndern
// der Klassen erforderlich machen.

// Ab Java 8 lassen sich in Schnittstellen auch statische Methoden als
// Hilfsmethoden implementieren. Diese Methoden besitzen, im Gegensatz zu 
// abstrakten Methoden einen Methodenrumpf.
// Ein Zugriff auf die statische Schnittstellenmethode ist ausschliesslich �ber den Namen
// der Schnittstelle m�glich. Sie muss auch nicht implementiert werden.
// Bei statischen Methoden von Klassen ist im Prinzip auch ein Zugriff �ber eine Referenz
// erlaubt. Bei statischen Methoden einer Schnittstelle ist das nicht zul�ssig.

// Default Methoden
// Ist eine Schnittstelle einmal verbreitet, so sollte es dennoch m�glich sein, Operationen hinzuzuf�gen.
// Java 8 bringt daf�r eine Sprach�nderung mit die es erlaubt, Operationen einzuf�gen, ohne das die Unterklassen
// verpflichtet werden diese Methoden zu implementieren.
// Eine Default-Methode unterscheidet sich syntaktisch in 2 Dingen von herk�mmlichen implizit abstrakten
// Methodendeklarationen:
// 1. Die Deklaration einer Default-Methode beginnt mit dem Schl�sselwort 'default'.
// 2. Statt eines Semikolons markiert bei einer Default-Methode ein Block von geschweiften Klammern das
//    Ende der Deklaration (sie enth�lt einen Methodenrumpf).


// Schnittstellen, die statische oder Default-Methoden enthalten werden als funktionale Schnittstellen bezeichnet.

public class Schnittstellen
{

	public static void main(String[] args)
	{
		
		// �ber die Objectvariable m ist die konkrete Methode getName() verf�gbar, sowie alle
		// Methoden der Schnittstelle 'IPrintable' und 'IPrintSetup' und
		// die Variable 'varPrint'.
		Mitarbeiter m = new Mitarbeiter("Max Mustermann", 2000);
		System.out.println(m.getName());
		m.print();
		m.printerSetup();
		m.preview();
		System.out.println(m.varPrint);
		
		System.out.println(IPrintable.getDefaultPrinter());
		System.out.println(m.pause());
		
		
		System.out.println();
		
		
		AbstractMitarbeiter m2 = new Mitarbeiter("Mitarbeiter 2", 2100);
		
		// �ber m2 ist die konkrete Methode getName() verf�gbar, sowie die
		// abstrakte Methode getGehalt(). 
		
		// Eine Schnittstellenvariable kann auf ein Objekt einer Klasse
		// verweisen, welches die Schnittstelle implementiert hat.
		IPrintable m3 = new Mitarbeiter("Mitarbeiter 3", 2000);
		
		// �ber m3 sind nur die Methoden der Schnittstelle 'IPrintable'
		// verf�gbar und die Variable 'varPrint'.
		
		
		IPrintSetup m4 = new Mitarbeiter("Mitarbeiter 4", 1800);
		// �ber m4 sind nur die Methoden der Schnittstelle 'IPrintSetup'
		// verf�gbar.
		
		
		Object m5 = new Mitarbeiter("Mitarbeiter 5", 1900);
		
		// Mit m5 sind alle Bez�ge zu dem Mitarbeiter Objekt verloren.
		
		System.out.println(m5);
		
		// Durch eine Konvertierung von Objekt in Mitarbeiter kann auch wieder
		// auf die Methoden von Mitarbeiter zugegriffen werden.
		if (m5 instanceof Mitarbeiter)
			System.out.println(((Mitarbeiter)m5).getGehalt());
		
		
		if (m5 instanceof IPrintable)
			System.out.println(((IPrintable)m5).varPrint);
		
		System.out.println();
		
		Mitarbeiter ma1 = new Mitarbeiter("Mitarbeiter 1", 1800);
		Mitarbeiter ma2 = new Mitarbeiter("Mitarbeiter 2", 2100);
		Mitarbeiter ma3 = new Mitarbeiter("Mitarbeiter 3", 2000);
		Mitarbeiter ma4 = new Mitarbeiter("Mitarbeiter 4", 1800);
		Mitarbeiter ma5 = new Mitarbeiter("Mitarbeiter 5", 1900);
		Mitarbeiter ma6 = new Mitarbeiter("Mitarbeiter 6", 1200);
		
		Mitarbeiter ma7 = new Mitarbeiter("Mitarbeiter 6", 1200);
		
		
		Mitarbeiter[] arrMitarbeiter = new Mitarbeiter[]
				{ ma6, ma2, ma4, ma3, ma5, ma1 };
		
		Arrays.sort(arrMitarbeiter);
		System.out.println(Arrays.toString(arrMitarbeiter));
		
		// Absteigende Sortierung nach Name
		Mitarbeiter.sort(arrMitarbeiter, Mitarbeiter.SortField.NAME, Mitarbeiter.SortOrder.DESCENDING);
		System.out.println(Arrays.toString(arrMitarbeiter));
		
		
		// Aufteigende Sortierung nach Gehalt
		Mitarbeiter.sort(arrMitarbeiter, Mitarbeiter.SortField.GEHALT, Mitarbeiter.SortOrder.ASCENDING);
		System.out.println(Arrays.toString(arrMitarbeiter));
		
		
		
//		// HasCodes ausgeben
//		for (Mitarbeiter ma : arrMitarbeiter)
//			System.out.println(ma + ", " + ma.hashCode());
//		
//		System.out.println(ma7 + ", " + ma7.hashCode());
//		
//		System.out.println();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
