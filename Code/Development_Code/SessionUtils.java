import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;



/**
 * Static utilities.  Handles large chunks of code for Session, and SessionManager.
 * @author ben
 *
 */
public class SessionUtils
{

	private static final int basePort = 3000;
	private static final double range = 32000.0;
	
	/**
	 * Runs on SERVER
	 * Sever got a connection.
	 * Setup some logicals, this is step 1.
	 * @param client Socket that client connected on.
	 * @param s Session socket connected to.
	 * 
	 * This makes a new connection entry in the s.networkMembers<> Arraylist
	 * Note this is a Server <--> Client new-connection handler
	 * not a Peer <--> Peer new-connection handler YET!
	 */
	public static void acceptNewClient(Socket client, Session s)
	{

		try
		{
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			Member m = new Member(Member.nullName);
			String ipAddress = client.getInetAddress().getHostAddress();
			NetworkBundle newConn = new NetworkBundle(m, ois, oos, client, ipAddress, client.getLocalPort());
			
			PeerThread p = new PeerThread(s, s.localUser,newConn);
			
			
			s.threads.add(p);
			s.networkMembers.add(newConn);
			Output.processMessage("Server accepted conn, no data yet", Constants.Message_Type.info);
			
			p.start();
		}
		catch (Exception e)
		{
			Output.processMessage("ServerUtils.acceptNewClient: Error in server accept client", Constants.Message_Type.error);

		}
	}

	/**
	 * Runs on CLIENT
	 * Client connected to another machine.
	 * Client is trying to send a message to say 
	 * "Hi, I'm here, and my name is ***"
	 * @param s A session where the localUser, and master objects are mostly setup.
	 * @param reason Why we are making connection.  Join Session, or Join Peer are valid reasons.
	 */
	public static void requestSessionJoin(Session s, NetworkBundle peer, NetworkObject.reason reason)
	{
		try 
		{
			ObjectOutputStream masterStream = peer.oos;

			UUID tempId = UUID.randomUUID();
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(s.localUser.person);
			data.add(tempId);
			peer.tempId = tempId;

			NetworkObject hello = new NetworkObject
			(s.localUser.person,
			 peer.person,
			 data,
			 reason
			);

			masterStream.writeObject(hello);
			masterStream.flush();

			Output.processMessage("This client connected to server, sent hello", Constants.Message_Type.info);

		} 
		catch (IOException e) 
		{
			Output.processMessage("ServerUtils.acceptConn2: Error in client connect to server", Constants.Message_Type.error);
		}
	}

	/**
	 * Method to create a Master Session.
	 * @param m Member who will be the master.
	 * @param c Canvas, will be in focus of the Session created.
	 * @return Returns an operation Session, if the port is available, else null.
	 */
	public static Session buildSession(Member m, DrawingCanvas c, ArrayList<ToolController> tools, MenuBarView menuBar, ChatPanelView chatPanel, ControlPanelView controlPanel)
	{
		Session result;
		boolean portNotFound = true;
		Random gen = new Random();
		int port = 0;
		ServerSocket serverSock = null;
		try 
		{
			while(portNotFound)
			{
				try
				{
					port = basePort + (int)(gen.nextFloat() * range);
					serverSock = new ServerSocket(port);
					portNotFound = false;
				}
				catch(Exception e)
				{
					
				}
			}
			String ipAddress = java.net.InetAddress.getLocalHost().getHostAddress();
			
			NetworkBundle creater = new NetworkBundle(m, null, null, null, ipAddress, port);
			result = new Session(serverSock, creater, c, tools, chatPanel, controlPanel);
			Output.processMessage("Built session " + ipAddress + "@" + port, Constants.Message_Type.info);
			
		} 
		catch (IOException e) 
		{
			Output.processMessage("ServerUtils.buildSession, Unable to build master session", Constants.Message_Type.error);
			result = null;
		}

		return result;
	}

