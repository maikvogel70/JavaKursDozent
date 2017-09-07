package w5t4_Dozent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DrawText extends JFrame implements ActionListener
{

	private JButton btnDraw1, btnDraw2;

	private String buttonText = "";

	public DrawText()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{
		
		this.setTitle("Textausgabe mit Drawing" );
		this.setSize(300, 300);
		
		// Kein Layout
		this.setLayout(null);
		
		this.setFont(new Font("Arial", Font.BOLD, 18));
		this.setForeground(Color.RED);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		btnDraw1 = new JButton("Textausgabe (1)");
		btnDraw1.setBounds(50, 150, 200, 30);
		btnDraw1.addActionListener(this);
		this.add(btnDraw1);
		
		btnDraw2 = new JButton("Textausgabe (2)");
		btnDraw2.setBounds(50, 185, 200, 30);
		btnDraw2.addActionListener(this);
		this.add(btnDraw2);
		
	}

	private void initFrame()
	{
		this.setLocationRelativeTo(null);
	}

	public void showFrame()
	{
		initFrame();
		this.setVisible(true);
	}

	private void drawTextOnFrame(String text)
	{

		Graphics g = this.getGraphics();

		g.drawString(text, 30, 50);

	}

	public static void main(String[] args)
	{

		DrawText f = new DrawText();
		f.showFrame();

	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		g.drawString(buttonText, 30, 50);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		// if (e.getSource() instanceof JButton)
		// {
		// drawTextOnFrame(((JButton)e.getSource()).getText() + " wurde ausgewählt");
		//
		// }

		if (e.getSource() instanceof JButton)
		{
			buttonText = ((JButton) e.getSource()).getText() + " wurde ausgewählt";

			// Neuzeichnen des Frames erzwingen
			// this.repaint();

			// oder (ist schneller als repaint())
			this.update(this.getGraphics());

		}

	}

}
