package w8t4_Dozent;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class JTreeDemo extends JFrame implements ActionListener, MouseListener, KeyListener, AWTEventListener
{

	private JMenuBar	        menuBar;
	private JMenu	           		menuDatei, menuBearbeiten;
	private JMenuItem	        miReduzieren, miErweitern, miSerialisieren, miDeSerialisieren, miBeenden;
	private JMenuItem	        miAllesLoeschen;
	
	private JSplitPane splitPane;
	private JPanel rightPanel, infoPanel;
	private MyJTree tree;
   private DefaultMutableTreeNode root ;
   private DefaultTreeModel treeModel;  
   private JLabel lblTitel, lblBLZ, lblBezeichnung, lblPLZ, lblOrt, lblKurzbezeichnung, lblPAN, lblBIC;
   private JLabel lblBLZInfo, lblBezeichnungInfo, lblPLZInfo, lblOrtInfo, lblKurzbezeichnungInfo, lblPANInfo, lblBICInfo;

   private JTextField tfSuche; 
   
   private ArrayList<DefaultMutableTreeNode> TreeNodeArray;  
   private int TreeNodeArrayIndex = -1;
   
   private static enum MenueItemType
	{
		ITEM_PLAIN, ITEM_CHECK, ITEM_RADIO
	};
	
	public JTreeDemo()
	{
		InitComponents();
	}

	private void InitComponents() 
	{
		this.setTitle("Banken anzeigen");
		this.setPreferredSize(new Dimension(800, 600));

		// Zum Abfangen der F3 Taste über den Dialog.
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);		
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.add(splitPane);
		
		CreateMenue();
		
      root = new DefaultMutableTreeNode("Banken");

      Font font = new Font("Tahoma", Font.PLAIN, 12);
      
      // Die Daten eines Baums sitzen in einem Model, das die Schnittstelle TreeModel implementiert. 
      // Das Model ist sehr einfach und muss lediglich die Aussage treffen, ob das Element ein Blatt 
      // oder eine Wurzel darstellt und wo ein Element in der Baumverästelung liegt.

      // Für einfache Bäume ist es nicht nötig, sich mit dem TreeModel auseinanderzusetzen, da Swing eine 
      // andere Möglichkeit bietet, die Verästelung darzustellen. 
      // Dazu gibt es für Knoten eine Schnittstelle TreeNode, die einen Eintrag im Baum repräsentiert. 
      // Die konkrete Klasse DefaultMutableTreeNode stellt einen Standardbaumknoten dar, der universell eingesetzt
      // werden kann; er ist eine Implementierung der Schnittstelle MutableTreeNode, die wiederum TreeNode erweitert. 
      // Mit der add()-Methode vom DefaultMutableTreeNode kann eine Baumstruktur geschaffen werden.

      treeModel = new DefaultTreeModel(root); 
      tree = new MyJTree(treeModel);
      tree.setFont(font);
      tree.addMouseListener(this);
      JScrollPane treeScrollPane = new JScrollPane(tree);
      splitPane.setLeftComponent(treeScrollPane);
		
      rightPanel = new JPanel( new BorderLayout());
      rightPanel.setBackground(Color.WHITE);
      splitPane.setRightComponent(rightPanel);
      
      lblTitel = new JLabel("Information");
      lblTitel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
      lblTitel.setOpaque(true);
      lblTitel.setBackground(Color.GRAY);
      lblTitel.setForeground(Color.WHITE);
      lblTitel.setHorizontalAlignment(JLabel.CENTER);
      lblTitel.setPreferredSize(new Dimension(300, 40));
      
      font = new Font("Tahoma", Font.BOLD, 16);
      lblTitel.setFont(font);
      rightPanel.add(lblTitel, BorderLayout.PAGE_START);
      
      // Bank Info Panel
      
      font = new Font("Tahoma", Font.PLAIN, 12);
          
      infoPanel = new JPanel(null);
      infoPanel.setBackground(Color.WHITE);
      infoPanel.setFont(font);
      
       rightPanel.add(infoPanel, BorderLayout.CENTER);
      
       lblBLZ = new JLabel("Bankleitzahl");
       lblBLZ.setBounds(20, 60, 200, 30);
       lblBLZ.setFont(infoPanel.getFont());
 		 infoPanel.add(lblBLZ);
 		 
 	   lblBLZInfo = new JLabel();
 	   lblBLZInfo.setBounds(300, 60, 300, 30);
 	  lblBLZInfo.setFont(infoPanel.getFont());
		 infoPanel.add(lblBLZInfo);
 		 
 		lblBezeichnung = new JLabel("Bezeichnung");
 		lblBezeichnung.setBounds(20, 100, 200, 30);
 		lblBezeichnung.setFont(infoPanel.getFont());
 		infoPanel.add(lblBezeichnung);
 		
 		lblBezeichnungInfo = new JLabel();
 		lblBezeichnungInfo.setBounds(300, 100, 300, 30);
 		lblBezeichnungInfo.setFont(infoPanel.getFont());
 		infoPanel.add(lblBezeichnungInfo);
 		
      lblPLZ = new JLabel("Postleitzahll");
      lblPLZ.setBounds(20, 140, 200, 30);
      lblPLZ.setFont(infoPanel.getFont());
		infoPanel.add(lblPLZ);
		
		 lblPLZInfo = new JLabel();
		 lblPLZInfo.setBounds(300, 140, 300, 30);
		 lblPLZInfo.setFont(infoPanel.getFont());
		infoPanel.add(lblPLZInfo);
			
		lblOrt = new JLabel("Ort");
		lblOrt.setBounds(20, 180, 200, 30);
		lblOrt.setFont(infoPanel.getFont());
		infoPanel.add(lblOrt);
		
		 lblOrtInfo = new JLabel();
		 lblOrtInfo.setBounds(300, 180, 300, 30);
		 lblOrtInfo.setFont(infoPanel.getFont());
	   infoPanel.add(lblOrtInfo);
			
		lblKurzbezeichnung = new JLabel("Kurzbezeichnung");
		lblKurzbezeichnung.setBounds(20, 220, 200, 30);
		lblKurzbezeichnung.setFont(infoPanel.getFont());
		infoPanel.add(lblKurzbezeichnung);
		
		lblKurzbezeichnungInfo = new JLabel();
		lblKurzbezeichnungInfo.setBounds(300, 220, 300, 30);
		lblKurzbezeichnungInfo.setFont(infoPanel.getFont());
		infoPanel.add(lblKurzbezeichnungInfo);
		
      
		// Primary Account Number
		lblPAN = new JLabel("Zahlungskartennummer (PAN)");
		lblPAN.setBounds(20, 260, 200, 30);
		lblPAN.setFont(infoPanel.getFont());
		infoPanel.add(lblPAN);
		
		lblPANInfo = new JLabel();
		lblPANInfo.setBounds(300, 260, 300, 30);
		lblPANInfo.setFont(infoPanel.getFont());
		infoPanel.add(lblPANInfo);
		
		
		// Bank Identifier Code
		lblBIC = new JLabel("Identifizierungsnummer (BIC)");
		lblBIC.setBounds(20, 300, 200, 30);
		lblBIC.setFont(infoPanel.getFont());
		infoPanel.add(lblBIC);
		
		lblBICInfo = new JLabel();
		lblBICInfo.setBounds(300, 300, 300, 30);
		lblBICInfo.setFont(infoPanel.getFont());
		infoPanel.add(lblBICInfo);
		
		
      // Permanentes Anpassen der Komponenten innerhalb der SplitPane
      splitPane.setContinuousLayout(true);
      splitPane.setDividerSize(3);
      // Absolute Position des Trenners
      //splitPane.setDividerLocation(400);
      // Verteilung der Bereiche der Splitpane nach Gewichtung
      splitPane.setResizeWeight(0.5);
      
	}
	
	private void CreateMenue()
	{
		menuBar = new JMenuBar();

		menuDatei = new JMenu("Datei");
		menuBar.add(menuDatei);

		miErweitern = CreateMenuItem(menuDatei, MenueItemType.ITEM_PLAIN, "Alle Knoten erweitern", null,  'e', null);
		miReduzieren = CreateMenuItem(menuDatei, MenueItemType.ITEM_PLAIN, "Alle Knoten reduzieren", null, 'r', null);
		menuDatei.addSeparator();

		miSerialisieren = CreateMenuItem(menuDatei, MenueItemType.ITEM_PLAIN, "Serialisieren", null,  's', null);
		miDeSerialisieren = CreateMenuItem(menuDatei, MenueItemType.ITEM_PLAIN, "Serialisierung einlesen", null,  'l', null);
		menuDatei.addSeparator();
		
		miBeenden = CreateMenuItem(menuDatei, MenueItemType.ITEM_PLAIN, "Beenden", null, 0, null);
		miBeenden.setName("miBeenden");
		
		menuBearbeiten = new JMenu("Bearbeiten");
		menuBar.add(menuBearbeiten);
		
		miAllesLoeschen = CreateMenuItem(menuBearbeiten, MenueItemType.ITEM_PLAIN, "Alle Einträge löschen", null,  'A', null);
		
		/*
		 * Die BoxLayout-Klasse bietet verschiedene statische Hilfskomponenten an, um Lücken zu schaffen und das Wiederherstellen der Größe zu steuern. 
		 * Diese Komponenten können genau wie jede andere Komponente zur Box hinzugefügt werden.
		 * Beispiele für solche Komponenten sind: 
		 * 		Component createGlue();
		 * 		Component createHorizontalGlue();
		 * 		Component createVerticalGlue();
       *
       * Diese Methoden erstellen "glue" (Leim) - Komponenten, welche zwischen 2 Komponenten mit fester Größe gesetzt werden können. 
       * Eigentlich ist die Bezeichnung "Leimkomponente" falsch gewählt. Es würde voraussetzen, dass sie die Komponenten an einem Platz "festklebt". 
       * Das ist jedoch nicht der Fall. Die Komponente kann nachrutschen, wenn die Größe des Muttercontainers verändert wird und füllt somit eventuell 
       * entstandene Lücken aus. Glue - Komponenten können überall eingefügt werden, wo freier Platz benötigt oder gewünscht wird.
       * Die horizontalen und vertikalen Glue-Komponenten strecken sich entlang der entsprechenden Achse. 
       * Die allgemeine Glue-Komponente kann sich in beide Richtungen strecken, falls dies nötig sein sollte. 
		 */
		
		Box box = Box.createHorizontalBox();
      box.add(Box.createHorizontalGlue());
      
      JLabel lblSuche = new JLabel("Suche [F3]:  ");
      box.add(lblSuche);
      
      tfSuche = new JTextField();
		tfSuche.setPreferredSize(new Dimension(200,25));
		tfSuche.setMaximumSize(new Dimension(200,25));
		tfSuche.addKeyListener(this);
		box.add(tfSuche);
		
		menuBar.add(box);
            
		this.setJMenuBar(menuBar);
		
	}
	
	private JMenuItem CreateMenuItem(JMenu menu, MenueItemType  miType, String miText, ImageIcon image, int acceleratorKey, String toolTip) 
	{
		JMenuItem menuItem = null;
		
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
			
		default:
			menuItem = new JMenuItem();
			break;
			
		}

		// Hinzufügen des Textes
		menuItem.setText(miText);
		
		// Optionales Image hinzufügen
		if (image != null)
			menuItem.setIcon(image);
		
		// Accelerator Key hinzufügen
		if (acceleratorKey > 0)
			menuItem.setMnemonic(acceleratorKey);
		
		// Optionalen Tooltip hinzufügen
		if (toolTip != null)
			menuItem.setToolTipText(toolTip);
		
		// Aktion Handler für dieses MenuItem hinzufügen
		menuItem.addActionListener(this);
		
		menu.add(menuItem);
				
		return menuItem;	
	}
	
	public void Show()
	{
		pack();
		InitFrame();
		this.setVisible(true);
	}
	
	private void InitFrame()
	{
	    
		infoPanel.setVisible(false);
		tree.requestFocus();
		
		
		 // Thread zum Einlesen der Postleitzahlen verwenden.
		 // Nur so kann die Statusanzeige aktualisiert werden.
		 Thread t  = new Thread(new ReadFileIntoTree("C:\\Temp\\BLZ.csv", tree));
		 t.start();
	 
	}
	 
   public void removeNode(DefaultMutableTreeNode selNode) 
   {
       if (selNode != null) 
       { 
           //get the parent of the selected node
           MutableTreeNode parent = (MutableTreeNode)(selNode.getParent());
       
           // if the parent is not null
           if (parent != null) 
           {
               //get the sibling node to be selected after removing the
               //selected node
               MutableTreeNode toBeSelNode = getSibling(selNode);
               
               //if there are no siblings select the parent node after removing the node
               if(toBeSelNode == null)
               {
                   toBeSelNode = parent;
               }
           
               //make the node visible by scroll to it
               TreeNode[] nodes = treeModel.getPathToRoot(toBeSelNode);
               TreePath path = new TreePath(nodes); 
               tree.scrollPathToVisible(path); 
               tree.setSelectionPath(path);

            
               //remove the node from the parent
               treeModel.removeNodeFromParent(selNode);
           }    
       }
       }  
       
       private MutableTreeNode getSibling(DefaultMutableTreeNode selNode)
       {
           //get previous sibling
           MutableTreeNode sibling = (MutableTreeNode)selNode.getPreviousSibling();

           if(sibling == null)
             {
                 //if previous sibling is null, get the next sibling
               sibling    = (MutableTreeNode)selNode.getNextSibling();
           }
           
           return sibling;
       } 
       
	private void insertNode(DefaultMutableTreeNode node, String childName)
	{

		// Create new node
		MutableTreeNode newNode = new DefaultMutableTreeNode(childName);

		// Insert new node as last child of node
		treeModel.insertNodeInto(newNode, node, node.getChildCount());
		
	}
	
	private void SelectedPath(TreePath path)
	{
		// Es wurde kein Knoten angeklickt, sondern das Symbol zum Auf- bzwe. Zuklappen
		if (path == null) return;
		
		DefaultMutableTreeNode node = (	DefaultMutableTreeNode) path.getLastPathComponent();
		
		if (node.getLevel() < 2) 
		{
				lblTitel.setText(path.getLastPathComponent().toString());
				infoPanel.setVisible(false);
		}
		
		if (node.getLevel() == 2 && node.getUserObject() instanceof Bank)
		{
			lblTitel.setText(node.getParent().toString());
			ShowBankInfo((Bank)node.getUserObject());
		}
		
	}
	
	private void SucheBankInArrayList(String suchBegriff)
	{
	
		DefaultMutableTreeNode node = null;
		Matcher m ;
		
		if (suchBegriff.length() == 0) return;
		
		// Das Muster mit dem regulären Ausdruck erstellen::
		// Findet alle Suchbegriff innerhalb einer
		// Vergleichszeichenkette
		// unabhängig von Gross- oder Kleinschreibung (?i)
		Pattern p = Pattern.compile("(?i).*(" + suchBegriff + ").*");
		
		for (int i = ++TreeNodeArrayIndex; i < TreeNodeArray.size(); i++)
		{
			// EInen Matcher mit dem Vergleichszeichenkette erstellen
			m = p.matcher(TreeNodeArray.get(i).getUserObject().toString());
			if (m.find())
			{
				node =  TreeNodeArray.get(i);
				TreeNodeArrayIndex = i;
				break;
			}	
		}

		if (node == null) 
			TreeNodeArrayIndex = -1;
		else
			tree.selectNode(node);

	}
	
	private void SucheBank(String suchBegriff)
	{
	
		if (suchBegriff.length() == 0) return;
		
		DefaultMutableTreeNode node = tree.searchNode(root, suchBegriff);
		if (node != null) tree.selectNode(node);
			
		
	}
	
	private void ShowBankInfo(Bank bank)
	{
	
		lblBLZInfo.setText(bank.getBankleitzahl());
		lblBezeichnungInfo.setText(bank.getBezeichnung());
		lblPLZInfo.setText(bank.getPostleitzahl());
		lblOrtInfo.setText(bank.getOrt());
		lblKurzbezeichnungInfo.setText(bank.getKurzbezeichnung());
		lblPANInfo.setText(bank.getPAN());
		lblBICInfo.setText(bank.getBIC());
		
		infoPanel.setVisible(true);
		
	}
	
	private void Serialisieren()
	{
		try
		{
				ObjectOutputStream objOut = new ObjectOutputStream(new 	BufferedOutputStream(new FileOutputStream("C:\\Temp\\JTreeDemo.ser")));
				objOut.writeObject(treeModel);
				objOut.close();
		}
		catch (Exception ex) {	}
		
	}
	
	private void SerialisierungLesen()
	{	
		treeModel = new DefaultTreeModel(root);
		
		try
		{
		ObjectInputStream objIn = new ObjectInputStream(new	BufferedInputStream(new FileInputStream("C:\\Temp\\JTreeDemo.ser")));
		treeModel  = (DefaultTreeModel) objIn.readObject( );
	 
		objIn.close();
		}
		catch (Exception ex) 	{	}
	
		tree.setModel(treeModel);
	
	}
	
	private void AllesLoeschen()
	{
		
		tree.setModel(null);
		treeModel = null;
		
	}
	
	
	private class MyJTree extends JTree
	{

	    /**
	     * Um eine Performance-Steigerung beim Aufklappen eines JTrees zu erreichen, muß eine neue Klasse angelegt werden, 
	     * die JTree erweitert,  d.h. von ihr erbt. 
	     * Deshalb wird in dem Beispiel die Klasse myJTree erstellt. Das wichtigste an der Klasse ist die Methode 
	     * public Enumeration getExpandedDescendants(final TreePath parent). Dabei handelt es sich um eine Methode des JTree, 
	     * welche aufgerufen wird, wenn der Baum aufgeklappt wird. Diese Methode tut meines Wissens nichts, was im Normalfall benötigt wird. 
	     * Allerdings benötigt sie eine Menge Rechenzeit und beansprucht Speicherplatz, wenn der Baum aufgeklappt werden soll. 
	     * Deshalb wurde sie überschreiben und liefert einfach den Wert null zurück. 
	     * 
	     */
		
	    public MyJTree(DefaultTreeModel treeModel) 
	    {
	        super(treeModel);
	    }
	 
	    public void expandAll() 
	    {
	        int row = 1;
	        while (row < getRowCount())
	        {
	            expandRow(row);
	            row++;
	        }
	    }
	 
		 private void collapseAll() 
		 {
		        int row = 1;
		        while (row < tree.getRowCount()) 
		        {
		           tree.collapseRow(row);
		            row++;
		        }
		      }
		 
		   public DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, String suchBegriff)
		  	{
		    	     	
		  		for (int i = 0; i < root.getChildCount(); ++i) 
		  		{
		  			TreeNode child = root.getChildAt(i);
		  					  			
		  			if (child.toString().equals(suchBegriff)) 
		  				return (DefaultMutableTreeNode) child;
		  			
		  		}

		  		return null;
		  	}
	    
		private DefaultMutableTreeNode searchNode(DefaultMutableTreeNode node, String suchBegriff)
		{		
			
			DefaultMutableTreeNode child;
			TreeModel model = tree.getModel();
			int childCount = tree.getModel().getChildCount(node);
			Matcher m ;
			
			// Das Muster mit dem regulären Ausdruck erstellen::
			// Findet alle Suchbegriff innerhalb einer
			// Vergleichszeichenkette
			// unabhängig von Gross- oder Kleinschreibung (?i)
			Pattern p = Pattern.compile("(?i).*(" + suchBegriff + ").*");

			// EInen Matcher mit dem Vergleichszeichenkette erstellen
			m = p.matcher(node.getUserObject().toString());
			if (m.find()) return node;
			
			for (int i = 0; i < childCount; i++)
			{
				child = (DefaultMutableTreeNode) model.getChild(node, i);
				// EInen Matcher mit dem Vergleichszeichenkette erstellen
				m = p.matcher(child.getUserObject().toString());
				if (m.find())
					return child;
				
				if (!model.isLeaf(child))
				{
					 child = searchNode(child, suchBegriff);
					 if (child != null) return child;
				}
			}

			return null;
		}
		   
		   // Markiert den angegeben Knoten und sorgt dafür dass er angezeigt wird.
		   public void selectNode(DefaultMutableTreeNode node)
		   {
		   	TreeNode[] nodes = ((DefaultTreeModel)this.getModel()).getPathToRoot(node);
		   	TreePath path = new TreePath(nodes); 
		   	this.scrollPathToVisible(path); 
		   	this.setSelectionPath(path);
		   }
		   
	    @Override
	    public Enumeration getExpandedDescendants(final TreePath parent) {
	        return null;
	    }
	}
	
	private class Bank implements Serializable
	{
		private String BLZ, Bezeichnung, PLZ, Ort, Kurzbezeichnung, PAN, BIC;
				
		public Bank(String BLZ, String Bezeichnung, String PLZ, String Ort, String Kurzbezeichnung,String PAN, String BIC)
		{
			this.BLZ = BLZ;
			this.Bezeichnung = Bezeichnung;
			this.Ort = Ort;
			this.PLZ = PLZ;
			this.Kurzbezeichnung = Kurzbezeichnung;
			this.PAN = PAN;
			this.BIC = BIC;
		}
		
		public String getBankleitzahl()
		{
			return this.BLZ;
		}
		
		public String getBezeichnung()
		{
			return this.Bezeichnung;
		}
		
		public String getPostleitzahl()
		{
			return this.PLZ;
		}
		
		public String getOrt()
		{
			return this.Ort;
		}
		
		public String getKurzbezeichnung()
		{
			return this.Kurzbezeichnung;
		}
		
		public String getPAN()
		{
			return this.PAN;
		}
		
		public String getBIC()
		{
			return this.BIC;
		}

		@Override
		public String toString()
		{
			return this.PLZ + " " + this.Ort + " (" + this.Kurzbezeichnung + ")";
		}
		
	}
	
	private class ReadFileIntoTree  implements Runnable
	  {
		private String zeile = null;
		private String[] split;
		
		private String Filename;
		private MyJTree tree;
		
		private Bank bank;
	
		public ReadFileIntoTree(String Filename, MyJTree tree)
	{
		this.Filename = Filename;
		this.tree = tree;
	}
    public void run()
    {
		 
    	// Root Objekt sichern
   	 Object userObject = root.getUserObject();
   	 
   	 // Anzeige in Root-Objekt
   	  root.setUserObject("Banken werden gelesen...");
   	  treeModel.reload(root);
   	
   	TreeNodeArray = new ArrayList<DefaultMutableTreeNode>();
   	  
   	  try
   		{
   			Scanner scanner = new Scanner(new FileInputStream(Filename));
   			while (scanner.hasNextLine()) 
   			{
   				zeile = scanner.nextLine();
   				
   				split =zeile.split(";");
   				
   				bank = new Bank(split[0], split[2], split[3], split[4], split[5], split[6], split[7]);
   				   				
   				DefaultMutableTreeNode node = tree.findNode(root, bank.getBezeichnung());
   				if (node == null)
   				{
   					   node = new DefaultMutableTreeNode(bank.getBezeichnung());
   					   root.add(node);
   					   TreeNodeArray.add(node);
   				}

   				DefaultMutableTreeNode bankNode = new DefaultMutableTreeNode(bank);
   				node.add( bankNode);	
   				TreeNodeArray.add(bankNode);
   			}

   			scanner.close();	
   			
   		}
   		catch (Exception ex)
   		{
   			JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Datei " + Filename + ": " + ex.getMessage(), "E/A Fehler",  JOptionPane.ERROR_MESSAGE);
   		}
   
   	  // Wiederherstellen des zuvor gesicherten Root-Objekts
   		root.setUserObject(userObject);
   		
   	    treeModel.reload(root);
  
   	    tree.selectNode(root);
  }
}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		JTreeDemo f = new JTreeDemo();
		f.Show();

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(miBeenden)) 
			this.dispose();
		else if (e.getSource().equals(miReduzieren)) 
			tree.collapseAll();
		else if (e.getSource().equals(miErweitern)) 
			tree.expandAll();
		else if (e.getSource().equals(miSerialisieren)) 
			Serialisieren();
		else if (e.getSource().equals(miDeSerialisieren)) 
			SerialisierungLesen();
		else if (e.getSource().equals(miAllesLoeschen)) 
			AllesLoeschen();
	}

	@Override
   public void mouseClicked(MouseEvent e)
   {
		  if (e.getSource().equals(tree)) 
		  		 SelectedPath(tree.getPathForLocation(e.getX(), e.getY()));
		  
	  
		
   }

	@Override
   public void mouseEntered(MouseEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void mouseExited(MouseEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void mousePressed(MouseEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void mouseReleased(MouseEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void keyPressed(KeyEvent e)
   {
		if (e.getSource().equals(tfSuche) && e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if (tfSuche.getText().length() > 0)
			{
				//SucheBank(tfSuche.getText());
				SucheBankInArrayList(tfSuche.getText());
			}		
		}
   }

	@Override
   public void keyReleased(KeyEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void keyTyped(KeyEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
	public void eventDispatched(AWTEvent e)
	{
		// Wenn KeyEvent angekommen ist
		if (e instanceof KeyEvent)
		{
			KeyEvent event = (KeyEvent) e;

			// Wurde eine Taste gedrückt (KEY_PRESSED, KEY_RELEASED, KEY_TYPED)
			if (event.getID() == KeyEvent.KEY_PRESSED)
			{
				// Auswertung des Tastaturcodes
				switch (event.getKeyCode())
				{
				case KeyEvent.VK_F3:
					SucheBankInArrayList(tfSuche.getText());
					break;
				}
			}
		}
		
	}

}
