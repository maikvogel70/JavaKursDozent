package w7t4_Dozent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import util.ListItem;

public class ListBoxCheckBoxDemo extends JFrame implements ActionListener
{
	
	private DefaultListModel personenListModel;
	private JCheckBoxList list;
	
	private DefaultListModel<JCheckBoxListItem> listModel;
	private JCheckBoxList<JCheckBoxListItem> listBox;
	private JScrollPane listScrollPane;

private JButton btnAlleAuswählen, btnAuswahlLöschen, btnAuswahlLesen, btnBeenden;
	
	public ListBoxCheckBoxDemo()
	{
		initializeComponents();
	}
		
	private void initializeComponents()
	{
		this.setTitle("ListBox mit CheckBoxen");
		this.setLayout(null);
		this.setBounds(200, 200, 490, 260);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		listModel = new DefaultListModel<JCheckBoxListItem>();
		listBox = new JCheckBoxList<JCheckBoxListItem>(listModel);
		listBox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listScrollPane = new JScrollPane(listBox);
		listScrollPane.setBounds(10, 10, 300, 200);
		this.add(listScrollPane);
		
		btnAlleAuswählen = new JButton("Alle auswählen");
		btnAlleAuswählen.setBounds(320, 10, 150, 25);
		btnAlleAuswählen.addActionListener(this);
		this.add(btnAlleAuswählen);
		
		btnAuswahlLöschen = new JButton("Auswahl aufheben");
		btnAuswahlLöschen.setBounds(320, 40, 150, 25);
		btnAuswahlLöschen.addActionListener(this);
		this.add(btnAuswahlLöschen);
		
		btnAuswahlLesen = new JButton("Auswahl lesen");
		btnAuswahlLesen.setBounds(320, 70, 150, 25);
		btnAuswahlLesen.addActionListener(this);
		this.add(btnAuswahlLesen);
		
		btnBeenden = new JButton("Beenden");
		btnBeenden.setBounds(320, 185, 150, 25);
		btnBeenden.addActionListener(this);
		this.add(btnBeenden);
		
	}
	
	private void initFrame()
	{
		
		// Daten hinzufügen
		populateListBox();
		
	}
	
	
	private void populateListBox()
	{
		JCheckBoxListItem<ListItem> checkBox;
		
		for (int i = 1; i <= 20; i++)
		{
			checkBox = new JCheckBoxListItem(new ListItem(i, "Eintrag: " + i));
			listModel.addElement(checkBox);
		}
		
	}
	
	private void getCheckBoxListSelection()
	{
		JCheckBoxListItem<ListItem> checkBox;
		ListItem listItem;
		int size = listModel.getSize();
		
		System.out.println();
			
		for (int i = 0; i < size; i++)
		{
			checkBox = listModel.getElementAt(i);
			if (checkBox.isSelected())
			{
				listItem = (ListItem)checkBox.getItem();
				System.out.println(listItem.getValueMember() + " " + listItem.getDisplayMember());
			}
		}
	}
	
	public void Show()
	{
		initFrame();
		this.setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
		ListBoxCheckBoxDemo f = new ListBoxCheckBoxDemo();
		f.Show();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnBeenden)
			this.dispose();
		else if (e.getSource() == btnAlleAuswählen)
			listBox.selectAll();
		else if (e.getSource() == btnAuswahlLöschen)
			listBox.deselectAll();
		else if (e.getSource() == btnAuswahlLesen)
			getCheckBoxListSelection();
	}






	
}
