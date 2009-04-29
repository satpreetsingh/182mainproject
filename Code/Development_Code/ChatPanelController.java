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
 *
 * @author Mandi
 */
public class ChatPanelController implements ActionListener
{
    Session session;

    ChatPanelController(Session s)
    {
        session = s;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().toString().contains("Send"))
        {
            String msg = "";

            // send whatever is in the chat box to the chat
            Output o = new Output();
            o.processMessage(msg,Constants.Message_Type.none);


        }
    }
}
