package w3t2_Dozent.Vererbung;

public class WeisserSchaeferhund extends Polarwolf
{

	@Override
	public void fressen()
	{
		
		//super.fressen();
		
		System.out.println("Ich fresse wie ein Hund.");
	}
	
	// Fehler: Die finale Methode 'farbe()' kann nicht überschrieben werden.
//	public void farbe()
//	{
//		
//	}
	

}
