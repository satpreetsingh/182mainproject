import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


/**
 * Implements a network session.
 * Keeps track of members active, who's in charge, and 
 * manages data.
 * @author bmhelppi
 *
 */
public class Session {

	
	public ArrayList <NetworkBundle>  networkMembers;
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
			Member master = null;
			
			NetworkBundle masterBundle = new NetworkBundle(master, ois,oos,masterSocket);
			
			this.master = masterBundle;
			networkMembers.add(this.master);
			
			ServerUtils.acceptConn2(this);
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
	
	public void publishEvent()
	{
		//TODO:
	}
	
	public void publishState()
	{
		//TODO:
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
		return true;
	}
	
	
	/**
	 * Process an event to clear the last selected object.
	 */
	public void clearSelection()
	{
		if(this.drawable(this.localUser))
		{
			this.currentState.clearLastSelectedObject();
		}
	}
	
	/**
	 * Process an event to select a shape.
	 * @param s Shape to select.
	 */
	public void selectShape(Shape s)
	{
		if(this.drawable(this.localUser))
		{
			this.currentState.setLastSelected(s);
		}
	}
	
	/**
	 * Process an event to delete a shape.
	 * @param s Shape to delete.
	 */
	public void deleteShape (Shape s)
	{
		if (this.drawable(this.localUser))
		{
			ArrayList <Shape> drawableShapes = this.currentState.currentShapes();
			
			if (drawableShapes != null)
			{
				/* Scan the list for the object and remove it */
				for(int i = 0; i < drawableShapes.size(); i++) 
				{
					if ( (drawableShapes.get(i) == s)) 
					{
						/* We found the object now remove it from the list */  
						drawableShapes.remove(i);
						i = drawableShapes.size();
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
	public void setMainColor(Shape s, Color c)
	{
		s.set_MainColor(c);
	}
	
	/**
	 * Process an event to set the main type of a shape.
	 * @param s Shape to set.
	 * @param IsOutline boolean to set.
	 */
	public void setMainType(Shape s, boolean isoutline)
	{
		s.set_DrawingType(isoutline);
	}
	
	/**
	 * Process an event to delete all the shapes on the canvas.
	 * @param networkEvent TODO
	 */
	public void clearObjects(boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if (networkEvent)
			{
				ServerUtils.sendClearObjects(this);
			}
			this.currentState.currentShapes().clear();
			
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
				ServerUtils.sendKeyTyped(tool, keyPressed, this);
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
				ServerUtils.sendKeyReleased(tool, keyPressed, this);
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
				ServerUtils.sendKeyPressed(tool, keyPress, this);
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
				ServerUtils.sendMouseDrag(this,p, tool);
				
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
	 * @param networkFill TODO
	 */
	public void processMouseRelease(Point p, boolean networkEvent, Tool networkTool, Color networkColor, boolean networkFill)
	{
		if(this.drawable(this.localUser))
		{
			Tool tool;
			Color color;
			boolean fill;
			if(networkEvent == false)
			{
				tool = canvas.getcurrentTool();
				color = canvas.getpenColor();
				fill = canvas.getDrawingType();
				ServerUtils.sendMouseRelease(this, p, tool, color);
				
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
	 * @param networkEvent If true, event came from network.
	 * @param networkTool If NetworkEvent is true, then it is expected that this will exist.
	 */
	public void processMousePress(Point point, boolean networkEvent, Tool networkTool, boolean networkFill)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			Tool tool;
			boolean fill;
			if(networkEvent == false)
			{
				tool = canvas.getcurrentTool();
				fill = canvas.getDrawingType();
				ServerUtils.sendMousePress(this, point, tool);
			}
			else
			{
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
				tool.mousePressed(point,this.currentState.currentShapes(), this.canvas, fill);
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

}
