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
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * This class handles the control over all menu items except the predefined
 * tool list from ObjectDraw.
 * @author Joe
 *
 */
public class MenuBarController implements ActionListener {

	
	protected DrawingCanvas canvas;
	protected JFileChooser FileChooser;
	
	
	/**
	 * Constuctor passes canvas into the controller.
	 * @param DrawingCanvas c
	 */
	MenuBarController (DrawingCanvas c){
		canvas = c;
	}
	
	
    /**
     * If user selected a menu item, perform the assigned action.
     * @param e ActionEvent                                                                                                                                                                     
     */
	public void actionPerformed(ActionEvent e) {
	
		/* Initialize the filename path string */
		String filename = "";
		
		
		
		/* We want to SAVE a session */
		if (e.getSource().toString().contains("sessionItem1")) {
			
			/* Create the JFileChooser component, and set the directory to the user directory */
			FileChooser = new JFileChooser(System.getProperty("user.dir"));
			
		
			/* If the user clicked OK, get the path */
			if  (FileChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
		
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
		
		/* We want to SAVE a session */
		else if (e.getSource().toString().contains("sessionItem2")){
			
			/* Create the JFileChooser component, and set the directory to the user directory */
			FileChooser = new JFileChooser(System.getProperty("user.dir"));
			
		
			/* If the user clicked OK, get the path */
			if  (FileChooser.showDialog(null, "Open") == JFileChooser.APPROVE_OPTION) {
			
				/* Get the absolute path of the file the user entered in from the dialog window */
				filename = FileChooser.getSelectedFile().getAbsolutePath();
				
				/* Add the results as optional output (defined by user preferences) */
				Output.processMessage("Filepath = " + filename, Constants.Message_Type.info);
				
				
				try{
				
					/* Perform the loading of the canvas */
					canvas.doLoad(filename);
				
				} catch (Exception ex) {              
					/* Do nothing */
				}	
			
				
			}
			
		}	
		
		
		/* Help -> About option was selected, display a message dialog with project info */
		else if (e.getSource().toString().contains("helpItem1")){
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
	
}
