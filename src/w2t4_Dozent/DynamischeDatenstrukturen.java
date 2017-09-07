package w2t4_Dozent;

import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/*
 * Dynamische Datenstrukturen (Kollektionen) passen ihre Grösse der Anzahl
 * der Daten an, die sie aufnehmen.
 * 
 * Die wichtigsten Datenstrukturen sind - Listen, - Mengen, - Kellerspeicher
 * (Stapel) und - Assoziativspeicher.
 * 
 * Eine der größten Neuerungen, die die Java-Plattform eingeführt hat, ist
 * die Collection-API. Eine Collection oder auch Container ist ein Objekt, 
 * welches wiederum Objekte aufnimmt und die Verantwortung für die Elemente
 * übernimmt. Wir werden die Begriffe »Container« und »Collection« synonym verwenden.
 * 
 * Collection ist die Basis der Hierarchie, die bis auf die
 * Assoziativspeicher alle Datenstrukturen implementieren. Durch die
 * gemeinsame Schnittstelle Collection erhalten alle implementierenden
 * Klassen einen gemeinsamen, äußeren Rahmen. Mit den dort definierten
 * Operationen lassen sich Elemente hinzufügen, löschen, selektieren und
 * finden.
 */

public class DynamischeDatenstrukturen
{

	private static HashMap<String, String> PLZTable;
	
	
	
