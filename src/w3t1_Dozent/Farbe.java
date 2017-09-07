package w3t1_Dozent;

public class Farbe
{

	public int rot, gruen, blau;
	
	
	public Farbe(int rot, int gruen, int blau)
	{
		
		this.rot = rot;
		this.gruen = gruen;
		this.blau = blau;
		
	}
	
	
	public String getFarbe()
	{
		String s = rot + ", " + gruen + ", " + blau;
		return s;
		
	}
	
	
	
	
	
}
