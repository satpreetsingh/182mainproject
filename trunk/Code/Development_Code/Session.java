import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.swing.JOptionPane;


/**
 * Implements a network session.
 * Keeps track of members active, who's in charge, and 
 * manages data.
 * @author bmhelppi
 *
 */
public class Session {

	public ArrayList<PeerThread> threads = new ArrayList<PeerThread>();
	public ArrayList<NetworkBundle>  networkMembers;
	public ArrayList<ToolController> tools;
	
	public NetworkBundle localUser;
	public NetworkBundle master;
	public ServerSocket serverSock;
	
	public DrawState currentState;
	
	public DrawingCanvas canvas;
	
	/**
	 * Create a new Session on the local machine, that this application will not be in charge of.
	 * If the master specified can't be found, throws ActivationException.
	 * @param local
	 * @param serverSock
	 * @param canvas
	 * @param ip
	 * @param port
	 * @throws ActivationException
	 */
	public Session (NetworkBundle local, ServerSocket serverSock,DrawingCanvas canvas, String ip, int port,ArrayList<ToolController> tools) throws ActivationException
	{
		networkMembers = new ArrayList<NetworkBundle>();
		networkMembers.add(local);
		currentState = new DrawState();
		this.canvas = canvas;
		this.localUser = local;
		this.master = null;
		this.serverSock = serverSock;
		this.tools = tools;
		
		try 
		{
			/**
			 * Create a socket for the server that we are connecting to.
			 * Note when we create it we can't possibly fill out the member object, need to establish communication to do so.
			 */
			Socket masterSocket = new Socket(ip, port);
			ObjectOutputStream oos = new ObjectOutputStream(masterSocket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(masterSocket.getInputStream());
			Member master = new Member(Member.nullName);
			NetworkBundle masterBundle = new NetworkBundle(master, ois,oos,masterSocket, ip , masterSocket.getPort());
			
			this.master = masterBundle;
			networkMembers.add(this.master);
			
			PeerThread p = new PeerThread(this, this.localUser, this.master);
			this.threads.add(p);
			p.start();
			
			SessionUtils.requestSessionJoin(this, masterBundle, NetworkObject.reason.joinSessionRequest);
		} 
		catch (IOException e) 
		{
			Output.processMessage("Error creating Session", Constants.Message_Type.error);
			throw new ActivationException("Could not connect to server.");
		}
	}
	
	/**
	 * Create a new Session on the local machine that the local application will
	 * be in charge of.
	 * @param serverSock
	 * @param creater
	 * @param canvas
	 */
	public Session (ServerSocket serverSock,NetworkBundle creater, DrawingCanvas canvas,ArrayList<ToolController> tools)
	{
		networkMembers = new ArrayList<NetworkBundle>();
		networkMembers.add(creater);
		currentState = new DrawState();
		this.master = creater;
		this.canvas = canvas;
		this.localUser = creater;
		this.serverSock = serverSock;
		this.tools = tools;
	}
	
	
	public DrawState getCurrentState()
	{
		return currentState;
	}
	
	/**
	 * Indicate if the user can draw on the canvas.
	 * @param m Should be the local user.  Private method to prevent abuse.
	 * @return Returns indication of if the user can draw on the campus.
	 */
	private boolean drawable(NetworkBundle m)
	{
		return (m.person.id.equals(this.master.person.id));
	}
	
	
	/**
	 * Process an event to clear the last selected object.
	 */
	public void clearSelection(boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if(networkEvent == false)
			{
				SessionUtils.sendClearSelection(this);
			}
			this.currentState.clearLastSelectedObject();
		}
	}
	
