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
	private JComboBox comboBox;
  
	
	/**
	 * Create a new ControlPanelView
	 * @param s Session that the panel is linked to.
	 */
	public ControlPanelView(Session s) 
	{ 
	    session = s;
	    clearButton = new JButton("Clear");
	    add(clearButton);
	    add(new JLabel("Pen color"));
	    comboBox = new JComboBox();
	    comboBox.addItem("black");
	    comboBox.addItem("blue");
	    comboBox.addItem("green");
	    comboBox.addItem("red");
	    add(comboBox);
	    controller = new ControlPanelController(session);
	    clearButton.addActionListener((ActionListener)controller);
	    comboBox.addItemListener((ItemListener)controller);

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