package w7t5_Dozent;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import util.ImageUtil;

public class ScaleImage extends JFrame
{

	private JButton btnZoomIn, btnZoomOut;
	private ImageIcon zoomIn, zoomOut;
	
	
	public ScaleImage()
	{
		
		initializeComponents();
		
	}
	
	private void initializeComponents()
	{
		
		zoomIn = new ImageIcon(this.getClass().getResource("/images/lotto.png"));
		zoomOut = new ImageIcon(this.getClass().getResource("/images/lotto.png"));	
		
		this.setTitle("Bilder auf Steuerlemente skalieren");
		this.setBounds(10, 10, 510, 420);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnZoomOut = new JButton();
		btnZoomOut.setBounds(10, 10, 100, 50);
		btnZoomOut.setIcon(zoomOut);
		this.add(btnZoomOut);
	
		btnZoomIn = new JButton();
		btnZoomIn.setBounds(10, 140, 200, 100);
		btnZoomIn.setIcon(ImageUtil.scaleIconToComponent(zoomIn, btnZoomIn));
		this.add(btnZoomIn);
		
	}
	
	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}
	
	private void initFrame()
	{
		
		
		
		
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ScaleImage f = new ScaleImage();
		f.showFrame();
	}

}
