
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
public class ChatPanelView extends JToolBar
{
    private Session session;
    private ChatPanelController controller;
    private List lstUsers;
    
    public ChatPanelView(Session s) {
    	
    	
         session = s;
        
         
         
         
         
         /* Set up the main panel for this JToolBar container */
         JPanel pnlMain = new JPanel();                
         pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
         
         /* Set up the top panel */
         JPanel pnlTop = new JPanel();
         pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));

         
         /* Create the user list and add it to the main panel */
         lstUsers = new List();
         
         RefreshUsersList();
         
         pnlTop.add(lstUsers);
         


        //get a list of users and display them

        //display chat messages
        JTextArea chatbox = new JTextArea ("This is where chats should be sent");

        JScrollPane scrollPane = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        chatbox.setEditable(false);
        chatbox.setFont(new Font("Serif", Font.BOLD, 16));
        chatbox.setLineWrap(true);
        chatbox.setWrapStyleWord(true);

        
        
        JScrollPane areaScrollPane = new JScrollPane(chatbox);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 250));
        pnlTop.add(chatbox);

        
        /* Add the top panel to the main panel */    
        pnlMain.add(pnlTop);

        
        
        /* Set up the bottom panel */
        JPanel pnlBot = new JPanel();
        pnlBot.setLayout(new FlowLayout());
        
        
        JTextField txtMsg = new JTextField(20);
        pnlBot.add(txtMsg);
         
         
        JButton btnChat = new JButton("Send");
        pnlBot.add(btnChat);

        /* Add the bottom panel to main panel */
        pnlMain.add(pnlBot);
        
        
        /* Finally place the main panel within the JToolBar container */
        add(pnlMain);
        
    }
    
    public void RefreshUsersList () {
    	
       	
    	/* Load each person onto the list */
//    	for (int index = 0; index < session.networkMembers.size(); index++){
//    		lstUsers.add(session.networkMembers.get(index).person.name);
//    	}

    	lstUsers.add("Joe");
    	lstUsers.add("Ben");
    	
     }

}