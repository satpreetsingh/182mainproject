import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


/**
 * This class handles the control over all menu items except the predefined
 * tool list from ObjectDraw.
 * @author Joe
 *
 */
public class MenuBarController implements ActionListener {

	
	
    /**
     * If user selected a menu item, perform the assigned action.
     * @param e ActionEvent                                                                                                                                                                     
     */
	public void actionPerformed(ActionEvent e) {
	
		/* Help -> About option was selected */
		if (e.getSource().toString().contains("helpItem1")){
        	JOptionPane.showMessageDialog(
        			null,
        			"MultiDraw Development Team:  " + "\n" +
        			"Ben Helppi, " + "\n" +
        			"Joe Trapani, " + "\n" +
        			"Satpreet Singh, " + "\n" +
        			"Mark D'Souza, " + "\n" +
        			"Amanda Parker " + "\n" +
        			"\n" + 
        			"For questions, e-mail:  " + "\n" +
        			"benjamin-helppi@uiowa.edu, " + "\n" + 
        			"satpreetharcharan-singh@uiowa.edu, " + "\n" +
        			"jtrapani@uiowa.edu," + "\n" +
        			"amanda-parker@uiowa.edu," + "\n" +
        			"mark-dsouza@uiowa.edu," + "\n" +
        			"for any inquiries regarding MultiDraw.",
        			"About",
        			JOptionPane.INFORMATION_MESSAGE);			
		}
	
	
	
	}
	
	
}
