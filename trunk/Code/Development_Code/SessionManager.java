import java.net.Socket;
import java.util.ArrayList;

/**
 * Manages all the sessions that this program
 * participates in.
 * 
 * If the object returned from getActiveSessions() is modified,
 * correctness not guaranteed.
 * @author bmhelppi
 */
public class SessionManager extends Thread {
	
	private ArrayList <Session> activeSessions;
	private Session sessionInFocus = null;
	private DrawingCanvas canvas;
	
	/**
	 * Create a new SessionManager class.
	 */
	public SessionManager ()
	{
		activeSessions = new ArrayList<Session>();
	}
	
	/**
	 * Start the thread for this object.
	 */
	public void run ()
	{
		while(true)
		{
			for(int i = 0; i < activeSessions.size(); i++)
			{
				updateActiveSession(activeSessions.get(i));
			}
		}
		
	}
	
	
	private void updateActiveSessionPartThree(Session s)
	{
		try
		{
			s.serverSock.setSoTimeout(1);
			Socket client = s.serverSock.accept();
			
			Output.processMessage("Accepted a connection from: "+
		        		client.getInetAddress(), Constants.Message_Type.debug);
			
		
			if (client != null)
			{
				ServerUtils.acceptConn1(client, s);
			}
			
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * Update data for an active session.
	 * This includes:
	 * 1.  Receive messages from active connections.
	 * 2.  Do keep alive messages if master of the session.
	 * 3.  Accept new connections.
	 * @param s Session to update.
	 */
	private void updateActiveSession(Session s)
	{
		/**
		 * Part One
		 */
		for (int i = 0; i < s.networkMembers.size(); i++)
		{
			ServerUtils.checkMessageFromClient(s.networkMembers.get(i), s.localUser);
		}
		
		/**
		 * Part Two
		 */
		//TODO: BMH
		
		/**
		 * Part Three
		 */
		updateActiveSessionPartThree(s);
	
	}
	
	/**
	 * Create a new network session.  
	 * Automatically assumes this will be an active session.
	 * If no sessions exist, assume it is Session in focus.
	 * @param s New Session to publish.
	 */
	public void addNewSession(Session s)
	{
		if(activeSessions.size() == 0)
		{
			sessionInFocus = s;
		}
		activeSessions.add(s);
		
	}
	
	/**
	 * Update the canvas that this SessionManager has control of
	 * to focus on a new session.
	 * @param s
	 */
	public void setFocusOnSession(Session s)
	{
		if (s != null)
		{
			canvas.updateSession(s);
		}
	}
	
	public Session getSessionInFocus()
	{
		return sessionInFocus;
	}
	
	/**
	 * Get the active sessions in this application.
	 * @return Returns a list of sessions.
	 */
	public ArrayList<Session> getActiveSessions()
	{
		return this.activeSessions;
	}
	
	/**
	 * Remove a session from those being managed.
	 * @param s Session to remove.
	 */
	public void removeSession(Session s)
	{
		for(int i = 0; i < activeSessions.size(); i++)
		{
			if(activeSessions.get(i) == s)
			{
				activeSessions.remove(i);
			}
		}
	}
}
