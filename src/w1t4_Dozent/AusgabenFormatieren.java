package w1t4_Dozent;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AusgabenFormatieren
{

	public static void main(String[] args)
	{
		
		System.out.println("PI: " + Math.PI);
		
		double radius = 0.8;
		
		System.out.println("Kreisumfang: " + (2 * radius * Math.PI));
		
		
        // ---------------------------------
		// Formatieren von Ausgaben printf()
        // ---------------------------------

		// Syntax:
		// System.out.printf("<Werte formatiert ankündigen>", wert1, wert2, ...);
		
		// Formatierungsanweisung :
		// %[Schalter][Breite].[Genauigkeit]Umwandlung
		
		// Die optionalen Schalter verändern das Format der Ausgabe. Ein
		// Minuszeichen (-) z.B. sorgt für linksbündige Ausgabe.
		// Die optionale Breite ist eine nicht-negative Ganzzahl, die die
		// Mindestanzahl der ausgegebenen Zeichen bestimmt.
		// Mit der optionalen Genauigkeit wird üblicherweise die Anzahl der
		// ausgegebenen Zeichen eingeschränkt.
		// Umwandlung ist ein Buchstabe der anzeigt, wie das zugehörige Argument
		// formatiert wird.
		
		// Beispiele für mögliche Werte von Umwandlung sind:

		// %s - Der zugehörige Parameter wird als String ausgegeben.
		// %S - Der zugehörige Parameter wird als String in Großbuchstaben ausgegeben.
		// %d - Der zugehörige Parameter wird als Ganzzahl ausgegeben.
		// %f - Der zugehörige Parameter wird als Dezimalzahl ausgegeben.
		// %X - Der zugehörige Parameter wird als Hexadezimalwert ausgegeben.
		
		
		// Es wird keine Mindestbreite und keine Genauigkeit angegeben.
		// printf() verwendet dann Standardwerte.
		System.out.printf("PI: %f\n", Math.PI);
		
		
		// Es werden mindestens 30 Stellen für die Ausgabe bereitgestellt.
		// Da nur 5 Stellen benötigt werden (1 vor dem Komma, das Komma selbst und 3 Stellen nach dem Komma)
		// erscheinen 25 Leerstellen nach der Zahl.
		System.out.printf("PI: %-30.3f<<<\n", Math.PI);
		
		// Ähnliches gilt für diese Anweisung. Die Leerstellen erscheinen jedoch
		// vor der Zahl, da der Wert rechtsbündig ausgegeben werden soll.
		System.out.printf("PI: %30.3f<<<\n", Math.PI);
		
		System.out.printf("%S %d %s und %d %s\n", "endpreis", 7, "Euro", 12, "Cent");
		
		
		// Hexadezimal: X, x, H, h
		System.out.printf("X'%X'\n", 255);
		
		// Uhrzeit
		System.out.printf("%02d:%02d:%02d Uhr\n", 16, 8, 5);
		System.out.println();
		
		
		// Format-Spezifizierer für Datumswerte
		//
		// Symbol 		Beschreibung
		// --------- 	--------------------------------------------------------------
		// %tA, %ta 	vollständiger, abgekürzter Name des Wochentags
		// %tB, %tb 	vollständiger, abgekürzter Name des Monatsnamens
		// %tC 			zweistelliges Jahrhundert (00-99)
		// %te, %td 	Monatstag numerisch ohne bzw. mit führenden Nullen
		// %tk, %tl 	Stundenangabe im 24 bzw. 12 Stunden Format (0-23, 1-12)
		// %tH, %tI 	Zweistellige Stundenangabe im 24 bzw. 12 Stunden Format (00-23, 01-12)
		// %tj 			Tag des Jahres (001-366)
		// %tM 			Zweistellige Minutenangabe (00-59)
		// %tm 			Zweistellige Monatsangabe (01-12)
		// %tS 			Zweistellige Sekundenangabe (00-59)
		// %tY 			Vierstellige Jahresangabe
		// %ty 			Die letzten beiden Ziffern der Jahresangabe (00-99)
		// %tZ 			Abgekürzte Zeitzone
		// %tz 			Zeitzone mit Verschiebung zu GMT
		// %tR 			Stunden und Minuten in der Form %tH:%tM
		// %tT 			Stunden/Minuten/Sekunden: %tH:%tM:%tS
		// %tD 			Datum in der Form %tm/%td/%ty
		// %tF 			ISO-8601-Format %tY-%tm-%td
		// %tc 			Komplettes Datum mit der Zeit in der Form %ta %tb %td %tT %tZ %tY
		
		Date dz = new Date();
		
		System.out.println(dz);
		System.out.printf("%tc\n", dz);
		System.out.printf("%tA, %td %tB %tY\n", dz, dz, dz, dz);
		System.out.println();
		
		// --------------
		// Format-Klassen
		// --------------
		

		// DateFormat: 		Formatieren von Datums- und Zeitwerten
		// NumberFormat: 	Formatieren von Zahlen
		// MessageFormat: 	Formatieren von allgemeinen Programmmeldungen

		String s = DateFormat.getDateInstance().format(dz);
		System.out.println(s);
		System.out.println(DateFormat.getDateInstance().format(dz));
		System.out.println(DateFormat.getTimeInstance().format(dz));
		System.out.println(DateFormat.getDateTimeInstance().format(dz));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		System.out.println(sdf.format(dz));
		
		// Parameter 	Bedeutung 			Beispiel
		// ---------------------------------------------
		// yyyy 		Jahr vierstellig 		2010
		// yy 			Jahr zweistellig 	  	  10
		// MM 			Monat zweistellig 	  	  12
		// w 			Kalenderwoche 		  	  34
		// d 			Tag im Monat 		  	  15
		// HH 			Stunde (24h-Format)   	  13
		// mm 			Minute der Stunde 	  	  05
		// ss 			Sekunde der Minute 	  	  34
		
		sdf = new SimpleDateFormat("EEEE, dd. MMMM yyyy");
		System.out.println(sdf.format(dz));
		System.out.println();
		
		System.out.println(NumberFormat.getInstance().format(12345.678));
		System.out.println(NumberFormat.getCurrencyInstance().format(12345.678));
		System.out.println(NumberFormat.getPercentInstance().format(0.12));	
		System.out.println();
		
		// Dezimalzahlformatierung mit DecimalFormat
        // -----------------------------------------
		// DecimalFormat ist eine Unterklasse von NumberFormat und ermöglicht
		// individuellere Anpassungen an die Ausgabe.
		// Dem Konstruktor kann ein Formatierungs-String übergeben werden,
		// sozusagen eine Vorlage, wie die Zahlen zu formatieren sind.
		// Die Formatierung einer Zahl durch DecimalFormat erfolgt mit Rücksicht
		// auf die aktuell eingestellte Sprache.
		
		// Symbol 		Bedeutung
		// ------ 		----------------------------------------------------------------------------------
		// 0 	  		Repräsentiert eine Ziffer – ist die Stelle nicht belegt, wird eine Null angezeigt.
		// # 	  		Repräsentiert eine Ziffer – ist die Stelle nicht belegt, bleibt sie
		//   	  		leer, damit führende Nullen und unnötige Nullen hinter dem Komma nicht
		//   	  		angezeigt werden.
		// . 	  		Dezimaltrenner. Trennt Vor- und Nachkommastellen.
		// , 	  		Gruppiert die Ziffern (eine Gruppe ist so groß wie der Abstand von
		//   	  		"," zu ".").
		// ; 	  		Trennzeichen. Links davon steht das Muster für positive, rechts das
		//   	  		für negative Zahlen.
		// - 	  		Das Standardzeichen für das Negativpräfix
		// % 	  		Die Zahl wird mit 100 multipliziert und als Prozentwert ausgewiesen.
		// \u2030 		Die Zahl wird mit 1000 multipliziert und als Promillewert ausgewiesen.
		// \u00A4 		Nationales Währungssymbol (€ für Deutschland) - Unicode Escape
		//        		Sequenz
		// \u00A4\u00A4 Internationales Währungssymbol (EUR für Deutschland) -
		//        		Unicode Escape Sequenz
		
		
		// Beispiele
		
		double d = 12.0;
		DecimalFormat df;
		
		// Vierstellig, mit führenden Nullen
		df = new DecimalFormat("0000");
		System.out.println(df.format(d));	                       	// 0012           
		
		// Mit zwei Nachkommastellen
		df = new DecimalFormat(".00");
		System.out.println(df.format(d));							// 12,00 
		
		d = 12.3456;
		System.out.println(df.format(d));							// 12,35
		
		d = 0.3456;
		System.out.println(df.format(d));							// ,35
		
		// Mit mindestens einer Vorkommastelle und 2 Nachkommastellen
		df = new DecimalFormat("0.00");
		System.out.println(df.format(d));							// 0,35
		
		// Mit nationalem Währungssymbol
		df = new DecimalFormat("0.00 \u00A4");
		System.out.println(df.format(d));							// 0,35 €
		
		// Mit Trennzeichen für positive und negative Werte
		df = new DecimalFormat("0.00;0.00 -");
		System.out.println(df.format(d));							// 0,35
		System.out.println(df.format(-d));
		
		// Ausgabe als Prozentwert		
		df = new DecimalFormat("0.00 %");
		System.out.println(df.format(d));							// 34,56 %
		
		
		// Ausgabe als Promillewert
		df = new DecimalFormat("0 \u2030");
		System.out.println(df.format(d));							// 346 ‰
		
		System.out.println();
		
		// Die Klasse MessageFormat.
		
		s = MessageFormat.format("Unser Sonnensystem hat {0} Planeten.", 8);
		System.out.println(s);
		
		s = MessageFormat.format("Heute ist der {0, date, full}, {0, time} Uhr", new Date());
		System.out.println(s);
		
		
		
		
		
		
		
		
	}

}
