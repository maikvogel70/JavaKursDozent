package w3t1_Dozent;

public class Gebilde
{

	private Rechteck rechteck;
	private Farbe farbe;
	
	
	public Gebilde(int x, int y, int breite, int hoehe, Farbe farbe)
	{
		
		rechteck = new Rechteck(x, y, breite, hoehe);
		this.farbe = farbe;
		
	}


	public Rechteck getRechteck()
	{
		return rechteck;
	}


	public Farbe getFarbe()
	{
		return farbe;
	}
	
	
	
	
	
}
