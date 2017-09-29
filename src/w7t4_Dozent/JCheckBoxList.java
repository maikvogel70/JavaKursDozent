package w7t4_Dozent;


// Eine ListBox für CheckBoxen

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class JCheckBoxList<T> extends JList implements MouseListener, KeyListener
{
	private Color selectedBackgroundColor = UIManager.getColor("List.selectionBackground");
	private Color selectedForegroundColor = UIManager.getColor("List.selectionForeground");


	public JCheckBoxList()
	{
		this.setCellRenderer(new CellRenderer());
		this.addMouseListener(this);
		this.addKeyListener(this);
		
	}

	public JCheckBoxList(ListModel listModel)
	{
		this();
		this.setModel(listModel);
		
	}
	
	
	protected class CellRenderer implements ListCellRenderer
	{
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			JCheckBox checkBox = (JCheckBox)value;
				
			if (checkBox.isSelected())
			{
				checkBox.setForeground(selectedForegroundColor);
				checkBox.setBackground(selectedBackgroundColor);
			}
			else
			{
				if (isSelected)
				{
					checkBox.setForeground(selectedForegroundColor);
					checkBox.setBackground(selectedBackgroundColor);
				}
				else
				{
					checkBox.setForeground(UIManager.getColor("List.foreground"));
					checkBox.setBackground(UIManager.getColor("List.background"));
				}
			}
			
			return checkBox;
		}
	}

	public void selectAll()
	{
		int size = this.getModel().getSize();
		for (int i = 0; i < size; i++)
		{
			JCheckBox checkbox = (JCheckBoxListItem)this.getModel().getElementAt(i);
			checkbox.setSelected(true);
		}
		this.repaint();
		
	}

	public void deselectAll()
	{
		int size = this.getModel().getSize();
		for (int i = 0; i < size; i++)
		{
			JCheckBox checkbox = (JCheckBoxListItem)this.getModel().getElementAt(i);
			checkbox.setSelected(false);
		}
		this.repaint();
		
	}
	
	public Color getSelectedBackgroundColor()
	{
		return selectedBackgroundColor;
	}

	public void setSelectedBackgroundColor(Color selectedBackgroundColor)
	{
		this.selectedBackgroundColor = selectedBackgroundColor;
	}


	public Color getSelectedForegroundColor()
	{
		return selectedForegroundColor;
	}

	public void setSelectedForegroundColor(Color selectedForegroundColor)
	{
		this.selectedForegroundColor = selectedForegroundColor;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		int index = locationToIndex(e.getPoint());
		if (index != -1)
		{
			JCheckBoxListItem checkBox = (JCheckBoxListItem)getModel().getElementAt(index);
			checkBox.setSelected(!checkBox.isSelected());
			this.repaint();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Automatisch generierter Methodenstub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Automatisch generierter Methodenstub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Automatisch generierter Methodenstub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Automatisch generierter Methodenstub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Automatisch generierter Methodenstub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Automatisch generierter Methodenstub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == KeyEvent.VK_SPACE)
		{
			JCheckBoxListItem checkBox = (JCheckBoxListItem)this.getSelectedValue();
			checkBox.setSelected(!checkBox.isSelected());
			this.repaint();
		}
		
	}

	
}