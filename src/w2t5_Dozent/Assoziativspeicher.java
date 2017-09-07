package w2t5_Dozent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Assoziativspeicher
{

	private static HashMap<String, String> PLZTable;
	private static TreeMap<String, ArrayList<String>> PLZTree;
	
	private static Scanner console;
	private static String eingabe;
	
	public static void main(String[] args)
	{
		
		console = new Scanner(System.in);
		
//		leseDateiInHashMap("c:/temp/Orte.txt");
//		
//		System.out.println("\nSoll eine Liste aller Einträge der Postleitzahlentabelle ausgegeben werden ? (j/n)");
//		eingabe = console.nextLine();
//		if (Character.toString(eingabe.charAt(0)).equalsIgnoreCase("j"))
//			ausgabeNachPostleitzahlenAusHashMap();
		
//		leseDateiInTreeMap("c:\\temp\\Orte.txt");
//		
//		System.out.println("\nSoll eine Liste aller Einträge der Postleitzahlentabelle ausgegeben werden ? (j/n)");
//		eingabe = console.nextLine();
//		if (Character.toString(eingabe.charAt(0)).equalsIgnoreCase("j"))
//			ausgabeNachPostleitzahlenAusTreeMap();
//		
//		System.out.println("\nSoll nach einer Postleitzahl gesucht werden ? (j/n)");
//		eingabe = console.nextLine();
//		if (Character.toString(eingabe.charAt(0)).equalsIgnoreCase("j"))
//			postleitzahlSuchenInTreeMap();
//
//		System.out.println("\nSoll eine Liste aller Orte ausgegeben ? (j/n)");
//		eingabe = console.nextLine();
//		if (Character.toString(eingabe.charAt(0)).equalsIgnoreCase("j"))
//			ausgabeAllerOrteAusTreeMap();
		
		
		
//		demoPropertiesErstellen("c:\\temp\\Egenschaften.prop", false);		
//		demoPropertiesLesen("c:\\temp\\Egenschaften.prop", false);
//		System.out.println();
//		
//		demoPropertiesErstellen("c:\\temp\\Egenschaften.xml", true);		
//		demoPropertiesLesen("c:\\temp\\Egenschaften.xml", true);
		
		demoTreeSet();
		
		console.close();
		
	}

	private static void leseDateiInHashMap(String Dateiname)
	{
		
		Scanner in = null;
		String zeile;
		int recordCount = 0;
		int errCount = 0;
		
		String[] split;

		System.out.println("Die Postleitzahlendatei '" + Dateiname + "' wird eingelesen...");
		
		PLZTable = new HashMap<>();
		
		try
		{
			
			in = new Scanner(new FileInputStream(Dateiname));
			
			while(in.hasNextLine())
			{
				// Einlesen des nächsten Datensatzes
				zeile = in.nextLine();
				recordCount++;
				
				// Aufteilen des Datensatzes nach Postleitzahl und Ort
				// Das Trennzeichen ist das Semikolon (;), maximale Aufteilung in 2 Elemente.
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
	
	
	private static void ausgabeNachPostleitzahlenAusHashMap()
	{
		
		System.out.println("Liste aller Einträge der Tabelle Postleitzahlen\n");
		
		
		Set<String> keySet = PLZTable.keySet();
		
		// Umwandlung des KeySets in ein Array
		Object[] objArray = keySet.toArray();
		
		// Aufsteigende Sortierung 
		Arrays.sort(objArray);	
		
		for (Object obj: objArray)
		{
			System.out.println(obj + " - " + PLZTable.get(obj));
		}
		
		System.out.println("\n\n***   Ende der Liste   ***\n");
		
	}
	
	private static void leseDateiInTreeMap(String Dateiname)
	{
		
		Scanner in = null;
		String zeile;
		int recordCount = 0;
		int addCount = 0;
		int errCount = 0;
		ArrayList<String> lstOrte;
		
		String[] split;

		System.out.println("Die Postleitzahlendatei '" + Dateiname + "' wird eingelesen...");
		
		PLZTree = new TreeMap<>();
		
		try
		{
			
			in = new Scanner(new FileInputStream(Dateiname));
			
			while(in.hasNextLine())
			{
				// Einlesen des nächsten Datensatzes
				zeile = in.nextLine();
				recordCount++;
				
				// Aufteilen des Datensatzes nach Postleitzahl und Ort
				// Das Trennzeichen ist das Semikolon (;), maximale Aufteilung in 2 Elemente.
				split = zeile.split(";", 2);
				
				if (split.length == 2)
				{
					
					if (PLZTree.containsKey(split[0]))
					{
						lstOrte = PLZTree.get(split[0]);	
					}
					else
					{
						lstOrte = new ArrayList<>();
					}
					
					
					// Ist der Ort bereits vorhanden
					if (!lstOrte.contains(split[1]))
					{
						lstOrte.add(split[1]);
						addCount++;
						
						// Das Zurückschreiben der ArrayList ist eigentlich nur
						// bei einer Neuanlage nötig.
						PLZTree.put(split[0], lstOrte);
					}
				}	
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
		System.out.printf("Es wurden %s Einträge zur Postleitzahlentabelle hinzugefügt.\n", NumberFormat.getInstance().format(addCount));
		System.out.printf("Die Postleitzahlentabelle enthält %s Einträge.\n", NumberFormat.getInstance().format(PLZTree.size()));
		System.out.printf("Es wurden %s fehlerhafte Datensätze erkannt.\n", NumberFormat.getInstance().format(errCount));
		
	}
	
	private static void ausgabeNachPostleitzahlenAusTreeMap()
	{
		
		int entryCount = 0;
		ArrayList<String> lstOrte;
		
		System.out.println("Liste aller Einträge der Tabelle Postleitzahlen\n");
		
		
		Set<String> keySet = PLZTree.keySet();
		
		for (String key : keySet)
		{
			lstOrte = PLZTree.get(key);
			
			// Aufsteigende Sortierung der Orte
			Collections.sort(lstOrte);
			
			for (String s : lstOrte)
			{
				System.out.println(key + " - " + s);
				entryCount++;
			}

		}
		
		System.out.println("\n\n***   Ende der Liste   ***\n");
		System.out.println("Es wurden " + NumberFormat.getInstance().format(entryCount) + " Einträge gefunden.");
		
	}
	
	private static void postleitzahlSuchenInTreeMap()
	{
	
		
		String eingabe;
		ArrayList<String> lstOrte;
		
		
		do
		{
			System.out.println("\nPostleitzahl eingeben oder 'exit' für Beenden.");
			eingabe = console.nextLine();
			
			if (eingabe.equalsIgnoreCase("exit"))
				break;
			
			if (!PLZTree.containsKey(eingabe))
			{
				System.out.println("Die angegebene Postleitzahl wurde nicht gefunden.");
				continue;
			}
			
			lstOrte = PLZTree.get(eingabe);
			
			// Aufsteigende Sortierung der Orte
			Collections.sort(lstOrte);
			
			for (String s : lstOrte)
			{
				System.out.println(eingabe + " - " + s);
			}
			
			System.out.println("\n\n***   Ende der Liste   ***\n");
			System.out.println("Es wurden " + NumberFormat.getInstance().format(lstOrte.size()) + " Einträge gefunden.");
			

		}
		while (true);
		
	}
	
	
	private static void ausgabeAllerOrteAusTreeMap()
	{
		ArrayList<String> lstOrte;
		ArrayList<String> lstAlleOrte;
		
		
		System.out.println("Liste aller Orte der Tabelle Postleitzahlen\n");
		
		
		// Erstellen einer ArrayList für alle Orte der TreeMap
		lstAlleOrte = new ArrayList<>(PLZTree.size() * 2);
		
		
		
		for (String key : PLZTree.keySet())
		{
//			lstOrte = PLZTree.get(key);
//			lstAlleOrte.addAll(lstOrte);
			
			// oder
			lstAlleOrte.addAll(PLZTree.get(key));
			
		}
		
		
		// ArrayList mit allen Orten sortieren
		Collections.sort(lstAlleOrte);
		
		
		// Ausgabe
		for (String s : lstAlleOrte)
		{
			System.out.println(s);
		}
		
		System.out.println("\n\n***   Ende der Liste   ***\n");
		System.out.println("Es wurden " + NumberFormat.getInstance().format(lstAlleOrte.size()) + " Einträge gefunden.");
		
	}
	
	
	private static void demoPropertiesErstellen(String Dateiname, boolean toXML)
	{
		
		/*
		 * Die Klasse Properties ist eine Sonderform der Assoziativspeicher, bei der Schlüssel-Werte-Paare immer vom Typ
		 * String sind. Da sich die Einträge in einer Datei speichern und wieder auslesen lassen, können auf diese Weise
		 * Konfigurations-Einstellungen für ein Programm extern abgespeichert und geändert werden, ohne das das Programm
		 * geändert werden muss.
		 */
		
		FileOutputStream fw = null;
		
		
		Properties prop = new Properties();
		
		prop.setProperty("Benutzer", "      alfa Karlsruhe");
		prop.setProperty("Kennwort", "ützwurst");
		prop.setProperty("Sprache", "de");
		prop.setProperty("Anfangswert", "42");
		
		try
		{
			fw = new FileOutputStream(Dateiname);
			
			if (toXML )
				prop.storeToXML(fw, "Das ist ein Kommentar");
			else
				prop.store(fw, "Das ist ein Kommentar");
			
		}
		catch (Exception ex)
		{
			System.out.println("Fehler beim Speichern der Konfigurationseinstellungen: " + ex.getMessage());
		}
		
		try
		{
			fw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	
	}
	
	private static void demoPropertiesLesen(String Dateiname, boolean fromXML)
	{
		
		FileInputStream fr = null;
		
		Properties prop = new Properties();
		
		
		try
		{
			fr = new FileInputStream(Dateiname);
			
			if (fromXML)
				prop.loadFromXML(fr);
			else
				prop.load(fr);
			
		}
		catch (Exception ex)
		{
			System.out.println("Fehler beim Lesen der Konfigurationseinstellungen: " + ex.getMessage());
		}
		
		try
		{
			fr.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		// Auflistung aller Einträge von Properties mit list() über die Konsole
		prop.list(System.out);
		
		
		// Gezielter Zugriff auf Properties
		if (prop.containsKey("Benutzer"))
			System.out.println(prop.getProperty("Benutzer"));
		
		
	}
	
	private static void demoTreeSet()
	{
		
		/*
		 * Bei TreeSets sind, im Unterschied zu HashSets, die Elemente immer in aufsteigender Reihenfolge angeordnet.
		 * Der TreeSet basiert auf der TreeMap und implementiert die Schnittstelle Set.
		 */
		
		TreeSet<Integer> t  = new TreeSet<>();
		
		// Hinzufügen von Elementen
		t.add(6);
		t.add(2);
		t.add(9);
		t.add(8);
		t.add(4);
		t.add(3);
		t.add(7);
		t.add(5);
		
		// Doppelte Werte sind nicht erlaubt
		t.add(8);
		t.add(3);
		
		
		for (int n : t)
		{
			System.out.print(n +  ", ");
		}
		
		System.out.println();
		
		
		System.out.println("Kleinstes Element: " + t.first());
		System.out.println("Größtes   Element: " + t.last());
		System.out.println();
		
		// Bildung von Teilmengen
		
		SortedSet<Integer> ss;
		
		System.out.println("Alle Elemente kleiner als 5:");
		ss = t.headSet(5);
		
		for (int n : ss)
		{
			System.out.print(n +  ", ");
		}
		
		System.out.println();
		
		
		System.out.println("Alle Elemente größer gleich 4:");
		
		ss = t.tailSet(4);
		
		for (int n : ss)
		{
			System.out.print(n +  ", ");
		}
		
		System.out.println();
		
		System.out.println("Alle Elemente größer gleich 4 und kleiner als 7:");
		
		ss = t.subSet(4, 7);
		
		for (int n : ss)
		{
			System.out.print(n +  ", ");
		}
		
		System.out.println();
		
		
	}
	
	
	
	
}
