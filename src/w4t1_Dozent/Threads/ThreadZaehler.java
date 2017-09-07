package w4t1_Dozent.Threads;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ThreadZaehler implements Runnable
{

	private int timeOut;

	public ThreadZaehler()
	{

	}

	public ThreadZaehler(int timeOut)
	{

		this.timeOut = timeOut;

	}

	@Override
	public void run()
	{

		try
		{
			for (int i = 1; i <= 20; i++)
			{
				System.out.println(i);
				
				if (timeOut > 0)
					TimeUnit.SECONDS.sleep(timeOut);
				
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		System.out.println("Ende von " + this.getClass());

	}

}
