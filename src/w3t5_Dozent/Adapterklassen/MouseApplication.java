package w3t5_Dozent.Adapterklassen;

/* Oft wird bei der Verwendung von Interfaces nur ein Teil der vorhandenen Methoden verwendet. 
*  Da es lästig ist, auch die restlichen Methoden zu implementieren, gibt es Adapter-Klassen. 
*  Diese enthalten eine leere Implementierung für alle Methoden der entsprechenden Schnittstelle. 
*  Eine andere Klasse kann von dem Adapter abgeleitet werden, anstatt die Schnittstelle zu implementieren 
*  und muss dann nur die zu verwendenden Methoden überschreiben.
*/

public class MouseApplication extends MouseAdapter
{

	@Override
	public void MouseClick()
	{
		System.out.println("Maus wurde geklickt.");
	}

	@Override
	public void MouseDoubleClick()
	{
		
		System.out.println("Doppelklick mit der Maus.");
	}

}
