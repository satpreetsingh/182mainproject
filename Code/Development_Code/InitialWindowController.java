import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


/**
   * This class manages the Initial Window Controller.
   * @author jjtrapan
   *
   */

  public class InitialWindowController implements ActionListener {
  	  
	  JFrame InitialWindow;
	  JTextField editbox;
	  
	  JTextField txtUN;
	  JTextField txtIP; 
	  JTextField txtPort;
	  JTextField txtUN2;
	  JTabbedPane tabbedPane;
	  
	  
	  /**
  	   * Create a new instance of the Controller.
  	   */
  	  InitialWindowController(JFrame Window, 
  			  JTextField txtun, 
  			  JTextField txtip,
  			  JTextField txtport,
  			  JTextField txtun2,
  			  JTabbedPane tabbedpane){ 	
  		
  		InitialWindow = Window; 
  		txtUN = txtun;
  		txtIP = txtip;
  		txtPort = txtport;
  		txtUN2 = txtun2;
  		tabbedPane = tabbedpane;
  		
  	  }
  	  

  	  /**
  	   * The clearObjects button received an event,
  	   * ask Session to delete objects.
  	   */
  	  public void actionPerformed(ActionEvent e) {

  		  
  		  String username = "", ip = "", port = "";
  		  boolean IsSlave;
  		  
  		  
  		  /* If cancel button was clicked, exit application */
  		  if(e.getSource().toString().contains("Cancel")) { 
  			  System.exit(0);	   

  		  }
  		  
  		  /* btnOK was clicked */
  		  else if (e.getSource().toString().contains("OK")){  
 			  		    
    	
  			  
  			/* The user choose to be a client (slave) */  
  			IsSlave = (tabbedPane.getSelectedIndex() == 0);
  			
  			if (IsSlave) {
  	 			username = txtUN.getText();
        		ip		 = txtIP.getText();
        		port	 = txtPort.getText();    		
    		}
    		
    		else{
	   			username = txtUN2.getText();
    		}

  			System.out.println(username.trim());
  			
  			/* Check the user inputs to make sure they are valid */
    		if (username.trim().equals("")){
      			JOptionPane.showMessageDialog(
      		  			null,
      		  			"You must enter a valid username.",
      		  			"Input Error",
      		  			JOptionPane.INFORMATION_MESSAGE);   			
    		}
    		
    		else if ((ip.trim().equals("")) && 
    				 (IsSlave)){
          			JOptionPane.showMessageDialog(
          		  			null,
          		  			"You must enter a IP address.",
          		  			"Input Error",
          		  			JOptionPane.INFORMATION_MESSAGE); 
          			
    		}		
            else if ((port.trim().equals("")) && 
                     (IsSlave)){
              			JOptionPane.showMessageDialog(
              		  			null,
              		  			"You must enter a port.",
              		  			"Input Error",
              		  			JOptionPane.INFORMATION_MESSAGE);           			
            }
    		
   
            else{
            	
            
            	/* Display main canvas window */
            	JFrame frame = new JFrame();
            	frame.setTitle("MultiDraw Development Build");
            	frame.getContentPane().setLayout(new BorderLayout());
            	frame.getContentPane().add(new MultiDraw(),
  						      	BorderLayout.CENTER);
            	frame.addWindowListener(new AppCloser());
            	frame.pack();
            	frame.setSize(600, 600);
  		
  			
  			
  		
            	JOptionPane.showMessageDialog(
            			null,
            			username + ", welcome to MultiDraw!",
            			"Welcome",
            			JOptionPane.INFORMATION_MESSAGE);
  			
  			
            	/* Close the old window entirely and free it's memory */
            	InitialWindow.dispose();
            	InitialWindow.setVisible(false);
  			
            	/* Show the new main canvas window */
            	frame.setVisible(true);
            }
  			  
  		  }

  		  
  	  }  
  	  
  	  /* Inner class AppCLoser for terminating application  */
  	  /* when Close Window button of frame is clicked       */
  	  static class AppCloser extends WindowAdapter  {
  	    public void windowClosing(WindowEvent e) {
  	    	System.exit(0);
  	    }
  	  }  
  	  
  	  

  	
  	
  }