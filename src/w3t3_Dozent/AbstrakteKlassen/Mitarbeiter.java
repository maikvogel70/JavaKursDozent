package w3t3_Dozent.AbstrakteKlassen;

import java.text.NumberFormat;

public abstract class Mitarbeiter
{
	
	private String name;
	
	
	public Mitarbeiter(String name)
	{
		
		this.name = name;
		
		
	}
	
	
	public String getName()
	{
		return name;
	}
	
	
	public void anzeige()
	{
		System.out.println("Name: " + getName());
		System.out.println("Vorgesetzter: " + getVorgesetzter());
		System.out.printf("Gehalt: %s\n", NumberFormat.getCurrencyInstance().format(getGehalt()));
		System.out.println("--------------------------------------------------------------");
		
	}
	
	
	// Abstrakte Methoden
	public abstract void setVorgesetzter(String name);
	public abstract String getVorgesetzter();
	public abstract double getGehalt();
	

}
