package w5t5_Dozent;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormatSymbols;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import util.ListItem;

public class ListBoxComboBox extends JFrame implements ListSelectionListener, ItemListener, ActionListener, KeyListener, FocusListener
{

	private JList<ListItem<Integer, String>> listBox, msListBox;
	private DefaultListModel<ListItem<Integer, String>> listBoxModel, msListBoxModel;
	private JScrollPane listBoxScrollPane, msListBoxScrollPane;

	private JComboBox<ListItem<Integer, String>> cboFont, cboFontStyle, cboFontSize;
	private DefaultComboBoxModel<ListItem<Integer, String>> cboFontModel, cboFontStyleModel, cboFontSizeModel;

	private JTextArea taFontDemo;
	private JScrollPane taScrollPane;

	private JButton btnBeenden;

	private String fontDemoText;

	private String[] fontFamilies;

	
	private char decimalSeparator;
	
	
	public ListBoxComboBox()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{

		this.setTitle("ListBox und ComboBox Demo");
		this.setSize(510, 460);

		// Layout Manager ausschalten
		this.setLayout(null);

		// Keine Grössenänderung des Frames
		this.setResizable(false);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 1. Zuerst das Datenmodell erstellen
		listBoxModel = new DefaultListModel<>();

		// 2. Die Listbox erstellen und das Datenmodell als Referenz übergebn
		listBox = new JList<>(listBoxModel);
		listBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listBox.addListSelectionListener(this);

		// 3. Um die Listbox blättern zu können, eine ScrollPane erstellen und die
		// ListBox als Referenz übergeben.
		listBoxScrollPane = new JScrollPane(listBox);
		listBoxScrollPane.setBounds(10, 10, 280, 110);
		this.add(listBoxScrollPane);

		// Listbox mit Mehrfach-Selektion

		// 1. Zuerst das Datenmodell erstellen
		msListBoxModel = new DefaultListModel<>();

		// 2. Die Listbox erstellen und das Datenmodell als Referenz übergebn
		msListBox = new JList<>(msListBoxModel);
		msListBox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		msListBox.addListSelectionListener(this);

		// 3. Um die Listbox blättern zu können, eine ScrollPane erstellen und die
		// ListBox als Referenz übergeben.
		msListBoxScrollPane = new JScrollPane(msListBox);
		msListBoxScrollPane.setBounds(10, 130, 280, 110);
		this.add(msListBoxScrollPane);

		// Font ComboBox

		cboFontModel = new DefaultComboBoxModel<>();
		cboFont = new JComboBox<>(cboFontModel);
		cboFont.setBounds(300, 10, 180, 25);

		// Anzahl der sichtbaren Einträge in der Liste
		cboFont.setMaximumRowCount(12);

		cboFont.addItemListener(this);
		this.add(cboFont);

		// Font Style
		cboFontStyleModel = new DefaultComboBoxModel<>();
		cboFontStyle = new JComboBox<>(cboFontStyleModel);
		cboFontStyle.setBounds(10, 260, 220, 25);
		cboFontStyle.addItemListener(this);
		this.add(cboFontStyle);

		// Font Size
		cboFontSizeModel = new DefaultComboBoxModel<>();
		cboFontSize = new JComboBox<>(cboFontSizeModel);
		cboFontSize.setBounds(235, 260, 60, 25);

		// ComboBox editierbar machen
		// und einen KeyListener zur Überprüfung der Eingabe auf die editierbare Komponente zuweisen.
		// Zusätzlich auch noch einen FocusListener verwenden
		cboFontSize.setEditable(true);
		cboFontSize.getEditor().getEditorComponent().addKeyListener(this);
		cboFontSize.getEditor().getEditorComponent().addFocusListener(this);

		cboFontSize.addItemListener(this);
		this.add(cboFontSize);

		// Font Demo
		taFontDemo = new JTextArea();
		taFontDemo.setLineWrap(true);
		taFontDemo.setWrapStyleWord(true);
		taFontDemo.setMargin(new Insets(3, 3, 3, 3));
		taFontDemo.setFocusable(false);

		taScrollPane = new JScrollPane(taFontDemo);
		taScrollPane.setBounds(10, 290, 490, 90);
		taScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(taScrollPane);

		btnBeenden = new JButton("Beenden");
		btnBeenden.setBounds(195, 390, 120, 30);
		btnBeenden.addActionListener(this);
		this.add(btnBeenden);

	}

