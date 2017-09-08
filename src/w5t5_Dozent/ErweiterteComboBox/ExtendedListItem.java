package w5t5_Dozent.ErweiterteComboBox;

import java.awt.Color;

import javax.swing.Icon;

public class ExtendedListItem
{
	private String fontName;
	private int fontStyle;
	private int fontSize;
	private Color fontColor;
	private Icon ico;
	private String itemText;

	public ExtendedListItem(String fontName, int fontStyle, int fontSize, Color fontColor, Icon ico, String itemText)
	{
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		this.ico = ico;
		this.itemText = itemText;
	}

	public String getFontName()
	{
		return fontName;
	}

	public int getFontStyle()
	{
		return fontStyle;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public Color getFontColor()
	{
		return fontColor;
	}

	public Icon getIcon()
	{
		return ico;
	}

	public String getItemText()
	{
		return itemText;
	}

	@Override
	public String toString()
	{

		return getItemText();
	}

}
