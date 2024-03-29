import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
   
  protected DrawingCanvas canvas;
  protected Point startingMousePosition;
  protected Point currentMousePosition;
  protected TwoPointShape shape=null;
  protected TwoPointShapeFactory shapeFactory;
  
      /* ------------------------------------------------------- */	
	  /**
	   * Create a new instance of a TwoEndShapeTool
	   * @param c Canvas that tool will draw upon when
	   * given events.
	   * @param sf An object factory, that will create
	   * drawable shapes that the tool will manipulate
	   * on the canvas.
	   */
  
	  public TwoPointShapeTool
	  (DrawingCanvas c,
	   TwoPointShapeFactory sf)
	  {
	   canvas = c;
	   shapeFactory = sf;
	  }
	  
	  /**
	   * For a TwoPointShapeTool, when the mouse is pressed,
	   * create a new TwoPointShape on the canvas.
	   */
	 public void mousePressed(MouseEvent e, ArrayList<Shape> currentShapes) {
	
		 Graphics graphics = canvas.getimageBufferGraphics();
		 graphics.setXORMode(Color.lightGray);
		 graphics.setColor(Color.white);
		 
		 /* Draw the shape in white, so the XOR color is shown through as its natural color */
		 shape = shapeFactory.createShape (e.getPoint().x,
				 						   e.getPoint().y,
				 						   e.getPoint().x,
				 						   e.getPoint().y,
				 						   Color.WHITE, 
				 						   canvas.getDrawingType());
		 shape.draw(graphics);
		 canvas.repaint();
	 }
	 /* ------------------------------------------------------- */	
	 /**
	  * When the mouse is dragged for a TwoPointShapeTool,
	  * update the second point of the shape, and redraw it.
	  */
	 
	 public void mouseDragged(MouseEvent e,ArrayList<Shape> currentShapes)  {
	   Graphics graphics = canvas.getimageBufferGraphics();
	
	   /* Erase previous temporary figure by redrawing it */
	   shape.draw(graphics);
	
	   /* Draw new temporary figure */
	   shape.setSecondPoint
	   (e.getPoint().x,
		e.getPoint().y);
	   shape.draw(graphics);
	
	   canvas.repaint();
	 }


	 /* ------------------------------------------------------- */	
	 /**
	  * When the mouse is released for a TwoPointShapeTool, 
	  * the Shape is finalized, drawn a last time on the canvas, and
	  * then handed over to the CanvasController for future 
	  * manipulation. 
	  */
	
	 public void mouseReleased(MouseEvent e,ArrayList<Shape> currentShapes) { 
	    
		Graphics graphics = canvas.getimageBufferGraphics();
	
	    /* Save the object's color to match the pen's color */
	    shape.set_MainColor(canvas.getpenColor());
	    
	    
	    // JJT ADDED
	    /* Save the object's type */
	    shape.set_DrawingType(canvas.getDrawingType());
	   
	    /* Draw final "permanent" object */
	    shape.draw(graphics);
	    canvas.repaint();   
	
	    /* Add shape to list maintained by the controller */
	    currentShapes.add(shape);
	  }
	/* ------------------------------------------------------- */	
	@Override

	public void deselected() { }
	/* ------------------------------------------------------- */	


}
