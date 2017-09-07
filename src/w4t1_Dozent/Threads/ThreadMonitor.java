package w4t1_Dozent.Threads;

public class ThreadMonitor implements Runnable
{

	private String name;
	private SyncMonitor monitor;
	
	public ThreadMonitor(String name, SyncMonitor monitor)
	{
		
		this.name = name;
		this.monitor = monitor;

	}
	

	@Override
	public void run()
	{
		monitor.ausgabe(name);
		
	}
}
