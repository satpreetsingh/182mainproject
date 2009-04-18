import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
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



public class Session {

	
	public ArrayList <NetworkBundle>  networkMembers;
	
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
	public Session (NetworkBundle local, ServerSocket serverSock,DrawingCanvas canvas, String ip, int port) throws ActivationException
	{
		networkMembers = new ArrayList<NetworkBundle>();
		networkMembers.add(local);
		currentState = new DrawState();
		this.canvas = canvas;
		this.localUser = local;
		this.master = null;
		this.serverSock = serverSock;
		
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
	public Session (ServerSocket serverSock,NetworkBundle creater, DrawingCanvas canvas)
	{
		networkMembers = new ArrayList<NetworkBundle>();
		networkMembers.add(creater);
		currentState = new DrawState();
		this.master = creater;
		this.canvas = canvas;
		this.localUser = creater;
		this.serverSock = serverSock;
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
	
	public boolean drawable(NetworkBundle m)
	{
		return true;
	}
	
	
	public void clearSelection()
	{
		if(this.drawable(this.localUser))
		{
			this.currentState.clearLastSelectedObject();
		}
	}
	
	public void selectShape(Shape s)
	{
		if(this.drawable(this.localUser))
		{
			this.currentState.setLastSelected(s);
		}
	}
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
	
	public void setMainColor(Shape s, Color c)
	{
		s.set_MainColor(c);
	}
	public void clearObjects()
	{
		if(this.drawable(this.localUser))
		{
			this.currentState.currentShapes().clear();
		}
	}
	public void processKeyTyped(KeyEvent e)
	{
		if(this.drawable(this.localUser))
		{
			KeyboardTool tool = (KeyboardTool)canvas.getcurrentTool();
			if(tool != null) 
			{
				tool.keyTyped(e, this.currentState.currentShapes(), this.canvas);
			}
		}
	}
	public void processKeyRelease(KeyEvent e)
	{
		if(this.drawable(this.localUser))
		{
			KeyboardTool tool = (KeyboardTool)canvas.getcurrentTool();
			if(tool != null) {
				tool.keyReleased(e,this.currentState.currentShapes(), this.canvas);
			}
		}
	}
	public void processKeyPress(KeyEvent e)
	{
		if(this.drawable(this.localUser))
		{
			KeyboardTool tool = (KeyboardTool)canvas.getcurrentTool();
			if (tool != null) 
			{
				tool.keyPressed(e, this.currentState.currentShapes(), this.canvas);
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
				tool = networkTool;
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
	public void processMouseRelease(Point p, boolean networkEvent, Tool networkTool)
	{
		if(this.drawable(this.localUser))
		{
			Tool tool;
			if(networkEvent == false)
			{
				tool = canvas.getcurrentTool();
				ServerUtils.sendMouseRelease(this, p, tool);
			}
			else
			{
				tool = networkTool;
			}
			
			
			if(tool != null) 
			{
				tool.mouseReleased(p, this.currentState.currentShapes(), this.canvas);
			}
	  }
	}
	
	/**
	 * Process a mousePress event.
	 * @param p Point where event occurred.
	 * @param networkEvent If true, event came from network.
	 * @param networkTool If NetworkEvent is true, then it is expected that this will exist.
	 */
	public void processMousePress(Point p, boolean networkEvent, Tool networkTool)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			Tool tool;
			if(networkEvent == false)
			{
				tool = canvas.getcurrentTool();
				ServerUtils.sendMousePress(this, p, tool);
			}
			else
			{
				tool = networkTool;
			}
			
			
			if (this.currentState.lastSelected() != null)
			{
				canvas.repaint();	
				this.currentState.clearLastSelectedObject();	
			}
			if (tool != null) 
			{
				tool.mousePressed(p,this.currentState.currentShapes(), this.canvas);
			}
		}
	}
}
