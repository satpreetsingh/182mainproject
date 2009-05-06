import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


/**
 * Handles being the first window in view.
 * Purpose is to get data needed to build a session.
 * @author ben
 *
 */
public class InitialWindowView {

	private static InitialWindowController initialWindowController;

	protected JFrame initialWindow;
	
	/* Create a tabbed list of panes to display */
	protected JTabbedPane tabbedPane;
	protected JPanel pnlOne;
	protected JLabel lblUN;
	protected JTextField userNameTextBox;
	protected JLabel lblIP;
	protected JTextField userIPTextBox;
	protected JLabel lblPort;
	protected JTextField userPortTextBox;
	protected JPanel pnlTwo;
	protected JLabel lblUN2;
	protected JTextField userNameMasterTextBox;
	
	protected JPanel pnlThree;
	protected JButton buttonOk;
	protected JButton buttonCancel;
	
	/**
	 * Create a new InitialWindowView
	 * @param sessionManger
	 */
	InitialWindowView(SessionManager sessionManger)
	{
		initialWindow = new JFrame();
		initialWindow.setTitle("MultiDraw Startup");
		
		/* Use the grid layout manger */
		initialWindow.getContentPane().setLayout(new GridLayout(2,1));
		
		/* Create a tabbed list of panes to display */
		tabbedPane = new JTabbedPane();

		
		pnlOne = new JPanel();
		pnlOne.setLayout(new GridLayout(3,2));

		lblUN = new JLabel("Enter a username: ");
		pnlOne.add(lblUN);
		userNameTextBox = new JTextField(20);
		pnlOne.add(userNameTextBox);
		
		lblIP = new JLabel("Enter their IP to join a session: ");
		pnlOne.add(lblIP);
		userIPTextBox = new JTextField(20);
		pnlOne.add(userIPTextBox);
		
		lblPort = new JLabel("Enter their port: ");
		pnlOne.add(lblPort);
		userPortTextBox = new JTextField(10);
		pnlOne.add(userPortTextBox);
		
		tabbedPane.addTab(	"Join a sesion...", 
							null, 
							pnlOne,
							"Join a MultiDraw session collaboration!");	
		
		pnlTwo = new JPanel();	
		pnlTwo.setLayout(new GridLayout(1,2));
		
		lblUN2 = new JLabel("Enter a username: ");
		pnlTwo.add(lblUN2);
		userNameMasterTextBox = new JTextField(20);
		pnlTwo.add(userNameMasterTextBox);		
		
		tabbedPane.addTab(	"Create a session... ", 
							null, 
							pnlTwo,
	    					"Create a MultiDraw session collaboration!");
		
		initialWindowController = new InitialWindowController(this, sessionManger			
		);
		
		
		/* Add the tabbed pane to the window */
		initialWindow.add(tabbedPane);
		
		
		/* Create buttons for our entry form */
		pnlThree = new JPanel();
		pnlTwo.setLayout(new FlowLayout());
		initialWindow.add(pnlThree);
		
		buttonOk = new JButton("OK");
		buttonOk.addActionListener(initialWindowController);
		pnlThree.add(buttonOk);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(initialWindowController); 
		pnlThree.add(buttonCancel);
		
		/* Finish packing/resizing the window */
		initialWindow.pack();
		initialWindow.setSize(375,250);
		initialWindow.setVisible(true);
		
	}
	

	/* Methods to pass the value to MultiDraw.java */
	boolean getSlaveMaster(){
		return initialWindowController.IsSlave;
	}
	String getUserName(){
		return initialWindowController.username;
	}
	String getIP(){
		return initialWindowController.ip;
	}	
	int getPort(){
		return initialWindowController.port;
	}
	
	 
	
}