	/**
	 * Build a client session, will try to connect to another server.
	 * @param m Local member, will be a client of the session.
	 * @param c DrawingCanvas that will be in focus of the session.
	 * @param ip Ip to connect to.
	 * @param port Port to connect to.
	 * @return Returns a session, if success, else returns null; 
	 */
	public static Session buildSession(Member m, DrawingCanvas c, String ip, int port,ArrayList<ToolController> tools, MenuBarView menubar, ChatPanelView chatPanel, ControlPanelView controlPanel)
	{
		Session result;
		int localPort = 0;
		boolean portNotFound = true;
		Random gen = new Random();
		ServerSocket serverSock = null;
		try
		{
			while(portNotFound)
			{
				try
				{
					localPort = basePort + (int)(gen.nextFloat() * range);
					serverSock = new ServerSocket(localPort);
					portNotFound = false;
				}
				catch(Exception e)
				{
					
				}
			}
			String localIp = java.net.InetAddress.getLocalHost().getHostAddress();
			NetworkBundle local = new NetworkBundle(m,null,null,null, localIp, localPort);
			
			result = new Session(local, serverSock, c, ip, port,tools, menubar, chatPanel, controlPanel);
			Output.processMessage("Built session " + localIp + "@" + localPort, Constants.Message_Type.info);
		}
		catch(Exception e)
		{
			Output.processMessage("ServerUtils.buildSession, Unable to build client session", Constants.Message_Type.error);
			result = null;
		}

		return result;
	}


	private static void processUpdateToBaseline(Session session, NetworkObject data)
	{
		try
		{
			DrawState baseline = (DrawState)data.data;
			session.setBaseline(baseline, true);
		}
		catch(Exception e)
		{
			Output.processMessage("Defective baseline message recieved", Constants.Message_Type.error);
			
		}
		
	}
	
	private static void processChat(Session s, NetworkObject data)
	{
		try
		{
			String networkData = (String) data.data;

			s.processChatMessage(networkData, true);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective chat message recieved", Constants.Message_Type.error);
		}

	}
	
	private static void processCanvasOwnershipRequest(Session s, NetworkObject data)
	{
		try
		{
			s.processRequestOwnership(data.Originator, true);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective request owner message recieved", Constants.Message_Type.error);
		}

	}
	private static void processCanvasOwnershipConfirm(Session s, NetworkObject data)
	{
		try
		{
			Member networkData = (Member) data.data;

			NetworkBundle newOwner = null;
			for(int i = 0; i < s.networkMembers.size(); i++)
			{
				if(s.networkMembers.get(i).person.id.equals(networkData.id))
				{
					newOwner = s.networkMembers.get(i);
				}
			}
			if(newOwner != null)
			{
				s.processTransferOwnership(newOwner, true);
			}
			else
			{
				Output.processMessage("Defective new owner message recieved, person doesn't exist", Constants.Message_Type.error);
				
			}
		}
		catch (Exception e)
		{
			Output.processMessage("Defective new owner message recieved", Constants.Message_Type.error);
		}

	}
	
	private static void processCanvasOwnershipDeny(Session s, NetworkObject data)
	{
		try
		{
			s.processRejectOwnership(null, true);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective reject owner message recieved", Constants.Message_Type.error);
		}

	}

	private static void processCanvasOwnershipRebel(Session s, NetworkObject data)
	{
		try
		{
			s.processRespondtoRebellion(data.Originator);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective rebellion incite message recieved", Constants.Message_Type.error);
		}

	}
	
	private static void processCanvasOwnershipSupportRebellion(Session s, NetworkObject data)
	{
		try
		{
			s.processRebellionVotes();
		}
		catch (Exception e)
		{
			Output.processMessage("Defective rebel support message recieved", Constants.Message_Type.error);
		}

	}
	
	
	
