
/**
 * A peer thread.  Accepts messages from exactly one peer.
 * @author ben
 *
 */
public class PeerThread extends Thread{

	Session session;
	NetworkBundle local;
	NetworkBundle target;
	
	
	/**
	 * Create a new peer thread.
	 * @param session Session this thread belongs to.
	 * @param local The local user.
	 * @param target The user to accept messages from.
	 */
	public PeerThread(Session session,
	NetworkBundle local,
	NetworkBundle target)
	{
		this.session = session;
		this.local = local;
		this.target = target;
		
	}
	
	
	/**
	 * Start the thread for this object.
	 */
	public void run ()
	{
		boolean dontQuit = true;
		while(dontQuit)
		{
			if(this.target == null)
			{
				dontQuit = false;
			}
			try
			{
				SessionUtils.processMessageFromPeer(this,session, target, local);
			}
			catch(Exception e)
			{
				
			}
		}
		
	}

}
