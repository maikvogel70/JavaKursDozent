package w5t5_Dozent.Serialisierung;

import java.io.Serializable;

// Die erste Version einer Klassenbibliothek ist in der Regel nicht vollständig und nicht beendet. Es kann gut sein, dass Attribute und Methoden nachträglich 
// in die Klasse eingefügt, gelöscht oder modifiziert werden. 
// Das bedeutet aber auch, dass die Serialisierung zu einem Problem werden kann. Denn ändert sich der Variablentyp oder kommen Variablen hinzu, 
// ist eine gespeicherte Objektserialisierung nicht mehr gültig.

// Bei der Serialisierung wird in Java nicht nur der Objektinhalt geschrieben, sondern zusätzlich eine eindeutige Kennung der Klasse, die UID. 
// Die UID ist ein Hashcode aus Namen, Attributen, Parametern, Sichtbarkeit und so weiter. Sie wird als long wie ein Attribut gespeichert. 
// Ändert sich der Aufbau einer Klasse, ändern sich der Hashcode und damit die UID. Klassen mit unterschiedlicher UID sind nicht kompatibel. 
// Erkennt der Lesemechanismus in einem Datenstrom eine UID, die nicht zur Klasse passt, wird eine InvalidClassException ausgelöst. 
// Das bedeutet, dass schon ein einfaches Hinzufügen von Attributen zu einem Fehler führt.


public class SerialObject implements Serializable
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 270811954955650772L;
	
	
	
	private String Name;
	private int Ganzzahl;
	public SerialObject next; 
	
//	private  int temp1;
//	private  int temp2;
//	private  int temp3;
	
	/*
	 * Ausschluss von Attributen mit transient
	 * Es kommt vor, dass Objekte temporäre Daten speichern, die beim Serialisieren nicht hinausgeschrieben
	 * werden zu brauchen, weil man sie beim Deserialisieren effizienter neu initialisieren kann. Solche
	 * Attribute kann man als transient deklarieren.
	 * 
	 */
	
	private  transient int temp1;
	private  transient int temp2;
	private  transient int temp3;
	
	public SerialObject(String Name, int Ganzzahl)
	{
		
		this.Name = Name;
		this.Ganzzahl = Ganzzahl;
		
		this.next = null;
		
		temp1 = 1;
		temp2 = 2;
		temp3 = 3;
		
	}


	@Override
	public String toString()
	{
		return "TestObjekt: " + this.Name + " " + String.valueOf(this.Ganzzahl) + ", Temp1: " + temp1 + ", Temp2: " + temp2 + ", Temp3: " + temp3;
	}
	
	
	
	
	
	
}
