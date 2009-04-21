import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class SimpleChatPeer
{
	JTextArea incomingTextArea;
	JTextField outgoingTextField;

	private static CopyOnWriteArrayList<Socket> myPeers = new CopyOnWriteArrayList<Socket>();
	private static ServerSocket myServerSocket;
	private static CopyOnWriteArrayList<PrintWriter> peerOutputStreams = new CopyOnWriteArrayList<PrintWriter>();
	
	public static void main(String[] args) {
		System.out.println("ChatPeer");

		// Find an free socket to listen on
		try {
			myServerSocket = new ServerSocket(0);
			// Print self IP [address + socket] other peer(s) to connect to
			System.out.println("Listening on port " + myServerSocket.getLocalPort() 
					+ " on " + InetAddress.getLocalHost().getHostAddress());    	
		} catch (IOException e) {
			System.out.println("No free Socket available"); 
			return;
		}

		// Spawn off GUI + ServerSocketListener/Acceptor
		new SimpleChatPeer().go(); 

		// Connect to new peer from console-input
		String ipaddr;
		int socketnumber;

		while(true) {
			Scanner in = new Scanner(System.in); 
			System.out.print("To make a new connection, enter IP address: ");	    		
			ipaddr = in.nextLine();
			System.out.print("Enter socket number: ");	
			socketnumber = in.nextInt();
			System.out.println("Connecting to: " + ipaddr + " : " + socketnumber);
			in.close(); 

			// Setup & Launch peerSocketHandler thread
			Socket newPeerSocket;
			try {
				newPeerSocket = new Socket(ipaddr, socketnumber);
				System.out.println("Established connection with " + ipaddr + ":" + socketnumber);

				PrintWriter newPeerWriter = new PrintWriter(newPeerSocket.getOutputStream());
				peerOutputStreams.add(newPeerWriter);            
				
				Thread t = new Thread(new PeerSocketHandler(newPeerSocket));
				t.start();

//				} catch (UnknownHostException e) {
//				System.out.print(" UnknownHostException ");
//				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}


	public void go() {
		// Setup GUI
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
		JPanel mainPanel = new JPanel();
		incomingTextArea = new JTextArea(15, 50);
		incomingTextArea.setLineWrap(true);
		incomingTextArea.setWrapStyleWord(true);
		incomingTextArea.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incomingTextArea);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		outgoingTextField = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoingTextField);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

		// Setup & Launch myServerSocketListener thread
		Thread serverSocketListener = new Thread(new ServerSocketListener());
		serverSocketListener.start();

		// Show GUI
		frame.setSize(650, 500);
		frame.setVisible(true);
	}

	// GUI - SendButtonListener
	// When button clicked, send text to everyone
	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			tellEveryone(outgoingTextField.getText());
			outgoingTextField.setText("");
			outgoingTextField.requestFocus();
		}
	}
	public void tellEveryone(String message) {
		Iterator it = peerOutputStreams.iterator();
		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				writer.flush();
			} catch (Exception ex) { ex.printStackTrace(); }
		}
	}


	// THREAD: Listens to the peerSocket for incoming messages
	//		   When something found, put on GUI
	// 		   When connection lost, print so on GUI
	class PeerSocketHandler implements Runnable {
		BufferedReader peerSocketReader;
		Socket peerSocket;

		public PeerSocketHandler(Socket newPeerSocket) {
			try {
				peerSocket = newPeerSocket;
				InputStreamReader isReader = new InputStreamReader(peerSocket.getInputStream());
				peerSocketReader = new BufferedReader(isReader);
			} catch (Exception ex) { ex.printStackTrace(); }
		}

		public void run() {
			String message;
			try {
				while ((message = peerSocketReader.readLine()) != null) {
					// TODO: Print on GUI instead
					System.out.println("[" + peerSocket.getInetAddress() + "] : " + message);
				}
			} catch (Exception ex) { ex.printStackTrace(); }
		}
	}


	// THREAD: Listens to server-socket for incoming connection requests
	//		   Accepts requests and spawns off new peerSocketHandler
	class ServerSocketListener implements Runnable {
		public void run() {
			try {
				while(true) {
					Socket newPeerSocket = myServerSocket.accept();
					PrintWriter newPeerWriter = new PrintWriter(newPeerSocket.getOutputStream());
					peerOutputStreams.add(newPeerWriter);

					Thread t = new Thread(new PeerSocketHandler(newPeerSocket));
					t.start();
					// TODO: Print on GUI instead
					System.out.println("New Peer connected: " + newPeerSocket.getInetAddress() + " : " 
							+ newPeerSocket.getRemoteSocketAddress());
				}
			} catch (Exception ex) { ex.printStackTrace(); }
		}
	}


}

