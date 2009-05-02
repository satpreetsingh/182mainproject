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
	 * @param canvas Canvas passed to controller.
	 */
	MenuBarView(DrawingCanvas canvas, ArrayList<ToolController> actions) 
	{

		/* Create a new controller that handles most of the menu item clicks */
		controller = new MenuBarController(canvas);
		
		
        /* --------------------- FILE MenuItems -------------------------- */
        JMenu fileMenu = new JMenu("File");
         
        /* Create File menu items */
        JMenuItem fileItem1 = new JMenuItem("New");
        JMenuItem fileItem2 = new JMenuItem("Quit");       
        
        /* Set the file name and listener */
        fileItem1.setName("fileItem1");
        fileItem2.setName("fileItem2");
                
        fileItem1.addActionListener((ActionListener)controller);
        fileItem2.addActionListener((ActionListener)controller);
          
        
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

        /* Set the tool name and listener */
        toolItem1.setName("toolItem1");
        
        toolItem1.addActionListener((ActionListener)controller);
        
        toolMenu.add(toolItem1);
     
        
		/* Add the toolMenu to the MenuBar */
		add(toolMenu);
		
		/* --------------------- CONTROL MenuItems -------------------------- */
        JMenu ctrlMenu = new JMenu("Control");
	
        /* Create Control menu items */
        JMenuItem ctrlItem1 = new JMenuItem("Request");
        JMenuItem ctrlItem2 = new JMenuItem("Relinquish");
        
        /* Set the name of the objects */
        ctrlItem1.setName("ctrlItem1");
        ctrlItem2.setName("ctrlItem2");
        
        /* Add the listener for the session item menu options */
        ctrlItem1.addActionListener((ActionListener)controller);
        ctrlItem2.addActionListener((ActionListener)controller);
 
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

        /* Set the name of the objects */
        sessionItem1.setName("sessionItem1");
        sessionItem2.setName("sessionItem2");
        
        /* Add the listener for the session item menu options */
        sessionItem1.addActionListener((ActionListener)controller);
        sessionItem2.addActionListener((ActionListener)controller);
 
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
        
        /* Add the listener for the help menu item option */
        helpItem1.addActionListener((ActionListener)controller);
        
        /* Add Help menu items */
        helpMenu.add(helpItem1);

        /* Add the HelpMenu to the MenuBar */
        add(helpMenu);
        
	}
  
} 

