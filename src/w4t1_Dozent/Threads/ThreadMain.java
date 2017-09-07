package w4t1_Dozent.Threads;

/* Moderne Betriebssysteme geben dem Benutzer die Illusion, dass verschiedene Programme gleichzeitig ausgeführt werden – 
 * die Betriebssysteme nennen sich multitaskingfähig. Was wir dann wahrnehmen, ist eine Quasiparallelität, die im Deutschen 
 * auch »Nebenläufigkeit« genannt wird. 
 * Diese Nebenläufigkeit der Programme wird durch das Betriebssystem gewährleistet, welches auf Einprozessormaschinen die 
 * Prozesse alle paar Millisekunden umschaltet. Daher ist das Programm nicht wirklich parallel, sondern das Betriebssystem 
 * gaukelt uns dies durch verzahnte Bearbeitung der Prozesse vor. 
 * Erst wenn mehrere Prozessoren oder mehrere Prozessor-Kerne am Werke sind werden die Programmteile tatsächlich parallel abgearbeitet.
 * 
 * Der Teil des Betriebssystems, der die Umschaltung übernimmt, heißt Scheduler. Die dem Betriebssystem bekannten aktiven Programme bestehen 
 * aus Prozessen. Ein Prozess setzt sich aus dem Programmcode und den Daten zusammen und besitzt einen eigenen Adressraum. 
 * Des Weiteren gehören Ressourcen wie geöffnete Dateien oder belegte Schnittstellen dazu. Die virtuelle Speicherverwaltung des 
 * Betriebssystems trennt die Addressräume der einzelnen Prozesse. Dadurch ist es nicht möglich, dass ein Prozess den Speicherraum eines 
 * anderen Prozesses korrumpiert; er sieht den anderen Speicherbereich nicht. Damit Prozesse untereinander Daten austauschen können, 
 * wird ein besonderer Speicherbereich als Shared-Memory markiert.
 * 
 * Die Programmierung von Threads ist in Java einfach möglich, und die quasi parallel ablaufenden Aktivitäten ergeben für den Benutzer den 
 * Eindruck von Gleichzeitigkeit. In Java ist auch multi-threaded Software möglich, wenn das Betriebssystem des Rechners keine Threads direkt 
 * verwendet. In diesem Fall simuliert die virtuelle Maschine die Parallelität indem sie die Synchronisation und die verzahnte Ausführung regelt. 
 * Unterstützt das Betriebssystem Threads direkt, bildet die JVM die Thread-Verwaltung in der Regel auf das Betriebssystem ab.
 */

/* Die Zustände eines Threads.
 * 
 * 1. Nicht erzeugt: Der Lebenslauf eines Threads beginnt mit 'new', doch damit befindet er sich
 * 	  noch nicht im Zustand 'ausführend'.
 * 2. Laufend  (vom Scheduler berücksichtigt) und nicht laufend (vom Scheduler nicht
 * 	  berücksichtigt):  Durch start() gelangt der Thread in den Zustand 'ausführbar' bzw.
 * 	  'laufend'. Der Zustand kann sich ändern, wenn ein anderer Thread ausgeführt wird
 * 	  und dann dem aktuellen Thread dem Prozessor entzieht. Der vormals laufende
 * 	  Thread kommt in den Zustand 'nicht laufend' bis der Scheduler ihm wieder
 * 	  Rechenzeit zuordnet.
 * 3. Wartend: Dieser Zustand wird mittels spezieller Synchronisationstechniken oder
 * 	  Ein-/und Ausgabefunktionen erreicht.
 * 4. Beendet: Nachdem die Aktivität des Threads beendet wurde, kann er nicht mehr
 * 	  aktiviert werden und ist tot.
 * 
 * Zudem lässt sich die Methode isAlive() verwenden um abzufragen, ob der Thread
 * gestartet wurde, aber noch nicht tot ist.
 */

public class ThreadMain
{

	public static void main(String[] args)
	{

		// threadStart();

		// threadRun();

		// threadSleep();
		
		// threadJoin();

		//threadSync();
		
		// Aufruf eines Threads ohne die Schnittstelle 'Runnable'
		// indem die Klasse direkt von der Klasse 'Thread' abgeleitet
		// wurde.
		T1 t1 = new T1();
		t1.start();
		
		
		
		
		System.out.println("Ende von ThreadMain");
	}

	private static void threadStart()
	{

		/*
		 * Dem Konstruktor der Klasse Thread wird eine Referenz auf das auszuführende Objekt übergeben. Danach wird die Methode start() aufgerufen. Nachdem
		 * start() für den Thread eine Ablaufumgebung erstellt hat, ruft sie intern selbstständig die Methode run() genau einmal auf. Läuft der Thread bereits,
		 * so löst ein zweiter Aufruf der start()- Methode eine Ausnahmebedingung aus (IllegalThreadStateException).
		 */

		Thread t1 = new Thread(new ThreadDatum());
		t1.start();

		Thread t2 = new Thread(new ThreadZaehler());
		t2.start();

	}

