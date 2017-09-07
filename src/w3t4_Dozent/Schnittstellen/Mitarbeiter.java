package w3t4_Dozent.Schnittstellen;

public class Mitarbeiter extends AbstractMitarbeiter implements IPrintable, IPrintSetup
{
	
	private double gehalt;
	
	
	public Mitarbeiter(String name, double gehalt)
	{
		
		super(name);
		
		this.gehalt = gehalt;
	}


	@Override
	public double getGehalt()
	{
		return gehalt;
	}


	@Override
	public void print()
	{
		
		System.out.println("Druckerauswahl-Dialog anzeigen.");
		
	}


	@Override
	public void immediatePrint()
	{
		
		System.out.println("Sofortige Druckausgabe auf dem Standarddrucker.");
		
	}


	@Override
	public void preview()
	{
		
		System.out.println("Seitenansicht anzeigen.");
		
	}


	@Override
	public void printerSetup()
	{
		System.out.println("Dialog für Druckereinstellungen anzeigen.");
		
	}


	@Override
	public void pageSetup()
	{
		System.out.println("Dialog für Seiteneinstellungen anzeigen.");
		
	}


	@Override
	public void printTest()
	{
		System.out.println("Testdruck.");
		
	}
	


}
