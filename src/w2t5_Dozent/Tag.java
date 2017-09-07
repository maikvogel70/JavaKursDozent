package w2t5_Dozent;

// In der Klasse 'Tag' soll erzwungen werden,
// dass der Wert der Variablen 'tag' immer zwischen 1 und 31 liegt.



public class Tag
{

	private int tag;
	
	// Statische Variablen sind im Gegensatz zu anderen Variablen einer Klasse nicht an eine Instanz dieser
	// Klasse gebunden. 
	// Das hei�t, dass statische Variablen immer zur Klassendefinition selbst geh�ren und nicht mit einer neuen Instanz erstellt werden. 
	// Statische Variablen gelten also f�r alle Instanzen einer Klassendefinition. 
	
	// Daher werden sie auch nur ein einziges mal erzeugt. Auch der Zugriff unterscheidet sich. Eine solche Variable kann, sofern sie
	// �ffentlich ist, �ber den Klassennamen erreicht werden. Dazu wird der Variablenname nach der direkten Klasse durch einen Punkt getrennt. 
	
	// Gleiches gilt f�r statische Methoden.
	
	
	private static int DEFAULT_TAG = 1;
	
	
	public Tag()
	{
		tag = DEFAULT_TAG;
	}
	
	
	public Tag(int value)
	{
				
		// Aufrufe des Standradkonstruktors der eigenen Klasse
		// Muss die erste Anweisung in einem Konstruktor sein!
		this();
		
		setTag(value);
		
	}
	
	
	/**
	 * Setzt den aktuellen Tag.
	 * @param value
	 * 		Tag. G�ltige Werte <b>1 - 31</b>.
	 */
	public void setTag(int value)
	{
		if (checkTag(value))
		{
			tag = value;
		}
		else
		{
			System.out.println("\nUng�ltiger Tag.\nG�ltige Werte 1 - 31.");
		}
		
	}
	
	/**
	 * Gibt den aktuellen Tag zur�ck.
	 * @return
	 *     Tag.
	 */
	public int getTag()
	{
		return tag;
	}
	
	
	
	private static boolean checkTag(int value)
	{
		return (value > 0 && value <= 31);
	}
	
	public static void setDefaultTag(int value)
	{
		
		if (checkTag(value))
		{
			DEFAULT_TAG = value;
		}
		else
		{
			System.out.println("\nUng�ltiger Tag.\nG�ltige Werte 1 - 31.");
		}
		
	}
	
	public static int getDefaultTag()
	{
		return DEFAULT_TAG;
	}
	
	
	
}
