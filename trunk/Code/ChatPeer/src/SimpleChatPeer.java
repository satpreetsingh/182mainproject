import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatPeer
{
	JTextArea incoming;
	JTextField outgoing;
	static BufferedReader reader;
	static PrintWriter writer;
	Socket sock;

	private static CopyOnWriteArrayList<Socket> myPeers = new CopyOnWriteArrayList<Socket>();
	private static ServerSocket peerServerSocket;

	public static void main(String[] args) throws IOException {

		// Setup own ServerSocket on a free port
		// Write (IP + Socket) to console

		// Spawn off ServerSocketListener
		// Spawn off GUI 

		// Loop
		// If user want's to manually connect to a peer, 
		// take Socket+IP from console input, make a socket and add to list of myPeers

		// PARALLEL   	
		// If received a connect request, add to the list of connected peers (myPeers)
		// Read/Get server IP+Port of this new peer and connect to that?


		// PARALLEL
		// Setup GUI
		// When button clicked, send msg to all connected peers


		System.out.println("ChatPeer");

		// Find an free socket to listen on
		peerServerSocket = new ServerSocket(0);

		// Print self IP [address + socket] other peer(s) to connect to
		System.out.println("Listening on port " + peerServerSocket.getLocalPort() 
				+ " on " + InetAddress.getLocalHost().getHostAddress());    	

		// Spawn off GUI + Incoming Socket Listener + ServerSocketListener/Acceptor
		new SimpleChatPeer().go(); 

		// Read new connect requests from console-input
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

			setUpNewSocket(ipaddr, socketnumber);

		}

	}

	private static void setUpNewSocket(String ipaddr, int socketnumber) {
		try {
			Socket sock = new Socket(ipaddr, socketnumber);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("Established connection with " + ipaddr + ":" + socketnumber);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void go() {
		// Setup GUI
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15, 50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

		// Setup & Launch peerSocketsReader thread
		Thread peerSocketsReader = new Thread(new peerSocketsReader());
		peerSocketsReader.start();

		// Setup & Launch myServerSocketListener thread
		Thread myServerSocketAcceptor = new Thread(new myServerSocketAcceptor());
		myServerSocketAcceptor.start();

		// Show GUI
		frame.setSize(650, 500);
		frame.setVisible(true);
	}

	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				writer.println(outgoing.getText());
				writer.flush();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}


	class peerSocketsReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("client read " + message);
					incoming.append(message + "\n");
				}
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	class myServerSocketAcceptor implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("client read " + message);
					incoming.append(message + "\n");
				}
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}


}

