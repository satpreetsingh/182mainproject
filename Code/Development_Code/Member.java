import java.io.Serializable;
import java.util.UUID;


/**
 * Member class for a network session.
 * Defines a name, and a unique identifier for this user.
 * @author bmhelppi
 *
 */
public class Member implements Serializable {

	public static final String nullName = "the_Nameless";
	
	public UUID	id;
	public String name;
	public String ipAddress = "";
	public int port = 0;
	
	/**
	 * Create a new member.
	 * @param name Members name.
	 */
	public Member(String name)
	{
		this.name = name;
		id = UUID.randomUUID();
	}
}
