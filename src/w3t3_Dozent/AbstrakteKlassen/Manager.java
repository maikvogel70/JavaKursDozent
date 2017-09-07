package w3t3_Dozent.AbstrakteKlassen;

public class Manager extends Mitarbeiter
{
	
	private String vorgesetzter;
	private double gehalt;
	
	public Manager(String name, double gehalt)
	{
		
		super(name);
		
		this.gehalt = gehalt;
		
	}

	@Override
	public void setVorgesetzter(String name)
	{
		vorgesetzter = name;
		
	}

	@Override
	public String getVorgesetzter()
	{
		return vorgesetzter == null ? "" : vorgesetzter;
	}

	
	@Override
	public double getGehalt()
	{
		return  gehalt / 12;
	}

	@Override
	public void anzeige()
	{
		System.out.println("Manager");
		super.anzeige();
	}
	
	
	
	
	
	
	

}
