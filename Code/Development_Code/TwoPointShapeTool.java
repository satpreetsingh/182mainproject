import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Generic tool that manipulates objects that have two points.
 * This class assumes that any object created can safely be
 * drawn on top of any other objects, while it is being
 * manipulated, or finalized.  The object can later be manipulated
 * if it needs to be redrawn, deleted, etc...
 * @authors bmhelppi, jjtrapan
 *
 */
public class TwoPointShapeTool implements Tool {
   
	protected String name;
	protected Point startingMousePosition;
	protected Point currentMousePosition;
	protected Object shape = null;
	protected TwoPointShapeFactory shapeFactory;
  
	  /**
	   * Create a new instance of a TwoEndShapeTool
	   * @param shapeFactory An object factory, that will create
	   * drawable shapes that the tool will manipulate
	   * on the canvas.
	   * @param name A name for this tool.
	   */
	  public TwoPointShapeTool
	   (TwoPointShapeFactory shapeFactory,
	    String name)
	  {
		  this.name = name;
		  this.shapeFactory = shapeFactory;
	  }
	  
	  /**
	   * For a TwoPointShapeTool, when the mouse is pressed,
	   * create a new TwoPointShape on the canvas.
	   */
	 public void mousePressed (Point p, ArrayList<Object> currentShapes, DrawingCanvas canvas, boolean fill,UUID uniqueId)
	 {
	
		 Graphics graphics = canvas.getimageBufferGraphics();
		 graphics.setXORMode(Color.lightGray);
		 graphics.setColor(Color.white);
		 
		 /* Draw the shape in white, so the XOR color is shown through as its natural color */
		 shape = shapeFactory.createShape (p.x,
										   p.y,
										   p.x,
										   p.y,
				 						   Color.WHITE,
				 						   fill, uniqueId);

		 
		 try{
			 ((TwoPointShape) shape).draw(graphics);
		 }catch(Exception e){
		
			try {
			
				 Method mi = shape.getClass().getMethod("draw", Graphics.class );
				 Object argsArray[] = {graphics};
				 mi.invoke(shape, argsArray);
			
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
		 
		 
		 canvas.repaint();
	 	}
	
	 /**
	  * When the mouse is dragged for a TwoPointShapeTool,
	  * update the second point of the shape, and redraw it.
	  */
	 public void mouseDragged(Point p,ArrayList<Object> currentShapes, DrawingCanvas canvas)  
	 {
	   Graphics graphics = canvas.getimageBufferGraphics();
	
	   /* Erase previous temporary figure by redrawing it */
		 try{
			 /* First attempt to draw the shape from a static class */
			 ((TwoPointShape) shape).draw(graphics);
		 }catch(Exception e){
		
			/* Otherwise try to draw the shape from a dynamically loaded class */ 
			try {
				
				 Method mi = shape.getClass().getMethod("draw", Graphics.class );
				 Object argsArray[] = {graphics};
				 mi.invoke(shape, argsArray);
			
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
	
		 
		 
		 
	   /* Set the second point of the new temporary figure */
	   try{
		   /* First attempt to set the points of the shape from a static class */
		   ((TwoPointShape) shape).setSecondPoint (p.x,
				    							   p.y);
	   }catch(Exception e){
		
			/* Otherwise try to set the points of the shape from a dynamically loaded class */ 
			try {
		         Method mi = shape.getClass().getMethod("setSecondPoint", double.class, double.class);
				 Object argsArray[] = {p.x, p.y};
				 mi.invoke(shape, argsArray);
			
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
		 
	   
	   
	     /* Erase previous temporary figure by redrawing it */
		 try{
			 /* First attempt to draw the shape from a static class */
			 ((TwoPointShape) shape).draw(graphics);
		 }catch(Exception e){
		
			/* Otherwise try to draw the shape from a dynamically loaded class */ 
			try {
				
				 Method mi = shape.getClass().getMethod("draw", Graphics.class );
				 Object argsArray[] = {graphics};
				 mi.invoke(shape, argsArray);
			
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
	   
	   
	   
	   canvas.repaint();
	 }


	 /**
	  * When the mouse is released for a TwoPointShapeTool, 
	  * the Shape is finalized, drawn a last time on the canvas, and
	  * then handed over to the CanvasController for future 
	  * manipulation. 
	  */
	 public void mouseReleased(Point point,ArrayList<Object> currentShapes, DrawingCanvas canvas, Color finalColor, boolean filled) { 
	    
		Graphics graphics = canvas.getimageBufferGraphics();
	
		
		
		
	     /* Save the object's color to match the pen's color */
		 try{
			 /* First attempt to set the main color of a static class */
			 ((TwoPointShape) shape).set_MainColor(finalColor);
		 }catch(Exception e){
		
			/* Otherwise try to draw the shape from a dynamically loaded class */ 
			try {
				
				 Method mi = shape.getClass().getMethod("set_MainColor", Color.class);
				 Object argsArray[] = {finalColor};
				 mi.invoke(shape, argsArray);
			
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
	   
		 
	    
	   
	     /* Save the object's type */
		 try{
			 /* First attempt to set the main color of a static class */
			 ((TwoPointShape) shape).set_DrawingType(canvas.getDrawingType());
		 }catch(Exception e){
		
			/* Otherwise try to draw the shape from a dynamically loaded class */ 
			try {
				
				 Method mi = shape.getClass().getMethod("set_DrawingType", boolean.class);
				 Object argsArray[] = {canvas.getDrawingType()};
				 mi.invoke(shape, argsArray);
			
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
		 
	    
	    /* Draw final "permanent" object */
		 try{
			 /* First attempt to draw the shape from a static class */
			 ((TwoPointShape) shape).draw(graphics);
		 }catch(Exception e){
		
			/* Otherwise try to draw the shape from a dynamically loaded class */ 
			try {
				
				 Method mi = shape.getClass().getMethod("draw", Graphics.class );
				 Object argsArray[] = {graphics};
				 mi.invoke(shape, argsArray);
			
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
	   
	    
	    canvas.repaint();   
	
	    /* Add shape to list maintained by the controller */
	    currentShapes.add(shape);
	    
	    
	  }

	public void deselected(DrawingCanvas canvas) { }

	public String toolName() 
	{
		return this.name;
	}


}
