

/**
 * Any class that uses some session should implement this interface.
 * @author ben
 *
 */
public interface SessionListener {

	/**
	 * Update the current Session for the object.
	 * @param s New Session.
	 */
	public void setSession(Session s);
	
	/**
	 * Get the current session.
	 * @return Returns current session.
	 */
	public Session getSession();
}
