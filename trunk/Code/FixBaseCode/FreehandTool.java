import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FreehandTool implements Tool {
   
  protected DrawingCanvas canvas;
  protected Point startingMousePosition;
  protected FreeHandFactory factory;
  protected FreeHandObject freeHand;


  /* ------------------------------------------------------- */
  /* Constructor */
  
  public FreehandTool(DrawingCanvas c, FreeHandFactory f) {
	canvas = c;
    factory = f;
  }
  /* ------------------------------------------------------- */

  public void mousePressed(MouseEvent e, ArrayList<Shape> currentShapes)  {
		 
	freeHand = factory.createFreeHand(e.getX(), e.getY(), Color.WHITE, canvas.getDrawingType());
		 
	Graphics graphics = canvas.getimageBufferGraphics();
	graphics.setXORMode(Color.lightGray);
	graphics.setColor(Color.white);
				 
	freeHand.draw(graphics); 
    canvas.repaint();
  }
  /* ------------------------------------------------------- */	
	 
  public void mouseDragged(MouseEvent e, ArrayList<Shape> currentShapes)  {
		
	Graphics graphics = canvas.getimageBufferGraphics();
			
	/* erase previous temporary figure by redrawing it */
	freeHand.draw(graphics);
		 
	freeHand.appendSegment(e.getPoint());
    freeHand.draw(graphics);
		
    canvas.repaint();
		 
  }
  /* ------------------------------------------------------- */

  public void mouseReleased(MouseEvent e, ArrayList<Shape> currentShapes) {
	  
	   Graphics graphics = canvas.getimageBufferGraphics();
		
	    /* Save the object's color to match the pen's color */
	   freeHand.set_MainColor(canvas.getpenColor());
	   
	    /* Draw final "permanent" object */
	   freeHand.draw(graphics);
	    canvas.repaint();   
	
	    /* Add shape to list maintained by the controller */
	    currentShapes.add(freeHand);
	    
	    
   }
   /* ------------------------------------------------------- */

  @Override
  public void deselected() {
		
  }
  /* ------------------------------------------------------- */
}
