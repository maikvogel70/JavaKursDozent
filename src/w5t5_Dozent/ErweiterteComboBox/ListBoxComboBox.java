package w5t5_Dozent.ErweiterteComboBox;

import java.awt.Color;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import util.ListItem;

public class ListBoxComboBox extends JFrame
		implements ListSelectionListener, ItemListener, KeyListener, ActionListener, FocusListener {
	// private JList<String> listBox;
	private JList<ListItem<Integer, String>> listBox;
	private JList<ListItem<Integer, String>> msListBox;
	// private DefaultListModel<String> listBoxModel;
	private DefaultListModel<ListItem<Integer, String>> listBoxModel;
	private DefaultListModel<ListItem<Integer, String>> msListBoxModel;
	private JScrollPane listBoxScroller, msListBoxScroller;

	private JComboBox<ListItem<Integer, ExtendedListItem>> cboFont, cboFontStyle;
	private JComboBox<ListItem<Integer, String>> cboFontSize;
	private DefaultComboBoxModel<ListItem<Integer, ExtendedListItem>> cboFontModel, cboFontStyleModel;
	private DefaultComboBoxModel<ListItem<Integer, String>> cboFontSizeModel;

	private JButton btnBeenden;

	private String[] fontFamilies;

	private JTextArea taFontDemo;
	private JScrollPane taScrollPane;

	private String fontDemoText;

	private char decimalSeparator;

	private util.MyFocusTraversalPolicy newPolicy;

	public ListBoxComboBox() {
		initializeComponent();
	}

	private void initializeComponent() {
		this.setTitle("Listbox und ComboBox Demo");
		this.setSize(510, 460);
		this.setLayout(null);
		// Keine Grössenänderung des Frames.
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ListBox
		// Zuerst mit einem ListModel<String> demonstrieren.
		// Anschliessend mit ListModel<ListItem>
		// listBoxModel = new DefaultListModel<String>();
		// listBox = new JList<String>(listBoxModel);
		listBoxModel = new DefaultListModel<>();
		listBox = new JList<>(listBoxModel);
		listBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listBox.setLayoutOrientation(JList.VERTICAL);
		listBox.addListSelectionListener(this);
		listBoxScroller = new JScrollPane(listBox);
		listBoxScroller.setBounds(10, 10, 280, 110);
		this.add(listBoxScroller);

		// Erst nach Demo mit ListModel<String> alle anderen
		// ListBoxen oder ComboBoxen hinzufügen

		// Multiselect ListBox
		msListBoxModel = new DefaultListModel<>();
		msListBox = new JList<>(msListBoxModel);
		msListBox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		msListBox.setLayoutOrientation(JList.VERTICAL);
		msListBox.addListSelectionListener(this);
		msListBoxScroller = new JScrollPane(msListBox);
		msListBoxScroller.setBounds(10, 130, 280, 110);
		this.add(msListBoxScroller);

		// Font ComboBox

		cboFontModel = new DefaultComboBoxModel<>();
		cboFont = new JComboBox<>(cboFontModel);
		cboFont.setBounds(300, 10, 180, 25);
		cboFont.setBackground(Color.WHITE);

		// CellRenderer zur Anzeige der Einträge in der Combox in der jeweiligen
		// Schriftart
		ListCellRenderer<Object> cboFontRenderer = new ComboBoxCellRenderer();
		cboFont.setRenderer(cboFontRenderer);

		cboFont.addItemListener(this);
		this.add(cboFont);

		// Font Style
		cboFontStyleModel = new DefaultComboBoxModel<>();
		cboFontStyle = new JComboBox<>(cboFontStyleModel);
		cboFontStyle.setBounds(10, 260, 220, 25);
		cboFontStyle.setBackground(Color.WHITE);
		cboFontStyle.addItemListener(this);
		this.add(cboFontStyle);

		// Font Size
		cboFontSizeModel = new DefaultComboBoxModel<>();
		cboFontSize = new JComboBox<>(cboFontSizeModel);
		cboFontSize.setBounds(235, 260, 60, 25);

		// ComboBox editierbar machen und einen KeyListener zur
		// Überprüfung der Eingabe zuweisen.
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

	public void showFrame() {
		initFrame();
		this.setVisible(true);
	}

	private void initFrame() {

		this.setLocationRelativeTo(null);

		// Dezimaltrennzeichen des Systems ermitteln
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		decimalSeparator = dfs.getDecimalSeparator();

		fontDemoText = "Franz jagt im komplett verwahrlosten Taxi quer durch Bayern. 1234567890";
		taFontDemo.setText(fontDemoText);

		populateListBox();
		populateMultiselectListBox();
		populateFontComboBox();
		populateFontStyleComboBox("Dialog");
		populateFontSizeComboBox();

		// Programmatische Auswahl eines Listbox Eintrags
		// und im sichtbaren Bereich der ListBox anzeigen.
		listBox.setSelectedIndex(8);
		listBox.ensureIndexIsVisible(listBox.getSelectedIndex());

		// Programmatische Mehrfachauswahl
		msListBox.setSelectedIndices(new int[] { 2, 3, 5, 9 });

		// Die Schriftart erneut setzen, damit der Inhalt der FontStyle-ComboBox
		// aktualsiert wird.
		setFont(cboFont.getSelectedItem().toString());

	}

	private void populateListBox() {

		for (int i = 1001; i <= 1010; i++) {
			listBoxModel.addElement(new ListItem<>(i, String.format("ListBox Eintrag %03d", i)));
		}

	}

	private void populateMultiselectListBox() {

		for (int i = 2001; i <= 2010; i++)
			msListBoxModel.addElement(new ListItem<>(i, String.format("Multiselect ListBox Eintrag %03d", i)));

	}

	private void populateFontComboBox() {
		ExtendedListItem cboItem;
		int i = 0;
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();

		fontFamilies = e.getAvailableFontFamilyNames();

		for (String ff : fontFamilies) {
			cboItem = new ExtendedListItem(ff, Font.PLAIN, 14, Color.BLACK, null, ff);
			cboFontModel.addElement(new ListItem<Integer, ExtendedListItem>(++i, cboItem));
		}

	}

	private void populateFontStyleComboBox(String fontFamily) {
		ExtendedListItem cboItem;

		// Neues Datenmodell erstellen
		cboFontStyleModel = new DefaultComboBoxModel<>();

		Icon ico = new ImageIcon(this.getClass().getResource("/images/Font.png"));

		cboItem = new ExtendedListItem(fontFamily, Font.PLAIN, 14, Color.BLACK, ico, "Standard");
		cboFontStyleModel.addElement(new ListItem<>(Font.PLAIN, cboItem));
		cboItem = new ExtendedListItem(fontFamily, Font.BOLD, 14, Color.BLACK, ico, "Fett");
		cboFontStyleModel.addElement(new ListItem<>(Font.PLAIN, cboItem));
		cboItem = new ExtendedListItem(fontFamily, Font.ITALIC, 14, Color.BLACK, ico, "Kursiv");
		cboFontStyleModel.addElement(new ListItem<>(Font.PLAIN, cboItem));
		cboItem = new ExtendedListItem(fontFamily, Font.BOLD + Font.ITALIC, 14, Color.BLACK, ico, "Fett und Kursiv");
		cboFontStyleModel.addElement(new ListItem<>(Font.PLAIN, cboItem));

		// CellRenderer zuweisen
		ListCellRenderer<Object> cboFontStyleRenderer = new ComboBoxCellRenderer();
		cboFontStyle.setRenderer(cboFontStyleRenderer);

		// Neues Datenmodell zuweisen
		cboFontStyle.setModel(cboFontStyleModel);

	}

	private void populateFontSizeComboBox() {
		int[] arrSize = new int[] { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72 };

		for (int i = 0; i < arrSize.length; i++)
			cboFontSizeModel.addElement(new ListItem<Integer, String>(arrSize[i], String.format("%d", arrSize[i])));

	}

	private int getCurrentFontIndex(String fontFamily) {
		int retValue = -1;
		for (int i = 0; i < fontFamilies.length; i++) {
			if (fontFamily.equalsIgnoreCase(fontFamilies[i])) {
				retValue = i;
				break;
			}
		}

		return retValue;
	}

	private void showValue(ListItem li) {
		System.out.println(li.getValueMember() + " - " + li.getDisplayMember());
	}

	private void showMultiSelectItems() {
		System.out.println();

		for (ListItem<Integer, String> li : msListBox.getSelectedValuesList())
			showValue(li);

		System.out.println();

	}

	private void setFont(String fontFamily) {

		ListItem<Integer, ExtendedListItem> listItem;

		Font font = new Font(fontFamily, taFontDemo.getFont().getStyle(), taFontDemo.getFont().getSize());
		taFontDemo.setFont(font);

		// Falls die Einträge in der Font ComboBox in ihrere eigenen Schriftart
		// angezeigt
		// werden, anpassen des ausgewählten Eintrags.
		// Die Auflistung der ComboBox und der Bereich zur Anzeige des
		// ausgewählten Eintrags sind unterschiedliche Komponenten.

		listItem = (ListItem<Integer, ExtendedListItem>) cboFont.getSelectedItem();
		cboFont.setFont(new Font(fontFamily, listItem.getDisplayMember().getFontStyle(),
				listItem.getDisplayMember().getFontSize()));

		// Aktualisieren der FontStyle-Datenmodells mit der neuen Schriftart

		// Sichern des aktuell ausgewählten Font Styles
		int i = cboFontStyle.getSelectedIndex();

		// Die Font COmboBox wird zuerst gefüllt und erst danach die FontStyle-ComboBox
		// Indikator dafür, ob die FontStyle-ComboBox bereits gefüllt wurde ist der
		// selektierte Eintrag.
		if (i > -1) {

			listItem = (ListItem<Integer, ExtendedListItem>) cboFontStyle.getSelectedItem();

			// // Datenmodel mit der neuen Schriftart aktualisieren
			populateFontStyleComboBox(fontFamily);
			//
			// // Den zuvor gesicherten FontStyle wieder setzen
			cboFontStyle.setSelectedIndex(i);

			// Den ausgewählten Eintrag ebenfalls aktualisieren
			cboFontStyle.setFont(new Font(fontFamily, listItem.getDisplayMember().getFontStyle(),
					listItem.getDisplayMember().getFontSize()));

		}

	}

	private void setFontStyle(int fontStyle) {
		ListItem<Integer, ExtendedListItem> li = null;
		ExtendedListItem cboItem = null;

		taFontDemo.setFont(taFontDemo.getFont().deriveFont(fontStyle));

		// Die Anzeige in der ComboBox dem ausgewählten Schriftstil anpassen
		// Nur wenn sich innerhalb des ListItems um ein ExtendeListItem als
		// DisplayMember handelt.
		// Die Auflistung der ComboBox und der Bereich zur Anzeige des
		// ausgewählten Eintrags
		// sind unterschiedliche Komponenten.

		if (cboFontStyle.getSelectedItem() instanceof ListItem) {
			li = (ListItem<Integer, ExtendedListItem>) cboFontStyle.getSelectedItem();
			if (li.getDisplayMember() instanceof ExtendedListItem) {
				cboItem = (ExtendedListItem) li.getDisplayMember();
				cboFontStyle.setFont(cboFontStyle.getFont().deriveFont(cboItem.getFontStyle()));

				taFontDemo.setFont(taFontDemo.getFont().deriveFont(cboItem.getFontStyle()));
			}
		}

	}

	private void setFontSize(float fontSize) {
		taFontDemo.setFont(taFontDemo.getFont().deriveFont(fontSize));
	}

	private boolean isCharacterAllowed(char c, JTextComponent tc) {

		boolean retValue = false;

		// Nur Ziffern 0 - 9 und ein Dezimaltrennzeichen (Komma) sind erlaubt.

		// if (Character.isDigit(c))
		// retValue = true;
		// else if (c == decimalSeparator &&
		// !tc.getText().contains(Character.toString(decimalSeparator)))
		// retValue = true;

		// oder:
		if (Character.isDigit(c)
				|| (c == decimalSeparator && !tc.getText().contains(Character.toString(decimalSeparator))))
			retValue = true;

		return retValue;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListBoxComboBox f = new ListBoxComboBox();
		f.showFrame();

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		// Das Auswählen eines Listbox-Eintrages löst eine Serie
		// von Ereignissen aus. Erst wenn alle Ereignisse abgeschlossen
		// sind (getValueIsAdjusting()), wird der ausgewählte Eintrag
		// übernommen.

		if (e.getSource() instanceof JList && !e.getValueIsAdjusting()) {

			if (e.getSource() == listBox)
				showValue(listBox.getSelectedValue());
			else if (e.getSource() == msListBox)
				showMultiSelectItems();

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Das keyPressed-Ereignis in der editierbaren Combobox löst
		// auch ein ItemStateChanged-Ereignis des ItemListeners aus.
		// Der Index ist dann, da keine Auswahl über die Liste getroffen
		// wurde, -1.
		if (e.getSource().equals(cboFontSize.getEditor().getEditorComponent()) && e.getKeyCode() == KeyEvent.VK_ENTER)
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource().equals(cboFontSize.getEditor().getEditorComponent())) {
			// Die Enter-Taste löst auch das in keyTyped-Ereignis aus.
			// Da die Taste nicht durch die Überprüfung von isDigit()
			// den Rückgabewert 'false' zurückliefert wird vorher
			// die Methode isISOControl() verwendet, die alle Steuer- und
			// Kontroll-Tasten erkennt. Dadurch wird der Signalton, der durch
			// die Return-Taste ausgelöst werden würde, unterdrücken.
			if (Character.isISOControl(e.getKeyChar()))
				return;

			// if (!Character.isDigit(e.getKeyChar()))
			// {
			// Toolkit.getDefaultToolkit().beep();
			// e.consume();
			// return;
			// }

			// Einen evtl. eingegeben Punkt durch ein Komma ersetzen.
			if (e.getKeyChar() == '.')
				e.setKeyChar(decimalSeparator);

			if (!isCharacterAllowed(e.getKeyChar(), (JTextComponent) e.getSource())) {
				Toolkit.getDefaultToolkit().beep();
				e.consume();
				return;
			}

		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JComboBox<?> cb = null;
		ListItem<?, ?> listItem = null;

		if (e.getSource() instanceof JComboBox) {

			cb = (JComboBox<?>) e.getSource();
			listItem = (ListItem<?, ?>) cb.getSelectedItem();

			// Nur auf das Ereignis für eine ComboBox reagieren,
			// wenn der Eintrag ausgewählt wird (vorher wird ein
			// anderer Eintrag deselektiert). Es treten also immer
			// 2 Ereignisse bei einer ComboBox auf. Der
			// SelectedIndex muss > -1 sein.

			// Die Verwendung von ItemEvent.DESELECTED verhindert, dass durch
			// das Füllen der Combobox ein Ereignis ausgelöst wird
			// denn das ist das Ereignis ItemEvent.SELECTED.
			// Somit muss auch der ItemListener nicht temporär
			// für die Combobox entfernt werden.
			// Trotz der Verwendung von ItemEvent.DESELECTED wird
			// beim Aufruf der Methode getSelectedIndex() der korrekte
			// Eintrag zurückgeliefert.

			// Keine Eintrag ausgewählt: selectedIndex = - 1.
			// Betrifft editierbare ComboBox 'cboFontSize', wenn
			// über die Tatatur eine Grösse eingegeben wurde.
			if (e.getStateChange() == ItemEvent.SELECTED && cb.getSelectedIndex() > -1) {

				showValue(listItem);

				if (e.getSource().equals(cboFont))
					setFont(listItem.toString());
				else if (e.getSource().equals(cboFontStyle))
					setFontStyle((int) listItem.getValueMember());
				else if (e.getSource().equals(cboFontSize))
					// Eine direkte Umwandlung von Object->Integer->Float
					// geht nicht.
					// Deshalb: mit der Methode floatValue() oder valueOf() der
					// Wrapper-Klasse Integer eine neue Instanz von Float
					// erstellen.

					setFontSize(new Float(((ListItem<Integer, String>) listItem).getValueMember()));
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnBeenden))
			this.dispose();

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == cboFontSize.getEditor().getEditorComponent())
			cboFontSize.getEditor().selectAll();
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == cboFontSize.getEditor().getEditorComponent())
			// Nur für Nachkommawerte. Dann muss das Komma durch einen Punkt
			// ersetzt werden.
			setFontSize(Float.valueOf("0" + cboFontSize.getEditor().getItem().toString().replaceAll(",", ".")));

	}

}
