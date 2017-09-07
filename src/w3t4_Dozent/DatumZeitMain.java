package w3t4_Dozent;

public class DatumZeitMain
{

	public static void main(String[] args)
	{
		
		DatumZeit dz = new DatumZeit(31, 12, 1999, 23, 58, 0);
		System.out.println(dz.toLongString());
		
		dz.addiereStunden(123456789);
		System.out.println(dz.toLongString());
		
		dz.addiereStunden(-123456789);
		System.out.println(dz.toLongString());
		System.out.println();
		
		dz.addiereMinuten(123456789);
		System.out.println(dz.toLongString());
	
		dz.addiereMinuten(-123456789);
		System.out.println(dz.toLongString());
		System.out.println();
		
		dz.addiereSekunden(123456789);
		System.out.println(dz.toLongString());
	
		dz.addiereSekunden(-123456789);
		System.out.println(dz.toLongString());
		System.out.println();
		
		String s = "AlfaTraining";
		s = s.toLowerCase().toUpperCase().replace('A', 'X');
		System.out.println(s);
		System.out.println();
		
		Datum d = new Datum();
		d.addiereTage(1).addiereMonate(1).addiereJahre(1).addiereTage(1);
		System.out.println(d.toLongString());
		
		
		dz = new DatumZeit();
		((DatumZeit)dz.addiereStunden(1).addiereMinuten(1).addiereSekunden(1).addiereJahre(1)).addiereSekunden(1);
		System.out.println(dz.toLongString());
		
		dz.addiereStunden(1).addiereMinuten(1).addiereSekunden(1).addiereJahre(1).addiereSekunden(1).addiereMonate(1).addiereMinuten(1);
		System.out.println(dz.toLongString());
		
	
	}

}
