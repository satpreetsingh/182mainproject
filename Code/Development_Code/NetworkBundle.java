import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


public class NetworkBundle implements Serializable{

	public Member person;
	public ObjectInputStream ois = null;
	public ObjectOutputStream oos = null;
	public Socket sock = null;
	
	public NetworkBundle
	(Member m,	 
	 ObjectInputStream ois,
	 ObjectOutputStream oos,
	 Socket sock)
	{
		person = m;
		this.ois = ois;
		this.oos = oos;
		this.sock = sock;
	}
}
