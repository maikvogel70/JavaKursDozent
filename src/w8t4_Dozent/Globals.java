package w8t4_Dozent;

import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Globals
{

	static private String appConfigPath;

	public static enum MenuItemType
	{
		ITEM_PLAIN, ITEM_CHECK, ITEM_RADIO
	}

	// Top-Level-Klassen können nicht statisch sein!
	//
	// Privater Standardkonstruktor.
	// Alle Methoden dieser Klasse sind statisch. Durch die Deklaration eines
	// eigenen
	// Standardkonstruktors wird verhindert, dass Java einen Standardkonstruktor
	// erstellt.
	// Die Änderung des Zugriffsmodifizierers von 'public' in 'private'
	// verhindert, dass
	// eine Instanz dieser Klasse erstellt werden kann.

	private Globals()
	{
		throw new AssertionError();
	}

	static public void setApplicationConfigPath(String Filename)
	{
		appConfigPath = Filename;
	}

	static public String getApplicationConfigPath()
	{
		return appConfigPath;
	}

	static public JMenuItem createMenuItem(JMenu menu, MenuItemType miType, String sText, ImageIcon image, int acceleratorKey, String sToolTip)
	{
		// Menutem erstellen
		JMenuItem menuItem = new JMenuItem();

		switch (miType)
		{
		case ITEM_PLAIN:
			menuItem = new JMenuItem();
			break;
		case ITEM_RADIO:
			menuItem = new JRadioButtonMenuItem();
			break;

		case ITEM_CHECK:
			menuItem = new JCheckBoxMenuItem();
			break;
		}

		// Add the item test
		menuItem.setText(sText);

		// Add the optional icon
		if (image != null)
			menuItem.setIcon(image);

		// Add the accelerator key
		if (acceleratorKey > 0)
			menuItem.setMnemonic(acceleratorKey);

		// Add the optional tool tip text
		if (sToolTip != null)
			menuItem.setToolTipText(sToolTip);

		menu.add(menuItem);

		return menuItem;
	}

	public static void centerOnDesktop(Window w)
	{
		w.setLocationRelativeTo(null);
	}

	public static void CenterOnParent(Window c, Window parent)
	{
		c.setLocationRelativeTo(parent);
	}

	public static long getNextKey()
	{
		long retValue = -1;

		String SQL = "SELECT MAX(PRIMARYKEY) FROM POSTLEITZAHLEN";
		Object obj = DBConnection.executeScalar(SQL);
		if (obj != null)
			retValue = Long.parseLong(obj.toString());

		return ++retValue;

	}

	public static String quote(String value)
	{
		return "'" + value.replaceAll("'", "''") + "'";
	}

	public static boolean istPLZOrtVorhanden(String PLZ, String Ort)
	{
		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN";
		SQL += " WHERE PLZ = " + Globals.quote(PLZ);
		SQL += " AND ORT = " + Globals.quote(Ort);

		Object obj = DBConnection.executeScalar(SQL);

		return Long.parseLong(obj.toString()) > 0;

	}

}
