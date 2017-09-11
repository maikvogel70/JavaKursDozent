package util;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class StatusBar extends JMenuBar
{

	
	private JLabel statusText;
	
	
	public StatusBar()
	{
		
		// Das Standard-Layout der MenuBar ist das BoxLayout.
		// Damit sich der Label horizontal und vertikal an die 
		// MenuBar anpassen kann, muss das BorderLayout verwendet
		// verwendet werden.
		
		// Das BoxLayout durch eine BorderLayout ersetzen
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		statusText = new JLabel();
		
		this.add(statusText);
	}
	
	
	public void setText(String text)
	{
		statusText.setText(text);
		
	}
	
	
	public String getText()
	{
		return statusText.getText();
	}
	
	
	public void setIcon(Icon icon)
	{
		statusText.setIcon(icon);
	}
	
	
	public void setBorder(Border border)
	{
		
		super.setBorder(border);
	}
	
	public void setStatusLabelFont(Font font)
	{
		statusText.setFont(font);
	}
	
	
	public Font getStatusLabelFont()
	{
		return statusText.getFont();
	}
	
	public void setHorizontalAlignment(int alignment)
	{
		
		statusText.setHorizontalAlignment(alignment);
	}
	
	
	
}
