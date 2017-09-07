package w4t1_Dozent.Threads;

// Wenn eine Klasse bereits von einer anderen Basisklasse abgeleitet wurde, aber
// trotzdem als Thread ausgeführt werden soll, kann stattdessen die Schnittstelle
// 'Runnable' implementiert werden.

public class T1 extends Thread
{
	
	// Wenn die Klasse von Thread abgeleitet wurde muss der
	// Programmierer selbst dafür sorgen, dass die Methode run überschrieben wird.

	@Override
	public void run()
	{
		
		try
		{
			for (int i = 1; i <= 20; i++)
			{
				System.out.println(i);
				Thread.sleep(200);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	
	
	

}
