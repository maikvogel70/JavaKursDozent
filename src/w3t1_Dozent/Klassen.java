package w3t1_Dozent;

import w2t5_Dozent.MeineKlasse;

/* 
 * Klassen sind Baupl�ne f�r Objekte; so wie ein Bauplan f�r ein Haus kein Haus ist,
 * ist eine Klasse etwas, was nicht direkt verwendet wird, sondern als Vorschrift
 * zur Erstellung von etwas dient, was man verwenden kann. Wenn sie ein Haus bauen,
 * dann bauen Sie ein neues Haus; wenn sie nach der Bauplan-Vorschrift einer Klasse ein
 * Objekt erstellen (im Speicher aufbauen), dann wird immer das Schl�sselwort 'new'
 * verwendet.
 * 
 * Klassen implementieren beliebig abstrakte, benutzerdefinierte Datentypen. 
 *
 * Das Ziel des Klassenkonzepts ist es, dem Programmierer die M�glichkeit zu geben,
 * neue Datentypen zu schaffen und sie so leicht wie Standard-Typen zu benutzen.
 * Zus�tzlich stellen abgeleitete Klassen (Vererbung) Mechanismen bereit, die es
 * dem Programmierer erlauben, ihre Zusammenh�nge auszunutzen.
 *
 * Die Verwendung von Klassen bietet mehrere Vorteile. Mit Hilfe geeigneter Zugriffsattribute 
 * lassen sich Daten verpacken und sch�tzen. Man spricht von Kapselung. Die ideale Klasse sieht so aus, 
 * dass von anderen Klassen ausschlie�lich auf Methoden zugegriffen werden darf. 
 * Alle Eigenschaften - also Instanz- und Klassenvariablen - sind privat und k�nnen von au�en 
 * nicht ge�ndert werden. 
 *
 * Grundgedanke des Kapselungprinzips : Der G�ltigkeitsbereich von Variablen muss so weit wie m�glich 
 * eingeschr�nkt werden. Klassen sind das ideale Instrument, um diesen Grundsatz in Programmen umzusetzen: 
 * Wenn eine Klasse nur private Eigenschaften besitzt, k�nnen nur Methoden der Klasse selber Eigenschaften 
 * ver�ndern. Treten in einem Programm Fehler auf, weil irgendwelche Eigenschaften einer Klasse pl�tzlich 
 * falsche Werte enthalten, so m�ssen im Idealfall nur die Methoden der Klasse nach dem Fehler durchsucht werden
 * - sie sind schlie�lich die einzigen,  die auf die Eigenschaften zugreifen d�rfen. 
 *
 * Neben Kapselung unterst�tzen Klassen durch die Vererbung ein Konzept, das Wiederverwendung genannt wird. 
 * Wiederverwendung bedeutet, dass in einem Programm auf Code zur�ckgegriffen werden kann, der bereits 
 * anderweitig geschrieben wurde. Das Konzept der Wiederverwendung spart nicht nur Arbeit, sondern beugt auch 
 * Fehlern vor. Anstatt dass jeder Programmierer den gleichen Code f�r sich neu entwickelt, greifen alle 
 * auf den gleichen Code zu. Das bedeutet, jeder Programmierer kann sich wirklich auf neue Aufgaben konzentrieren 
 * und verl��t sich darauf, dass der wiederverwendete Code korrekt arbeitet. 
 * 
 * Der Punkt-Operator ist dazu da, ein Memberelement einer Klasse �ber eine
 * Objektreferenz anzusprechen.
 *
 *     objektName.memberdatum bzw. objektName.membermethode()
 *
 * Der oder die Konstruktor(en) einer Klasse dient bzw. dienen dem ausdr�cklichen
 * Zweck, Objekte -- also Instanzen bzw. konkrete Auspr�gungen -- einer Klasse zu
 * initialisieren. Ein Konstruktor einer Klasse ist dadurch gekennzeichnet, dass er
 * denselben Namen wie die zugeh�rige Klasse tr�gt.
 * 
 * Neben Konstruktoren gibt es in Java auch Destruktoren. 
 * Ein Destruktor wird als parameterlose Methode mit dem Namen finalize definiert. 
 * 
 * Der Destruktor wird aufgerufen, bevor der Garbage Collector das entsprechende
 * Objekt vom Heap entfernt, d.h. beim Aufruf des Destruktors ist das Objekt "noch
 * so gerade lebendig".
 * 
 * Anders als in C++ ist die Verwendung von Destruktoren in Java eher selten. In C++
 * sind Destruktoren haupts�chlich damit besch�ftigt, dynamisch allokierten Speicher
 * freizugeben, ein Problem, was in Java vom Garbage Collector gel�st wird.
 * 
 */

