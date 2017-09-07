package w3t5_Dozent.Schnittstellen;

public abstract class AbstractMitarbeiter //implements IPrintable
{

	private String name;
	
	public AbstractMitarbeiter(String name)
	{
		this.name = name;
		
	}
	
	public String getName()
	{
		return name;
	}
	
	
	public abstract double getGehalt();

	
}
