
import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * Manages viewable state of control panel.
 * @author ben, joe
 *
 */
public class ChatPanelView extends JToolBar implements SessionListener
{
	private Constants.Message_Type currentPriority = Constants.Message_Type.debug;
	
    private Session session;
    private ChatPanelController controller;
    private List lstUsers;
    
    private JPanel pnlMain;
    private JPanel pnlTop;
    
    protected JTextArea chatbox;
    JScrollPane scrollPane;
    JScrollPane areaScrollPane;
    JPanel pnlBot;
    JTextField txtMsg;
    JButton btnChat;

    
    /**
     * Create a new chatPanelView
     */
    public ChatPanelView() 
    {
        session = null;
         
        /* Set up the main panel for this JToolBar container */
        pnlMain = new JPanel();                
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
         
        /* Set up the top panel */
        pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
         
        /* Create the user list and add it to the main panel */
        lstUsers = new List();
                  
        refreshUsersList();         

        pnlTop.add(lstUsers);
 
         
        /* Create scrolling text area */
        chatbox = new JTextArea("", 20, 20);
        chatbox.setEditable(false);
        chatbox.setFont(new Font("Serif", Font.BOLD, 14));
        chatbox.setLineWrap(true);
        chatbox.setWrapStyleWord(true);
         
        JScrollPane scrollingResult = new JScrollPane(chatbox);
        scrollingResult.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlTop.add(scrollingResult);
       
        /* Add the top panel to the main panel */    
        pnlMain.add(pnlTop);
        
        /* Set up the bottom panel */
        pnlBot = new JPanel();
        pnlBot.setLayout(new FlowLayout());
        
        
        txtMsg = new JTextField(20);
        pnlBot.add(txtMsg);
         
        btnChat = new JButton("Send");
        pnlBot.add(btnChat);

        /* Add the bottom panel to main panel */
        pnlMain.add(pnlBot);
        
        
        /* Finally place the main panel within the JToolBar container */
        add(pnlMain);
        
    }
    
    /**
     * Update the list of users.
     */
    public void refreshUsersList () 
    {
    	lstUsers.removeAll();
    	if(session != null)
    	{
	    	for(int i = 0; i < session.networkMembers.size(); i++)
	    	{
	    		lstUsers.add(session.networkMembers.get(i).person.name);
	    		
	    	}
    	}
    	
     }

	public Session getSession() {
		return session;
	}

	public void setSession(Session s) {
		session = s;
		refreshUsersList();
		
	}
	
	/**
	 * Process a message.
	 * @param s Message to process.
	 * @param m Message priority.
	 */
	public void processMessage(String s, Constants.Message_Type m)
	{
		String finalOutput = "";
		if(m == Constants.Message_Type.none)
		{
			finalOutput = ("Someone did something dumb, asked a message to be printed with priority none.");
		}
		else
		{
			if (currentPriority == Constants.Message_Type.debug)
			{
				finalOutput = s;
			}
			else if (currentPriority == Constants.Message_Type.info)
			{
				if(m != Constants.Message_Type.debug)
				{
					finalOutput = s;
				}
			}
			else if (currentPriority == Constants.Message_Type.error)
			{
				if(m == Constants.Message_Type.error ||
				   m == Constants.Message_Type.chat)
				{
					finalOutput = s;
				}
			}
			else if (currentPriority == Constants.Message_Type.chat)
			{
				if(m == Constants.Message_Type.chat)
				{
					finalOutput =s;
				}
			}
			else
			{
				
			}
		}
		
		/* If valid text was sent, add it too on a new line */
		if(finalOutput.equals("") == false)
		{
			
			chatbox.append (finalOutput + "\n");
			
			/* Set the carrot at the end of the last message */
			chatbox.setCaretPosition(chatbox.getText().length());
		
		}
		
	}


}