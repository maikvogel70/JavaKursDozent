package w2t4_Dozent;

public class Kommandozeilenargumente
{

	public static void main(String[] args)
	{
		
		if (args.length > 0)
			ausgabeArgumente(args);
		else
			System.out.println("Es wurden keine Kommandozeilenargumente übergeben.");
		

	}

	
	private static void ausgabeArgumente(String[] args)
	{
		
		for (String s : args)
			System.out.println(s);
		
		
		System.out.println();
		
	}
	
}

// Klassendatei mit Java.exe ausführen.
// 1. Die Umgebungsvariable PATH auf das Installationsverzeichnung des JDK erweitern z.B: C:\Program Files\Java\jdk1.8.n\bin
// 2. Eingabeaufforderung aufrufen und in das Verzeichnis ..workspace\Javakus\bin wechseln.
// 3. Aufruf ohne Argumente: java w2t4_Dozent.Kommandozeilenargumente
// 4. Aufruf mit Argumenten: java w2t4_Dozent.Kommandozeilenargumente arg1 arg2 arg3 arg4 ...
