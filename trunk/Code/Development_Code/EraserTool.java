import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Implements an erasing tool, which will delete objects drawn on the canvas.
 * @author bmhelppi, jjtrapan
 *
 */

/* DO NOT rename this class, DrawingCanvas.java depends upon it (line 99) */
public class EraserTool implements Tool {
   
	private String name;
	
	/**
     * Create a new instance of the Eraser tool.
     */
	public EraserTool(String name) 
	{
		this.name = name;
	}

  
	/**
	 * Dragging mouse does nothing for this class.
	 */
	public void mouseDragged(Point p, ArrayList<Object> currentShapes,DrawingCanvas canvas) {}

  
	/**
	 * When mouse pressed, check list of shapes, and if one
	 * is found, delete it, and redraw the canvas.
	 */
	public void mousePressed(Point p,
		  ArrayList<Object> currentShapes,
		  DrawingCanvas canvas, 
		  boolean fill,
		  UUID uniqueId) 
	{
		Object shape;
	    for (int i = 0; i < currentShapes.size(); i++) 
	    {
	    	shape = currentShapes.get(i);
	    	
			
	    	try{
				 /* Attempt to cast the eraser of type Shape from a static class */
	    		if (((Shape)shape).near(p.x, p.y)) 
	    		{
	    			currentShapes.remove(i);
	    			i = currentShapes.size();
	    			canvas.refresh();
	    		}
	    	}catch(Exception e){
	    	}
	    	
	    }
  }
  
  
  /**
   * This event does nothing for this tool.
   */
  public void mouseReleased(Point point, ArrayList<Object> currentShapes,DrawingCanvas canvas, Color finalColor, boolean filled) {}

  
  /**
   * This event does nothing for this tool.
   */
  public void deselected(DrawingCanvas canvas) {}

  /**
   * Return name of this tool.
   */
  public String toolName() 
  {
	  return name;
  }
	
}
