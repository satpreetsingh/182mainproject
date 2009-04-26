package ChatPeer;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

//import ChatMessageObject;

public class SimpleChatPeer
{
	JTextArea incomingTextArea;
	JTextField outgoingTextField;

	private static CopyOnWriteArrayList<Socket> myPeers = new CopyOnWriteArrayList<Socket>();
	private static ServerSocket myServerSocket;
	private static CopyOnWriteArrayList<PrintWriter> peerOutputStreams = new CopyOnWriteArrayList<PrintWriter>();
	private static String myOpenPortString;

	public static void main(String[] args) {
		System.out.println("ChatPeer");

		// Find an free socket to listen on
		try {
			myServerSocket = new ServerSocket(0);
			// Print self IP [address + socket] other peer(s) to connect to
			System.out.println("Listening on port " + myServerSocket.getLocalPort() 
					+ " on " + InetAddress.getLocalHost().getHostAddress()); 
			myOpenPortString = "" + InetAddress.getLocalHost().getHostAddress() 
			+ ":" + myServerSocket.getLocalPort();
		} catch (IOException e) {
			System.out.println("No free Socket available"); 
			return;
		}

		// Spawn off GUI + ServerSocketListener/Acceptor
		SimpleChatPeer chatPeerInstance = new SimpleChatPeer();
		chatPeerInstance.go(); 

		// Connect to new peer from console-input
		String ipaddr;
		int socketnumber;
		Scanner consoleinput; 
		while(true) {
			consoleinput = new Scanner(System.in);
			System.out.print("To make a new connection, enter IP address: ");	    		
			ipaddr = consoleinput.nextLine();
			System.out.print("Enter socket number: ");	
			socketnumber = consoleinput.nextInt();
			System.out.println("Connecting to: " + ipaddr + " : " + socketnumber);

//			// Setup & Launch peerSocketHandler thread
			Socket newPeerSocket = null;
			try {
				newPeerSocket = new Socket(ipaddr, socketnumber);
				System.out.println("\nEstablished connection with " + newPeerSocket.getRemoteSocketAddress());
				chatPeerInstance.incomingTextArea.append("\nEstablished connection with " + newPeerSocket.getRemoteSocketAddress());
				
				PrintWriter newPeerWriter = new PrintWriter(newPeerSocket.getOutputStream());
				peerOutputStreams.add(newPeerWriter);            

				Thread t = new Thread(chatPeerInstance.new PeerSocketHandler(newPeerSocket));
				t.start();

			} catch (UnknownHostException e) {
				System.out.print(" UnknownHostException ");
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//consoleinput.close(); 

	}

	// THREAD -- GUI
	public void go() {
		// Setup GUI
		JFrame frame = new JFrame("ChatPeer -- " + "Listening on " + myOpenPortString );
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
			String chatSendText = outgoingTextField.getText();
			incomingTextArea.append("\n(Self) : " + chatSendText);
			tellEveryone(chatSendText);
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
			//String message;
			ChatMessageObject message;
			try {
				while ((message = (ChatMessageObject)(Object)peerSocketReader.readLine()) != null) {
					// TODO: Print on GUI instead
					incomingTextArea.append("\n[" + peerSocket.getRemoteSocketAddress() + "] : " + message);
//					System.out.println("\n[" + peerSocket.getRemoteSocketAddress() + "] : " + message);
				}
			} catch (Exception ex) { /* ex.printStackTrace(); */ ; }
		}
	}



//	THREAD: Listens to server-socket for incoming connection requests
//	Accepts requests and spawns off new peerSocketHandler
	class ServerSocketListener implements Runnable {
		public void run() {
			try {
				while(true) {
					Socket newPeerSocket = myServerSocket.accept();
					PrintWriter newPeerWriter = new PrintWriter(newPeerSocket.getOutputStream());
					peerOutputStreams.add(newPeerWriter);

					Thread t = new Thread(new PeerSocketHandler(newPeerSocket));
					t.start();
					incomingTextArea.append("\nNew Peer connected: " + newPeerSocket.getRemoteSocketAddress());
//					System.out.println("\nNew Peer connected: " + newPeerSocket.getRemoteSocketAddress());
				}
			} catch (Exception ex) { ex.printStackTrace(); }
		}
	}


}

