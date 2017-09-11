package w6t1_Dozent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JackpotKnacker extends JFrame implements ActionListener
{
	// Breite des Lottozahlenfeldes
	final int fieldWidth = 40;
	// Höhe des Lottozahlenfeldes
	final int fieldHeight = 40;
	// Abstand zwischen den Lottozahlenfeldern
	final int fieldDistance = 2;
	// Abstand vom linken Rand des Fensters
	final int leftOffset = 10;
	// Abstand vom oberen Rand des Fensters
	final int topOffset = 10;
	// Anzahl der Lottozahlen, die getippt werden können
	final int tippCount = 6;

	// Schriftgrösse der Lottozahlenfelder
	// (abhängig von der Feldhöhe).
	final int fontSize = fieldHeight / 2;

	// Schrift-Stil der Lottozahlenfelder
	final int fontStyle = Font.PLAIN;

	// Höhe der Titelzeile
	private int titleBarHeight = 0;
	// Breite des Fensterrahmens
	private int borderWidth = 0;

	// Hintergrundfarbe für gezogene Lottozahlen
	private final Color lottozahlBackColor = Color.orange;
	
	// Standard Hintergrundfarbe des Buttons (Lottozahl)
	private Color buttonBackColor = Color.white;

	private JButton btnTip, btnNeueZiehung, btnBeenden;

	private Lottozahl[] lottoFeld;

	public JackpotKnacker()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{
		this.setTitle("Jackpot Knacker");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(("/images/Lotto.png"))));
		this.getContentPane().setBackground(Color.WHITE);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Keine Grössenänderung des Frames.
		this.setResizable(false);

		// Layout Manager abschalten.
		this.setLayout(null);

		// Die tatsächliche Grösse wird später über die Grösse der 
		// Lottozahlen ermittelt.
		//this.setSize(800, 800);

		btnTip = new JButton("Tip");
		btnTip.setBounds(10, 80, 100, 30);
		btnTip.setToolTipText("Neuen Tip abgeben");
		btnTip.addActionListener(this);
		this.add(btnTip);

		btnNeueZiehung = new JButton("Ziehung");
		btnNeueZiehung.setBounds(10, 120, 100, 30);
		btnNeueZiehung.setToolTipText("Neue Ziehung");
		btnNeueZiehung.addActionListener(this);
		this.add(btnNeueZiehung);

		btnBeenden = new JButton("Beenden");
		btnBeenden.setBounds(10, 80, 160, 30);
		btnBeenden.setToolTipText("Jackpot Knacker beenden");
		btnBeenden.addActionListener(this);
		this.add(btnBeenden);

		erstelleLottofelder();

		int contenPaneWidth = this.getWidth() - (2 * borderWidth);
		int contenPaneHeight = this.getHeight() - titleBarHeight;

		// Maximale Breite der Buttons:
		// Breite der ContentPane - linker und rechter Rand - (Anzahl der Buttons * Abstand zwischen den Feldern),
		// dividiert dur Anzahl der Buttons
		int btnWidth = (contenPaneWidth - (2 * leftOffset) - (3 * fieldDistance) + fieldDistance) / 3;

		btnTip.setBounds(leftOffset, contenPaneHeight - topOffset - btnTip.getHeight(), btnWidth, btnTip.getHeight());
		btnNeueZiehung.setBounds(leftOffset + fieldDistance + btnWidth, btnTip.getY(), btnWidth, btnTip.getHeight());
		btnBeenden.setBounds(leftOffset + (2 * (fieldDistance + btnWidth)), btnTip.getY(), btnWidth, btnTip.getHeight());

	}

	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}

	private void initFrame()
	{
		// In der Mitte des Bildschirms anzeigen
		this.setLocationRelativeTo(null);
		
		btnNeueZiehung.setEnabled(false);
	}

	private void erstelleLottofelder()
	{

		// Bündelung aller Ereignisse der Lottozahlen in einem eigenen ActionListener
		ActionListener actionListener = new Lottozahl_ActionListener();

		// Instanziierung des Lotto-Feldes
		lottoFeld = new Lottozahl[49];

		for (int i = 0; i < lottoFeld.length; i++)
		{
			// Lottozahl-Objekt instanziieren und direkt
			// im Lotto-Feld speichern.
			lottoFeld[i] = new Lottozahl();
			lottoFeld[i].setText(String.format("%d", i + 1));

			// Position für linke obere Ecke (x,y) ermitteln
			int x = leftOffset + ((fieldWidth + fieldDistance) * (i % 7));
			int y = topOffset + ((fieldHeight + fieldDistance) * (i / 7));

			// Positionieren des Buttons
			lottoFeld[i].setBounds(x, y, fieldWidth, fieldHeight);

			// Font anpassen
			lottoFeld[i].setFont(new Font("Tahoma", fontStyle, fontSize));

			// Abstand zwischen Umrandung des Buttons und Text setzen.
			lottoFeld[i].setMargin(new Insets(0, 0, 0, 0));


			// Click Ereignis überwachen
			lottoFeld[i].addActionListener(actionListener);

			// Hinzufügen der Komponente zu Frame
			this.add(lottoFeld[i]);
		}

		// Standard Hintergrundfarbe der Buttons sichern
		buttonBackColor = lottoFeld[0].getBackground();

		// Vorläufige Grösse für den Frame setzen und mit der Methode pack()
		// festlegen.

		// pack() passt die Grösse des Frames an seinen Inhalt (Steuerelemente)
		// an. Ohne den Aufruf dieser Methode kann die die Höhe der Titelzeile und
		// die Rahmenbreite mit getInsets() zu diesem Zeitpunkt nicht ermittelt werden.
		this.pack();

		// Mit getInsets() die Höhe der Titlezeile und die
		// Rahmenbreite ermitteln.
		titleBarHeight = this.getInsets().top;
		borderWidth = this.getInsets().left;

		// Dafür sorgen, dass der Lottozahlenblock immer horizontal und vertikal
		// mittig ausgerichtet ist.
		// Breite = 2 * linker Offset + die Position der am weitesten rechts stehended Lottozahl + 2 * Rahmenbreite
		int frameWidth = (2 * leftOffset) + (7 * (fieldWidth + fieldDistance)) - fieldDistance + (2 * borderWidth);
		// Höhe: Y-Position der letzten Lottozahl + Höhe + Höhe der Buttons + 2 * oberer Offset + Höhe der Titelzeile
		int frameHeight = lottoFeld[lottoFeld.length - 1].getY() + fieldHeight + btnTip.getHeight() + (2 * topOffset) + titleBarHeight;

		// Grösse des Frames festlegen.
		this.setSize(frameWidth, frameHeight);

	}

	private void resetLottozahlen()
	{
		for (int i = 0; i < lottoFeld.length; i++)
		{
			lottoFeld[i].setBackground(buttonBackColor);
			lottoFeld[i].setCorrect(false);
			lottoFeld[i].setDrawn(false);
			lottoFeld[i].repaint();
		}
	}

	private void zieheLottozahlen()
	{
		Random zufall = new Random();
		int gezogen = 0;

		// Evtl. Angaben einer vorherigen Ziehung zurücksetzen
		resetLottozahlen();

		// Lottozahlen
		while (gezogen < tippCount)
		{
			// Liefert eine Zufallszahl zwischen 0 und 48 zurück.
			int index = zufall.nextInt(49);

			// Der Index für den Zugriff auf die Lottozahlen beginnt bei 0!
			// Wenn die Zahl bereits gezogen wurde, neu Ziehung.
			if (lottoFeld[index].isDrawn())
				continue;

			lottoFeld[index].setDrawn(true);
			gezogen++;

			// Hintergrundfarbe für gezogene Lottozahl setzen.
			lottoFeld[index].setBackground(lottozahlBackColor);

			// Wenn es sich um eine getippte Zahl handelt als richtige Zahl markieren.
			if (lottoFeld[index].isTip())
			{
				lottoFeld[index].setCorrect(true);
				lottoFeld[index].repaint();
			}
		}
	}

	private void resetAll()
	{
		// Angaben einer vorherigen Ziehung/Tipps zurücksetzen
		for (int i = 0; i < lottoFeld.length; i++)
		{
			lottoFeld[i].setBackground(buttonBackColor);
			lottoFeld[i].setTip(false);
			lottoFeld[i].setCorrect(false);
			lottoFeld[i].setDrawn(false);
			lottoFeld[i].repaint();
		}

		btnNeueZiehung.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnBeenden)
			this.dispose();
		else if (e.getSource() == btnNeueZiehung)
			zieheLottozahlen();
		else if (e.getSource() == btnTip)
			resetAll();

	}

	// ActionListener nur für die Lottozahlen
	private class Lottozahl_ActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int count = 0;

			Lottozahl z = (Lottozahl) e.getSource();

			// Wenn bereits angekreuzt, dann löschen.
			if (z.isTip())
			{
				z.setTip(false);
				// Erzwingt das Neuzeichnen der Lottozahl
				z.repaint();
				btnNeueZiehung.setEnabled(false);
				return;
			}

			// Anzahl der ausgewählten Lottozahlen ermitteln 
			for (int i = 0; i < lottoFeld.length; i++)
			{
				if (lottoFeld[i].isTip())
					count++;
			}

			// Nur wenn noch getippt werden darf
			if (count < tippCount)
			{
				z.setTip(true);
				count++;
			}
			else
				// Beep über Lausprecher
				Toolkit.getDefaultToolkit().beep();

			// Erzwingt das Neuzeichnen der Lottozahl
			z.repaint();

			btnNeueZiehung.setEnabled(!(count < tippCount));
		}
	}

	// Lottozahl von einem JButton ableiten und mit zusätzlichen Eigenschaften versehen:
	// Als getippt kennzeichnen, als gezogen kenzeichnen
	class Lottozahl extends JButton
	{
		// Farbe für die getippte Zahl
		private Color tipColor = Color.blue;
		// Farbe für die getippte + gezogene Zahl
		private Color winColor = Color.red;

		// Pinselbreite für getippte Lottozahl
		private int tipPenWidth = 3;
		// Pinselbreite für Markierung der richtig getippten Lottozahl
		private int winPenWidth = 3;

		private boolean correct;
		private boolean tip;
		private boolean drawn;

		public Lottozahl()
		{
			init();
		}

		public Lottozahl(Action a)
		{
			super(a);
			init();

		}

		public Lottozahl(Icon icon)
		{
			super(icon);
			init();

		}

		public Lottozahl(String text, Icon icon)
		{
			super(text, icon);
			init();

		}

		public Lottozahl(String text)
		{
			super(text);
			init();
		}

		private void init()
		{
			// Anpassung an den originalen Lottozettel:
			// Hintergrundfarbe: Weiß
			this.setBackground(Color.WHITE);

			// Vordergrundfarbe: Rot
			this.setForeground(Color.RED);

			// Keinen eigenen Rahmen zeichnen.
			// Wird in der Methode paint() gezeichnet.
			this.setBorderPainted(false);

			// Fokus Rechteck nicht anzeigen
			this.setFocusPainted(false);
		}

		public boolean isCorrect()
		{
			return this.correct;
		}

		public void setCorrect(boolean value)
		{
			this.correct = value;
		}

		public boolean isTip()
		{
			return this.tip;
		}

		public void setTip(boolean value)
		{
			this.tip = value;
		}

		public boolean isDrawn()
		{
			return this.drawn;
		}

		public void setDrawn(boolean value)
		{
			this.drawn = value;
		}

		@Override
		public void paint(Graphics g)
		{
			Rectangle oRect;

			super.paint(g);

			// Graphics in Graphics2D umwandeln um zusätzliche Grafik-Methoden
			// zu erhalten.
			Graphics2D g2 = (Graphics2D) g;

			// Eigenen Rahmen mit abgerundeten Ecken in Vordergrundfarbe zeichnen
			g2.setColor(this.getForeground());
			oRect = new Rectangle(0, 0, getWidth(), getHeight());
			g2.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 5, 5);

			// Wenn die Zahl ausgewählt wurde, mit einem Kreuz kennzeichnen.
			if (isTip())
			{
				oRect = new Rectangle(0, 0, getWidth(), getHeight());
				oRect.x += (tipPenWidth * 2);
				oRect.y += (tipPenWidth * 2);
				oRect.width -= (tipPenWidth * 2);
				oRect.height -= (tipPenWidth * 2);

				// Kreuz zeichnen
				// -------------------

				// Pinselstärke für nachfolgende Draw-Methoden setzen
				Stroke stroke = new BasicStroke(tipPenWidth);
				g2.setStroke(stroke);
				g2.setColor(tipColor);
				// g.setXORMode(checkedPenColor);
				g2.drawLine(oRect.x, oRect.y, oRect.width, oRect.height);
				g2.drawLine(oRect.x, oRect.height, oRect.width, oRect.y);
			}

			// Wenn die Zahl ausgewählt und gezogen wurde zusätzlich mit einem Kreis kennzeichenen
			if (isTip() && isCorrect())
			{
				oRect = new Rectangle(0, 0, getWidth(), getHeight());
				oRect.x += winPenWidth;
				oRect.y += winPenWidth;
				oRect.width -= (2 * winPenWidth);
				oRect.height -= (2 * winPenWidth);

				// Ellipse zeichnen
				// --------------------

				// Pinselstärke für nachfolgende Draw-Methoden setzen
				Stroke stroke = new BasicStroke(winPenWidth);
				g2.setStroke(stroke);
				g.setColor(winColor);
				g.drawOval(oRect.x, oRect.y, oRect.width, oRect.height);
			}
		}
	}

	public static void main(String[] args)
	{
		JackpotKnacker f = new JackpotKnacker();
		f.showFrame();

	}
}
