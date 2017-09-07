package w4t1_Dozent.Threads;

import java.util.concurrent.TimeUnit;

public class ThreadJoin implements Runnable
{

	private String name;
	private int timeOut;
	
	
	public ThreadJoin(String name, int timeOut)
	{
		
		this.name = name;
		this.timeOut = timeOut;
		
		
	}
	
	@Override
	public void run()
	{
		
		
		try
		{
			
			for (int i = 1; i <= 20; i++)
			{
				
				System.out.println(name + ": " + i);
				if (timeOut > 0)
					TimeUnit.SECONDS.sleep(timeOut);
				
			}
			

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		System.out.println("Ende von " + name);
		
		
	}

}
