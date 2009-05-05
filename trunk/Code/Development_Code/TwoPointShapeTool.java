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
	protected Object shape=null;
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
	 public void mousePressed
	 (Point p, 
			 ArrayList<Shape> currentShapes, 
			 DrawingCanvas canvas, 
			 boolean fill,
			 UUID uniqueId)
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
			 Graphics argsArray[] = {graphics};
			 mi.invoke(shape, argsArray);
			
			
		 } catch (SecurityException e0) {
			// TODO Auto-generated catch block
			e0.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InvocationTargetException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}	 
		
		
		 }
		 
		 
		 canvas.repaint();
	 }
	
	 /**
	  * When the mouse is dragged for a TwoPointShapeTool,
	  * update the second point of the shape, and redraw it.
	  */
	 public void mouseDragged(Point p,ArrayList<Shape> currentShapes, DrawingCanvas canvas)  
	 {
	   Graphics graphics = canvas.getimageBufferGraphics();
	
	   /* Erase previous temporary figure by redrawing it */
	   ((TwoPointShape) shape).draw(graphics);
	
	   /* Draw new temporary figure */
	   ((TwoPointShape) shape).setSecondPoint
	   (p.x,
	    p.y);
	   ((TwoPointShape) shape).draw(graphics);
	
	   canvas.repaint();
	 }


	 /**
	  * When the mouse is released for a TwoPointShapeTool, 
	  * the Shape is finalized, drawn a last time on the canvas, and
	  * then handed over to the CanvasController for future 
	  * manipulation. 
	  */
	 public void mouseReleased(Point point,ArrayList<Shape> currentShapes, DrawingCanvas canvas, Color finalColor, boolean filled) { 
	    
		Graphics graphics = canvas.getimageBufferGraphics();
	
	    /* Save the object's color to match the pen's color */
	    ((TwoPointShape) shape).set_MainColor(finalColor);
	   
	    /* Save the object's type */
	    ((TwoPointShape) shape).set_DrawingType(canvas.getDrawingType());

	    /* Draw final "permanent" object */
	    ((TwoPointShape) shape).draw(graphics);
	    canvas.repaint();   
	
	    /* Add shape to list maintained by the controller */
	    currentShapes.add((TwoPointShape) shape);
	  }

	public void deselected(DrawingCanvas canvas) { }

	public String toolName() 
	{
		return this.name;
	}


}
