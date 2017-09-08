package w5t5_Dozent.Serialisierung;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * Serialisieren und Deserialisieren
 * Das Serialisieren ist eine sehr komfortable Möglichkeit, ein ganzes Geflecht von Objekten in eine
 * serielle Form einer Folge von Bytes zu bringen, die sich zum Speichern in einer sequentiellen Datei
 * oder zum Transfer über die Zwischenablage eignet. 
 * Auf diese Weise lässt sich auch die Persistenz von Objekten implementieren, das ist das
 * 'Weiterleben' von Objekten über einen einzigen Programmlauf hinaus..
 * (Persistenz: das Dauerhafte bzw. das langfristige Fortbestehen einer Sache).
 * 
 * Objekte bilden im Arbeitsspeicher häufig ein über Referenzen verknüpftes Geflecht 
 * ( z.B. Listen, Bäume oder Graphen). Serialisieren bedeutet, von einem solchen Objektgeflecht eine lineare,
 * sequentielle Darstellung herzustellen. Die serialisierte Form der Daten erlaubt, beim Wiedereinlesen
 * (Deserialisierung) ein äquivalentes Geflecht von Objekten zu erstellen, mit den gleichen Inhalten und
 * den entsprechenden Verknüpfungen, aber natürlich an anderen Adressen des Arbeitsspeichers.
 *
 * Damit Objekte serialisiert werden können, müssen die Klassen die Schnittstelle 'Serializable' implementieren. 
 * Diese Schnittstelle enthält keine Methoden und ist nur eine Markierungsschnittstelle (engl. marker interface). 
 * Implementiert eine Klasse diese Schnittstelle nicht, folgt beim Serialisierungsversuch eine NotSerializableException. 
 * Der Serialisierer akzeptiert also nur die Klassen, die eine Instanz von 'Serializable' sind.
 * 
 * Nicht alle Objekte sind serialisierbar. Zu den nicht serialisierbaren Klassen gehören zum Beispiel Thread und Socket 
 * und viele weitere Klassen aus dem java.io-Paket. Das liegt daran, dass nicht klar ist, wie zum Beispiel eine Wiederherstellung
 * aussehen sollte. Wenn ein Thread etwa eine Datei zum Lesen geöffnet hat, wie soll der Zustand serialisiert werden, sodass er 
 * beim Deserialisieren auf einem anderen Rechner sofort wieder laufen und dort weitermachen kann, wo er mit dem Lesen aufgehört hat?
 * Ob Objekte als Träger sensibler Daten serialisierbar sein sollen, ist gut zu überlegen. Bei der Serialisierung der Zustände
 * werden auch private Attribute serialisiert. Durch das Kapselungsprinzip kann man normalerweise nicht an diese Daten herankommen.
 * Aus dem Datenstrom lassen sich die internen Belegungen aber ablesen und auch manipulieren.
 */


public class SerialisierungMain
{

	public static void main(String[] args)
	{
		
		String Dateiname;
		
		
		// Verwenden der Klasse SerialObject
		
		// Eine Kette von drei Objekten erzeugen
		
		// 1. Object
		SerialObject obj = new SerialObject("Anton", 1);
		
		// 2. Objekt
		obj.next = new SerialObject("Berta", 2);
		
		// 3. Objekt
		obj.next.next = new SerialObject("Cäsar", 3);
		
		
		System.out.println();
		System.out.println("Erzeugte Objekte");
		System.out.println(obj);
		System.out.println(obj.next);
		System.out.println(obj.next.next);
		
		Dateiname = "C:/temp/SerialObject.ser";
		

		// Ausgabestrom / Ausgabedatei erzeugen
		
		try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(Dateiname)))
		{
			objOut.writeObject(obj);
		}
		catch (Exception ex)
		{	
			System.out.println("Fehler bei der Serialisierung: " + ex.getMessage());	
		}
		
		
		System.out.println();
		System.out.println("Objekte werden gelöscht...");
		obj = null;
		
		// Objekte wieder einlesen
		
		// Eingabestrom erzeugen
		
		
		try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(Dateiname)))
		{
			
			obj = (SerialObject)objIn.readObject();
			
		}
		catch (Exception ex)
		{	
			System.out.println("Fehler bei der Deserialisierung: " + ex.getMessage());	
		}
		
		
		// Man beachte: Nicht nur obj wird eingelesen, sondern auch alle Objekte,
	    // auf die obj seinerzeit direkt oder indirekt verwiesen hatte.
		
		if (obj != null)
		{
			System.out.println();
			System.out.println("Eingelesene Objekte");
			System.out.println(obj);
			System.out.println(obj.next);
			System.out.println(obj.next.next);
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
