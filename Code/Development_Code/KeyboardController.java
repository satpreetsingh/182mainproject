import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

/**
 * KeyboardController provides a class to accept Key events, 
 * and hand them to the current session.
 * @author bmhelppi
 *
 */
public class KeyboardController  
     implements KeyListener 
{ 
	
	private Session session;
  
    /**
     * Create a new instance of a keyboard controller class.
     * @param s Session that this class will interact with.
     */
	public KeyboardController(Session s)
    {
    	session = s;
    }
  
	/**
	 * Accept a keyPressed event.
	 */
	public void keyPressed(KeyEvent e) 
	{
		if (session != null)
		{
			session.processKeyPress(e.getKeyChar(), false, null);
		}
	}

	/**
	 * Accept a keyReleasedEvent.
	 */
	public void keyReleased(KeyEvent e) { 
		if(session != null)
		{
			session.processKeyRelease(e.getKeyChar(), false, null);
		}
	}  

	/**
	 * Accept a keyTyped event.
	 */
	public void keyTyped(KeyEvent e) {
		if(session != null)
		{
			session.processKeyTyped(e.getKeyChar(), false, null);
		}
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
  
}
