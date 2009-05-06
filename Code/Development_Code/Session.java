import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.swing.Action;
import javax.swing.JOptionPane;


/**
 * Implements a network session.
 * Keeps track of members active, who's in charge, and 
 * manages data.
 * @author bmhelppi, jjtrapan
 *
 */
public class Session 
{

	
	private int rebellionCount = 0;
	public ArrayList<PeerThread> threads = new ArrayList<PeerThread>();
	public ArrayList<NetworkBundle>  networkMembers;
	public ArrayList<ToolController> tools;
	
	public NetworkBundle localUser;
	public NetworkBundle master;
	public ServerSocket serverSock;
	
	public DrawState currentState;
	
	public DrawingCanvas canvas;
	public MenuBarView menubar;
	public ChatPanelView chatPanel;
	public ControlPanelView controlPanel;
	
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
	public Session 
	(NetworkBundle local, 
			ServerSocket serverSock,
			DrawingCanvas canvas,
			String ip, 
			int port,ArrayList<ToolController> tools, 
			MenuBarView menubar, 
			ChatPanelView chatPanel,
			ControlPanelView controlPanel) throws ActivationException
			
	{
		networkMembers = new ArrayList<NetworkBundle>();
		networkMembers.add(local);
		currentState = new DrawState();
		this.canvas = canvas;
		this.localUser = local;
		this.master = null;
		this.serverSock = serverSock;
		this.tools = tools;
		this.menubar = menubar;
		this.chatPanel = chatPanel;
		this.controlPanel = controlPanel;
		
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
			this.controlPanel.refresh();
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
	public Session (ServerSocket serverSock,
					NetworkBundle creater, 
					DrawingCanvas canvas,
					ArrayList<ToolController> tools, 
					ChatPanelView chatPanel, 
					ControlPanelView controlPanel)
	{
		networkMembers = new ArrayList<NetworkBundle>();
		networkMembers.add(creater);
		currentState = new DrawState();
		this.master = creater;
		this.canvas = canvas;
		this.localUser = creater;
		this.serverSock = serverSock;
		this.tools = tools;
		this.chatPanel = chatPanel;
		this.controlPanel = controlPanel;
		this.controlPanel.refresh();
	}
	
	/**
	 * Get the current canvas state.
	 * @return Returns the current canvas state.
	 */
	public DrawState getCurrentState()
	{
		return currentState;
	}
	
	/**
	 * Indicate if the user can draw on the canvas.
	 * @param m Should be the local user.  
	 * @return Returns indication of if the user can draw on the campus.
	 */
	public boolean drawable(NetworkBundle m)
	{
		return (m.person.id.equals(this.master.person.id));
	}
	
	
	/**
	 * Process an event to clear the last selected object.
	 */
	public void processClearSelectedObject(boolean networkEvent)
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
	 * @param s Object to select.
	 */
	public void processSelectShape(Object s, boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if(networkEvent == false)
			{
				SessionUtils.selectShape(s, this);
			}
				
			ArrayList <Object> drawableShapes = this.currentState.currentShapes();
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
	 * @param s Object to delete.
	 */
	public void processDeleteShape (Object s, boolean networkEvent)
	{
		if (this.drawable(this.localUser))
		{
			if(networkEvent == false)
			{
				SessionUtils.sendDeleteShape(s, this);
			}
			
			ArrayList <Object> drawableShapes = this.currentState.currentShapes();
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
	 * @param s Object to set.
	 * @param c Color to set.
	 */
	public void processSetMainColor(Object s, Color c, boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if(networkEvent == false)
			{
				SessionUtils.setMainColor(s, c, this);
			}
			
			ArrayList <Object> drawableShapes = this.currentState.currentShapes();
			if (drawableShapes != null)
			{
				for(int i = 0; i < drawableShapes.size(); i++) 
				{
					if ( (drawableShapes.get(i).equals(s))) 
					{
					
						/* Attempt to cast as a shape and call the static method */
		    			try{
		    				((Shape)drawableShapes.get(i)).set_MainColor(c);
		    			}catch(Exception e){
		    			
		    				/* Try to call the shape draw method through reflection */
		    				try {
		    					
		    					 Method mi = drawableShapes.get(i).getClass().getMethod("set_MainColor", Color.class);
		    					 Object argsArray[] = {c};
		    					 mi.invoke(drawableShapes.get(i), argsArray);
		    				
		    			 	} catch (SecurityException e1) {
		    				 	e1.printStackTrace();
		    			 	} catch (NoSuchMethodException e2) {
		    					e2.printStackTrace();
		    				} catch (IllegalArgumentException e3) {
		    					e3.printStackTrace();
		    				} catch (IllegalAccessException e4) {
		    					e4.printStackTrace();
		    				} catch (InvocationTargetException e5) {
		    					e5.printStackTrace();
		    				}	 	
		    			}
		    			
						this.canvas.repaint();
					}
				}	
			}
		}
	}
	
	/**
	 * Process an event to set a new tool on the menubar.
	 * @param a Action to set.
	 */
	public void processSetNewTool(String name, byte[] rawdata)
	{		
		
		this.menubar.AddNewTool(name, rawdata);
		
	}
	
	
	
	/**
	 * Process an event to set the main type of a shape.
	 * @param shape Object to set.
	 * @param IsOutline boolean to set.
	 */
	public void processSetShapeFill(Object shape, boolean isoutline, boolean networkEvent)
	{
		if(this.drawable(this.localUser) || networkEvent)
		{
			if(networkEvent == false)
			{
				SessionUtils.setDrawingType(shape, isoutline, this);
			}
			
			ArrayList <Object> drawableShapes = this.currentState.currentShapes();
			if (drawableShapes != null)
			{
				for(int i = 0; i < drawableShapes.size(); i++) 
				{
					if ( (drawableShapes.get(i).equals(shape))) 
					{
						
					     /* Save the object's type */
						 try{
							 /* First attempt to set the main color of a static class */
							 ((Shape)drawableShapes.get(i)).set_DrawingType(isoutline);
						 }catch(Exception e){
						
							/* Otherwise try to draw the shape from a dynamically loaded class */ 
							try {
								
								 Method mi = drawableShapes.get(i).getClass().getMethod("set_DrawingType", boolean.class);
								 Object argsArray[] = {isoutline};
								 mi.invoke(drawableShapes.get(i), argsArray);
							
						 	} catch (SecurityException e1) {
							 	e1.printStackTrace();
						 	} catch (NoSuchMethodException e2) {
								e2.printStackTrace();
							} catch (IllegalArgumentException e3) {
								e3.printStackTrace();
							} catch (IllegalAccessException e4) {
								e4.printStackTrace();
							} catch (InvocationTargetException e5) {
								e5.printStackTrace();
							}	 
						 }
						 
						 this.canvas.refresh();
					}
				}	
			}
		}
		
	}
	
	/**
	 * Process an event to delete all the shapes on the canvas.
	 */
	public void processClearObjects(boolean networkEvent)
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
	
	/**
	 * Process a key typed event.
	 * @param keyPressed The key.
	 * @param networkEvent Is this event from another machine?
	 * @param networkTool If networkevent is true, this must exist.
	 */
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
	
	/**
	 * Process a key release event.
	 * @param keyPressed The key
	 * @param networkEvent If event from network
	 * @param networkTool If networkEvent is true, must exist.
	 */
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
	
	/**
	 * Process a keypress event
	 * @param keyPress The key.
	 * @param networkEvent If event from network.
	 * @param networkTool If networkEvent, this must exist.
	 */
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
	
	/**
	 * Process a new tool.
	 * @param NewClass Class.
	 * @param networkEvent If event from over network.
	 */
	public void processNewTool(String name, byte[] rawbytes, boolean networkEvent)
	{
		if(networkEvent == false)
		{
			SessionUtils.sendNewTool(this, name, rawbytes);
		}
		else
		{
			System.out.println("ProcessNewTool in Session.Java--networkEvent = True!!");
			this.menubar.AddNewTool(name, rawbytes);
			//this.chatPanel.processMessage(a, Constants.Message_Type.chat);
		}
	}
	
	/**
	 * Process a chat message.
	 * @param message The message.
	 * @param networkEvent If event from over network.
	 */
	public void processChatMessage(String message, boolean networkEvent)
	{
		if(networkEvent == false)
		{
			SessionUtils.sendChatMesage(this, message);
		}
		else
		{
			this.chatPanel.processMessage(message, Constants.Message_Type.chat);
		}
	}
	
	/**
	 * Process an event to set a different canvas owner.
	 * @param newOwner The new owner.
	 * @param networkEvent Indicates if this is local/network
	 */
	public void processTransferOwnership(NetworkBundle newOwner, boolean networkEvent)
	{
		this.master = newOwner;
		this.chatPanel.processMessage("New owner is " + newOwner.person.name, Constants.Message_Type.info);
		if(networkEvent == false)
		{
			SessionUtils.sendTransferOwnership(this, newOwner);
		}
		
		/* Refresh the controller/client IPs */
		controlPanel.refresh();
		
	}
	
	/**
	 * Process an event to reject an ownership request.
	 * @param newOwner The person who doesn't get to own canvas.
	 * @param networkEvent indicate if network event.
	 */
	public void processRejectOwnership(NetworkBundle newOwner, boolean networkEvent)
	{
		if(networkEvent == false)
		{
			SessionUtils.sendRejectOwnerShip(this, newOwner);
		}
		else
		{
  			JOptionPane.showMessageDialog(
  		  			null,
  		  			"Ownership request was rejected.",
  		  			"Input Error",
  		  			JOptionPane.ERROR_MESSAGE);   
		}
	}
	
	/**
	 * Process a request to own the session.
	 * @param requestor Person who wants to own session.
	 * @param networkEvent Indicate if local/remote event.
	 */
	public void processRequestOwnership(Member requestor, boolean networkEvent)
	{
		
		NetworkBundle requestBundle = null;
		for(int i = 0; i < networkMembers.size(); i++)
		{
			if(networkMembers.get(i).person.id.equals(requestor.id))
			{
				requestBundle = networkMembers.get(i);
			}
		}
		if(networkEvent && (requestBundle != null))
		{
	    	/* If controller accepts giving up control */
	    	if (JOptionPane.showConfirmDialog(
	    	    null,
	    	    "Would you like to pass control of the session to " + requestor.name + "?",
	    	    "Pass Control Request",
	    	    JOptionPane.YES_NO_OPTION) == 0)
	    	{
	    		this.processTransferOwnership(requestBundle, false);
	    	}
	    	else
	    	{
	    		this.processRejectOwnership(requestBundle, false);
	    	}
		}
		else
		{
			SessionUtils.sendOwnershipRequest(this);
		}
			
		
			
	}
	
	/**
	 * Attempt to wrest control from owner if enough people agree.
	 */
	public void processInciteRebellion()
	{
		this.rebellionCount = 0;
		SessionUtils.sendInciteRebellionRequest(this);
	}
	
	/**
	 * Accept an event to vote for a rebellion/not.
	 * @param rebel Person who started it.
	 */
	public void processRespondtoRebellion(Member rebel)
	{
		/* If controller accepts giving up control */
    	if (JOptionPane.showConfirmDialog(
    	    null,
    	    rebel.name + 
    	    " thinks the current owner is boring.  Want to replace " +
    	    this.master.person.name + "?",
    	    "Rebellion",
    	    JOptionPane.YES_NO_OPTION) == 0)
    	{
    		SessionUtils.sendRebellionSupport(this, rebel);
    	}
    	else
    	{
    	}
	}
	
	/**
	 * Process votes for a rebellion, if enough,
	 * hand control to yourself.
	 */
	public void processRebellionVotes()
	{
		this.rebellionCount = this.rebellionCount + 1;
		if(this.rebellionCount >= (this.networkMembers.size() / 2 - 1))
		{
			this.processTransferOwnership(this.localUser, false);
			this.rebellionCount = -10;
		}
	}

	public void setBaseline(DrawState baseline, boolean networkEvent)
	{
		this.currentState = baseline;
		this.canvas.refresh();
		if(networkEvent == false)
		{
			SessionUtils.sendDrawState(this, baseline);
		}
	}

	public void updatePeerListOnScreen() {
		this.chatPanel.refreshUsersList();
	}

}
