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
		  session.processClearObjects(false);
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
    
	  
    if("Blue".equals(item)) {
      return Color.BLUE;
    }
    else if("Cyan".equals(item)) {
        return Color.CYAN;
    }
    else if("Green".equals(item)) {
    	return Color.GREEN;
    }
    else if("Magenta".equals(item)) {
        return Color.MAGENTA;
    }
    else if("Red".equals(item)) {
        return Color.RED;
    }
    else if("Orange".equals(item)) {
        return Color.ORANGE;
    }
    else if("Pink".equals(item)) {
        return Color.PINK;
    }
    else if("Yellow".equals(item)) {
        return Color.YELLOW;
    }
    else {
    	return Color.BLACK;
    }
  }

  protected boolean itemToType(Object item) {
	  
		/* By default, we always return 1 (draw outline) */     
		if("Solid".equals(item)) {
		  return false;	
		}
		else {
		  return true;	
		}	  
  }  

} 
