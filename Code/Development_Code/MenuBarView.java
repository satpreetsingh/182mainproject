import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/**
 * Implements a MenuBar.
 * @author ben, jjtrapan
 *
 */
public class MenuBarView extends JMenuBar 
{
	
	private DrawingCanvas canvas;
	private MenuBarController mBController;
	private CompilingClassLoader loader;

	public JMenu fileMenu;
	public JMenuItem mnuNew;
	public JMenuItem mnuQuit;
	public JMenu toolMenu;
    public JMenuItem mnuAddTool;
    public JMenu ctrlMenu;
	public JMenuItem mnuRequest;
	public JMenuItem mnuRelinquish;
	public JMenuItem mnuRebellion;
	public JMenu sessionMenu;
    public JMenuItem mnuSave;
    public JMenuItem mnuLoad;
    public JMenu helpMenu;
    public JMenuItem mnuHelp;
    
    public ArrayList<ToolController> actions; 
    
    

	
	/**
	 * Create a new MenuBar.
	 * @param c Canvas passed to controller.
	 */
	MenuBarView(DrawingCanvas c, ArrayList<ToolController> a) 
	{
		
		canvas = c;
		actions = a;
		loader = new CompilingClassLoader();

		
		
        /* --------------------- FILE MenuItems -------------------------- */
        fileMenu = new JMenu("File");
         
        /* Create File menu items */
        mnuNew = new JMenuItem("New");
        mnuQuit = new JMenuItem("Quit");       

        /* Add File menu items */
        fileMenu.add(mnuNew);
        fileMenu.add(mnuQuit);
        
        /* Add the FileMenu to the MenuBar */
        add(fileMenu);
       
        /* --------------------- TOOL MenuItems -------------------------- */
		toolMenu = new JMenu("Tool");
		
		for(int i = 0; i < actions.size(); i++)
		{
			Action actionitem = (Action) actions.get(i);
			toolMenu.add(actionitem);
		}
    		
		/* Add a list breaker */
        toolMenu.add(new JSeparator());
        
        /* Additional tool menu items */
        mnuAddTool = new JMenuItem("Add Tool");
        
        /* Add another line breaker (for newly loaded tools) */
        toolMenu.add(new JSeparator());
        
        
        toolMenu.add(mnuAddTool);
     
        
		/* Add the toolMenu to the MenuBar */
		add(toolMenu);
		
		/* --------------------- CONTROL MenuItems -------------------------- */
        ctrlMenu = new JMenu("Control");
	
        /* Create Control menu items */
        mnuRequest = new JMenuItem("Request");
        mnuRelinquish = new JMenuItem("Relinquish");
        mnuRebellion = new JMenuItem("Rebellion");
        
        /* Add Control menu items */
        ctrlMenu.add(mnuRequest);
        ctrlMenu.add(mnuRelinquish);
        ctrlMenu.add(mnuRebellion);
        
        /* Add the ControlMenu to the MenuBar */
        add(ctrlMenu);
        
        /* --------------------- SESSION MenuItems -------------------------- */
        sessionMenu = new JMenu ("Session");

        /* Create Session menu items */
        mnuSave = new JMenuItem("Save");
        mnuLoad = new JMenuItem("Load");

        /* Add Session menu items */
        sessionMenu.add(mnuSave);
        sessionMenu.add(mnuLoad);
       
        /* Add the SessionMenu to the MenuBar */
        add(sessionMenu);
        
        /* --------------------- HELP MenuItems -------------------------- */
        helpMenu = new JMenu ("Help");

        /* Create File menu items */
        mnuHelp = new JMenuItem("About");

        /* Add Help menu items */
        helpMenu.add(mnuHelp);

        /* Add the HelpMenu to the MenuBar */
        add(helpMenu);
        
	}
  
	
	public void AddController (MenuBarController m){

		/* Pass in the controller */
		mBController = m;
		
		mnuNew.addActionListener((ActionListener)mBController);
		mnuQuit.addActionListener((ActionListener)mBController);
        mnuAddTool.addActionListener((ActionListener)mBController);     
       
        /* Add the listener for the session item menu options */
        mnuRequest.addActionListener((ActionListener)mBController);
        mnuRelinquish.addActionListener((ActionListener)mBController);
        mnuRebellion.addActionListener((ActionListener)mBController);
        
        /* Add the listener for the session item menu options */
        mnuSave.addActionListener((ActionListener)mBController);
        mnuLoad.addActionListener((ActionListener)mBController);
        
        /* Add the listener for the help menu item option */
        mnuHelp.addActionListener((ActionListener)mBController);      
        
	}
	
	
	
	public void AddNewTool(String name, byte[] classToLoad) {
		
		
		Class loadedclass = null;
	
		/* Convert the bytes into a class */
		try {loadedclass = loader.loadDynamicClass(name, classToLoad);} 
		catch (ClassNotFoundException e) {e.printStackTrace();}
		
		
		
		/* Load the new item on the tool bar */
		Action action = null;
		
		action = new ToolController(loadedclass.getName(), 
			    null, 	
			    "Draw a " + loadedclass.getName(), 
			    canvas, 
			    new TwoPointShapeTool(new DynamicTwoPointShapeFactory(loadedclass), loadedclass.getName()));

		
		/* Add the new tool to the list */
		if (action != null) {
			
			toolMenu.add(action);	
			
		}	
	}
	
	
} 
