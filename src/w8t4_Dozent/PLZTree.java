package w8t4_Dozent;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import util.ProgressWindow;
import util.StatusBar;

public class PLZTree extends JDialog implements ActionListener, MouseListener, WindowListener
{

	private JSplitPane splitPane;
	private MyJTree				tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	private JPanel rightPanel, infoPanel;
	
	private JMenuBar	        menuBar;
	private JMenu	           		menuDatei;
	private JMenuItem	        miReduzieren, miErweitern, miBeenden;
	private JMenuItem	        miGoogleMaps;
	
	private JPopupMenu contextMenu;
	
	private StatusBar	                statusBar;
	private JProgressBar		    progressBar;
	
	private Canvas canvas;
	private AWTBrowser browser;
	
	public PLZTree()
	{
		initializeComponents();
	
	}

	private void initializeComponents() 
	{
		Font font;
		this.setTitle("Postleitzahlen");
		this.setResizable(true);
		this.setPreferredSize(new Dimension(800, 600));
		
		// Das Schließen des Dialogs wird mit dem WindowListener überwacht. 
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		this.setLayout(new BorderLayout());
		
		createMenue();
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    // Permanentes Anpassen der Komponenten innerhalb der SplitPane
	    splitPane.setContinuousLayout(true);
	    splitPane.setDividerSize(3);
	    // Absolute Position des Trenners
	    //splitPane.setDividerLocation(400);
	     // Verteilung der Bereiche der Splitpane nach Gewichtung
	     splitPane.setResizeWeight(0.3);
	     
	     splitPane.setOneTouchExpandable(true);
	      
		this.add(splitPane, BorderLayout.CENTER);
		
		root = new DefaultMutableTreeNode("Postleitzahlen");
		
		font = new Font("Tahoma", Font.PLAIN, 12);
		
		treeModel = new DefaultTreeModel(root);
		tree = new MyJTree(treeModel);
		tree.setFont(font);
		tree.addMouseListener(this);
		JScrollPane treeScrollPane = new JScrollPane(tree);
		splitPane.setLeftComponent(treeScrollPane);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(200, 200));
		JScrollPane browserScrollPane = new JScrollPane(canvas);
		splitPane.setRightComponent( browserScrollPane);
		
		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(0, 25));
		statusBar.setStatusLabelFont(statusBar.getFont().deriveFont(Font.PLAIN));
		this.add(statusBar, BorderLayout.PAGE_END);
		
		// KontextMenü
		contextMenu = new JPopupMenu();
		miGoogleMaps = new JMenuItem("Ort in Google Maps anzeigen");
		miGoogleMaps.addActionListener(this);
		contextMenu.add(miGoogleMaps);
		
	}
	
	private void createMenue()
	{
		menuBar = new JMenuBar();

		menuDatei = new JMenu("Datei");
		menuBar.add(menuDatei);

		miErweitern =  Globals.createMenuItem(menuDatei, Globals.MenuItemType.ITEM_PLAIN, "Alle Knoten erweitern", null,  'e', null);
		miErweitern.addActionListener(this);
		miReduzieren =  Globals.createMenuItem(menuDatei, Globals.MenuItemType.ITEM_PLAIN, "Alle Knoten reduzieren", null, 'r', null);
		miReduzieren.addActionListener(this);
		
		menuDatei.addSeparator();

		miBeenden = Globals.createMenuItem(menuDatei, Globals.MenuItemType.ITEM_PLAIN, "Beenden", null, 0, null);
		miBeenden.addActionListener(this);
		
		this.setJMenuBar(menuBar);
	}
	
	private class MyJTree extends JTree
	{

		/**
		 * Um eine Performance-Steigerung beim Aufklappen eines JTrees zu
		 * erreichen, muß eine neue Klasse angelegt werden, die JTree erweitert,
		 * d.h. von ihr erbt. Deshalb wird in dem Beispiel die Klasse myJTree
		 * erstellt. Das wichtigste an der Klasse ist die Methode public
		 * Enumeration getExpandedDescendants(final TreePath parent). Dabei
		 * handelt es sich um eine Methode des JTree, welche aufgerufen wird, wenn
		 * der Baum aufgeklappt wird. Diese Methode tut meines Wissens nichts, was
		 * im Normalfall benötigt wird. Allerdings benötigt sie eine Menge
		 * Rechenzeit und beansprucht Speicherplatz, wenn der Baum aufgeklappt
		 * werden soll. Deshalb wurde sie überschreiben und liefert einfach den
		 * Wert null zurück.
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

		private DefaultMutableTreeNode searchNode(DefaultMutableTreeNode root, String suchBegriff)
		{

			Enumeration<?> e;

			DefaultMutableTreeNode node = null;

			// rooEnumeration erstellen
			if (root.getChildCount() > 0)
				e = root.breadthFirstEnumeration();
			else
				e = root.depthFirstEnumeration();

			// Enumeration durchlaufen
			while (e.hasMoreElements())
			{
				// Den nächsten Knoten lesen
				node = (DefaultMutableTreeNode) e.nextElement();
				// Den Root-Knoten überlesen.
				if (node.getParent() == null)
					continue;

				// Das Muster mit dem regulären Ausdruck erstellen::
				// Findet alle Suchbegriff innerhalb einer
				// Vergleichszeichenkette
				// unabhängig von Gross- oder Kleinschreibung (?i)
				Pattern p = Pattern.compile("(?i).*(" + suchBegriff + ").*");
				// EInen Matcher mit dem Vergleichszeichenkette erstellen
				Matcher m = p.matcher(node.getUserObject().toString());

				if (m.find())
				{
					// Knoten mit dem Suchbegriff gefunden
					return node;
				} else
				{
					if (node.getChildCount() > 0)
					{
						node = searchNode((DefaultMutableTreeNode) node.getFirstChild(), suchBegriff);
						if (node != null)
							return node;
					}
				}
			}

			// Es wurde kein Knoten mit dem Suchbegriff gefunden
			return null;
		}

		// Markiert den angegeben Knoten und sorgt dafür dass er angezeigt wird.
		public void selectNode(DefaultMutableTreeNode node)
		{
			TreeNode[] nodes = ((DefaultTreeModel) this.getModel()).getPathToRoot(node);
			TreePath path = new TreePath(nodes);
			this.scrollPathToVisible(path);
			this.setSelectionPath(path);
		}

		@Override
		public Enumeration getExpandedDescendants(final TreePath parent)
		{
			return null;
		}
	}
	
	public void showDialog()
	{
		pack();
		initDialog();
		this.setVisible(true);
		
		openBrowser();
		
		// Den Dialog erst ganz zum Schluss auf 'modal' setzen!
		this.setModal(true);
		
	}
	
	private void openBrowser()
	{

		browser = new AWTBrowser();
		
		 // AWT Browser als Thread starten
		 Thread t  = new Thread(browser);
		 t.start();
		 
	}
	
	private void initDialog()
	{
		readProperties(Globals.getApplicationConfigPath() + "\\" +  this.getClass().getName()+ ".config");
		populateTree();	
		tree.requestFocus();
		statusBar.setText("Bereit");
	}
	
	private void populateTree()
	{
		 // Thread zum Einlesen der Postleitzahlen verwenden.
		 Thread t  = new Thread(new ReadPostleitzahlenIntoTree());
		 t.start();
	}
	
	private void selectedPath(TreePath path)
	{
		String nodeValue = "";
		
		// Es wurde kein Knoten angeklickt, sondern das Symbol zum Auf- bzwe. Zuklappen
		if (path == null) return;
		
		DefaultMutableTreeNode node = (	DefaultMutableTreeNode) path.getLastPathComponent();
		
		switch (node.getLevel())
		{
		case 1:
			nodeValue = node.getUserObject().toString();
			break;
			
		case 2:
			nodeValue = node.getParent().toString() + " " + node.getUserObject().toString();
			break;
		}
		
		showGoogleMaps(nodeValue);
		
	}
	
	  private void showGoogleMaps(String adresse)
      {
			try
			{
				final String request = URLEncoder.encode(adresse,  "UTF-8");
				 browser.setURL(String.format("http://maps.google.de/maps?f=d&source=s_d&saddr=%s&hl=de&geocode=&mra=ls", request));
			}
			catch (UnsupportedEncodingException e) 	{}				
		}
	  
	private void readProperties(String Dateiname)
	{

		int x = 0;
		int y = 0;
		int width = -1;
		int height = -1;
		String property;
		
		Reader reader = null;

		try
		{
			reader = new FileReader(Dateiname);
			Properties prop = new Properties();
			prop.load(reader);

			property = prop.getProperty("Location.X");
			if (property != null) x = Integer.parseInt(property);
			property = prop.getProperty("Location.Y");
			if (property != null) y = Integer.parseInt(property);
			property = prop.getProperty("Width");
			if (property != null) width = Integer.parseInt(property);
			property = prop.getProperty("Height");
			if (property != null) height  = Integer.parseInt(property);
			
		}
		catch (IOException ex) 	{}
		finally
		{
			// Die close_Methode wirft eine Ausnahmebedingung.
			// Deshalb muss sie in einer try-catch-Anweisung formuliert
			// werden.
			try
			{
				reader.close();
			}
			catch (Exception ex) {	}
		}
		
		if (width == -1) width = this.getWidth();
		if (height == -1) height = this.getHeight();
		
		this.setBounds(x, y, width, height);

	}
	
	private void writeProperties(String Dateiname)
	{
		Writer writer = null;
		
		File f = new File(Dateiname);
		f = new File(f.getParent());
		
		if (!f.exists())
			f.mkdirs();
				
		Properties prop = new Properties();
		prop.setProperty("Location.X", String.valueOf(this.getLocation().x));
		prop.setProperty("Location.Y", String.valueOf(this.getLocation().y));
		prop.setProperty("Width", String.valueOf(this.getWidth()));
		prop.setProperty("Height", String.valueOf(this.getHeight()));
		
		try
		{
			writer = new FileWriter(Dateiname);
			prop.store(writer, null);
		}
		catch (IOException ex) 	{}
		finally
		{
			// Die close_Methode wirft eine Ausnahmebedingung.
			// Deshalb muss sie in einer try-catch-Anweisung formuliert
			// werden.
			try
			{
				writer.close();
			}
			catch (Exception ex) {	}
		}
	}
	
	private class ReadPostleitzahlenIntoTree implements Runnable
	{

		String SQL;
		int recordCount, readCount;
		
		ProgressWindow progressWindow;
		
		public ReadPostleitzahlenIntoTree()
		{
		
		}
		
		public void run()
		{
			
			 // Verhindern dass während des Imports Benutzermenü-Funktionen
			 // aufgerufen werden können.
			 for (int i = 0; i < menuBar.getMenuCount(); i++)
				 menuBar.getMenu(i).setEnabled(false);
			 
			 // Anzahl der Datensätze ermitteln
			  SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN";
			  Object obj = DBConnection.executeScalar(SQL);
	   		recordCount =  Integer.parseInt(obj.toString());
	   		
	   		// Fortschrittsanzeige vorbereiten und sichtbar machen
			progressWindow = new ProgressWindow(PLZTree.this);
			progressWindow.setMaxValue(recordCount);
			progressWindow.setValue(0);
			
			Thread pw = new Thread(progressWindow);
			pw.start();
			
			String SQL = "SELECT PLZ, ORT FROM POSTLEITZAHLEN ORDER BY PLZ";

			// Root Objekt sichern
			Object userObject = root.getUserObject();

			// Anzeige in Root-Objekt
			root.setUserObject("Postleitzahlen werden gelesen...");
			treeModel.reload(root);

			try
			{
				ResultSet rSet = DBConnection.executeQuery(SQL);

				while (rSet.next())
				{
					progressWindow.setValue(++readCount);
					
					if (readCount % 10 == 0)
						progressWindow.setMessage(String.format("Datensätze werden gelesen ...  [%d]", readCount));
					
					DefaultMutableTreeNode node = tree.findNode(root, rSet.getString("PLZ"));
					if (node == null)
					{
						node = new DefaultMutableTreeNode(rSet.getString("PLZ"));
						root.add(node);
					}

					node.add(new DefaultMutableTreeNode(rSet.getString("ORT")));
					
				}
			} catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Fehler beim EInlesen der Postleitzahlen: " + ex.getMessage(), "Postleitzahlen", JOptionPane.INFORMATION_MESSAGE);
			}

			// Benutzermenü wieder aktivieren
			 for (int i = 0; i < menuBar.getMenuCount(); i++)
				 menuBar.getMenu(i).setEnabled(true);	
			
			// Fortschrittsanzeige schließen
			 progressWindow.close();
			 			 
			// Wiederherstellen des zuvor gesicherten Root-Objekts
			root.setUserObject(userObject);

			treeModel.reload(root);

			tree.selectNode(root);
			
		}

	}

	private class AWTBrowser implements Runnable
	{

		private Browser browser;
		private Shell shell;
		private Display display;

		@Override
		public void run()
		{
			display = new Display();
			shell = SWT_AWT.new_Shell(display, canvas);
			shell.setSize(800, 600);
			shell.setLayout(new FillLayout());

			browser = new Browser(shell, SWT.NONE);
			browser.setSize(canvas.getWidth(), canvas.getHeight());
			
			shell.open();

			while (!shell.isDisposed())
			{
				if (!display.readAndDispatch())
				{
					// If no more entries in event queue
					display.sleep();
				}
			}
			
			display.dispose();
		}
	
		public void setURL(final String url)
		{
			
			try
			{

				display.syncExec(new Runnable()
				{
					@Override
					public void run()
					{
						browser.setUrl(url);
					}
				});
			}
			catch (Exception ex)
			{
			}
		}
		
		public void close()
		{
			try
			{
				display.syncExec(new Runnable()
				{

					@Override
					public void run()
					{
						shell.close();
						shell.dispose();
					}

				});
			}
			catch (Exception ex)
			{
			}
		}
}
 
	@Override
	public void mouseClicked(MouseEvent e)
	{
		 if (e.getClickCount() == 2 &&  e.getSource().equals(tree)) 
		  		 selectedPath(tree.getPathForLocation(e.getX(), e.getY()));	
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
	public void mouseReleased(MouseEvent e)
	{
		if (e.isPopupTrigger() && e.getSource().equals(tree))
			contextMenu.show(e.getComponent(), e.getX(), e.getY());
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(miBeenden)) 
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if (e.getSource().equals(miReduzieren)) 
			tree.collapseAll();
		else if (e.getSource().equals(miErweitern)) 
			tree.expandAll();
		else if (e.getSource().equals(miGoogleMaps))
			selectedPath(tree.getSelectionPath());
	}

	@Override
   public void windowActivated(WindowEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void windowClosed(WindowEvent arg0)
   {
		writeProperties(Globals.getApplicationConfigPath() + "\\" +  this.getClass().getName()+ ".config");
	   
   }

	@Override
   public void windowClosing(WindowEvent e)
   {
	   // Wenn das Datei-Menü nicht aktiviert ist,
			// Programm nicht beenden.
			if (!menuDatei.isEnabled()) return;
			
			browser.close();
			this.dispose();
	   
   }

	@Override
   public void windowDeactivated(WindowEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void windowDeiconified(WindowEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void windowIconified(WindowEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void windowOpened(WindowEvent arg0)
   {
	   // TODO Auto-generated method stub
	   
   }

}
