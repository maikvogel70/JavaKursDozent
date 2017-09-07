package w3t2_Dozent.Vererbung;

public class V2 extends K2
{

	// Eine Methode mit gleichem Namen und Signatur 
	// überschreibt auch ohne die Annotation '@Override'
	// eine evtl. ererbte Methode.
	// Damit kann ein vollständiges Überschreiben oder auch
	// nur ein Erweitern der Methode erreicht werden. 
	
	@Override
	public void f()
	{
		
		// Zusätzliche Anweisungen
		System.out.println("V2.f()");
		
		
		// TODO Auto-generated method stub
		super.f();
		
		

		
		
	}

	
	
	
	
}
