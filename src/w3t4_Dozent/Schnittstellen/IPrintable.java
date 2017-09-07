package w3t4_Dozent.Schnittstellen;



// Einne Schnittstelle kann auch von einer anderen Schnittstelle erben
//public interface IPrintable extends IPrintSetup
public interface IPrintable 
{

	// Variablen k�nnen ebenfalls in Schnittstellen deklariert werden.
	// Als Modifzierer sind lediglich 'public' und 'static' erlaubt.
	// Die Variablen sind implizit 'final' und 'static'.
	
	public int varPrint = 42;
	
	// F�r Methoden sind lediglich die Modifizierer 'public' und 'abstract' erlaubt.
	// Der Modifizierer 'abstract' ist implizit, wenn er nicht angegeben wurde.
	public void print();
	public void immediatePrint();
	public void preview();
	
	// Ein nachtr�gliches �ndern der Scnittstelle erfordert auch ein
	// Anpassen aller Klassen, die diese Schnittstelle implementieren
	//public void test();
	
	
	
	
}
