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
	private MenuBarController mBController;
	public JMenuItem mnuNew;
	public JMenuItem mnuQuit;
	public JMenuItem mnuRequest;
	public JMenuItem mnuRelinquish;
    public JMenuItem mnuAddTool;
    public JMenuItem mnuSave;
    public JMenuItem mnuLoad;
    public JMenuItem mnuHelp;
    
    
	
	/**
	 * Create a new MenuBar.
	 * @param canvas Canvas passed to controller.
	 */
	MenuBarView(DrawingCanvas canvas, ArrayList<ToolController> actions) 
	{
		
		
        /* --------------------- FILE MenuItems -------------------------- */
        JMenu fileMenu = new JMenu("File");
         
        /* Create File menu items */
        mnuNew = new JMenuItem("New");
        mnuQuit = new JMenuItem("Quit");       

        /* Add File menu items */
        fileMenu.add(mnuNew);
        fileMenu.add(mnuQuit);
        
        /* Add the FileMenu to the MenuBar */
        add(fileMenu);
       
        /* --------------------- TOOL MenuItems -------------------------- */
		JMenu toolMenu = new JMenu("Tool");
		
		for(int i = 0; i < actions.size(); i++)
		{
			Action a = (Action) actions.get(i);
			toolMenu.add(a);
		}
    		
		/* Add a list breaker */
        toolMenu.add(new JSeparator());
        
        /* Additional tool menu items */
        mnuAddTool = new JMenuItem("Add Tool");
        
        toolMenu.add(mnuAddTool);
     
        
		/* Add the toolMenu to the MenuBar */
		add(toolMenu);
		
		/* --------------------- CONTROL MenuItems -------------------------- */
        JMenu ctrlMenu = new JMenu("Control");
	
        /* Create Control menu items */
        mnuRequest = new JMenuItem("Request");
        mnuRelinquish = new JMenuItem("Relinquish");

        /* Add Control menu items */
        ctrlMenu.add(mnuRequest);
        ctrlMenu.add(mnuRelinquish);
        
        /* Add the ControlMenu to the MenuBar */
        add(ctrlMenu);
        
        /* --------------------- SESSION MenuItems -------------------------- */
        JMenu sessionMenu = new JMenu ("Session");

        /* Create Session menu items */
        mnuSave = new JMenuItem("Save");
        mnuLoad = new JMenuItem("Load");

        /* Add Session menu items */
        sessionMenu.add(mnuSave);
        sessionMenu.add(mnuLoad);
       
        /* Add the SessionMenu to the MenuBar */
        add(sessionMenu);
        
        /* --------------------- HELP MenuItems -------------------------- */
        JMenu helpMenu = new JMenu ("Help");

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
     
        /* Add the listener for the session item menu options */
        mnuSave.addActionListener((ActionListener)mBController);
        mnuLoad.addActionListener((ActionListener)mBController);
        
        /* Add the listener for the help menu item option */
        mnuHelp.addActionListener((ActionListener)mBController);      
        
	}
	
	
} 

