import java.awt.*;
import java.awt.event.*;

/**
 * This class manages the controlPanel.
 * @author ben
 *
 */
public class ControlPanelController
	implements ActionListener, ItemListener 
{

  Session session;
  
  /**
   * Create a new instance of the Controller, with the session it should be interacting with.
   * @param s Session to interact with.
   */
  ControlPanelController(Session s) 
  {
	  session = s;
  }

  /**
   * The clearObjects button received an event,
   * ask Session to delete objects.
   */
  public void actionPerformed(ActionEvent e) {
	  if(session != null)
	  {
		  session.clearObjects();
	  }
  }
  
  /**
   * The color of the pen changed, update it.
   */
  public void itemStateChanged(ItemEvent e)  
  {
    if (e.getStateChange() == ItemEvent.SELECTED)
    {
    	if(session != null)
    	{
    		session.canvas.setpenColor(itemToColor(e.getItem()));
    	}
    }
  }
  
  /**
   * Change the Session that this Controller focuses on.
   * @param s Session to focus on.
   */
  public void updateSession(Session s)
  {
	  session = s;
  }
  private Color itemToColor(Object item) {
    
    if("black".equals(item)) 
    {
      return Color.black;
    }
    else if("blue".equals(item)) 
    {
      return Color.blue;
    }
    else if("green".equals(item)) 
    {
    	return Color.green;
    }
    else 
    {
    	return Color.red;
    }
  }
  
} 
