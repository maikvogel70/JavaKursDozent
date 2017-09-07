package w3t1_Dozent;

public class Punkt
{
	
	public int x;
	public int y;
	
	public Punkt (int x, int y)
	{
		
		// Die lokalen Variablen 'x' und 'y' verdecken die Objektvariablen 'x' und 'y'.
		// Das heißt aber nicht, dass auf die äusseren Variablen nicht mehr
		// zugegriffen werden kann. Mit der this-Referenz kann auf das aktuelle
		// Objekt zugegriffen werden und mit dem Punkt-Operator auf Variablen
		// des Objekts.
		
		this.x = x;
		this.y = y;

		
	}
	
	
	
	

}
