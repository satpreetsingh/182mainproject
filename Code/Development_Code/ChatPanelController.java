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
 * Implments a chatPanel controller.  Accepts events to send messages.
 *
 * @author Mandi
 * @author ben
 * 
 */
public class ChatPanelController implements ActionListener, SessionListener
{
	private Session session;
    private ChatPanelView chatView;
   
    /**
     * Create a new instance of chat panel controller.
     * @param chatview
     */
    ChatPanelController(ChatPanelView chatview)
    {
       this.session = null;
       this.chatView = chatview;
       
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
            
            this.chatView.processMessage(msg,Constants.Message_Type.chat);
            if (session != null)
            {
            	session.processChatMessage(msg, false);
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