public class Klassen
{

	public static void main(String[] args)
	{
		

		// Die Klasse 'Punkt' verwenden
		Punkt p1 = new Punkt(2, 4);
		System.out.printf("P1 = (%d, %d)\n", p1.x, p1.y);
		System.out.println();
		
		
		// Die Klasse 'Rechteck' verwenden
		
		Rechteck r =  new Rechteck(2, 4, 100, 200);
		System.out.printf("Rechteck Punkt P0 = (%d, %d)\n", r.p0.x, r.p0.y);
		System.out.printf("Rechteck Punkt P1 = (%d, %d)\n", r.p1.x, r.p1.y);
		System.out.printf("Rechteck X        = %d \n", r.getX());
		System.out.printf("Rechteck Y        = %d \n", r.getY());
		System.out.printf("Rechteck Breite   = %d \n", r.getBreite());
		System.out.printf("Rechteck H�he     = %d \n", r.getHoehe());
		System.out.printf("Rechteck Fl�che   = %d \n", r.getFlaeche());
		System.out.println();
		
		
		// Verwenden einer Klasse aus einem anderen Package.
		// Eclipse schl�gt automat. die entsprechenden Import-Anweisung vor.
		MeineKlasse mk = new MeineKlasse();
	
		
		// Die Klasse 'Farbe' verwenden
		Farbe f1 = new Farbe(255, 0, 0);
		
		
		// Die Klasse 'Gebilde' verwenden
		//Gebilde g1 = new Gebilde(2, 4, 100, 200, f1);
		// oder
		Gebilde g1 = new Gebilde(2, 4, 100, 200, new Farbe(0, 255, 0));
		
		Farbe f2 = g1.getFarbe();
		f2.rot = 255;
		
		System.out.printf("Gebilde, Rechteck, Punkt P0 = (%d, %d)\n", g1.getRechteck().p0.x, g1.getRechteck().p0.y);
		System.out.printf("Gebilde, Rechteck, Punkt P1 = (%d, %d)\n", g1.getRechteck().p1.x, g1.getRechteck().p1.y);
		System.out.printf("Gebilde, Rechteck, Breite   = %d\n", g1.getRechteck().getBreite());
		System.out.printf("Gebilde, Rechteck, H�he     = %d\n", g1.getRechteck().getHoehe());
		System.out.printf("Gebilde, Rechteck, Fl�che   = %d\n", g1.getRechteck().getFlaeche());
		System.out.printf("Gebilde, Farbe              = (%d, %d, %d)\n", g1.getFarbe().rot, g1.getFarbe().gruen, g1.getFarbe().blau);
		
		System.out.println();
		
		
		// Innere Klassen
		
		Haus h = new Haus();                             // Exemplar von Haus erstellen
		Haus.Zimmer z = h.new Zimmer();                  // Innerhalb des Hauses ein Exemplar von Zimmer erstellen. 
		Haus.Zimmer.Schrank s = z.new Schrank();         // Innerhalb des Zeimmers ein Exemplar von Schrank erstellen.
		
		s.ausgabe();
		
		// Ausgabe
		// Schrank
		// Schrank
		// Zimmer
		// Haus
		// Mantel im Schrank
		
		System.out.println();
		
		
		// Wrapper-Objekte sind �immutable� (unver�nderbar).
		// Ist ein Wrapper-Objekt erst einmal erzeugt, l�sst sich der im
		// Wrapper-Objekt gespeicherte Wert nachtr�glich
		// nicht mehr ver�ndern.
		// Um dies auch wirklich sicherzustellen, sind die konkreten
		// Wrapper-Klassen allesamt final.
		// Die Wrapper-Klassen sind nur als Ummantelung und nicht als
		// vollst�ndiger Datentyp gedacht.
		// Da sich der Wert nicht mehr �ndern l�sst, hei�en Objekte mit dieser
		// Eigenschaft auch Werte-Objekte.

		Integer i1 = 41;
		// oder
		Integer i2 = new Integer(10);
		System.out.println("Integer1 : " + i1 + ", Integer2: " + i2);
		
		swap(i1, i2);
		System.out.println("Integer1 : " + i1 + ", Integer2: " + i2);
		
		System.out.println();
		
		
		MyInteger a = new MyInteger(42);
		MyInteger b = new MyInteger(10);
		System.out.println("MyInteger a : " + a.getValue() + ", MyInteger b: " + b.getValue());
		
		swap(a, b);
		System.out.println("MyInteger a : " + a.getValue() + ", MyInteger b: " + b.getValue());
		
		System.out.println();
		
		// Template Klassen (generische Klassen)
		GenClass<Integer> swapInteger1 = new GenClass<>(22);
		GenClass<Integer> swapInteger2 = new GenClass<>(99);
		
		System.out.println("SwapInteger 1: " + swapInteger1.getValue() + ", SwapInteger 2: " + swapInteger2.getValue());
		swap(swapInteger1, swapInteger2);
		System.out.println("SwapInteger 1: " + swapInteger1.getValue() + ", SwapInteger 2: " + swapInteger2.getValue());
		System.out.println();
		
		GenClass<Double> swapDouble1 = new GenClass(22.11);
		GenClass<Double> swapDouble2 = new GenClass(99.55);
		System.out.println("swapDouble 1: " + swapDouble1.getValue() + ", swapDouble 2: " + swapDouble2.getValue());
		swap(swapDouble1, swapDouble2);
		System.out.println("swapDouble 1: " + swapDouble1.getValue() + ", swapDouble 2: " + swapDouble2.getValue());
		System.out.println();

		GenClass<String> swapString1 = new GenClass<>("Links");
		GenClass<String> swapString2 = new GenClass<>("Rechts");
		System.out.println("swapString 1: " + swapString1.getValue() + ", swapString 2: " + swapString2.getValue());
		swap(swapString1, swapString2);
		System.out.println("swapString 1: " + swapString1.getValue() + ", swapString 2: " + swapString2.getValue());
		System.out.println();
		
		GenClass<Farbe> swapFarbe1 = new GenClass<>(new Farbe(255, 0, 0));
		GenClass<Farbe> swapFarbe2 = new GenClass<>(new Farbe(0, 0, 255));
		
		System.out.println("swapFarbe1: " + swapFarbe1.getValue().getFarbe() + ", swapFarbe2: " + swapFarbe2.getValue().getFarbe());
		swap(swapFarbe1, swapFarbe2);
		System.out.println("swapFarbe1: " + swapFarbe1.getValue().getFarbe() + ", swapFarbe2: " + swapFarbe2.getValue().getFarbe());
		
		
	}
	
	
	
	private static void swap(Integer a, Integer b)
	{
		
		Integer i = a;
		
		a = b;
		b = i;
		
	}
	
	private static void swap(MyInteger a, MyInteger b)
	{
		
		int i = a.getValue();
		
		a.setValue(b.getValue());
		b.setValue(i);
		
	}
	
	
	private static void  swap(GenClass a, GenClass b)
	{
		Object obj = a.getValue();
		
		a.setValue(b.getValue());
		b.setValue(obj);
		
	}
	
	
	

}
