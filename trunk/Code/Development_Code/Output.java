
public class Output {

	public static void processMessage(String s, Constants.Message_Type m)
	{
		
		if(m == Constants.Message_Type.none)
		{
			System.out.println("Someone did something dumb, asked a message to be printed with priority none.");
			System.out.println(s);
		}
		else
		{
			if (Constants.messageLevel == Constants.Message_Type.debug)
			{
				System.out.println(s);
			}
			else if (Constants.messageLevel == Constants.Message_Type.info)
			{
				if(m != Constants.Message_Type.debug)
				{
					System.out.println(s);
				}
			}
			else if (Constants.messageLevel == Constants.Message_Type.error)
			{
				if(m == Constants.Message_Type.error)
				{
					System.out.println(s);
				}
			}
			else
			{
				
			}
		}
		
	}
}
