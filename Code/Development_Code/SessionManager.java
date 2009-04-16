import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;


/**
 * Manages all the sessions that this program
 * participates in.
 * 
 * @author bmhelppi
 */
public class SessionManager extends Thread {
	
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
	
	
	public void run ()
	{
		while(true)
		{
			for(int i = 0; i < availableSessions.size(); i++)
			{
				updateActiveSession(activeSessions.get(i));
			}
		}
		
	}
	
	private void sendWelcome(Socket client)
	{
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
		try
		{
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
		    oos.writeObject(new Date());
	        oos.flush();
		}
		catch (Exception e)
		{
			
		}
	}
	
	/**
	 * Update all sessions.
	 * TODO: Should be called periodically in some way.
	 */
	public void updateActiveSession(Session s)
	{
		try
		{
			Socket client = s.server.accept();
			if (Constants.messageLevel == Constants.Messages.debug)
			{
			   System.out.println("Accepted a connection from: "+
		        		client.getInetAddress());
		        
			}
			if (client != null)
			{
				s.clientSockets.add(client);
				sendWelcome(client);
			}
			
		}
		catch(Exception e)
		{
			
		}
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
}
