import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Date;


/**
 * Static utilities.  Handles large chunks of code for Session, and SessionManager.
 * @author ben
 *
 */
public class ServerUtils
{

	
	/**
	 * Sever got a connection.
	 * Setup some logicals, this is step 1.
	 * @param client Socket that client connected on.
	 * @param s Session socket connected to.
	 */
	public static void acceptConn1(Socket client, Session s)
	{
		
		try
		{
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			Member m = null;
			
			NetworkBundle newConn = new NetworkBundle(m, ois, oos, client);
			s.networkMembers.add(newConn);
		    Output.processMessage("Server accepted conn, no data yet", Constants.Message_Type.info);
		}
		catch (Exception e)
		{
			Output.processMessage("ServerUtils.acceptConn1: Error in server accept client", Constants.Message_Type.error);
			
		}
	}
	
	/**
	 * Client connected to server.
	 * Client is trying to send a message to say 
	 * "Hi, I'm here, and my name is ***"
	 * @param s A session where the localUser, and master objects are mostly setup.
	 */
	public static void acceptConn2(Session s)
	{
		try 
		{
			ObjectOutputStream masterStream = s.master.oos;
			
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(s.localUser.person);
			
			NetworkObject hello = new NetworkObject
			(s.localUser.person,
			 s.master.person,
			 data,
			 NetworkObject.reason.joinRequest,
			 1
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
	public static Session buildSession(Member m, DrawingCanvas c, ArrayList<ToolController> tools)
	{
		Session result;
		try 
		{
			ServerSocket serverSock = new ServerSocket(3000);
			NetworkBundle creater = new NetworkBundle(m, null, null, null);
		 	result = new Session(serverSock, creater, c, tools);
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
	public static Session buildSession(Member m, DrawingCanvas c, String ip, int port,ArrayList<ToolController> tools)
	{
		Session result;
		try
		{
			ServerSocket serverSock = new ServerSocket(3001);
			NetworkBundle local = new NetworkBundle(m,null,null,null);
		
			result = new Session(local, serverSock, c, ip, port,tools);
		
		}
		catch(Exception e)
		{
			Output.processMessage("ServerUtils.buildSession, Unable to build client session", Constants.Message_Type.error);
			result = null;
		}
		
		return result;
	}
	
	
	private static void processMouseRelease(Session s, NetworkObject data)
	{
		try
		{
			ArrayList<Object> networkData = (ArrayList<Object>)data.data;
			Point point = (Point)networkData.get(0);
			Tool tool = (Tool)networkData.get(1);
			Color color = (Color)networkData.get(2);
			
			s.processMouseRelease(point, true, tool, color);
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
			
			s.processMousePress(point, true, tool);
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

	
	protected static void checkMessageFromClient(Session session, NetworkBundle client, NetworkBundle local)
	{
		/**
		 * Don't check ourself for messages.
		 */
		if (client != local)
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
					
				
			} 
			catch (Exception e) 
			{
				Output.processMessage("ServerUtils.checkMessageFromClient unable to get network object", Constants.Message_Type.error);
			}
			
		}
	}
	
	/**
	 * Helper method to send an object o to all members in s for reason.
	 * @param s Session  to send message for.  Will be checked for people
	 * to send message to.
	 * @param o Object to send.
	 * @param reason Reason for sending this message.
	 */
	private static void genericSend(Session s, Object o, NetworkObject.reason reason)
	{
		try
		{
			for(int i = 0; i < s.networkMembers.size(); i ++)
			{
				NetworkBundle target = s.networkMembers.get(i);
				
				/**
				 * Make sure we don't send message to ourself.  That never works well.
				 */
				if(target != s.localUser)
				{
					
					NetworkObject genericObject = new NetworkObject
					(s.localUser.person,
					 target.person,
					 o,
					 reason,
					 1
					);
					
					ObjectOutputStream stream = target.oos;
					
					stream.writeObject(genericObject);
					stream.flush();
					
				}
			}
		}
		catch(Exception e)
		{
			Output.processMessage("Error in generic send", Constants.Message_Type.error);
			
		}
	}
	
	/**
	 * Send a mousePress message.
	 * @param s Session to send message for.
	 * @param p Point where mousePress occurred.
	 * @param t Tool selected when this happened.
	 */
	public static void sendMousePress(Session s, Point p, Tool t)
	{
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(p);
		data.add(t);
		
		genericSend(s, data, NetworkObject.reason.mousePress);
		Output.processMessage("Master is sending mousePress", Constants.Message_Type.info);
			
	}

	/**
	 * Send a mouseRelease message.
	 * @param s Session to send message for.
	 * @param p Point event occurred at.
	 * @param t Tool selected when event occurred.
	 */
	public static void sendMouseRelease(Session s, Point p, Tool t, Color c)
	{
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(p);
		data.add(t);
		data.add(c);
		
		genericSend(s, data, NetworkObject.reason.mouseRelease);
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
		
		genericSend(s, data, NetworkObject.reason.mouseDrag);
		Output.processMessage("Master is sending mouseDrag", Constants.Message_Type.info);
	}



}
