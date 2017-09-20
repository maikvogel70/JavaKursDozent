package util;

import java.awt.event.ActionListener;

import javax.swing.*;

public class SwingUtil
{

	
	// Art des Menüeintrags
	public static enum MenuItemType
	{
		ITEM_PLAIN, ITEM_CHECK, ITEM_RADIO
	}
	
	
	/**
	 * <li><b><i>createMenu</i></b></li>
	 * <br>
	 * <br>
	 * public JMenu createMenu(JMenuBar menuBar, String menuText, String menuName, int shortKey)
	 * <br>
	 * <br>
	 * Erstellt einen Menü.
	 * <br>
	 * <br>
	 * @param menuBar
	 * - Die Menüleiste, zu dem dieses Menü gehört.
	 * @param menuText
	 * - Der Text des Menüs.
	 * @param menuName
	 * - Optionaler Name des Menüs oder <b>null</b>.
	 * @param shortKey
	 * - Optionales Tastaturkürzel oder <b>0</b>.
	 * @return
	 * 	Menü.
	 */
	public static JMenu createMenu(JMenuBar menuBar, String menuText, String menuName, int shortKey)
	{
		
		JMenu menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);
		
		
		// Optionales Tastaturkürzel hinzufügen
		if (shortKey > 0)
		{
			menu.setMnemonic(shortKey);
		}
		
		menuBar.add(menu);
		
		return menu;
	}
	
	/**
	 * <li><b><i>createSubMenu</i></b></li>
	 * <br>
	 * <br>
	 * public JMenu createSubMenu(JMenu mainMenu, String menuText, String menuName, int shortKey)
	 * <br>
	 * <br>
	 * Erstellt ein Untermenü.
	 * <br>
	 * <br>
	 * @param mainMenu
	 * - Das Menü, zu dem das Untermenü hinzugefügt werden soll.
	 * @param menuText
	 * - Der Text des Menüs.
	 * @param menuName
	 * - Optionaler Name des Untermenüs oder <b>null</b>.
	 * @param shortKey
	 * - Optionales Tastaturkürzel oder <b>0</b>.
	 * @return
	 * - Untermenü.
	 */
	public static JMenu createSubMenu(JMenu mainMenu, String menuText, String menuName, int shortKey)
	{
		JMenu menu = null;

		// Hinzufügen des Menüs als Untermenü des Hauptmenüs
		menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);

		// Optionales Tastaturkürzel hinzufügen
		if (shortKey > 0)
			menu.setMnemonic(shortKey);

		mainMenu.add(menu);

		return menu;
	}
	
	/**
	 * <li><b><i>createPopUpMenuItem</i></b></li> 
	 * <br>
	 * <br>
	 * public JMenuItem createPopUpMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener,<br>
	 * &nbsp String miText, ImageIcon icon, int shortKey, String miToolTip) <br>
	 * <br>
	 * Erstellt einen Menüeintrag für ein Popup Menü. <br>
	 * <br>
	 * 
	 * @param menu
	 *            - Das Popup Menü, zu dem dieser Menüeintrag gehört.
	 * @param miName
	 *            - Optionaler Name des Menüeintrags oder <b>nulMenul</b>.
	 * @param miType
	 *            - Der Typ des Menüeintrags <b>MenuItemType</b>.
	 * @param actionListener
	 *            - Der ActionListener, der verwendet werden soll, wenn der Menüeintrag ausgewählt wurde oder <b>null</b>.
	 * @param miText
	 *            - Der Text des Menüeintrags.
	 * @param icon
	 *            - Symbol, welches links vor dem Text angezeigt werden soll oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturkürzel oder <b>0</b>.
	 * @param miToolTip
	 *            - Optionaler Text für den Tooltip oder <b>null</b>.
	 * @return Menüeintrag.
	 */
	public static JMenuItem createPopUpMenuItem(JPopupMenu menu, String miName, MenuItemType miType, ActionListener actionListener, String miText,
			ImageIcon icon, int shortKey, String miToolTip)
	{

		
		JMenuItem menuItem= null;

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

		// Name des Menüeintrags
		menuItem.setName(miName);

		// Menü Text hinzufügen
		menuItem.setText(miText);

		// Optionales Image
		menuItem.setIcon(icon);

		// Optionales Tastaturkürzel übernehmen
		if (shortKey > 0)
			menuItem.setMnemonic(shortKey);

		// Optionalen Tooltip hinzufügen
		menuItem.setToolTipText(miToolTip);

		// ActionListener hinzufügen
		menuItem.addActionListener(actionListener);

		// Menüeintrag zum Menü hinzufügen
		menu.add(menuItem);

		// Rückgabe des Menueintrags
		return menuItem;
	}
	
	/**
	 * <li><b><i>createMenuItem</i></b></li>
	 * <br>
	 * <br>
	 * public JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener,<br>&nbsp String miText, ImageIcon icon, int shortKey, String miToolTip)
	 * <br>
	 * <br>
	 * Erstellt einen Menüeintrag.
	 * <br>
	 * <br>
	 * @param menu
	 * 	- Das Menü, zu dem dieser Menüeintrag gehört.
	 * @param miName
	 * - Optionaler Name des Menüeintrags oder <b>null</b>.
	 * @param miType
	 * 	- Der Typ des Menüeintrags <b>MenuItemType</b>.
	 * @param actionListener
	 *  - Der ActionListener, der verwendet werden soll, wenn der Menüeintrag ausgewählt wurde oder <b>null</b>.
	 * @param miText
	 * 	- Der Text des Menüeintrags.
	 * @param icon
	 * 	- Symbol, welches links vor dem Text angezeigt werden soll oder <b>null</b>.
	 * @param shortKey
	 * 	- Optionales Tastaturkürzel oder <b>0</b>.
	 * @param miToolTip
	 * 	- Optionaler Text für den Tooltip oder <b>null</b>.
	 * @return
	 * 	  Menüeintrag.
	 */
	
	public static JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener, String miText, ImageIcon icon, int shortKey,
											String miToolTip)
	{
		
		JMenuItem menuItem = null;
		
		switch (miType)
		{
		
			case ITEM_CHECK:
				menuItem = new JCheckBoxMenuItem();
				break;
				
			case ITEM_RADIO:
				menuItem = new JRadioButtonMenuItem();
				break;
				
			default:
				menuItem = new JMenuItem();
				break;
		
		}
		
		
		menuItem.setName(miName);
		menuItem.setText(miText);
		menuItem.setIcon(icon);
		
		// Optionales Tastaturkürzel hinzufügen
		if (shortKey > 0)
		{
			menuItem.setMnemonic(shortKey);
		}

		menuItem.setToolTipText(miToolTip);
		menuItem.addActionListener(actionListener);
		
		menu.add(menuItem);
		
		return menuItem;
		
	}
	
	
}
