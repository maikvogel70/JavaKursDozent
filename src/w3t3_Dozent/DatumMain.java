package w3t3_Dozent;

public class DatumMain
{

	public static void main(String[] args)
	{
		
		Datum d1 = new Datum(29, 2, 2016);
		System.out.println(d1.toLongString());
		
		d1.addiereJahre(5);
		System.out.println(d1.toLongString());
		
		System.out.println();
		
		d1 = new Datum(31, 10, 2016);
		System.out.println(d1.toLongString());
		
		d1.addiereMonate(11);
		System.out.println(d1.toLongString());
		
		d1.addiereMonate(-11);
		System.out.println(d1.toLongString());
		System.out.println();
		
		d1 = new Datum(31, 1, 2010);
		System.out.println(d1.toLongString());
		
		d1.addiereTage(1000000);
		System.out.println(d1.toLongString());
		
		d1.addiereTage(-1000000);
		System.out.println(d1.toLongString());
		System.out.println();
		
		
		System.out.println(Datum.getOstersonntag(2017));
		System.out.println(Datum.getOstersonntag(2017).toLongString());
		System.out.println();
		
		d1 = Datum.getOstersonntag(2017);
		d1.addiereTage(49);
		System.out.println(d1.toLongString());
		System.out.println();
		
		
		d1 = Datum.getBussUndBetTag(2017);
		System.out.println(d1.toLongString());
		
		
		
		
		
		
		
		
		
	

	}

}
