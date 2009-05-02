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
public class ControlPanelView extends JPanel {
  
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
        //Line type
	    comboType.addItem("outline");
	    comboType.addItem("solid");
	    add(comboType);
	    
	    
	    comboColor = new JComboBox();
	    
	    /* Make sure the set the name of this component for evaluation in the controller */
	    comboColor.setName("comboColor");
        comboColor.addItem("dark gray");
        comboColor.addItem("blue");
        comboColor.addItem("cyan");
        comboColor.addItem("green");
        comboColor.addItem("magenta");
	    comboColor.addItem("red");
        comboColor.addItem("orange");
        comboColor.addItem("pink");
        comboColor.addItem("yellow");
        comboColor.addItem("light grey");
   	    comboColor.addItem("black");
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
	
    
    
    
	/**
	 * Change the Session that this ControlPanel is associated with.
	 * @param s Session to focus on.
	 */
	public void updateSession(Session s)
	{
		session = s;
		controller.updateSession(s);
	}

	  
  
}