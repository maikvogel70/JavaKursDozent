package w6t5_Dozent;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Globals
{
	
	
	private static HashMap<String, String> hmLAF = new HashMap<>();
	
	// Privater Standard-Konstruktor.
	// Verhindert, dass eine Instanz dieser Klasse erstellt werden kann.
	// Dies ist auch nicht erforderlich, da diese Klasse nur
	// öffentliche und statische Methoden enthält.
	private Globals()
	{
		
	}
	

	public static int getLookAndFeels()
	{
		
		LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
		
		for (LookAndFeelInfo laf : lafInfo)
		{
			hmLAF.put(laf.getName(), laf.getClassName());	
		}
		
		return hmLAF.size();
		
	}
	
	public static HashMap<String, String> getLAFTable()
	{
		return hmLAF;
	}
	
	public static String getLookAndFeelName()
	{
		return UIManager.getLookAndFeel().getName();
	}
	
	
	public static void setLookAndFeel(String lafName, Component component)
	{
		
		if (!hmLAF.containsKey(lafName))
			return;
		
		
		String lafClassName = hmLAF.get(lafName);
		
		try
		{
			UIManager.setLookAndFeel(lafClassName);
			SwingUtilities.updateComponentTreeUI(component);
			
		}
		catch (Exception ex)
		{
			
		}
		
		
	}
	
	
	
}
