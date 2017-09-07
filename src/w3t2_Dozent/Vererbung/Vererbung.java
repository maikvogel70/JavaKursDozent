package w3t2_Dozent.Vererbung;

import java.util.LinkedList;
import java.util.List;

/* Die Klassen in Java sind in einer Hierarchie geordnet. Von Object erben automatisch alle Klassen, direkt oder indirekt. 
 * Eine neu definierte Klasse kann durch das Schlüsselwort extends eine Klasse erweitern. 
 * Sie wird dann zur Unter- oder Subklasse beziehungsweise Kindklasse. Die Klasse, von der die Unterklasse erbt, heißt 
 * Oberklasse (auch Superklasse oder Elternklasse). 
 * Durch den Vererbungsmechanismus werden alle sichtbaren Eigenschaften der Oberklasse auf die Unterklasse übertragen. 
 * Eine Oberklasse vererbt also Eigenschaften, und die Unterklasse erbt sie.
 * Syntaktisch wird die Vererbung durch das Schlüsselwort extends beschrieben.
 *
 */

/* In Java ist auf direktem Weg nur die Einfachvererbung (engl. single inheritance) erlaubt. 
 * In der Einfachvererbung kann eine Klasse lediglich eine andere erweitern. 
 * In Programmiersprachen wie C++ können auch mehrere Klassen zu einer neuen verbunden werden. 
 * Dies bringt aber einige Probleme mit sich, die in Java vermieden werden. 
 * 
 * Beispiel:
 * Nehmen wir an, die Klassen K1 und K2 definieren beide eine öffentliche Funktion f(), und V1 ist eine Klasse, 
 * die von K1 und K2 erbt. Steht in V1 ein Funktionsaufruf f(), ist nicht klar, welche der beiden Funktion gemeint ist. 
 * In C++ löst der Bereichsoperator (::) das Problem, in dem der Entwickler immer angibt, aus welcher Oberklasse 
 * die Funktion anzusprechen ist.
 * 
 */

/*
 * Eine Unterklasse erbt neben den Variablen auch die Methoden der Superklasse. Oft ist das vorgegebene Verhalten 
 * der geerbten Methode in der Unterklasse nicht mehr adäquat, sie muss modifiziert werden. In diesem Fall besteht
 * die Möglichkeit, die geerbte Methode zu überschreiben.
 * Beim Überschreiben oder Overriding von Methoden besitzt eine Methode in einer Unterklasse den Namen und die 
 * Parameterliste einer geerbten Methode. Durch diese neue Methode in der Unterklasse wird die ursprüngliche Methode
 * der Superklasse ersetzt.
 * Alle öffentlichen Methoden können in Java überschrieben werden. Zusätzliche Attribute wie 'virtual' in C++ oder C#
 * oder 'overridable' in VB.Net werden nicht benötigt.   
 * 
 * Ein Überschreiben von Methoden kann mit 'final' verhindert werden (final public void Methode()).  
 * 
 * Klassen die mit 'final' deklariert wurden erlauben keine Spezialisierung durch eine Unterklasse, d.h. von ihnen
 * kann keine Klasse mit 'extends' abgeleitet werden.   
 */

public class Vererbung
{

	public static void main(String[] args)
	{

		// Demo Beispiel K1, k2, V1, V2

		V1 v1 = new V1();
		v1.f();

		V2 v2 = new V2();
		v2.f();

		System.out.println();

		Tier t = new Tier();
		t.fressen();

		Wolf w = new Wolf();
		w.fressen();
		w.geschlecht();

		Polarwolf pw = new Polarwolf();
		pw.fressen();
		pw.geschlecht();
		
		System.out.println();
		
		WeisserSchaeferhund ws = new WeisserSchaeferhund();
		ws.fressen();
		

		System.out.println();
		
		
		// Polymorphie bei Vererbung.
		// Objektvariablen können Objekte unterschiedlicher Klassen aufnehmen.
		// Das ist allerdings beschränkt auf die Oberklasse und die
		// davon abgeleiteten Klassen. Beispiel aus der Vererbung: Tier -> Wolf
		// -> Polarwolf... oder Tier -> Fisch -> Goldfisch...
		// Der Wolf besitzt alle Eigenschaften von Tier. Dass der Wolf noch ein
		// paar zusätzliche Eigenschaften besitzt stört den Kompiler nicht.
		
		// Hinzufügen von unterschiedlichen Tieren, die alle von der Basisklasse
		// 'Tier' abgeleitet wurden.
		
		List<Tier> tiere = new LinkedList<>();
		
		tiere.add(new Tier());
		tiere.add(new Wolf());
		tiere.add(new Polarwolf());
		tiere.add(new WeisserSchaeferhund());
		tiere.add(new Fisch());
		tiere.add(new Goldfisch());
		tiere.add(new AnderesTier());
		
		
		// Ausgabe
		for (Tier aktuellesTier : tiere)
		{
			System.out.println(aktuellesTier.getClass());
			aktuellesTier.fressen();
			
			// Wenn 'aktuellesTier' eine Instanz von 'Wolf' enthält oder ist 
			if (aktuellesTier instanceof Wolf)
				((Wolf)aktuellesTier).geschlecht();
			
			if (aktuellesTier instanceof Polarwolf)
				((Polarwolf)aktuellesTier).farbe();
			
			System.out.println();
		}
		
		
		System.out.println();
		
		// Das Konvertieren von 'Polarwolf' in die Basisklasse 'Tier'
		// hat keinen Einfluss, auf die zu diesem Zeitpunkt von
		// 'Wolf' überschriebenen Methode fressen().
		// Die originalen Methoden der Klasse 'Tier' sind überschrieben
		// und stehen nicht mehr zur Verfügung (late Binding).
		
		pw = new Polarwolf();
		System.out.println(pw.getClass());
		pw.fressen();
		
		t = (Tier)pw;
		System.out.println(t.getClass());
		t.fressen();
		
		
		
		
		
		
		
		
		
		
	}

}
