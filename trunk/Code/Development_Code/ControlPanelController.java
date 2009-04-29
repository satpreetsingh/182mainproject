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
		  session.clearObjects(false);
	  }
	  
	 
	  
  }
 
  /**
   * The color of the pen changed, update it.
   */
  public void itemStateChanged(ItemEvent e)  
  {    
	if ((e.getStateChange() == ItemEvent.SELECTED) && 
		(session != null)){ 
	   	
		if(e.getSource().toString().contains("comboColor")) { 
			session.canvas.setpenColor(itemToColor(e.getItem()));   
		}
		else if(e.getSource().toString().contains("comboType")) {
			session.canvas.setShapeType(itemToType(e.getItem()));

		}
    
		/* Refresh the canvas, which deselects the object */
		session.canvas.refresh();
    
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
    
    if("dark gray".equals(item))
    {
        return Color.darkGray;
    }
    else if("blue".equals(item))
    {
      return Color.blue;
    }
    else if("cyan".equals(item))
    {
        return Color.cyan;
    }
    else if("green".equals(item)) 
    {
    	return Color.green;
    }
    else if("magenta".equals(item))
    {
        return Color.magenta;
    }
    else if("red".equals(item))
    {
        return Color.red;
    }
    else if("orange".equals(item))
    {
        return Color.orange;
    }
    else if("pink".equals(item))
    {
        return Color.pink;
    }
    else if("yellow".equals(item))
    {
        return Color.yellow;
    }
    else if("light gray".equals(item))
    {
        return Color.lightGray;
    }
    else 
    {
    	return Color.black;
    }
  }

  protected boolean itemToType(Object item) {
	  
		/* By default, we always return 1 (draw outline) */     
		if("solid".equals(item)) {
		  return false;	
		}
		else {
		  return true;	
		}	  
  }  

} 
