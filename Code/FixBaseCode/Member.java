import java.util.UUID;


/**
 * Member class for a network session.
 * Defines a name, and a unique identifier for this user.
 * @author bmhelppi
 *
 */
public class Member {

	public UUID	id;
	public String name;
	
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
