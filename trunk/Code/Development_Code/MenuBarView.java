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
	private MenuBarController controller;
		
	
	/**
	 * Create a new MenuBar.
	 * @param s Session passed to controller.
	 */
	MenuBarView(ArrayList<ToolController> actions) 
	{

		/* Create a new controller that handles most of the menu item clicks */
		controller = new MenuBarController();
		
		
        /* --------------------- FILE MenuItems -------------------------- */
        JMenu fileMenu = new JMenu("File");
         
        /* Create File menu items */
        JMenuItem fileItem1 = new JMenuItem("New");
        JMenuItem fileItem2 = new JMenuItem("Quit");       
        
        /* Add File menu items */
        fileMenu.add(fileItem1);
        fileMenu.add(fileItem2);
        
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
        JMenuItem toolItem1 = new JMenuItem("Add Tool");
        JMenuItem toolItem2 = new JMenuItem("Share Tool");

        toolMenu.add(toolItem1);
        toolMenu.add(toolItem2);      
        
		/* Add the toolMenu to the MenuBar */
		add(toolMenu);
		
		/* --------------------- CONTROL MenuItems -------------------------- */
        JMenu ctrlMenu = new JMenu("Control");
	
        /* Create Control menu items */
        JMenuItem ctrlItem1 = new JMenuItem("Request");
        JMenuItem ctrlItem2 = new JMenuItem("Relinquish");
        
        /* Add Control menu items */
        ctrlMenu.add(ctrlItem1);
        ctrlMenu.add(ctrlItem2);
        
        /* Add the ControlMenu to the MenuBar */
        add(ctrlMenu);
        
        /* --------------------- SESSION MenuItems -------------------------- */
        JMenu sessionMenu = new JMenu ("Session");

        /* Create File menu items */
        JMenuItem sessionItem1 = new JMenuItem("Save");
        JMenuItem sessionItem2 = new JMenuItem("Load");

        
        /* Add Session menu items */
        sessionMenu.add(sessionItem1);
        sessionMenu.add(sessionItem2);
       
        /* Add the SessionMenu to the MenuBar */
        add(sessionMenu);
        
        /* --------------------- HELP MenuItems -------------------------- */
        JMenu helpMenu = new JMenu ("Help");

        /* Create File menu items */
        JMenuItem helpItem1 = new JMenuItem("About");
        
        /* Set the name of the object */
        helpItem1.setName("helpItem1");
        
        /* Add a listener */
        helpItem1.addActionListener((ActionListener)controller);
        
        /* Add Help menu items */
        helpMenu.add(helpItem1);

        /* Add the HelpMenu to the MenuBar */
        add(helpMenu);
        
	}
  
} 

