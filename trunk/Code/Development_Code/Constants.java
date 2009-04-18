
public class Constants {

	/**
	 * The static value assigned here determines which messages will be output.
	 * Debug has lowest priority, then info, then error, and finally none means nothing will be output.
	 */
	public static Message_Type messageLevel = Message_Type.debug;
	
	public enum Message_Type
	{
		debug,
		info,
		error,
		none
	}
}
