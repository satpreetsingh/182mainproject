import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Implments a chatPanel controller.  Accepts events to send messages.
 *
 * @authors ben, joe
 * 
 */
public class ChatPanelController implements ActionListener, SessionListener
{
	private Session session;
    private ChatPanelView chatView;
    private String localUserName;
    
    /**
     * Create a new instance of chat panel controller.
     * @param chatview
     */
    ChatPanelController(ChatPanelView chatview)
    {
       this.session = null;
       this.chatView = chatview;
       
       /* Set the listener up for the send button */
       this.chatView.btnChat.addActionListener(this);
 
       
       
    }
    

    /**
     * Accept an action event. 
     * If a text post event, send it out on network, and put up on local box.
     */
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource() == this.chatView.btnChat)
        {
            String msg = this.chatView.txtMsg.getText();
            this.chatView.txtMsg.setText("");
            
            this.chatView.processMessage(session.localUser.person.name + ": " + msg,Constants.Message_Type.chat);
            if (session != null)
            {
            	session.processChatMessage(session.localUser.person.name + ": " + msg, false);
            }
        }
    }

	public Session getSession() {
		return session;
	}

	public void setSession(Session s) {
		session = s;
	}
	
	

}
