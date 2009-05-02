import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


/**
 * Implments a chatPanel controller.  Accepts events to send messages,
 * and also process events to post text.
 *
 * @author Mandi
 * @author ben
 * 
 */
public class ChatPanelController implements ActionListener, SessionListener
{
	private Session session;
    private ChatPanelView chatView;
    private Constants.Message_Type currentPriority = Constants.Message_Type.debug;

    ChatPanelController(Session session, ChatPanelView chatview)
    {
       this.session = session;
       this.chatView = chatview;
       
       this.chatView.btnChat.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource() == this.chatView.btnChat)
        {
            String msg = this.chatView.txtMsg.getText();
            this.chatView.txtMsg.setText("");
            session.chatMessage(msg, false);
            processMessage(msg,Constants.Message_Type.chat);


        }
    }

	public Session getSession() {
		return session;
	}

	public void setSession(Session s) {
		session = s;
		
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
		
		if(finalOutput.equals("") == false)
		{
			chatView.chatbox.append(finalOutput);
		}
		
	}

}
