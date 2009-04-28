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



public class InitialWindowView {

	private static InitialWindowController IWC;

	
	InitialWindowView(){

		
		JFrame InitialWindow = new JFrame();
		InitialWindow.setTitle("MultiDraw Startup");

		
		/* Use the grid layout manger */
		InitialWindow.getContentPane().setLayout(new GridLayout(2,1));
		
		
		/* Create a tabbed list of panes to display */
		JTabbedPane tabbedPane = new JTabbedPane();



		
		JPanel pnlOne = new JPanel();
		pnlOne.setLayout(new GridLayout(3,2));

		JLabel lblUN = new JLabel("Enter a username: ");
		pnlOne.add(lblUN);
		JTextField txtUN = new JTextField(20);
		pnlOne.add(txtUN);
		
		JLabel lblIP = new JLabel("Enter their IP to join a session: ");
		pnlOne.add(lblIP);
		JTextField txtIP = new JTextField(20);
		pnlOne.add(txtIP);
		
		
		
		JLabel lblPort = new JLabel("Enter their port: ");
		pnlOne.add(lblPort);
		JTextField txtPort = new JTextField(10);
		pnlOne.add(txtPort);
		
		tabbedPane.addTab(	"Join a sesion...", 
							null, 
							pnlOne,
							"Join a MultiDraw session collaboration!");	
		
		
		JPanel pnlTwo = new JPanel();	
		pnlTwo.setLayout(new GridLayout(1,2));
		
		JLabel lblUN2 = new JLabel("Enter a username: ");
		pnlTwo.add(lblUN2);
		JTextField txtUN2 = new JTextField(20);
		pnlTwo.add(txtUN2);		
		
		tabbedPane.addTab(	"Create a session... ", 
							null, 
							pnlTwo,
	    					"Create a MultiDraw session collaboration!");
		
		
		
		IWC = new InitialWindowController(InitialWindow,
				txtUN,txtIP,txtPort,txtUN2,tabbedPane			
		);
		
		
		/* Add the tabbed pane to the window */
		InitialWindow.add(tabbedPane);
		
		
		/* Create buttons for our entry form */
		JPanel pnlThree = new JPanel();
		pnlTwo.setLayout(new FlowLayout());
		InitialWindow.add(pnlThree);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(IWC);
		pnlThree.add(btnOK);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(IWC); 
		pnlThree.add(btnCancel);
		
		

		
		
		/* Finish packing/resizing the window */
		InitialWindow.pack();
		InitialWindow.setSize(375,250);
		InitialWindow.setVisible(true);
		InitialWindow.addWindowListener(new AppCloser());
		
	}
	

	/* Methods the pass the value to MultiDraw.java */
	boolean getSlaveMaster(){
		return IWC.IsSlave;
	}
	String getUserName(){
		return IWC.username;
	}
	String getIP(){
		return IWC.ip;
	}	
	int getPort(){
		return IWC.port;
	}
	
	
	  /* Inner class AppCLoser for terminating application  */
	  /* when Close Window button of frame is clicked       */
	  static class AppCloser extends WindowAdapter  {
	    public void windowClosing(WindowEvent e) {
	    	System.exit(0);
	    }
	  }	
	
	
}
