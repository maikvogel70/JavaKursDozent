package w3t3_Dozent.AbstrakteKlassen;

import java.text.NumberFormat;

public class Arbeiter extends Mitarbeiter
{
	
	private String vorgesetzter;
	private double stunden, stundenlohn;
	
	
	public Arbeiter(String name, double stunden, double stundenlohn)
	{
		
		super(name);
		
		this.stunden = stunden;
		this.stundenlohn = stundenlohn;
		
		
	}


	@Override
	public void setVorgesetzter(String name)
	{
		vorgesetzter = name;
	}


	@Override
	public String getVorgesetzter()
	{
		return vorgesetzter;
	}

	public double getStunden()
	{
		return stunden;
	}


	public double getStundenlohn()
	{
		return stundenlohn;
	}


	@Override
	public double getGehalt()
	{
		return (getStunden() * getStundenlohn());
	}


	// Manuelles Überschreiben/Erweitern der Methode ausgabe()
	@Override
	public void anzeige()
	{
		System.out.println("Arbeiter");
		super.anzeige();
		System.out.printf("Anzahl Stunden: %.2f\n", getStunden());
		System.out.printf("Stundenlohn   : %s\n", NumberFormat.getCurrencyInstance().format(getStundenlohn()));
		System.out.println();
		
	}
	

	
	
	
}
