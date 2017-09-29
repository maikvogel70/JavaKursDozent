package util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageUtil
{

	private ImageUtil() {}
	
	public static ImageIcon scaleIconToComponent(ImageIcon ico, Component c)
	{

		return scaleIcon(ico, new Dimension(c.getWidth(), c.getHeight()));
		
	}

	public static Image scaleImageToComponent(Image image, Component c)
	{

		return scaleImage(image, new Dimension(c.getWidth(), c.getHeight()));
		
	}
	
	public static Image scaleImage(Image image, Dimension dim)
	{
		int newWidth, newHeight;
		float factor = getFactor(image.getWidth(null), image.getHeight(null), dim);
		
		newWidth = (int)(image.getWidth(null) * factor);
		newHeight = (int)(image.getHeight(null) * factor); 
		
		return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		 
	}

	
	public static ImageIcon scaleIcon(ImageIcon ico, Dimension dim)
	{
		ImageIcon newIcon = null;
		int newWidth, newHeight;
		
		float factor = getFactor(ico.getIconWidth(), ico.getIconHeight(), dim);
		
		newWidth = (int)(ico.getIconWidth() * factor);
		newHeight = (int)(ico.getIconHeight() * factor); 
		
		// TYPE_INT_ARGB sorgt dafür, dass transparente Hintergründe transparent bleiben.
		BufferedImage bi = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = bi.createGraphics();
		// Verbessert die Qualität des scalierten Images.
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(ico.getImage(), 0, 0, newWidth, newHeight, null);
		g.dispose();
		
		newIcon = new ImageIcon(bi);
		
		return newIcon;
	}

	
	public static BufferedImage scaleBufferedImage(BufferedImage image, Dimension dim)
	{
		BufferedImage newImage = null;
		int newWidth, newHeight;
		
		float factor = getFactor(image.getWidth(), image.getHeight(), dim);
		
		newWidth = (int)(image.getWidth() * factor);
		newHeight = (int)(image.getHeight() * factor); 
		
		// TYPE_INT_ARGB sorgt dafür, dass transparente Hintergründe transparent bleiben.
		newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = newImage.createGraphics();
		// Verbessert die Qualität des scalierten Images.
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(newImage, 0, 0, newWidth, newHeight, null);
		g.dispose();
		
		
		return newImage;
	}
	
	public static float getFactor(int width, int height, Dimension dim)
	{
		float fx = dim.width / (float)width;
		float fy = dim.height / (float)height;
		
		return Math.min(fx,  fy);
		
	}
	
	public static BufferedImage toBufferedImage(Image image)
	{
	    if (image instanceof BufferedImage)
	           return (BufferedImage) image;
	    
	    // Create a buffered image with transparency
	    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D g = bufferedImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();

	    // Return the buffered image
	    return bufferedImage;
	}
	
	public static  BufferedImage rotate(BufferedImage img, int angle)
	{
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage rotateImage = new BufferedImage(w, h, img.getType());
		Graphics2D g = rotateImage.createGraphics();
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		g.drawImage(img, null, 0, 0);
		return rotateImage;
	}
	
	public static  BufferedImage verticalFlip(BufferedImage img)
	{

		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage flipImage = new BufferedImage(w, h, img.getType());
		Graphics2D g = flipImage.createGraphics();
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();

		return flipImage;
	}
	
	public static  BufferedImage horizontalFlip(BufferedImage img)
	{

		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage flipImage = new BufferedImage(w, h, img.getType());
		Graphics2D g = flipImage.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();

		return flipImage;
	}
}
