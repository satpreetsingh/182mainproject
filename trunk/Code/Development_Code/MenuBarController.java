import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;





/**
 * This class handles the control over all menu items except the predefined
 * tool list from ObjectDraw.
 * @author Joe
 *
 */
public class MenuBarController implements ActionListener, SessionListener {

	private Session session;
	protected DrawingCanvas canvas;
	protected JFileChooser FileChooser;
	protected MenuBarView menubarview;
	
	/**
	 * Constructor passes canvas into the controller.
	 * @param DrawingCanvas c
	 */
	MenuBarController (DrawingCanvas c, MenuBarView m){
		canvas = c;
		menubarview = m;	
	}
	
	
    /**
     * If user selected a menu item, perform the assigned action.
     * @param e ActionEvent                                                                                                                                                                     
     */
	public void actionPerformed(ActionEvent e) {
	
		/* Initialize the filename path string */
		String filename = "";
		
		
		
		/* Create a new session */
		if (e.getSource() == this.menubarview.mnuNew) {		
        	JOptionPane.showMessageDialog(
        			null,
        			"This version of MultiDraw is in beta development.  " + "\n" +
        			"Please look for upcoming releases which should include multiple session handling.",
        			"New Session",
        			JOptionPane.INFORMATION_MESSAGE);	

	
		}		
		
		/* Close the application */
		else if (e.getSource() == this.menubarview.mnuQuit) {		
			System.out.println("MenuBarController TODO:  Add in exiting a session code!");
			System.exit(0);			
		}		
		
		
		/* Attempt to dynamically load a class */
		else if (e.getSource() == this.menubarview.mnuAddTool) {
			/* Create the JFileChooser component, and set the directory to the user directory */
			FileChooser = new JFileChooser(System.getProperty("user.dir"));
		
			/* Initiate the classes to null */
			Class NewShapeClass = null;
			
			Object FactoryObject = null;
			Method FactoryMethod = null;
			
		

			/* Now load the shape class */
			if  (FileChooser.showDialog(null, "Open Shape file") == JFileChooser.APPROVE_OPTION) {
				
				System.out.println(FileChooser.getSelectedFile().getName());
				
				filename = FileChooser.getSelectedFile().getName();
				
				/* Trim off the extension */
				filename = filename.substring(0, filename.indexOf("."));
				
				
				/* Add the results as optional output (defined by user preferences) */
				Output.processMessage("Filename = " + filename, Constants.Message_Type.info);
				
				CompilingClassLoader classloader = new CompilingClassLoader();
				
				/* Load the class */
				try {
					NewShapeClass = classloader.loadClass(filename);
					
				} catch (ClassNotFoundException e1) {
					Output.processMessage("Cannot load class = " + filename, Constants.Message_Type.error);
				}			
			}
			
			
			if (NewShapeClass != null) {
			
				
				/* Create a live object */
				try {
					FactoryObject = NewShapeClass.newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				/* We have the factory and shape class, now append the ToolList 
	
				/* Reload all of the items on the menubar */
		
			
			}
			
			else{
				/* Reset the objects to null */
				NewShapeClass = null;
			}	
		}

		
		
		/* We want to request control of a session */
		else if (e.getSource() == this.menubarview.mnuRequest) {
			
			
			/* Attempt to gain ownership of the session */
			session.processRequestOwnership(session.localUser.person, false);
				
			
			
		}
		
		/* We want to relinquish control of a session */
		else if (e.getSource() == this.menubarview.mnuRelinquish) {
				
        	JOptionPane.showMessageDialog(
        			null,
        			"This version of MultiDraw is in beta development.  " + "\n" +
        			"Please look for upcoming releases which should include relinquishing control of a session.",
        			"New Session",
        			JOptionPane.INFORMATION_MESSAGE);	
        	
		}
		
		/* We want to SAVE a session */
		else if (e.getSource() == this.menubarview.mnuSave) {
			
			/* Create the JFileChooser component, and set the directory to the user directory */
			FileChooser = new JFileChooser(System.getProperty("user.dir"));
			
		
			/* If the user clicked OK, get the path */
			if  (FileChooser.showDialog(null, "Save Session") == JFileChooser.APPROVE_OPTION) {
		
				/* Get the absolute path of the file the user entered in from the dialog window */
				filename = FileChooser.getSelectedFile().getAbsolutePath();
				 
				
				/* Add the results as optional output (defined by user preferences) */
				Output.processMessage("Filepath = " + filename, Constants.Message_Type.info);

				try {
					
					/* Save the canvas state to a user defined text file */
					canvas.doSave(filename);
					
				} catch (Exception ex) {              
					/* Do nothing */
				}		
			}      		
		}
		
		/* We want to LOAD a session */
		else if (e.getSource() == this.menubarview.mnuLoad){
			
			
			/* Only controllers can load a new session */
			if (session.drawable(session.localUser)){
				
			
			
				/* Create the JFileChooser component, and set the directory to the user directory */
				FileChooser = new JFileChooser(System.getProperty("user.dir"));
			
		
				/* If the user clicked OK, get the path */
				if  (FileChooser.showDialog(null, "Load Session") == JFileChooser.APPROVE_OPTION) {
			
					/* Get the absolute path of the file the user entered in from the dialog window */
					filename = FileChooser.getSelectedFile().getAbsolutePath();
				
					/* Add the results as optional output (defined by user preferences) */
					Output.processMessage("Filepath = " + filename, Constants.Message_Type.info);
				
					
					try{
						
						/*  Make sure the user selects a valid filename before loading (and clearing the canvas) */
						if (FileChooser.getSelectedFile().exists()) {
							
							/* Perform the loading of the canvas */
							canvas.doLoad(filename);
						}
						else {
		          			JOptionPane.showMessageDialog(
		          		  			null,
		          		  			"You must enter valid file.",
		          		  			"Input Error",
		          		  			JOptionPane.ERROR_MESSAGE); 
						}
					
					} catch (Exception ex) {              
						/* Do nothing */
					}	
				
					
				}
			}
			else{
      			JOptionPane.showMessageDialog(
      		  			null,
      		  			"You must be a controller of a session to load a new canvas!",
      		  			"Cannot process request",
      		  			JOptionPane.ERROR_MESSAGE);   
			}	
			
		}	
		
		
		/* Cast a vote among all users to overthrow the controller's power */
		else if (e.getSource() == this.menubarview.mnuRebellion) {
			
			
			/* Only controllers can load a new session */
			if (session.drawable(session.localUser) == false){
				session.processInciteRebellion();
			}
			else{
      			JOptionPane.showMessageDialog(
      		  			null,
      		  			"You must be a non-controller of a session to incite a rebellion!",
      		  			"Cannot process request",
      		  			JOptionPane.ERROR_MESSAGE); 
				
			}
			
		}
		
		
		/* Help -> About option was selected, display a message dialog with project info */
		else if (e.getSource() == this.menubarview.mnuHelp){
        	JOptionPane.showMessageDialog(
        			null,
        			"MultiDraw Development Team:  " + "\n" +
        			"Ben Helppi, " + "\n" +
        			"Joe Trapani, " + "\n" +
        			"Satpreet Singh, " + "\n" +
        			"Mark D'Souza, " + "\n" +
        			"Amanda Parker " + "\n" +
        			"\n" + 
        			"For questions, e-mail:  " + "\n" +
        			"benjamin-helppi@uiowa.edu, " + "\n" + 
        			"satpreetharcharan-singh@uiowa.edu, " + "\n" +
        			"jtrapani@uiowa.edu," + "\n" +
        			"amanda-parker@uiowa.edu," + "\n" +
        			"mark-dsouza@uiowa.edu," + "\n" +
        			"for any inquiries regarding MultiDraw.",
        			"About",
        			JOptionPane.INFORMATION_MESSAGE);			
		}
	}



	public Session getSession() {
		
		return session;
	}


	public void setSession(Session s) {
		
		session = s;
		
	}
	
}