	private static void processMouseRelease(Session s, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Point point = (Point)networkData.get(0);
			Tool tool = (Tool)networkData.get(1);
			Color color = (Color)networkData.get(2);
			boolean fillObject= (Boolean)networkData.get(3);

			s.processMouseRelease(point, true, tool, color, fillObject);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective mouseRelease message recieved", Constants.Message_Type.error);
		}
	}
	
	private static void processMousePress(Session s, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Point point = (Point)networkData.get(0);
			Tool tool = (Tool)networkData.get(1);
			boolean fill = (Boolean)networkData.get(2);
			UUID uniqueId = (UUID)networkData.get(3);
			s.processMousePress(point, true, tool, fill, uniqueId);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective mouseRelease message recieved", Constants.Message_Type.error);
		}
	}

	private static void processMouseDrag(Session s, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Point point = (Point)networkData.get(0);
			Tool tool = (Tool)networkData.get(1);

			s.processMouseDrag(point, true, tool);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective mouseRelease message recieved", Constants.Message_Type.error);
		}
	}

	private static void	processKeyPressed(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			char key = (Character)networkData.get(0);
			Tool tool = (Tool)networkData.get(1);

			session.processKeyPress(key, true, tool);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective keyPressed message recieved", Constants.Message_Type.error);
		}


	}
	
	
	private static void processKeyReleased(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			char key = (Character)networkData.get(0);
			Tool tool = (Tool)networkData.get(1);

			session.processKeyRelease(key, true, tool);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective keyRelease message recieved", Constants.Message_Type.error);
		}

	}
	
	private static void processKeyTyped(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			char key = (Character)networkData.get(0);
			Tool tool = (Tool)networkData.get(1);

			session.processKeyTyped(key, true, tool);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective keyTyped message recieved", Constants.Message_Type.error);
		}

	}
	
	private static void processClear(Session session, NetworkObject data)
	{
		session.processClearObjects(true);
	}

	private static void processClearSelection(Session session, NetworkObject data)
	{
		session.processClearSelectedObject(true);
	}
	
	private static void processSelectShape(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Object shape= (Object)networkData.get(0);

			session.processSelectShape(shape, true);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective selectShape message recieved", Constants.Message_Type.error);
		}

	}

	private static void processSetNewTool(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			String name = (String)networkData.get(0);
			byte[] rawdata = (byte[])networkData.get(1);
			

			session.processSetNewTool(name, rawdata);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective set NewTool message recieved", Constants.Message_Type.error);
		}

	}

	
	
	private static void processSetShapeFill(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Object shape = (Object)networkData.get(0);
			boolean isFill = (Boolean)networkData.get(1);

			session.processSetShapeFill(shape, isFill, true);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective set ShapeFill message recieved", Constants.Message_Type.error);
		}

	}

	private static void processSetShapeColor(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Object shape= (Object)networkData.get(0);
			Color color = (Color)networkData.get(1);

			session.processSetMainColor(shape, color, true);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective set shapeColor message recieved", Constants.Message_Type.error);
		}

	}

	private static void processDeleteShape(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Object shape= (Object)networkData.get(0);

			session.processDeleteShape(shape, true);
		}
		catch (Exception e)
		{
			Output.processMessage("Defective set shapeColor message recieved", Constants.Message_Type.error);
		}

	}

	/* Runs on only a CLIENT */
	private static void processPeerListUpdate(Session session, NetworkObject data)
	{
		try
		{
			ArrayList<Member> networkData = (ArrayList<Member>)data.data;
			session.master.person.id = data.Originator.id;
			session.localUser.person.id = data.Recipient.id;
			
			int myself = networkData.size() + 1;
			for(int i = 0 ; i < networkData.size(); i++)
			{
				boolean doesNotExist = true;
				Member fromNewList = networkData.get(i);
				for(int j = 0; j < session.networkMembers.size(); j++)
				{
					
					Member fromOldList = session.networkMembers.get(j).person;
					
					if(fromNewList.id.equals(session.localUser.person.id))
					{
						doesNotExist = false;
						j = session.networkMembers.size() + 1;
						myself = i;
					}
					else if(fromNewList.id.equals(fromOldList.id))
					{
						fromOldList.name = fromNewList.name;
						doesNotExist = false;
						j = session.networkMembers.size() + 1;
					}
				}
				
				if (doesNotExist && myself < i)
				{
					/**
					 * This guy doesn't exist, but I've been around longer.  I'll say hello.
					 **/
					Socket newGuySocket = new Socket(fromNewList.ipAddress, fromNewList.port);
					ObjectOutputStream oos = new ObjectOutputStream(newGuySocket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(newGuySocket.getInputStream());
					Member newGuy = fromNewList;
					NetworkBundle newGuyBundle = new NetworkBundle
					(newGuy, ois,oos,newGuySocket, fromNewList.ipAddress , newGuySocket.getPort());
					
					session.networkMembers.add(newGuyBundle);
					requestSessionJoin(session, newGuyBundle, NetworkObject.reason.joinPeerRequest);
					
					PeerThread p = new PeerThread(session,session.localUser, newGuyBundle);
					p.start();
					session.threads.add(p);
					
				}
				else if(doesNotExist && myself < i)
				{
					/**
					 * This guy doesn't exist, but he is older.  I'll wait for him to say hello.
					 * So do nothing...
					**/
					
				}
			}
			
			System.out.println("Peers: " + networkData.toString());
		}
		catch (Exception e)
		{
			Output.processMessage("Defective peerListUpdate message recieved", Constants.Message_Type.error);
		}
		session.updatePeerListOnScreen();
	}

	/* Runs on only the MASTER */
	private static void processJoinRequest(NetworkBundle client, Session session, NetworkObject data) 
	{
		try
		{
			System.out.println("Processing Join Session Request " + data.toString());
			if(client.person.name.equals(Member.nullName) && client.person.ipAddress.equals(data.Originator.ipAddress))
			{
				client.person = data.Originator;
				
				UUID tempId = (UUID)((ArrayList)data.data).get(1);
				sendJoinConfirm(session, client,tempId);
				
				System.out.println("Sending PeerList to all peers ");
				sendPeerListToAllPeers(session);
				
				sendDrawState(session, session.currentState);
				session.updatePeerListOnScreen();
			}
			else
			{
				Output.processMessage("Defective join session", Constants.Message_Type.error);
			}
		
		}
		catch (Exception e)
		{
			Output.processMessage("Defective JoinSessionRequest message recieved", Constants.Message_Type.error);
		}
	}
	
	private static void processJoinResponse(Session session, NetworkObject data) 
	{
		try
		{
			UUID tempId = (UUID)data.data;
			
			NetworkBundle joinBundle = null;
			for(int i = 0; i < session.networkMembers.size(); i++)
			{
				if(session.networkMembers.get(i).tempId != null)
				{
					if(session.networkMembers.get(i).tempId.equals(tempId))
					{
						joinBundle = session.networkMembers.get(i);
						joinBundle.person = data.Originator;
						joinBundle.tempId = null;
					}
				}
			}
		
		}
		catch (Exception e)
		{
			Output.processMessage("Defective JoinSessionResponse message recieved", Constants.Message_Type.error);
		}
	}

	
	private static void sendJoinConfirm(Session session, NetworkBundle client, UUID tempId)
	{
		genericSendToAllPeers(session, tempId, NetworkObject.reason.joinRequestResponse, client);
	}
	
	private static void processQuit(Session session, NetworkBundle client, PeerThread p)
	{
		session.networkMembers.remove(client);
		session.threads.remove(p);
		p.target = null;
		p = null;
	}
	
	private static void considerReset(NetworkBundle client, Session session)
	{
		if(client.person.id.equals(session.master.person.id))
		{
			Output.processMessage("Master dead, resetting" , Constants.Message_Type.info);
			boolean lowest = true;
			UUID self = session.localUser.person.id;
			
			for(int i = 0; i < session.networkMembers.size(); i++)
			{
				NetworkBundle temp = session.networkMembers.get(i);
				
				if(temp.person.id.equals(session.master) == false &&
						temp.person.id.compareTo(self) < 0)
				{
					lowest = false;
				}
			}
			
			if (lowest)
			{
				Output.processMessage("Master dead, This unit taking control" , Constants.Message_Type.info);
				
				session.processTransferOwnership(session.localUser, false);
			}
		}
	}
	/* This can run on either MASTER or CLIENT */
	protected static void processMessageFromPeer(PeerThread p, Session session, NetworkBundle client, NetworkBundle local)
	{
		/**
		 * Don't check ourself for messages.
		 */
		if (client != local && client != null)
		{
			try 
			{
				NetworkObject data = (NetworkObject)client.ois.readObject();
				
				Output.processMessage("Got message of type " + data.objectReason.toString(), Constants.Message_Type.info);
				if(data.objectReason == NetworkObject.reason.mouseRelease)
				{
					processMouseRelease(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.mousePress)
				{
					processMousePress(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.mouseDrag)
				{
					processMouseDrag(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.keyPressed)
				{
					processKeyPressed(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.keyReleased)
				{
					processKeyReleased(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.keyTyped)
				{
					processKeyTyped(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.clearCanvas)
				{
					processClear(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.clearSelection)
				{
					processClearSelection(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.selectShape)
				{
					processSelectShape(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.setNewTool)
				{
					processSetNewTool(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.setShapeFill)
				{
					processSetShapeFill(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.setShapeColor)
				{
					processSetShapeColor(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.deleteShape)
				{
					processDeleteShape(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.joinSessionRequest)
				{
					processJoinRequest(client, session, data);
				}
				else if (data.objectReason == NetworkObject.reason.joinPeerRequest)
				{
					processJoinRequest(client, session, data);
				}
				else if (data.objectReason == NetworkObject.reason.peerListUpdate)
				{
					processPeerListUpdate(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.chat)
				{
					processChat(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.canvasOwnershipConfirm)
				{
					processCanvasOwnershipConfirm(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.canvasOwnershipDeny)
				{
					processCanvasOwnershipDeny(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.canvasOwnershipRequest)
				{
					processCanvasOwnershipRequest(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.canvasOwnershipRebel)
				{
					processCanvasOwnershipRebel(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.canvasOwnershipSupportRebellion)
				{
					processCanvasOwnershipSupportRebellion(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.dataState)
				{
					processUpdateToBaseline(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.joinRequestResponse)
				{
					processJoinResponse(session, data);
				}
				else if (data.objectReason == NetworkObject.reason.quit)
				{
					processQuit(session, client, p);
				}
				else
				{
					Output.processMessage("Broken message recieved", Constants.Message_Type.error);
				}
			
			} 

			catch (StreamCorruptedException streamCorr)
			{
				Output.processMessage(p.target.person.name + " has a CorruptSocket, dropping connection", Constants.Message_Type.error);
				
				considerReset(client, session);
				session.threads.remove(p);
				session.networkMembers.remove(p.target);
				session.updatePeerListOnScreen();
				p.target = null;
				p = null;
			}
			catch (SocketException sock)
			{
				Output.processMessage(p.target.person.name + " is assumed to have quit, dropping connection", Constants.Message_Type.info);
				considerReset(client, session);
				session.threads.remove(p);
				session.networkMembers.remove(p.target);
				session.updatePeerListOnScreen();
				p.target = null;
				p = null;
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();

				Output.processMessage("ServerUtils.checkMessageFromClient unable to get network object", Constants.Message_Type.error);
			}

		}
	}

	/**
	 * Generic send.
	 * @param s Session to send to.
	 * @param o Object to send.
	 * @param reason Reason to send.  IMPORTANT, each reason has one style of packing/unpacking.
	 * Anything else will cause errors.
	 * @param specialTarget If message should only go to one person, initialize this, else null.
	 */
	private static void genericSendToAllPeers(Session s, Object o, NetworkObject.reason reason, NetworkBundle specialTarget)
	{
		try
		{
			if (specialTarget != null)
			{
				try
				{
					NetworkObject genericObject = new NetworkObject
					(s.localUser.person,
							specialTarget.person,
							o,
							reason
					);

					ObjectOutputStream stream = specialTarget.oos;

					stream.writeObject(genericObject);
					stream.flush();
				}
		
				catch(Exception e)
				{
					Output.processMessage("Error in generic send for a person", Constants.Message_Type.error);
				}
			}
			else
			{
				for(int i = 0; i < s.networkMembers.size(); i ++)
				{
					NetworkBundle target = s.networkMembers.get(i);
	
					/**
					 * Make sure we don't send message to ourself.  That never works well.
					 */
					if(target != s.localUser)
					{
						try
						{
							NetworkObject genericObject = new NetworkObject
							(s.localUser.person,
									target.person,
									o,
									reason
							);
		
							ObjectOutputStream stream = target.oos;
		
							stream.writeObject(genericObject);
							stream.flush();
							}
						catch(Exception e)
						{
							Output.processMessage("Error in generic send for a person", Constants.Message_Type.error);
						}
	
					}
				}
			}
		}
		catch(Exception e)
		{
			Output.processMessage("Error in generic send", Constants.Message_Type.error);

		}
	}

	private static void sendPeerListToAllPeers(Session s) 
	{
		ArrayList<Member> currentPeerList = new ArrayList<Member>();
		for(int i = 0; i < s.networkMembers.size(); i++)
		{
			currentPeerList.add(s.networkMembers.get(i).person);
		}

		genericSendToAllPeers(s, currentPeerList, NetworkObject.reason.peerListUpdate, null);
		Output.processMessage("Master is sending peerListUpdate", Constants.Message_Type.info);
	}


	/**
	 * Send a mousePress message.
	 * @param s Session to send message for.
	 * @param p Point where mousePress occurred.
	 * @param t Tool selected when this happened.
	 * @param fillObject TODO
	 */
	public static void sendMousePress(Session s, Point p, Tool t, boolean fillObject, UUID uniqueId)
	{
		Boolean netBool = new Boolean(fillObject);
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(p);
		data.add(t);
		data.add(netBool);
		data.add(uniqueId);

		genericSendToAllPeers(s, data, NetworkObject.reason.mousePress, null);
		Output.processMessage("Master is sending mousePress", Constants.Message_Type.info);

	}

	/**
	 * Send a mouseRelease message.
	 * @param s Session to send message for.
	 * @param p Point event occurred at.
	 * @param t Tool selected when event occurred.
	 * @param fillObject TODO
	 */
	public static void sendMouseRelease(Session s, Point p, Tool t, Color c, boolean fillObject)
	{
		Boolean netBool = new Boolean(fillObject);
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(p);
		data.add(t);
		data.add(c);
		data.add(netBool);

		genericSendToAllPeers(s, data, NetworkObject.reason.mouseRelease, null);
		Output.processMessage("Master is sending mouseRelease", Constants.Message_Type.info);
	}

	/**
	 * Send a mouseDragged event.
	 * @param s Session to send message for.
	 * @param p Point where event occurred.
	 * @param t Tool selected when event occurred.
	 */
	public static void sendMouseDrag(Session s, Point p, Tool t)
	{
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(p);
		data.add(t);

		genericSendToAllPeers(s, data, NetworkObject.reason.mouseDrag, null);
		Output.processMessage("Master is sending mouseDrag", Constants.Message_Type.info);
	}

	/**
	 * Send a key typed event.
	 * @param tool Tool on which the event occurred.
	 * @param keyPressed The key which was pressed.
	 * @param session Session to send event for.
	 */
	public static void sendKeyTyped
	(KeyboardTool tool, 
			char keyPressed,
			Session session) 
	{
		Character networkChar = new Character(keyPressed);
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(networkChar);
		data.add(tool);
		genericSendToAllPeers(session, data, NetworkObject.reason.keyTyped, null);

		Output.processMessage("Master is sending keyTyped", Constants.Message_Type.info);

	}

	/**
	 * Send a key released event.
	 * @param tool Tool on which the event occurred.
	 * @param keyPressed The key which was released.
	 * @param session Session to send event for.
	 */
	public static void sendKeyReleased(KeyboardTool tool, char keyPressed,
			Session session) {
		Character networkChar = new Character(keyPressed);
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(networkChar);
		data.add(tool);
		genericSendToAllPeers(session, data, NetworkObject.reason.keyReleased, null);

		Output.processMessage("Master is sending keyReleased", Constants.Message_Type.info);


	}

	/**
	 * Send a key pressed event.
	 * @param tool Tool on which the event occurred.
	 * @param keyPressed The key which was pressed.
	 * @param session Session to send event for.
	 */
	public static void sendKeyPressed(KeyboardTool tool, char keyPress,
			Session session) 
	{
		Character networkChar = new Character(keyPress);
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(networkChar);
		data.add(tool);
		genericSendToAllPeers(session, data, NetworkObject.reason.keyPressed, null);

		Output.processMessage("Master is sending keyPressed", Constants.Message_Type.info);


	}

	/**
	 * Send clearobjects event.
	 * @param session Session to clear objects for.
	 */
	public static void sendClearObjects(Session session) {
		ArrayList<Object> data = new ArrayList<Object>();
		genericSendToAllPeers(session, data, NetworkObject.reason.clearCanvas, null);

		Output.processMessage("Master is sending clear", Constants.Message_Type.info);

	}

	/**
	 * Send clear selection event.  (i.e. unhighlight object).
	 * @param session Session to do this for.
	 */
	public static void sendClearSelection(Session session) {
		ArrayList<Object> data = new ArrayList<Object>();
		genericSendToAllPeers(session, data, NetworkObject.reason.clearSelection, null);

		Output.processMessage("Master is sending clearSelection", Constants.Message_Type.info);

	}

	/**
	 * Send highlight notice for a shape.
	 * @param s Object to highlight.
	 * @param session Session to do this for.
	 */
	public static void selectShape(Object s, Session session) {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(s);
		genericSendToAllPeers(session, data, NetworkObject.reason.selectShape, null);

		Output.processMessage("Master is sending selectShape", Constants.Message_Type.info);

	}

	/**
	 * Delete a shape (object).
	 * @param s Object to delete.
	 * @param session Session to do this for.
	 */
	public static void sendDeleteShape(Object s, Session session) {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(s);
		genericSendToAllPeers(session, data, NetworkObject.reason.deleteShape, null);

		Output.processMessage("Master is sending deleteShape", Constants.Message_Type.info);

	}

	/**
	 * Set a shapes color.
	 * @param s Object to set
	 * @param c Color to set.
	 * @param session Session to do this for.
	 */
	public static void setMainColor(Object s, Color c, Session session) {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(s);
		data.add(c);
		genericSendToAllPeers(session, data, NetworkObject.reason.setShapeColor, null);

		Output.processMessage("Master is sending set Color", Constants.Message_Type.info);

	}

	/**
	 * Set fill/empty on an object.
	 * @param shape Object to set for.
	 * @param isoutline Value of filled/not.
	 * @param session Session to do event for.
	 */
	public static void setDrawingType(Object shape, boolean isoutline,
			Session session) 
	{
		Boolean networkBool = new Boolean(isoutline);
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(shape);
		data.add(networkBool);
		genericSendToAllPeers(session, data, NetworkObject.reason.setShapeFill, null);

		Output.processMessage("Master is sending set fill", Constants.Message_Type.info);

	}
	
	/**
	 * Set a new tool.
	 * @param session Session to send message to.
	 * @param message Message to send.
	 */
	public static void sendNewTool(Session session, String name, byte[] rawbytes) 
	{
		
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(name);
		data.add(rawbytes);
		
		genericSendToAllPeers(session, data, NetworkObject.reason.setNewTool, null);
		
	}

	/**
	 * Send a chat message.
	 * @param session Session to send message to.
	 * @param message Message to send.
	 */
	public static void sendChatMesage(Session session, String message) 
	{
		genericSendToAllPeers(session, message, NetworkObject.reason.chat, null);
		
	}

	/**
	 * Tell everyone a new guy is running the show.
	 * @param session Session to do this for.
	 * @param newOwner The new owner.
	 */
	public static void sendTransferOwnership(Session session,
			NetworkBundle newOwner) 
	{
		genericSendToAllPeers(session, newOwner.person, NetworkObject.reason.canvasOwnershipConfirm, null);
		
	}

	/**
	 * Tell somebody they aren't going to be in charge.
	 * @param session Session to do this for.
	 * @param newOwner Disappointed guy.
	 */
	public static void sendRejectOwnerShip(Session session,
			NetworkBundle newOwner) 
	{
		genericSendToAllPeers(session, newOwner.person, NetworkObject.reason.canvasOwnershipDeny, newOwner);
	}

	/**
	 * Ask to be in charge.
	 * @param session Session to do this for.
	 */
	public static void sendOwnershipRequest(Session session) 
	{
		genericSendToAllPeers(session, session.master.person, NetworkObject.reason.canvasOwnershipRequest, session.master);
		
	}

	/**
	 * Send a request to invoke a rebellion.
	 * @param session Session to do this for.
	 */
	public static void sendInciteRebellionRequest(Session session) {
		genericSendToAllPeers(session, session.master.person, NetworkObject.reason.canvasOwnershipRebel, null);
	}

	/**
	 * Support a rebellion.
	 * @param session Session to do this for.
	 */
	public static void sendRebellionSupport(Session session, Member m) 
	{
		NetworkBundle target = null;
		for(int i = 0; i < session.networkMembers.size(); i++)
		{
			if(session.networkMembers.get(i).person.id.equals(m.id))
			{
				target = session.networkMembers.get(i);
			}
		}
		
		if (target != null)
		{
			genericSendToAllPeers
			(session, 
				m,
				NetworkObject.reason.canvasOwnershipSupportRebellion, 
				target);
	
		}
	}

	public static void sendQuitMessage(Session session)
	{
		genericSendToAllPeers(session, session.localUser.person, NetworkObject.reason.quit, null);
	}
	
	public static void sendDrawState(Session session, DrawState drawState)
	{
		genericSendToAllPeers(session,drawState, NetworkObject.reason.dataState, null);
	}
}
