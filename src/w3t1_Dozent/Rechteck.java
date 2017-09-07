package w3t1_Dozent;

public class Rechteck
{
	
	public Punkt p0;
	public Punkt p1;
	
	
	public Rechteck(int x, int y, int breite, int hoehe)
	{
		
		p0 = new Punkt(x, y);
		p1 = new Punkt(x + breite, y + hoehe);
			
		
	}
	
	public int getX()
	{
		return p0.x;
	}

	public int getY()
	{
		return p0.y;
	}
	
	public int getBreite()
	{
		return (p1.x - p0.x);	
	}
	
	public int getHoehe()
	{
		return (p1.y - p0.y);
	}
	
	
	public int getFlaeche()
	{
	
		return (getBreite() * getHoehe());
	}
	
	
	
	
}
