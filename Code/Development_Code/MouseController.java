import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Controller of the DrawingCanvas.
 * Handles events for the canvas.
 * @author bmhelppi
 *
 */
public class MouseController 
     implements MouseListener, MouseMotionListener
{
	
  private Session session;

  /**
   * Create a new MouseController, which
   * will control interactions for the active session.
   * @param s Session that is available to the Canvas.
   * Note if null, or Session doesn't allow it, no
   * drawing can be done.
   */
  public MouseController(Session s) 
  {
	  session = s;
  }

	/**
	 * Get the current Session.
	 * @return Return the session.
	 */
	public Session getCurrentSession()
	{
		return session;
	}
	
	/**
	 * Update the current Session
	 * @param s Session that will be used going forward.
	 */
	public void updateSesion(Session s)
	{
		session = s;
	}

  
  
  /**
   * Controller handles mouse pressed events.
   */
  public void mousePressed(MouseEvent e) 
  {
	  if (session != null)
	  {
		  session.processMousePress(e.getPoint(), false,null);
	  }
  }

  
  /**
   * Controller handles mouseReleased events.
   */
  public void mouseReleased(MouseEvent e) 
  { 
	  if (session != null)
	  {
		  session.processMouseRelease(e.getPoint(), false,null);
	  }
  }
  
  	/**
  	 * Controller handles mousedragged events.
  	 */
    public void mouseDragged(MouseEvent e) 
    {
	  if (session != null)
	  {
		  session.processMouseDrag(e.getPoint(), false,null);
	  }
    }

    /* Null listener methods */
	  public void mouseClicked(MouseEvent e) {}
	  public void mouseEntered(MouseEvent e) {}
	  public void mouseExited(MouseEvent e) {}
	  public void mouseMoved(MouseEvent e) {}
  
} 
