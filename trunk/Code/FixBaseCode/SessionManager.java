import java.util.ArrayList;


/**
 * Manages all the sessions that this program
 * participates in.
 * 
 * @author bmhelppi
 */
public class SessionManager {
	
	ArrayList <Session> availableSessions;
	ArrayList <Session> activeSessions;
	
	private DrawingCanvas canvas;
	
	/**
	 * Create a new SessionManager class.
	 */
	public SessionManager ()
	{
		availableSessions = new ArrayList<Session>();
		activeSessions = new ArrayList<Session>();
	}
	
	/**
	 * Update all sessions.
	 * TODO: Should be called periodically in some way.
	 */
	public void update()
	{
		
	}
	
	/**
	 * Create a new network session.  
	 * Automatically assumes this will be an active session.
	 * @param s New Session to publish.
	 */
	public void addNewSession(Session s)
	{
		activeSessions.add(s);
	}
	
	public void setFocusOnSession(Session s)
	{
		if (s != null)
		{
			canvas.updateSession(s);
		}
	}
}
