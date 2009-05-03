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
 * @author ben, joe 
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
	public ControlPanelView(ControlPanelController controller) 
	{ 

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
        lblClientIP = new JLabel();
        refreshClientIP();
        add(lblClientIP);

        /* Add a dummy buffer for spacing needs */
        JLabel buffer = new JLabel();
        add(buffer);
        
        JLabel lblControllerIPTitle = new JLabel("Host IP Address: ");
        add(lblControllerIPTitle);

        /* Create the controller IP address label */ 
        lblControllerIP = new JLabel("");
        refreshControllerIP();
        add(lblControllerIP);
        
        repaint();

	    /* Assign the listener to the components */
	   
	    clearButton.addActionListener((ActionListener)controller);
	    comboColor.addItemListener((ItemListener)controller);
	    comboType.addItemListener((ItemListener)controller);

	}

	/**
	 * Get the masters ip address.
	 * @return Returns the ip address of master.
	 */
    public void refreshControllerIP()
    {
    	String ip;
    	if(session != null)
    	{
    		ip = session.master.person.ipAddress + "@";
    		if (session.master.sock != null)
    		{
    			ip = ip + session.master.sock.getPort();
    		}
    	}
    	else
    	{
    		ip= "";
    	}
    	this.lblControllerIP.setText(ip);
    }
    
    
    /**
     * Get local ip address.
     * @return Returns local ip address.
     */
    public void refreshClientIP() 
    {
    	String ip;
    	if (session != null)
    	{
    		ip =  session.localUser.person.ipAddress + "@";
    		if (session.localUser.sock != null)
    		{
    			ip = ip + session.localUser.sock.getLocalPort(); 
    		}
    		
    	}
    	else
    	{
    		ip = "";
    	}
    	this.lblClientIP.setText(ip);

    }
	
  
	public Session getSession() 
	{
		return session;
		
	}
	public void setSession(Session s) 
	{
		session = s;
		refreshClientIP();
		refreshControllerIP();
	}
}

	  
