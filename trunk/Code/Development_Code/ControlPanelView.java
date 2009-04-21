import java.awt.event.*;
import java.util.EventListener;
import javax.swing.*;


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

	
	
	
	/**
	 * Create a new ControlPanelView
	 * @param s Session that the panel is linked to.
	 */
	public ControlPanelView(Session s) 
	{ 
	    session = s;
	    clearButton = new JButton("Clear");
	    add(clearButton);
	    comboType = new JComboBox();
	    
	    /* Make sure the set the name of this component for evaluation in the controller */
	    comboType.setName("comboType");
	    
	    comboType.addItem("outline");
	    comboType.addItem("solid");
	    add(comboType);
	    
	    
	    comboColor = new JComboBox();
	    
	    /* Make sure the set the name of this component for evaluation in the controller */
	    comboColor.setName("comboColor");
	    comboColor.addItem("black");
	    comboColor.addItem("blue");
	    comboColor.addItem("green");
	    comboColor.addItem("red");
	    add(comboColor);

	    /* Assign the listener to the components */
	    controller = new ControlPanelController(session);
	    clearButton.addActionListener((ActionListener)controller);
	    comboColor.addItemListener((ItemListener)controller);
	    comboType.addItemListener((ItemListener)controller);
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