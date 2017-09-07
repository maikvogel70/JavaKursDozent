package w3t4_Dozent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SystemDatumKlassen
{

	public static void main(String[] args)
	{
		
		int addTage, addMonate, addJahre;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		// Gregorian Calendar
		
		// Für das aktuelle Systemdatum
		Calendar cal = new GregorianCalendar();
		System.out.println(sdf.format(cal.getTime()));
		System.out.println();
		
		
	
		// Setzen eines eigenen Datums: 29.02.2016
		// Argumente: Jahr, Monat (0 ...11)
		cal = new GregorianCalendar(2016, 1, 29);
		System.out.println(sdf.format(cal.getTime()));
		System.out.println();
		
		// Addition Jahre
		addJahre = 1234;
		cal.add(Calendar.YEAR, addJahre);
		System.out.println(sdf.format(cal.getTime()));
		
		// Subtraktion Jahre
		cal.add(Calendar.YEAR, -addJahre);
		System.out.println(sdf.format(cal.getTime()));
		System.out.println();
		
		// Addition Monate
		addMonate = 1234;
		
		cal.add(Calendar.MONTH, addMonate);
		System.out.println(sdf.format(cal.getTime()));
		
		// Subtraktion Monate
		cal.add(Calendar.MONTH, -addMonate);
		System.out.println(sdf.format(cal.getTime()));
		System.out.println();
		
		// Addition Tage
		addTage = 123456789;
		
		cal.add(Calendar.DAY_OF_MONTH, addTage);
		System.out.println(sdf.format(cal.getTime()));
		
		cal.add(Calendar.DAY_OF_MONTH, -addTage);
		System.out.println(sdf.format(cal.getTime()));
		System.out.println();
		
		// Setzen eines eigen (ungültigen) Datums
		cal = new GregorianCalendar(2016, 14, 35);
		System.out.println(sdf.format(cal.getTime()));
		System.out.println();
		
		
		// Java 8 Date Time API
		
		// 1. LocalDate
		
		// Heute 
		LocalDate datum = LocalDate.now();
		System.out.println(datum);
		
		// Formatierte Ausgabe Deutsch
		String strDatum = datum.format(DateTimeFormatter.ofPattern("dd. MMMM yyyy", new Locale("de")));
		System.out.println(strDatum);
		
		// Formatierte Ausgabe Französisch
		strDatum = datum.format(DateTimeFormatter.ofPattern("dd. MMMM yyyy", new Locale("fr")));
		System.out.println(strDatum);
		
		// Formatierte Ausgabe 
		strDatum = datum.format(DateTimeFormatter.ofPattern("dd. MMMM yyyy"));
		System.out.println(strDatum);
		System.out.println();
		
		System.out.println("Ist Schaltjahr: " + datum.isLeapYear());
		System.out.println();
		
		// Setzen eines eigenen Datums
		datum = LocalDate.of(2017, 8, 24);
		strDatum = datum.format(DateTimeFormatter.ofPattern("dd. MMMM yyyy"));
		System.out.println(strDatum);
		
		// Der 65. Tag in 2010 (06.03.2010)
		datum = LocalDate.ofYearDay(2010, 65);
		strDatum = datum.format(DateTimeFormatter.ofPattern("dd. MMMM yyyy", new Locale("de")));
		System.out.println(strDatum);
		System.out.println();
		
		// LocalDateTime
		
		 // Aktuelles Datum mit Zeit
	    LocalDateTime dz = LocalDateTime.now();
	    strDatum = dz.format(DateTimeFormatter.ofPattern("dd. MMMM yyyy HH:mm:ss"));
	  	System.out.println(strDatum); 
	  		
	  	dz = LocalDateTime.of(2016, 2, 24, 15, 35, 15);
	  	strDatum = dz.format(DateTimeFormatter.ofPattern("dd. MMMM yyyy HH:mm:ss"));
		System.out.println(strDatum); 
		
		// Informationen über Datum und Zeit
		
		// Informationen über den Monat
		
		System.out.println("Monat: " + dz.getMonth());													// FEBRUARY
		// oder sprachspezifisch
	    System.out.println("Monat: " + dz.format(DateTimeFormatter.ofPattern("MMMM")));             	// Februar
	    
	    System.out.println("Mindestanzahl von Tagen  : "   + dz.getMonth().minLength());            	// 28
	    System.out.println("Mindestanzahl von Tagen  : "   + dz.getMonth().maxLength());            	// 29
	    System.out.println("Erster Monat in diesem Quartal: " + dz.getMonth().firstMonthOfQuarter());   // JANUARY
	    System.out.println();
		
	    // Informationen über das Jahr
	    
	    System.out.println("Jahr: " + dz.getYear()); 
	    System.out.println("Anzahl Tage im Jahr: " + datum.lengthOfYear());								// 365
	    
	    TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
	    System.out.println("Kalenderwoche: " + dz.get(weekOfYear));										// 8
	    
	    System.out.println("Tag der Woche: " + datum.getDayOfWeek().getValue());                        // 6
	    
	    System.out.println("Wochentag: " + datum.getDayOfWeek().name());                                // SATURDAY

	    System.out.println("Tag des Monats: " + datum.getDayOfMonth());                                	// 15
	    
	    System.out.println("Beginn des Tages: " + datum.atStartOfDay());                                // 2014-02-15T00:00
	        
	    // Informationen über eine Zeit
	    System.out.println("Zeit  				: " + LocalTime.of(15, 30));									// 15:30:00 
	    System.out.println("Stunde 				: " + LocalTime.of(15, 30).getHour());							// 15
	    System.out.println("Minute 			 	: " + LocalTime.of(15, 30).getMinute());						// 30
	    System.out.println("Sekunde			 	: " + LocalTime.of(15, 30).getSecond());						// 0
	    System.out.println("Sekunde des Tages	: " + LocalTime.of(15, 30).toSecondOfDay());					// 55800
	    System.out.println();
	    
	    // Datum und Zeit Manipulationen
	    
	    // Morgen
	    System.out.println("Morgen: " + LocalDate.now().plusDays(1));
	     
	    // Vor 5 Stunden und 30 Minuten (ohne Berücksichtigung von Sommer- und Winterzeit)
	    System.out.println(LocalDateTime.now());
	    System.out.println("Vor 5 Stunden und 30 Minuten: " + LocalDateTime.now().minusHours(5).minusMinutes(30));
	    
	    
	    
	    
	    
		
	}

}