	public static void main(String[] args)
	{
		
		// 1. LinkedList
		// LinkedList ist eine doppelt verkettete Liste, also eine Liste von Einträgen
		// mit einer Referenz auf den jeweiligen Nachfolger und Vorgänger. 
		// Das ist nützlich beim Einfügen und Löschen von Elementen an beliebigen
		// Stellen innerhalb der Liste.
		
		// Ein großes Problem mit Datenstrukturen bis Java 5 ist, dass sie prinzipiell offen
		// für jeden Typ sind, da sie Objekte vom allgemeinsten Typ Object beim Speichern
		// entgegennehmen und diesen auch als Rückgabe liefern.
		
		// Seit Java 5 sind alle Datenstrukturen generisch deklariert.
		// Erst dadurch wird bessere Typsicherheit gewährleistet, da nur ganz spezielle Objekte
		// in die Datenstruktur kommen. Mit den Generics lässt sich bei der Konstruktion einer
		// Collection-Datenstruktur angeben, welche Objekte in die Liste aufgenommen werden dürfen. 
		
		

//		LinkedList<String> list = new LinkedList<String>();
//		
//		for (int i = 1; i <= 10; i++)
//		{
//			//list.add("" + i);
//			list.add(String.valueOf(10 - i));
//		}
//		
//		// Ausgabe mit for-Schleife
//		for (int i = 0; i < list.size(); i++)
//			System.out.println(list.get(i));
//		
//		System.out.println();
//		
//		// Ausgabe mit erweiterter for-Schleife
//		for (String s : list)
//			System.out.println(s);
//		
//		System.out.println();
//		
//		// Konvertieren einer dyn. Datenstruktur in ein Array von Objekten
//		Object[] objArr = list.toArray();
//		// Aufsteigende Sortierung
//		Arrays.sort(objArr);
//		
//		// Ausgabe
//		for (Object o : objArr)
//		{
//			System.out.println(o);
//		}
//		
//		System.out.println();
//		
//		
//		LinkedList<Integer> list2 = new LinkedList<>();
//		
//		for (int i = 101; i <= 110; i++)
//		{
//			//list2.add(new Integer(i));	
//			list2.add(i);	
//		}
//		
//		// Ausgabe
////		for (Integer i : list2)
////		{
////			System.out.println(i);
////		}
//		
//		// oder
//		for (int i : list2)
//		{
//			System.out.println(i);
//		}
//		
//		System.out.println();
		
		
		
		
		//demoArrayList();
		
		//demoQueue();
		
		//demoStack();
		
		demoHashMap();
		
	}
	
	
	private static void demoArrayList()
	{
		
		
		// ArrayList speichert Elemente in einem internen Array.
		// Dadurch wird der Zugriff auf ein einzelnes Element über die
		// Position in der Liste sehr schnell.
		// Es bedeuted jedoch viel Arbeit für ein Array, wenn Elemente
		// eingefügt oder gelöscht werden.
		// Ausserdem kann die Grösse des internen Feldes zu klein werden.
		// Dann bleibt der Laufzeitumgebung nichts anderes übrig, als ein
		// neues grösseres Objekt anzulegen und alle Elemente zu kopieren.
		
		// ArrayList als Liste von Objekten verwenden
		ArrayList<Object> al = new ArrayList<>();
		
		al.add(13);
		al.add('A');
		al.add(true);
		al.add(3.14);
		al.add("Java ist toll.");
		al.add(new Date());
		
		// Problematisch beim Auslesen, da jedes mal geprüft werden muss,
		// um welchen Typ es sich bei dem jeweiligen Objekt handelt.	
		
		for (int i = 0; i < al.size(); i++)
			System.out.printf("%s -> %s\n", al.get(i), al.get(i).getClass());
		
		System.out.println();
		
		ArrayList<String> dynFeld = new ArrayList<>();
		
		dynFeld.add("Franz");
		dynFeld.add("jagt");
		dynFeld.add("im");
		
		ArrayList<String> strings = new ArrayList<>();
		strings.add("verwahrlosten");
		strings.add("Taxi");
		
		dynFeld.addAll(strings);
		
		dynFeld.add("quer");
		dynFeld.add("durch");
		dynFeld.add("Bayern");
		
		ausgabe(dynFeld);
		
		// Einfügen eines neuen Eintrags
		dynFeld.add(3, "komplett");
		ausgabe(dynFeld);
		
		// Entfernen eines Eintrags
		dynFeld.remove("quer");
		ausgabe(dynFeld);
		
		// Einen Bereich entfernen:
		// Ab Indexposition 2, 4 Einträge
		
		
		for (int i = 1; i <= 4; i++)
			dynFeld.remove(2);
		
		ausgabe(dynFeld);
		
		// Aufsteigende Sortierung einer dynamischen Datenstruktur
		Collections.sort(dynFeld);
		ausgabe(dynFeld);
		
	}
	
	
	private static void ausgabe(ArrayList<String> al)
	{
		
		for (String s : al)
			System.out.print(s + " ");
		
		System.out.println();
		
	}
	
	
	private static void demoQueue()
	{
		
		// Queues sind Datenstrukturen, die nach dem FIFO-Prinzip
		// (First-In, First-Out) arbeiten.
		
		// Die Schnittstelle Queue erweitert die Collection (LinkedList). 
		Queue<String> schlange = new LinkedList<>();
		
		// Hinzufügen zu einer Queue entweder mit
		// add(), was durch die Collection zur Verfügung stünde,
		// oder mit offer(). Unterschied: im Fehlerfall löst add()
		// eine Exception aus, während offer() durch die Rückgabe
		// false anzeigt, das das Element nicht hinzugefügt wurde.
		
		schlange.offer("Fiat");
		schlange.offer("LKW");
		schlange.offer("Opel");
		schlange.offer("Audi");
		schlange.offer("Bus");
		schlange.offer("Motorrad");
		schlange.offer("Toyota");
		
		// Jetzt wird die Ampel grün
		// Nur die ersten 4 Fahrzeuge können die Kreuzung überqueren.
		
		for (int i = 1; i <= 4; i++)
		{
			System.out.println(schlange.poll());
		}
		
		System.out.println();
		
		// Während der Rot-Phase kommen weitere Fahrzeuge
		schlange.offer("Ford");
		schlange.offer("Honda");
		
		
		// Alle Fahrzeuge können während der nächsten Grün-Phase die Kreuzung überqueren.
		
		while(!schlange.isEmpty())
		{
			System.out.println(schlange.poll());
		}
		
		System.out.println();
		
		
		
	}
	
	
	private static void demoStack()
	{
		
		// Die Klasse Stack repräsentiert einen Stapelspeicher, der
		// nach dem LIFO-Prinzip (Last-In, First-Out) arbeitet.
		
		Stack<String> stapel = new Stack<>();
		
		
		stapel.push("BWM");
		stapel.push("Audi");
		stapel.push("Mercedes");
		stapel.push("Honda");
		stapel.push("VW");
		stapel.push("Toyota");
		stapel.push("Porsche");
		
		while(!stapel.isEmpty())
		{
			System.out.println(stapel.pop());
		}
		
		System.out.println();
		
		
	}
	
	
	private static void demoHashMap()
	{
		
		// Ein assoziativer Speicher verbindet einen Schlüssel mit einem
		// Wert. 
		// Die HashMap arbeitet mit Schlüssel-Werte-Paaren. Aus dem
		// Schlüssel wird nach einer mathematischen Funktion, der sogenannten
		// Hash-Funktion, ein Hashcode berechnet. Dieser dient dann als
		// Index für ein internes Array.
		
		
		PLZTable = new HashMap<>();
		
//		PLZTable.put("20097", "Hamburg");
//		PLZTable.put("63069", "Offenbach/Main");
//		PLZTable.put("76133", "Karlsruhe");
//		PLZTable.put("10178", "Berlin");
//		
//		
//		
//		System.out.println(PLZTable.get("76133"));
//		
//		// Der Schlüsselbegriff für eine HashMap muss eindeutig sein.
//		// Eine erneute Verwendung eines bereits vorhandenen Schlüssels
//		// führt zu keiner Fehlermeldung sondern der dazugehörige Wert wird
//		// kommentarlos überschrieben.
//		
//		PLZTable.put("76133", "Karlsruhe - Durlach");
//		System.out.println(PLZTable.get("76133"));
//		
//		System.out.println();
//		
//		// Aufruf eines Schlüsselwertes, der nicht vorhanden ist.
//		System.out.println(PLZTable.get("12345"));
//		
//		if (PLZTable.containsKey("12345"))
//			System.out.println(PLZTable.get("12345"));
//		else
//			System.out.println("Die angegebene Postleitzahl ist nicht vorhanden.");
//		
//		if (PLZTable.containsKey("76133"))
//			System.out.println(PLZTable.get("76133"));
//		else
//			System.out.println("Die angegebene Postleitzahl ist nicht vorhanden.");
		
		
		leseDateiInHashMap("c:\\temp\\Orte.txt");
		
		
		
	}
	
	
	private static void leseDateiInHashMap(String Dateiname)
	{
		
		Scanner in = null;
		String zeile;
		int recordCount = 0;
		int errCount = 0;
		String sOrt;
		
		
		String[] split;
		
		try
		{
			
			in = new Scanner(new FileInputStream(Dateiname));
			
			while(in.hasNextLine())
			{
				
				// Einlesen des nächsten Datensatzes
				zeile = in.nextLine();
				recordCount++;
				
//				split = zeile.split(";");
//				
//				if (split.length >= 2)
//				{
//					
//					sOrt = split[1];
//					
//					for (int i = 2; i < split.length; i++)
//						sOrt += ";" + split[i];
//
//					PLZTable.put(split[0], sOrt);
//					
//				}
//				else
//					errCount++;
				
				// oder
				split = zeile.split(";", 2);
				
				if (split.length == 2)
					PLZTable.put(split[0], split[1]);
				else
					errCount++;
				
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("Fehler beim Einlesen der Datei: " + ex.getMessage());
		}
		
		
		if (in != null)
			in.close();
		
		
		System.out.printf("\nEs wurden %s Datensätze erfolgreich eingelesen.\n", NumberFormat.getInstance().format(recordCount));
		System.out.printf("Die Postleitzahlentabelle enthält %s Einträge.\n", NumberFormat.getInstance().format(PLZTable.size()));
		System.out.printf("Es wurden %s fehlerhafte Datensätze erkannt.\n", NumberFormat.getInstance().format(errCount));
		
	}
	
	
	

}