	private static void threadRun()
	{

		/*
		 * Ein Fehler, der schnell passiert ist: Anstelle von start() die Methode run() aufzurufen. Die Ausführung wurde dadurch ebenfalls gestartet, Der
		 * Unterschied ist jedoch, dass die Objekt-Methode nicht parallel zum übrigen Programm abgearbeitet wird sondern sequenziell. Der Fehler fällt nicht
		 * sofort auf, denn die Ausführung findet ja statt - nur nicht nebenläufig.
		 */

		Thread t1 = new Thread(new ThreadDatum());
		t1.run();

		Thread t2 = new Thread(new ThreadZaehler());
		t2.run();

	}

	private static void threadSleep()
	{

		/*
		 * Manchmal ist es notwendig, einen Thread für eine bestimmte Zeit anzuhalten. Dazu lassen sich die Methoden zweier Klassen nutzen:
		 * 
		 * 1. Die überladene statische Methode Thread.sleep(), 2. Die Objektmethode sleep() auf einem TimeUnit-Objekt. Der Vorteil dieser Methode ist, dass hier
		 * die Zeiteinheiten besser sichtbar sind.
		 */

		/*
		 * Neben der Methode sleep() gibt es eine weitere Methode, um kooperative Threads zu programmieren: die Methode yield(). Sie funktioniert etwas anders
		 * als sleep(), da hier nicht nach Ablauf einer Zeit zum Thread zurückgekehrt wird, sondern yield() den Thread wieder, entsprechend seiner Priorität, in
		 * die Thread-Warteschlange des Systems einordnet. Der Thread hat einfach eine Runde ausgesetzt und wartet bis er das nächste Mal dran ist.
		 * 
		 */

		Thread t1 = new Thread(new ThreadDatum(1));
		t1.start();

		Thread t2 = new Thread(new ThreadZaehler(2));
		t2.start();

	}

	private static void threadJoin()
	{

		/*
		 * Warten auf den Langsamsten Große Probleme lassen sich in mehrere Teile zerlegen und jedes Teilproblem kann dann von einem Thread gelöst werden. Das
		 * ist insbesondere bei Mehrprozessorsystemen eine lohnenswerte Investition. Zum Schluss muss nur noch darauf gewartet werden, bis alle Threads zu Ende
		 * gekommen sind, und das Ergebnis einzusammeln. Dazu eignet sich join() gut.
		 * 
		 */

		Thread a = new Thread(new ThreadJoin("Thread A", 1));
		Thread b = new Thread(new ThreadJoin("Thread B", 2));
		Thread c = new Thread(new ThreadJoin("Thread C", 3));
		Thread d = new Thread(new ThreadJoin("Thread D", 1));

		a.start();
		b.start();
		c.start();
		d.start();

		System.out.println("Thread A is alive: " + a.isAlive());
		System.out.println("Thread B is alive: " + b.isAlive());
		System.out.println("Thread C is alive: " + c.isAlive());
		System.out.println("Thread D is alive: " + d.isAlive());

		try
		{
			a.join();
			
			
			
			b.join();
			c.join();
			d.join();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		System.out.println("Thread A is alive: " + a.isAlive());
		System.out.println("Thread B is alive: " + b.isAlive());
		System.out.println("Thread C is alive: " + c.isAlive());
		System.out.println("Thread D is alive: " + d.isAlive());
		
		
	}
	
	
	private static void threadSync()
	{
		
		/* Da Threads ihre eigenen Daten verwalten – sie haben alle eigene lokale Variablen und einen Stack –, 
		 * kommen sie sich gegenseitig nicht in die Quere. Auch wenn mehrere Threads gemeinsame Daten nur lesen, 
		 * ist das unbedenklich; Schreiboperationen sind jedoch kritisch. Wenn sich zehn Nutzer einen Drucker teilen, 
		 * der die Ausdrucke nicht als unteilbare Einheit bündelt, lässt sich leicht ausmalen, wie das Ergebnis 
		 * aussieht. Seiten, Zeilen oder gar einzelne Zeichen aus verschiedenen Druckaufträgen werden bunt 
		 * gemischt ausgedruckt.
		 *   
		 */
		
		
		// Enthält die Methoden die synchroniert ausgeführt werden sollen.
		SyncMonitor monitor = new SyncMonitor();
		
		Thread t1 = new Thread(new ThreadMonitor("Thread 1", monitor));
		Thread t2 = new Thread(new ThreadMonitor("Thread 2", monitor));
		
		t1.start();
		t2.start();
		
		
	}
	
	
	
	
	
	

}
