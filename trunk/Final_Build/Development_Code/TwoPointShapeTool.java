import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Generic tool that manipulates objects that have two points.
 * This class assumes that any object created can safely be
 * drawn on top of any other objects, while it is being
 * manipulated, or finalized.  The object can later be manipulated
 * if it needs to be redrawn, deleted, etc...
 * @author bmhelppi, jjtrapan
 *
 */
public class TwoPointShapeTool implements Tool {
   
	protected String name;
	protected Point startingMousePosition;
	protected Point currentMousePosition;
	protected TwoPointShape shape=null;
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
		 shape.draw(graphics);
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
	   shape.draw(graphics);
	
	   /* Draw new temporary figure */
	   shape.setSecondPoint
	   (p.x,
	    p.y);
	   shape.draw(graphics);
	
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
	    shape.set_MainColor(finalColor);
	   
	    /* Save the object's type */
	    shape.set_DrawingType(canvas.getDrawingType());

	    /* Draw final "permanent" object */
	    shape.draw(graphics);
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
