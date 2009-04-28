import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Tool for manipulating FreeHandObjects
 * @author ben
 *
 */
public class FreehandTool implements Tool {
   
	private String name;
	protected Point startingMousePosition;
	protected FreeHandFactory factory;
	protected FreeHandObject freeHand;


  /**
   * Create a new FreeHandTool
   * @param c DrawingCanvas tool will interact with.
   * @param f Factory for creating new drawable objects.
   */
  public FreehandTool(DrawingCanvas c, FreeHandFactory f, String name) {
	  factory = f;
	  this.name = name;
  }

  
  public void mousePressed(Point p,
		  ArrayList<Shape> currentShapes,
DrawingCanvas canvas, 
boolean fill,
UUID uniqueId)  
  {
	freeHand = factory.createFreeHand(p.x, 
									  p.y, 
									  Color.WHITE,
									  canvas.getDrawingType(), 
									  uniqueId);
		 
	Graphics graphics = canvas.getimageBufferGraphics();
	graphics.setXORMode(Color.lightGray);
	graphics.setColor(Color.white);
				 
	freeHand.draw(graphics); 
    canvas.repaint();
  }
	 
  public void mouseDragged(Point p, ArrayList<Shape> currentShapes, DrawingCanvas canvas)  
  {
		
	Graphics graphics = canvas.getimageBufferGraphics();
			
	/* erase previous temporary figure by redrawing it */
	freeHand.draw(graphics);
		 
	freeHand.appendSegment(p);
    freeHand.draw(graphics);
    canvas.repaint();
		 
  }

  public void mouseReleased(Point point, ArrayList<Shape> currentShapes, DrawingCanvas canvas,Color finalColor, boolean filled) 
  {
	   Graphics graphics = canvas.getimageBufferGraphics();
		
	    /* Save the object's color to match the pen's color */
	   freeHand.set_MainColor(finalColor);
	   
	    /* Draw final "permanent" object */
	   freeHand.draw(graphics);
	    canvas.repaint();   
	
	    /* Add shape to list maintained by the controller */
	    currentShapes.add(freeHand);
	    
	    
   }

  public void deselected(DrawingCanvas canvas) {}


  public String toolName() 
  {
	  return this.name;
  }
  
}
