
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;



/**
 * Manages viewable state of control panel.
 * @author ben
 *
 */
public class ChatPanelView extends JPanel
{
    private Session session;
    private ChatPanelController controller;

    public ChatPanelView(Session s)
    {
        session = s;
        JToolBar chat = new JToolBar("Chat", JToolBar.VERTICAL);

                 int amtUsers = 5;
         String[] users = new String[amtUsers];

         users = getUsers(5);

         JLabel list0 = new JLabel(users[0]);
         JLabel list1 = new JLabel(users[1]);
         JLabel list2 = new JLabel(users[2]);
         JLabel list3 = new JLabel(users[3]);
         JLabel list4 = new JLabel(users[4]);

         chat.add(list0);
         chat.add(list1);
         chat.add(list2);
         chat.add(list3);
         chat.add(list4);
         chat.addSeparator();

        //get a list of users and display them

        //display chat messages
        JTextArea chatbox = new JTextArea( "This is where chats should be sent");

        JScrollPane scrollPane = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    chatbox.setEditable(false);
        chatbox.setFont(new Font("Serif", Font.BOLD, 16));
        chatbox.setLineWrap(true);
        chatbox.setWrapStyleWord(true);

        JScrollPane areaScrollPane = new JScrollPane(chatbox);
        areaScrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setPreferredSize(new Dimension(250, 250));

         chat.add(chatbox);

         chat.addSeparator();
         JTextArea mychat = new JTextArea(1,20);
         JScrollPane scrollPane2 = new JScrollPane(mychat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    mychat.setEditable(true);

         chat.add(mychat);

         JButton chatBTN = new JButton("Send");
         chat.add(chatBTN);

    }
     public static String[] getUsers(int amt)
     {
        // This is eventually going to get a list of users from the chat section
        // Possibly in array form??
        // Right now there are dummy values for this in an array, please adjust as needed
                int amtUsers = amt; // constant here, this would be retrieved
        String[] users = new String[amtUsers];
        users[0] = "Amanda";
        users[1] = "Ben";
        users[2] = "Joe";
        users[3] = "Mark";
        users[4] = "Satpreet";

        return users;
     }

}