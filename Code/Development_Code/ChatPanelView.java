
import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * Manages viewable state of control panel.
 * @author ben
 *
 */
public class ChatPanelView extends JToolBar implements SessionListener
{
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

    
    public ChatPanelView(Session s) {
    	
    	
         session = s;
        
         
         /* Set up the main panel for this JToolBar container */
         pnlMain = new JPanel();                
         pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
         
         /* Set up the top panel */
         pnlTop = new JPanel();
         pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));

         
         /* Create the user list and add it to the main panel */
         lstUsers = new List();
         
         RefreshUsersList();
         
         pnlTop.add(lstUsers);
         


        //get a list of users and display them

        //display chat messages
        chatbox = new JTextArea ("This is where chats should be sent");

        scrollPane = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        chatbox.setEditable(false);
        chatbox.setFont(new Font("Serif", Font.BOLD, 16));
        chatbox.setLineWrap(true);
        chatbox.setWrapStyleWord(true);

        
        
        areaScrollPane = new JScrollPane(chatbox);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 250));
        pnlTop.add(chatbox);

        
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
    
    public void RefreshUsersList () 
    {
    	lstUsers.clear();
    	for(int i = 0; i < session.networkMembers.size(); i++)
    	{
    		lstUsers.add(session.networkMembers.get(i).person.name);
    		
    	}
    	
     }

	public Session getSession() {
		return session;
	}

	public void setSession(Session s) {
		session = s;
		RefreshUsersList();
		
	}

}