import java.awt.*;
import java.awt.event.*;

/**
 * This class manages the controlPanel.
 * @author ben
 *
 */
public class ControlPanelController
	implements ActionListener, ItemListener, SessionListener
{
    
	private Session session = null;
  

	/**
     * The clearObjects button received an event,
     * ask Session to delete objects.
     */
    public void actionPerformed(ActionEvent e) 
    {
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

  
  private Color itemToColor(Object item) 
  {
    
	  
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

  	protected boolean itemToType(Object item) 
  	{
  		
		/* By default, we always return 1 (draw outline) */     
		if("Solid".equals(item)) 
		{
		  return false;	
		}
		else 
		{
		  return true;	
		}	  
  	}

	@Override
	public Session getSession() {
		return session;
	}
	
	@Override
	public void setSession(Session s) {
		session = s;
		
	}  

} 
