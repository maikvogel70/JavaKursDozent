package w6t4_Dozent;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.ProgressWindow;

// Programm zum Testen des Fensters für die Fortschrittsanzeige

public class ProgressWindowDemo extends JFrame implements ActionListener, ChangeListener
{
	JLabel	lblBreite, lblHöhe, lblFiller1, lblTransparenz, lblStatustext, lblFarbe, lblBenutzertext;
	JRadioButton	rbCenterOnParent, rbCenterOnDesktop;
	JRadioButton	rbHorizontal, rbVertikal;
	JCheckBox	 checkTitelzeile, checkProzentwert, checkAnzeigeUnbestimmt, checkAbbruch, checkModal, checkAlwaysOnTop;
	JTextField	 tfTitelzeile, tfBreite, tfHöhe, tfStatustext, tfBenutzertext;
	JSlider	    sliderTransparenz;
	JButton	    btnOK, btnBeenden, btnFarbe;

	public ProgressWindowDemo()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{
		this.setTitle("Demo Fortschrittsanzeige-Fenster");
		this.setResizable(false);
		this.setSize(400, 400);
  		
		// GridLayout Argumente:
        // Anzahl Zeilen:   0 = jede Anzahl von Zeilen (nicht definiert).
        // Anzahl Spalten:  0 = jede Anzahl von Spalten (nicht definiert).
        // Horizontaler Zwischenraum
        // Vertikaler Zwischenraum
		this.setLayout(new GridLayout(0, 2, 5, 5));

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ButtonGroup bgCenter = new ButtonGroup();

		rbCenterOnDesktop = new JRadioButton("Zentrieren auf Desktop");
		bgCenter.add(rbCenterOnDesktop);
		this.add(rbCenterOnDesktop);

		rbCenterOnParent = new JRadioButton("Zentrieren auf Fenster");
		bgCenter.add(rbCenterOnParent);
		this.add(rbCenterOnParent);

		tfTitelzeile = new JTextField("Fortschrittsanzeige...");
		this.add(tfTitelzeile);

		checkTitelzeile = new JCheckBox("Titelzeile anzeigen");
		checkTitelzeile.addChangeListener(this);
		this.add(checkTitelzeile);

		checkModal = new JCheckBox("Modale Anzeige");
		this.add(checkModal);

		checkAlwaysOnTop = new JCheckBox("Immer als oberstes Fenster anzeigen");
		this.add(checkAlwaysOnTop);

		lblBreite = new JLabel("Breite:");
		this.add(lblBreite);
		tfBreite = new JTextField("400");
		this.add(tfBreite);

		lblHöhe = new JLabel("Höhe:");
		this.add(lblHöhe);
		tfHöhe = new JTextField("60");
		this.add(tfHöhe);

		checkProzentwert = new JCheckBox("Prozentwert anzeigen");
		checkProzentwert.addChangeListener(this);
		this.add(checkProzentwert);

		checkAnzeigeUnbestimmt = new JCheckBox("Anzeige unbestimmt");
		this.add(checkAnzeigeUnbestimmt);

		btnFarbe = new JButton("Farbe für Fortschrittsbalken auswählen ...");
		btnFarbe.addActionListener(this);
		this.add(btnFarbe);

		lblFarbe = new JLabel();
		lblFarbe.setOpaque(true);
		lblFarbe.setHorizontalAlignment(JLabel.CENTER);
		lblFarbe.setBackground(SystemColor.activeCaption);	

		this.add(lblFarbe);

		lblTransparenz = new JLabel("Transparenz:");
		this.add(lblTransparenz);

		sliderTransparenz = new JSlider(0, 100, 0);
		sliderTransparenz.addChangeListener(this);
		this.add(sliderTransparenz);

		lblBenutzertext = new JLabel("Benutzertext:");
		this.add(lblBenutzertext);
		tfBenutzertext = new JTextField();
		this.add(tfBenutzertext);

		lblStatustext = new JLabel("Statustext:");
		this.add(lblStatustext);
		tfStatustext = new JTextField();
		this.add(tfStatustext);

		checkAbbruch = new JCheckBox("Abbruch ermöglichen");
		this.add(checkAbbruch);

		lblFiller1 = new JLabel();
		this.add(lblFiller1);

		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		this.add(btnOK);

		btnBeenden = new JButton("Beenden");
		btnBeenden.addActionListener(this);
		this.add(btnBeenden);

	}

	public void showFrame()
	{
		initFrame();
		// Passt den Frame an das Layout und die Steuerelemente an.
		pack();
		this.setVisible(true);

	}

	private void initFrame()
	{
		this.setLocationRelativeTo(null);
		
		rbCenterOnParent.setSelected(true);
		checkTitelzeile.setSelected(true);
		checkModal.setSelected(true);
		checkAlwaysOnTop.setSelected(true);
		checkProzentwert.setSelected(true);
		lblFarbe.setText("RGB(" + lblFarbe.getBackground().getRed() + ", " + lblFarbe.getBackground().getGreen() + "," + lblFarbe.getBackground().getBlue() + ")");
		sliderTransparenz.setEnabled(!checkTitelzeile.isSelected());
		
		setTransparenz();

	}

	private void setTransparenz()
	{
		lblTransparenz.setText(String.format("Transparenz: %d", sliderTransparenz.getValue()) + " %");
	}

