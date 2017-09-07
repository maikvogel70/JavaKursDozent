package w2t5_Dozent;

/* 
 * Klassen sind Baupläne für Objekte; so wie ein Bauplan für ein Haus kein Haus ist,
 * ist eine Klasse etwas, was nicht direkt verwendet wird, sondern als Vorschrift
 * zur Erstellung von etwas dient, was man verwenden kann. Wenn sie ein Haus bauen,
 * dann bauen Sie ein neues Haus; wenn sie nach der Bauplan-Vorschrift einer Klasse ein
 * Objekt erstellen (im Speicher aufbauen), dann wird immer das Schlüsselwort 'new'
 * verwendet.
 * 
 * Klassen implementieren beliebig abstrakte, benutzerdefinierte Datentypen. 
 *
 * Das Ziel des Klassenkonzepts ist es, dem Programmierer die Möglichkeit zu geben,
 * neue Datentypen zu schaffen und sie so leicht wie Standard-Typen zu benutzen.
 * Zusätzlich stellen abgeleitete Klassen (Vererbung) Mechanismen bereit, die es
 * dem Programmierer erlauben, ihre Zusammenhänge auszunutzen.
 *
 * Die Verwendung von Klassen bietet mehrere Vorteile. Mit Hilfe geeigneter Zugriffsattribute 
 * lassen sich Daten verpacken und schützen. Man spricht von Kapselung. Die ideale Klasse sieht so aus, 
 * dass von anderen Klassen ausschließlich auf Methoden zugegriffen werden darf. 
 * Alle Eigenschaften - also Instanz- und Klassenvariablen - sind privat und können von außen 
 * nicht geändert werden. 
 *
 * Grundgedanke des Kapselungprinzips : Der Gültigkeitsbereich von Variablen muss so weit wie möglich 
 * eingeschränkt werden. Klassen sind das ideale Instrument, um diesen Grundsatz in Programmen umzusetzen: 
 * Wenn eine Klasse nur private Eigenschaften besitzt, können nur Methoden der Klasse selber Eigenschaften 
 * verändern. Treten in einem Programm Fehler auf, weil irgendwelche Eigenschaften einer Klasse plötzlich 
 * falsche Werte enthalten, so müssen im Idealfall nur die Methoden der Klasse nach dem Fehler durchsucht werden
 * - sie sind schließlich die einzigen,  die auf die Eigenschaften zugreifen dürfen. 
 *
 * Neben Kapselung unterstützen Klassen durch die Vererbung ein Konzept, das Wiederverwendung genannt wird. 
 * Wiederverwendung bedeutet, dass in einem Programm auf Code zurückgegriffen werden kann, der bereits 
 * anderweitig geschrieben wurde. Das Konzept der Wiederverwendung spart nicht nur Arbeit, sondern beugt auch 
 * Fehlern vor. Anstatt dass jeder Programmierer den gleichen Code für sich neu entwickelt, greifen alle 
 * auf den gleichen Code zu. Das bedeutet, jeder Programmierer kann sich wirklich auf neue Aufgaben konzentrieren 
 * und verläßt sich darauf, dass der wiederverwendete Code korrekt arbeitet. 
 * 
 * Der Punkt-Operator ist dazu da, ein Memberelement einer Klasse über eine
 * Objektreferenz anzusprechen.
 *
 *     objektName.memberdatum bzw. objektName.membermethode()
 *
 * Der oder die Konstruktor(en) einer Klasse dient bzw. dienen dem ausdrücklichen
 * Zweck, Objekte -- also Instanzen bzw. konkrete Ausprägungen -- einer Klasse zu
 * initialisieren. Ein Konstruktor einer Klasse ist dadurch gekennzeichnet, dass er
 * denselben Namen wie die zugehörige Klasse trägt.
 * 
 * Neben Konstruktoren gibt es in Java auch Destruktoren. 
 * Ein Destruktor wird als parameterlose Methode mit dem Namen finalize definiert. 
 * 
 * Der Destruktor wird aufgerufen, bevor der Garbage Collector das entsprechende
 * Objekt vom Heap entfernt, d.h. beim Aufruf des Destruktors ist das Objekt "noch
 * so gerade lebendig".
 * 
 * Anders als in C++ ist die Verwendung von Destruktoren in Java eher selten. In C++
 * sind Destruktoren hauptsächlich damit beschäftigt, dynamisch allokierten Speicher
 * freizugeben, ein Problem, was in Java vom Garbage Collector gelöst wird.
 * 
 */

public class Klassen
{

	public static void main(String[] args)
	{
		
		MeineKlasse mk = new MeineKlasse();
		
		mk.a = 42;
		
		System.out.println("MeineKlasse.a: " + mk.a);
		System.out.println();
		
		
		Tag.setDefaultTag(15);
		
		
		Tag t1 = new Tag();
		System.out.println("Tag: " + t1.getTag());
		
		t1.setTag(18);

		System.out.println("Tag: " + t1.getTag());
		
		t1.setTag(99);
		System.out.println("Tag: " + t1.getTag());
		System.out.println();
		
		Tag t2 = new Tag(99);
		System.out.println("Tag: " + t2.getTag());
		System.out.println();
		
		System.out.println(t2.getDefaultTag());
		
		
		
		

	}

}
