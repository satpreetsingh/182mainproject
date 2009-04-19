import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.UUID;



public class Session {

	public UUID	id;
	public ArrayList <Member> members;
	public Member localUser;
	
	public DrawState currentState;
	
	public DrawingCanvas canvas;
	
	
	public Session (Member creater, DrawingCanvas canvas)
	{
		members = new ArrayList<Member>();
		members.add(creater);
		currentState = new DrawState();
		this.canvas = canvas;
		this.localUser = creater;
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
	
	public boolean drawable(Member m)
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
	
	
	// JJT ADDED
	public void setMainType(Shape s, int type)
	{
		s.set_DrawingType(type);
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
				tool.keyTyped(e, this.currentState.currentShapes());
			}
		}
	}
	public void processKeyRelease(KeyEvent e)
	{
		if(this.drawable(this.localUser))
		{
			KeyboardTool tool = (KeyboardTool)canvas.getcurrentTool();
			if(tool != null) {
				tool.keyReleased(e,this.currentState.currentShapes());
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
				tool.keyPressed(e, this.currentState.currentShapes());
			}
		}
	}
	public void processMouseDrag(MouseEvent e)
	{
		if (this.drawable(this.localUser))
		{
			Tool tool = canvas.getcurrentTool();
			if(tool != null) 
			{
				tool.mouseDragged(e,this.currentState.currentShapes());
			}
		}
	}
	public void processMouseRelease(MouseEvent e)
	{
		if(this.drawable(this.localUser))
		{
			  Tool tool = canvas.getcurrentTool();
			  if(tool != null) 
			  {
				  tool.mouseReleased(e, this.currentState.currentShapes());
			  }
	  }
	}
	public void processMousePress(MouseEvent e)
	{
		if(this.drawable(this.localUser))
		{
		 //TODO: BMH SESSION Management of events.
			
			
		  if (this.currentState.lastSelected() != null)
		  {
			  canvas.repaint();	
			  this.currentState.clearLastSelectedObject();	
		  }
		  Tool tool = canvas.getcurrentTool();
		  if (tool != null) 
		  {
			  tool.mousePressed(e,this.currentState.currentShapes());
		  }
		}
	}
}
