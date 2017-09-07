package w4t1_Dozent.Threads;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadDatum implements Runnable
{

	private int timeOut;

	public ThreadDatum()
	{

	}

	public ThreadDatum(int timeOut)
	{
		this.timeOut = timeOut;
	}

	@Override
	public void run()
	{

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd. MMMM yyyy");

		try
		{

			for (int i = 1; i <= 20; i++)
			{
				System.out.println(sdf.format(new Date()));
				if (timeOut > 0)
					Thread.sleep(timeOut * 1000);

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		System.out.println("Ende von " + this.getClass());

	}

}
