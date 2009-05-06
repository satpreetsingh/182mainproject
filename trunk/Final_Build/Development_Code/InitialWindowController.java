import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
  * This class manages the Initial Window Controller.
  * @author jjtrapan
  *
  */

public class InitialWindowController implements ActionListener 
{
  	  
	InitialWindowView initialView;
	JTextField editbox;
	  
	private SessionManager sessionManger;
    protected String username = "", ip = "";
    protected int port = -1;
    protected boolean IsSlave;
  		  
  		  
	  /**
  	   * Create a new instance of the Controller.
  	   */
  	  InitialWindowController
  	  (InitialWindowView initialView,
  			  SessionManager sessionManger)
    { 	
  		  this.sessionManger = sessionManger;
  		  this.initialView = initialView;
    }
  	  

  	  /**
  	   * The clearObjects button received an event,
  	   * ask Session to delete objects.
  	   */
  	  public void actionPerformed(ActionEvent e) {
  		  
  		  boolean portError = false;
  		  
  		  if(e.getSource() == initialView.buttonCancel)
  		  {
  			  System.exit(0);	   
  		  }
  		  else if (e.getSource() == initialView.buttonOk)
  		  {
  			  
  			/* The user choose to be a client (slave) */  
  			IsSlave = (initialView.tabbedPane.getSelectedIndex() == 0);
  			
  			if (IsSlave) 
  			{
  	 			username = initialView.userNameTextBox.getText();
        		ip		 = initialView.userIPTextBox.getText();
        		
        		
        		/** Attempt to convert the text into an integer.  
        		 * If it fails, then set the port to 3000.
        		 */
        		try
        		{
        			port	 = Integer.valueOf(initialView.userPortTextBox.getText()).intValue(); 
        				
        		}
        		catch(NumberFormatException e1) 
        		{
        			portError = true;
        			
        		}
        		
    		}
    		
    		else
    		{
	   			username = initialView.userNameMasterTextBox.getText();
    		}
  			
  			/* Check the user inputs to make sure they are valid */
  			
  			if (portError)
  			{
  				JOptionPane.showMessageDialog(
      		  			null,
      		  			"You must enter a valid port.",
      		  			"Input Error",
      		  			JOptionPane.ERROR_MESSAGE);   	
  			}
  			else if (username.trim().equals(""))
  			{
      			JOptionPane.showMessageDialog(
      		  			null,
      		  			"You must enter a valid username.",
      		  			"Input Error",
      		  			JOptionPane.ERROR_MESSAGE);   			
    		}
    		else if ((ip.trim().equals("")) && 
    				 (IsSlave))
    		{
          			JOptionPane.showMessageDialog(
          		  			null,
          		  			"You must enter a IP address.",
          		  			"Input Error",
          		  			JOptionPane.ERROR_MESSAGE); 
          			
    		}		
   
            else
            {
            	/* Display main canvas window */
            	JFrame frame = new JFrame();
            	frame.setTitle("MultiDraw Release Build 1.0");
            	frame.getContentPane().setLayout(new BorderLayout());
            	frame.getContentPane().add(new MultiDraw(sessionManger),
  						      	BorderLayout.CENTER);
            	frame.pack();
            	frame.setSize(800, 600);
  		
            	JOptionPane.showMessageDialog(
            			null,
            			username + ", welcome to MultiDraw!",
            			"Welcome",
            			JOptionPane.INFORMATION_MESSAGE);
  			
  			
            	/* Close the old window entirely and free it's memory */
            	initialView.initialWindow.setVisible(false);
            	initialView.initialWindow.dispose();
  			
            	/* Show the new main canvas window */
            	frame.setVisible(true);
            }
  		  }
  	  }  
  	  
}