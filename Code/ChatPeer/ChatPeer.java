import java.net.*;

public class ChatPeer {

	public static void main(String[] args) throws Exception {
		
		System.out.println("ChatPeer");
		
		// Find an free socket to listen on
		ServerSocket serverSocket = new ServerSocket(0);
//		int socketPortNo = serverSocket.getLocalPort();
//		InetAddress socketInetAddress = serverSocket.getInetAddress();
		
		// Print self IP [address + socket] other peer(s) to connect to
		System.out.println("Listening on port " + serverSocket.getLocalPort() + " on " + InetAddress.getLocalHost().getHostAddress());
		
		// Vector<IP Add, Socket> of all connected peers
		// Get a username for user
		
		
		// How/When to connect to other peers?
		// 
		
		serverSocket.close();
		
	}

}

// Thread 1: Chat Socket-Listener to get incoming chat requests (sets up session)

// Thread N: Listens to chat msgs. for peer N 
