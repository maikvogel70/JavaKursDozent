package w1t3_Dozent;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

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
		// System.out.printf("<Werte formatiert ank�ndigen>", wert1, wert2, ...);
		
		// Formatierungsanweisung :
		// %[Schalter][Breite].[Genauigkeit]Umwandlung
		
		// Die optionalen Schalter ver�ndern das Format der Ausgabe. Ein
		// Minuszeichen (-) z.B. sorgt f�r linksb�ndige Ausgabe.
		// Die optionale Breite ist eine nicht-negative Ganzzahl, die die
		// Mindestanzahl der ausgegebenen Zeichen bestimmt.
		// Mit der optionalen Genauigkeit wird �blicherweise die Anzahl der
		// ausgegebenen Zeichen eingeschr�nkt.
		// Umwandlung ist ein Buchstabe der anzeigt, wie das zugeh�rige Argument
		// formatiert wird.
		
		// Beispiele f�r m�gliche Werte von Umwandlung sind:

		// %s - Der zugeh�rige Parameter wird als String ausgegeben.
		// %S - Der zugeh�rige Parameter wird als String in Gro�buchstaben ausgegeben.
		// %d - Der zugeh�rige Parameter wird als Ganzzahl ausgegeben.
		// %f - Der zugeh�rige Parameter wird als Dezimalzahl ausgegeben.
		// %X - Der zugeh�rige Parameter wird als Hexadezimalwert ausgegeben.
		
		
		// Es wird keine Mindestbreite und keine Genauigkeit angegeben.
		// printf() verwendet dann Standardwerte.
		System.out.printf("PI: %f\n", Math.PI);
		
		
		// Es werden mindestens 30 Stellen f�r die Ausgabe bereitgestellt.
		// Da nur 5 Stellen ben�tigt werden (1 vor dem Komma, das Komma selbst und 3 Stellen nach dem Komma)
		// erscheinen 25 Leerstellen nach der Zahl.
		System.out.printf("PI: %-30.3f<<<\n", Math.PI);
		
		// �hnliches gilt f�r diese Anweisung. Die Leerstellen erscheinen jedoch
		// vor der Zahl, da der Wert rechtsb�ndig ausgegeben werden soll.
		System.out.printf("PI: %30.3f<<<\n", Math.PI);
		
		System.out.printf("%S %d %s und %d %s\n", "endpreis", 7, "Euro", 12, "Cent");
		
		
		// Hexadezimal: X, x, H, h
		System.out.printf("X'%X'\n", 255);
		
		// Uhrzeit
		System.out.printf("%02d:%02d:%02d Uhr\n", 16, 8, 5);
		System.out.println();
		
		
		// Format-Spezifizierer f�r Datumswerte
		//
		// Symbol 		Beschreibung
		// --------- 	--------------------------------------------------------------
		// %tA, %ta 	vollst�ndiger, abgek�rzter Name des Wochentags
		// %tB, %tb 	vollst�ndiger, abgek�rzter Name des Monatsnamens
		// %tC 			zweistelliges Jahrhundert (00-99)
		// %te, %td 	Monatstag numerisch ohne bzw. mit f�hrenden Nullen
		// %tk, %tl 	Stundenangabe im 24 bzw. 12 Stunden Format (0-23, 1-12)
		// %tH, %tI 	Zweistellige Stundenangabe im 24 bzw. 12 Stunden Format (00-23, 01-12)
		// %tj 			Tag des Jahres (001-366)
		// %tM 			Zweistellige Minutenangabe (00-59)
		// %tm 			Zweistellige Monatsangabe (01-12)
		// %tS 			Zweistellige Sekundenangabe (00-59)
		// %tY 			Vierstellige Jahresangabe
		// %ty 			Die letzten beiden Ziffern der Jahresangabe (00-99)
		// %tZ 			Abgek�rzte Zeitzone
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
		
		System.out.println(NumberFormat.getCurrencyInstance(Locale.US).format(123.34));
		
//		MessageFormat
		
		if(true) {
			int a;
		}
	}

}