	private void showColorDialog()
	{
		Color newColor = JColorChooser.showDialog(this, "Farbe für Fortschrittsanzeige auswählen", lblFarbe.getBackground());
		if (newColor != null) lblFarbe.setBackground(newColor);

		lblFarbe.setText("RGB(" + lblFarbe.getBackground().getRed() + ", " + lblFarbe.getBackground().getGreen() + "," + lblFarbe.getBackground().getBlue() + ")");

	}

	private void showProgressWindow()
	{

		Thread t = new Thread(new DemoProgress());
		t.start();

	}

	// Beim AWT und bei Swing gibt es einen Thread, der für die
	// Oberflächenelemente verantwortlich ist: den AWT-Thread.
	// Er läuft parallel zum Hauptprogramm (ein Thread namens main) und führt den
	// Programmcode in den Listenern aus.
	// Der gestartete Thread darf keine Methoden auf Swing-Komponenten aufrufen –
	// das darf nur der AWT-Thread.
	// Andernfalls könnten zwei Programmteile parallel eine Swing-Komponente
	// verändern, was den Zustand ruinieren kann,
	// da Swing-Komponenten nicht gegen parallelen Zugriff geschützt sind.
	// Die Veränderung des Fortschritts muss also aus dem eigenen Thread heraus
	// erfolgen. Dadurch wird ein Ereignis in die
	// Ereigniswarteschlange eingefügt welches dann vom für die
	// Oberflächenelemente verantwortlichen AWT-Thread bearbeitet wird.

	private class DemoProgress implements Runnable
	{

		ProgressWindow	progressWindow;

		public DemoProgress()
		{
			// Vorbereiten der Fortschrittsanzeige und Anzeige
			Component owner = null;
			if (rbCenterOnParent.isSelected()) owner = ProgressWindowDemo.this;
			progressWindow = new ProgressWindow(owner);

			progressWindow.setMaxValue(10);
			progressWindow.setValue(0);
			progressWindow.setMessage(tfStatustext.getText());

			progressWindow.showTitleBar(checkTitelzeile.isSelected());
			progressWindow.setTitle(tfTitelzeile.getText());

			progressWindow.setAlwaysOnTop(checkAlwaysOnTop.isSelected());
			progressWindow.setModal(checkModal.isSelected());

			if (tfBenutzertext.getText().length() > 0) progressWindow.setString(tfBenutzertext.getText());

			if (tfBreite.getText().length() > 0) progressWindow.setWidth(Integer.parseInt(tfBreite.getText()));

			if (tfHöhe.getText().length() > 0) progressWindow.setHeight(Integer.parseInt(tfHöhe.getText()));

			progressWindow.showPercentage(checkProzentwert.isSelected());
			progressWindow.setIndeterminate(checkAnzeigeUnbestimmt.isSelected());

			progressWindow.setTransparency(sliderTransparenz.getValue());

			progressWindow.setProgressbarColor(lblFarbe.getBackground());

			if (!checkTitelzeile.isSelected()) progressWindow.showCancelButton(checkAbbruch.isSelected());

			// In einem eigenen Thread starten, damit der Dialog modal ausgeführt werden kann.
			Thread t = new Thread(progressWindow);
			t.start();

		}

		@Override
		public void run()
		{

			// Verarbeitung, die in der Fortschrittsanzeige dargestellt werden
			// soll.

			try
			{
				for (int i = 0; i < progressWindow.getMaxValue(); i++)
				{
					progressWindow.setValue(i);
					// Künstliche Verlängerung des Vorgangs
					Thread.sleep(1000);

					// Die Abfrage ob abgebrochen werden soll ist in der
					// Verarbeitungs-Routine besser, da man
					// hier den günstigsten Zeitpunkt für einen eventuellen Abbruch
					// wählen kann.
					if (progressWindow.cancelRequest())
					{
						
						if (queryCancel()) 
							break;
						else
							// Zurücksetzen der Abbruchanforderung
							progressWindow.setCancelRequest(false);
					}
				}

			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			progressWindow.close();

		}

		private boolean queryCancel()
		{
			boolean retValue = false;
			
			Toolkit.getDefaultToolkit().beep();
			Object[] options = { "Ja", "Nein" };
			
			if (JOptionPane.showOptionDialog(null, "Soll der laufende Prozess abgebrochen werden", "Abbruch", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == JOptionPane.YES_OPTION)
				retValue = true;
			
			return retValue;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ProgressWindowDemo f = new ProgressWindowDemo();
		f.showFrame();

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnOK)
			showProgressWindow();
		else if (e.getSource() == btnFarbe)
			showColorDialog();
		else if (e.getSource() == btnBeenden) 
			this.dispose();
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		
		if (e.getSource() == sliderTransparenz)
			setTransparenz();
		else if (e.getSource() == checkTitelzeile)
		{
			sliderTransparenz.setEnabled(!checkTitelzeile.isSelected());
			if (checkTitelzeile.isSelected())
			{
				sliderTransparenz.setValue(0);
				checkAbbruch.setSelected(true);
			}
		}
		else if (e.getSource() == checkProzentwert)
		{
			tfBenutzertext.setEnabled(checkProzentwert.isSelected());
		}
		
	}

}
