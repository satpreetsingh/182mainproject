import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EventListener;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JTextField;

/**
 * Manages viewable state of control panel.
 * @author ben
 *
 */
public class ControlPanelView extends JPanel implements SessionListener
{
  
	private Session session;
	private ControlPanelController controller;
	private JButton clearButton;
	protected JComboBox comboType;
	protected JComboBox comboColor;
	protected JLabel lblClientIP;
	protected JLabel lblControllerIP;
	
	/**
	 * Create a new ControlPanelView
	 * @param s Session that the panel is linked to.
	 */
	public ControlPanelView(Session s) 
	{ 
	    session = s;

        GridLayout gl = new GridLayout(3,0);
        setLayout(gl);
        setBackground(Color.LIGHT_GRAY);

        clearButton = new JButton("Clear Canvas");
	    add(clearButton);

	    /* Make sure the set the name of this component for evaluation in the controller */
        comboType = new JComboBox();
	    comboType.setName("comboType");
        
	    /* Set the possible shape types */
	    comboType.addItem("Outline");
	    comboType.addItem("Solid");
	    
	    add(comboType);
	    
	    
	    
	    comboColor = new JComboBox();
	    
	    /* Make sure the set the name of this component for evaluation in the controller */
	    comboColor.setName("comboColor");
	    comboColor.addItem("Black");
        comboColor.addItem("Blue");
        comboColor.addItem("Cyan");
        comboColor.addItem("Green");
        comboColor.addItem("Magenta");
	    comboColor.addItem("Red");
        comboColor.addItem("Orange");
        comboColor.addItem("Pink");
        comboColor.addItem("Yellow");
   	    
	    add(comboColor);

	    
	    
        JLabel lblclientIPTitle = new JLabel("My IP Address: ");
        add(lblclientIPTitle);

        
        
        /* Create the client IP address label */
        lblClientIP = new JLabel(RefreshClientIP());
        add(lblClientIP);

        /* Add a dummy buffer for spacing needs */
        JLabel buffer = new JLabel();
        add(buffer);
        
        
        JLabel lblControllerIPTitle = new JLabel("Host IP Address: ");
        add(lblControllerIPTitle);

        /* Create the controller IP address label */ 
        lblControllerIP = new JLabel(getControllerIP());
        add(lblControllerIP);
        
        
        
        repaint();

	    /* Assign the listener to the components */
	    controller = new ControlPanelController(session);
	    clearButton.addActionListener((ActionListener)controller);
	    comboColor.addItemListener((ItemListener)controller);
	    comboType.addItemListener((ItemListener)controller);

	}

    public String getControllerIP()
    {
        String ip = "Display the IP";

        //grab the IP and return it
        return ip;
    }
    
    
    
    public String RefreshClientIP() {
       	
    	try {
    		
            /* Get the user's IP Address */
            return InetAddress.getLocalHost().getHostAddress();
            
        } catch (UnknownHostException e) {
        	return "IP Not Found.";
        }

    }
	
    
    
  
	public Session getSession() 
	{
		return session;
		
	}
	public void setSession(Session s) 
	{
		session = s;
		controller.updateSession(s);
	}
}

	  