	/**
	 * Process an event to select a shape.
	 * @param s Shape to select.
	 */
	public void selectShape(Shape s, boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if(networkEvent == false)
			{
				SessionUtils.selectShape(s, this);
			}
				
			ArrayList <Shape> drawableShapes = this.currentState.currentShapes();
			if (drawableShapes != null)
			{
				for(int i = 0; i < drawableShapes.size(); i++) 
				{
					if ( (drawableShapes.get(i).equals(s))) 
					{
						this.currentState.setLastSelected(drawableShapes.get(i));
					}
				}	
			}
		}
	}
	
	/**
	 * Process an event to delete a shape.
	 * @param s Shape to delete.
	 */
	public void deleteShape (Shape s, boolean networkEvent)
	{
		if (this.drawable(this.localUser))
		{
			if(networkEvent == false)
			{
				SessionUtils.sendDeleteShape(s, this);
			}
			
			ArrayList <Shape> drawableShapes = this.currentState.currentShapes();
			if (drawableShapes != null)
			{
				for(int i = 0; i < drawableShapes.size(); i++) 
				{
					if ( (drawableShapes.get(i).equals(s))) 
					{
						drawableShapes.remove(i);
						this.canvas.refresh();
					}
				}	
			}
		}
	}
	
	/**
	 * Process an event to set the main color of a shape.
	 * @param s Shape to set.
	 * @param c Color to set.
	 */
	public void setMainColor(Shape s, Color c, boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if(networkEvent == false)
			{
				SessionUtils.setMainColor(s, c, this);
			}
			
			ArrayList <Shape> drawableShapes = this.currentState.currentShapes();
			if (drawableShapes != null)
			{
				for(int i = 0; i < drawableShapes.size(); i++) 
				{
					if ( (drawableShapes.get(i).equals(s))) 
					{
						drawableShapes.get(i).set_MainColor(c);
						this.canvas.repaint();
					}
				}	
			}
		}
	}
	
	/**
	 * Process an event to set the main type of a shape.
	 * @param shape Shape to set.
	 * @param IsOutline boolean to set.
	 */
	public void setShapeFill(Shape shape, boolean isoutline, boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if(networkEvent == false)
			{
				SessionUtils.setDrawingType(shape, isoutline, this);
			}
			
			ArrayList <Shape> drawableShapes = this.currentState.currentShapes();
			if (drawableShapes != null)
			{
				for(int i = 0; i < drawableShapes.size(); i++) 
				{
					if ( (drawableShapes.get(i).equals(shape))) 
					{
						drawableShapes.get(i).set_DrawingType(isoutline);
						this.canvas.refresh();
					}
				}	
			}
		}
		
	}
	
	/**
	 * Process an event to delete all the shapes on the canvas.
	 */
	public void clearObjects(boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if (networkEvent == false)
			{
				SessionUtils.sendClearObjects(this);
			}
			this.currentState.currentShapes().clear();
			this.canvas.clearCanvas();
		}
	}
	
	public void processKeyTyped(char keyPressed, boolean networkEvent, Tool networkTool)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			
			KeyboardTool tool; 
				
			if (networkEvent)
			{
				tool = (KeyboardTool)convertNetworkTool(networkTool);
			}
			else
			{
				tool = (KeyboardTool)canvas.getcurrentTool();
				SessionUtils.sendKeyTyped(tool, keyPressed, this);
			}
				
				
			if(tool != null) 
			{
				tool.keyTyped(keyPressed, this.currentState.currentShapes(), this.canvas);
			}
		}
	}
	
	public void processKeyRelease(char keyPressed, boolean networkEvent, Tool networkTool)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			
			KeyboardTool tool; 
				
			if (networkEvent)
			{
				tool = (KeyboardTool)convertNetworkTool(networkTool);
			}
			else
			{
				tool = (KeyboardTool)canvas.getcurrentTool();
				SessionUtils.sendKeyReleased(tool, keyPressed, this);
			}
				
				
			if(tool != null) 
			{
				tool.keyReleased(keyPressed, this.currentState.currentShapes(), this.canvas);
			}
		}
	}
	
	public void processKeyPress(char keyPress, boolean networkEvent, Tool networkTool)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			
			KeyboardTool tool; 
				
			if (networkEvent)
			{
				tool = (KeyboardTool)convertNetworkTool(networkTool);
			}
			else
			{
				tool = (KeyboardTool)canvas.getcurrentTool();
				SessionUtils.sendKeyPressed(tool, keyPress, this);
			}
				
				
			if(tool != null) 
			{
				tool.keyPressed(keyPress, this.currentState.currentShapes(), this.canvas);
			}
		}
	}
	
	/**
	 * Process a mouseDrag event.
	 * @param p Point where the event occurred.
	 * @param networkEvent If true, event came from the network.
	 * @param networkTool If networkEvent is true, then it is expected that this will exist.
	 */
	public void processMouseDrag(Point p, boolean networkEvent, Tool networkTool)
	{
		if (this.drawable(this.localUser) || networkEvent)
		{
			Tool tool;
			if(networkEvent == false)
			{
				tool = canvas.getcurrentTool();
				SessionUtils.sendMouseDrag(this,p, tool);
				
			}
			else
			{
				tool = convertNetworkTool(networkTool);
			}
			
			if(tool != null) 
			{
				tool.mouseDragged(p,this.currentState.currentShapes(), this.canvas);
			}
		}
	}
	
	
	/**
	 * Process a mouseRelease event.
	 * @param p Point where event occurred.
	 * @param networkEvent If true, event came from network.
	 * @param networkTool If NetworkEvent is true, then it is expected that this will exist.
	 */
	public void processMouseRelease(Point p, boolean networkEvent, Tool networkTool, Color networkColor, boolean networkFill)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			Tool tool;
			Color color;
			boolean fill;
			if(networkEvent == false)
			{
				tool = canvas.getcurrentTool();
				color = canvas.getpenColor();
				fill = canvas.getDrawingType();
				SessionUtils.sendMouseRelease(this, p, tool, color, fill);
				
			}
			else
			{
				tool = convertNetworkTool(networkTool);
				color = networkColor;
				fill = networkFill;
			}
			
			
			if(tool != null) 
			{
				tool.mouseReleased(p, this.currentState.currentShapes(), this.canvas, color, fill);
			}
	  }
	}
	
	/**
	 * Process a mousePress event.
	 * @param point Point where event occurred.
	 * 
	 * @param networkEvent If true, event came from network.
	 * @param networkTool If NetworkEvent is true, then it is expected that this will exist.
	 */
	public void processMousePress(Point point, 
			boolean networkEvent, 
			Tool networkTool, 
			boolean networkFill,
			UUID networkUUID)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			UUID objectId;
			Tool tool;
			boolean fill;
			if(networkEvent == false)
			{
				objectId = UUID.randomUUID();
				tool = canvas.getcurrentTool();
				fill = canvas.getDrawingType();
				SessionUtils.sendMousePress(this, point, tool, fill, objectId);
			}
			else
			{
				objectId = networkUUID;
				fill = networkFill;
				tool = convertNetworkTool(networkTool);
			}
			
			
			if (this.currentState.lastSelected() != null)
			{
				canvas.repaint();	
				this.currentState.clearLastSelectedObject();	
			}
			if (tool != null) 
			{
				tool.mousePressed(point,this.currentState.currentShapes(), this.canvas, fill,objectId);
			}
		}
	}
	
	
	/**
	 * Converts a network tool object to a local tool object.
	 * @param networkTool Tool received with event.
	 * @return Returns a local tool.  
	 * Works as long as each tool has a unique name.
	 */
	private Tool convertNetworkTool(Tool networkTool)
	{
		Tool localTool = null;;
		for(int i = 0; i < tools.size(); i++)
		{
			if(tools.get(i).tool.toolName().equals(networkTool.toolName()))
			{
				localTool = tools.get(i).tool;
			}
		}
		return localTool;
	}
	
	public void chatMessage(String message, boolean networkEvent)
	{
		
	}
	
	public void transferOwnership(NetworkBundle newOwner)
	{
		
	}
	
	public void requestOwnership()
	{
		//Step 1 Joe call this
		//Step 2 Ben check if user isn't the controller, then continue on to Network this
		//Step 3 Joe add popup confirm/deny.
		
		
    	/* If controller accepts giving up control */
    	if (JOptionPane.showConfirmDialog(
    	    null,
    	    "Would you like to pass control of the session?",
    	    "Pass Control Request",
    	    JOptionPane.YES_NO_OPTION) == 0){
    		
    	}
    	else{
    		/* Alert user his request got denied */
    	}
			
		
			
	}

}
