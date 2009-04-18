import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FreehandTool implements Tool {
   
  protected Point startingMousePosition;
  protected FreeHandFactory factory;
  protected FreeHandObject freeHand;


  /**
   * Create a new FreeHandTool
   * @param c DrawingCanvas tool will interact with.
   * @param f Factory for creating new drawable objects.
   */
  public FreehandTool(DrawingCanvas c, FreeHandFactory f) {
    factory = f;
  }

  public void mousePressed(Point p, ArrayList<Shape> currentShapes, DrawingCanvas canvas)  
  {
		 
	freeHand = factory.createFreeHand(p.x, p.y, Color.WHITE);
		 
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

  public void mouseReleased(Point p, ArrayList<Shape> currentShapes, DrawingCanvas canvas) 
  {
	  
	   Graphics graphics = canvas.getimageBufferGraphics();
		
	    /* Save the object's color to match the pen's color */
	   freeHand.set_MainColor(canvas.getpenColor());
	   
	    /* Draw final "permanent" object */
	   freeHand.draw(graphics);
	    canvas.repaint();   
	
	    /* Add shape to list maintained by the controller */
	    currentShapes.add(freeHand);
	    
	    
   }

  public void deselected(DrawingCanvas canvas) {
		
  }
}
