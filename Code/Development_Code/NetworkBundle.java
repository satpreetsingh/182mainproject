import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


/**
 * Macro class, puts some things together for convenient management.
 * @author ben
 *
 */
public class NetworkBundle implements Serializable{

	public Member person;
	public ObjectInputStream ois = null;
	public ObjectOutputStream oos = null;
	public Socket sock = null;
	
	
	/**
	 * Create a new network bundle.
	 * Note, most are null if the network bundle is for ones self.
	 * @param m The person this is associated.
	 * @param ois A stream to get objects from the person.
	 * @param oos A stream to send objects to the person
	 * @param sock The socket shared between here, and the person.
	 */
	public NetworkBundle
	(Member m,	 
	 ObjectInputStream ois,
	 ObjectOutputStream oos,
	 Socket sock,
	 String ipAddress,
	 int port)
	{
		m.ipAddress = ipAddress;
		m.port = port;
		person = m;
		this.ois = ois;
		this.oos = oos;
		this.sock = sock;
	}
}
