package w4t1_Dozent.Threads;

public class SyncMonitor
{

	
/*  Durch das Schlüsselwort 'synchronized' ist der Zugriff
	auf ein Objekt der Klasse 'SyncMonitor' wechselseitig aus-
	geschlossen. Es darf jeweils höchstens ein Thread in
	einer der mit synchronized markierten Methoden des Ob-
	jekts aktiv sein.
 */
	
	public synchronized void ausgabe(String text)
	//public void ausgabe(String text)
	{
		try
		{
			for (int i = 1; i <= 10; i++)
			{
				System.out.println(text + " - " + i);
				Thread.sleep(200);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		System.out.println("Ende von " + text);
		
	}
	
	
}
