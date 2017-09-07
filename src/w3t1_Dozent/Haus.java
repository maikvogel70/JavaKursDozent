package w3t1_Dozent;

public class Haus
{
	
	private String s = "Haus";
	
	
	
	public class Zimmer
	{
		
		private String s = "Zimmer";
		
		
		
		public class Schrank
		{
			
			private String s = "Schrank";
			
		
			public void ausgabe()
			{
				
				String s = "Ausgabe";
				
				System.out.println(s);                                       // Ausgabe
				// Verwendung von this als Selbstreferenz
				System.out.println(this.s);                                  // Schrank 
				System.out.println(Schrank.this.s);                          // Schrank
				
				System.out.println(Zimmer.this.s);                           // Zimmer
				System.out.println(Haus.this.s);                             // Haus
				
				// Erstellen der eingebetteten private Klasse 'Mantel'
				Mantel m = new Mantel();
				m.ausgabe();
				
				
			}
			
			
			private class Mantel
			{
				private String s = "Mantel"; 
				
				
				public void ausgabe()
				{
					
					System.out.println(s + " im " +  Schrank.this.s);        // Mantel im Schrank
					
				}
				
			
			}
			
		}
		
	}

}