	private void initFrame()
	{
		// In der Mitte des Desktops anzeigen
		this.setLocationRelativeTo(null);

		fontDemoText = "Franz jagt im komplett verwahrlosten Taxi quer durch Bayern. 1234567890";
		taFontDemo.setText(fontDemoText);

		// Dezimaltrennzeichen des Systems ermitteln
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		decimalSeparator = dfs.getDecimalSeparator();
		
		populateListBox();
		populateMultiSelectListBox();

		populateFontComboBox();
		populateFontStyleComboBox();
		populateFontSizeComboBox();

		// Programmatische Auswahl eines ListBox Eintrags
		listBox.setSelectedIndex(8);
		listBox.ensureIndexIsVisible(listBox.getSelectedIndex());

		// Programmatische Mehrfachauswahl
		msListBox.setSelectedIndices(new int[]
		{ 2, 3, 5, 9 });

	}

	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}

	private void populateListBox()
	{

		for (int i = 1001; i <= 1010; i++)
		{
			listBoxModel.addElement(new ListItem<Integer, String>(i, "ListBox Eintrag " + i));
		}

	}

	private void populateMultiSelectListBox()
	{
		for (int i = 2001; i <= 2010; i++)
		{
			msListBoxModel.addElement(new ListItem<Integer, String>(i, "Multi-Select ListBox Eintrag " + i));
		}

	}

	private void populateFontComboBox()
	{

		int i = 0;

		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();

		fontFamilies = e.getAvailableFontFamilyNames();

		for (String ff : fontFamilies)
		{
			cboFontModel.addElement(new ListItem<Integer, String>(++i, ff));

		}

	}

	private void populateFontStyleComboBox()
	{

		cboFontStyleModel.addElement(new ListItem<Integer, String>(Font.PLAIN, "Standard"));
		cboFontStyleModel.addElement(new ListItem<Integer, String>(Font.BOLD, "Fett"));
		cboFontStyleModel.addElement(new ListItem<Integer, String>(Font.ITALIC, "Kursiv"));
		cboFontStyleModel.addElement(new ListItem<Integer, String>(Font.BOLD + Font.ITALIC, "Fett und Kursiv"));

	}

	private void populateFontSizeComboBox()
	{
		int[] arrSize = new int[]
		{ 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72 };

		for (int i = 0; i < arrSize.length; i++)
		{
			cboFontSizeModel.addElement(new ListItem<Integer, String>(arrSize[i], String.valueOf(arrSize[i])));
		}

	}

	private void showValue(ListItem<Integer, String> listItem)
	{

		System.out.println(listItem.getValueMember() + " - " + listItem.getDisplayMember());

	}

	private void showMultiSelectItems()
	{

		System.out.println();

		for (ListItem<Integer, String> li : msListBox.getSelectedValuesList())
		{
			showValue(li);
		}

		System.out.println();
	}

	private void setFont(String fontFamily)
	{

		Font font = new Font(fontFamily, taFontDemo.getFont().getStyle(), taFontDemo.getFont().getSize());
		taFontDemo.setFont(font);
	}

	private void setFontStyle(int fontStyle)
	{
		taFontDemo.setFont(taFontDemo.getFont().deriveFont(fontStyle));
	}

	private void setFontSize(float fontSize)
	{
		taFontDemo.setFont(taFontDemo.getFont().deriveFont(fontSize));
	}
	
	
	private boolean isCharacterAllowed(char c, JTextComponent tc)
	{
		
		boolean retValue = false;
		
		// Nur Ziffern 0 - 9 und ein Dezimaltrennzeichen (Komma) sind erlaubt.
		
		if (Character.isDigit(c))
		{
			retValue = true;
		}
		else if (c == decimalSeparator && !tc.getText().contains(Character.toString(decimalSeparator)))
		{
			retValue = true;
		}
		
		return retValue;
	}
	

	public static void main(String[] args)
	{
		ListBoxComboBox f = new ListBoxComboBox();
		f.showFrame();

	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{

		// Das Auswählen eines Listbox-Eintrages löst eine Serie
		// von Ereignissen aus. Erst wenn alle Ereignisse abgeschlossen
		// sind (getValueIsAdjusting()), wird der ausgewählte Eintrag
		// übernommen.

		if (e.getSource() instanceof JList && !e.getValueIsAdjusting())
		{

			if (e.getSource() == listBox)
				showValue(listBox.getSelectedValue());
			else if (e.getSource() == msListBox)
				showMultiSelectItems();

		}

	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{

		JComboBox<ListItem<Integer, String>> cbo;
		ListItem<Integer, String> listItem;

		if (e.getSource() instanceof JComboBox && e.getStateChange() == ItemEvent.SELECTED)
		{

			cbo = (JComboBox<ListItem<Integer, String>>) e.getSource();

			// Keine Eintrag ausgewählt: selectedIndex = - 1.
			// Betrifft editierbare ComboBox 'cboFontSize', wenn
			// über die Tastatur eine Grösse eingegeben wurde.
			
			if (cbo.getSelectedIndex() > -1)
			{

				listItem = (ListItem<Integer, String>) cbo.getSelectedItem();

				showValue(listItem);

				if (e.getSource() == cboFont)
				{

					setFont(listItem.getDisplayMember());

				}
				else if (e.getSource() == cboFontStyle)
				{
					setFontStyle(listItem.getValueMember());
				}
				else if (e.getSource() == cboFontSize)
				{
					setFontSize(listItem.getValueMember());
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnBeenden)
			this.dispose();

	}

	@Override
	public void focusGained(FocusEvent e)
	{

		if (e.getSource() instanceof JTextComponent)
		{
			((JTextComponent) e.getSource()).selectAll();
		}

	}

	@Override
	public void focusLost(FocusEvent e)
	{
		// Falls der Wert über die Zwischenablage eingefügt wurde
		try
		{
			if (e.getSource() == cboFontSize.getEditor().getEditorComponent())
			{
				setFontSize(Float.valueOf("0" + cboFontSize.getEditor().getItem().toString().replace(',', '.')));
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Eingabefehler", JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void keyPressed(KeyEvent e)
	{

		if (e.getSource() == cboFontSize.getEditor().getEditorComponent() && e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

		if (e.getSource() == cboFontSize.getEditor().getEditorComponent())
		{

			if (Character.isISOControl(e.getKeyChar()))
				return;

//			if (!Character.isDigit(e.getKeyChar()))
//			{
//
//				Toolkit.getDefaultToolkit().beep();
//				e.consume();
//				return;
//
//			}
			
			// oder
			
			// Einen evtl. eingegebenen Punkt in das Dezimaltrennzeichen umwandeln
			if (e.getKeyChar() == '.')
				e.setKeyChar(decimalSeparator);
			

			if (!isCharacterAllowed(e.getKeyChar(), (JTextComponent)e.getSource()))
			{

				Toolkit.getDefaultToolkit().beep();
				e.consume();
				return;

			}

		}

	}

}
