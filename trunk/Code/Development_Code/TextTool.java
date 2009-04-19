import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * TextTool is an instance of KeyboardTool, capable of creating text objects.
 * @author bmhelppi
 *
 */
public class TextTool implements KeyboardTool {
   
  protected TextFactory textFactory;
  protected Text currentText = null;
  
  
     /**
  	  * Create a new TextTool
  	  * @param f Factory that this tool will use to instantiate objects.
  	  */
  	 public TextTool(TextFactory f) 
  	 {
	   textFactory = f;
	 }

  	 /**
	  * When mousePressed received, create a new text object,
	  * finalizing the previous one if it exists.
	  */
  	  public void mousePressed(Point p, ArrayList<Shape> shapes, DrawingCanvas canvas) {
  		  
		  Graphics graphics = canvas.getimageBufferGraphics();
		  canvas.requestFocus();
		  graphics.setXORMode(Color.lightGray);
		  graphics.setColor(Color.white);
			 
		  finalizeText(canvas);
		  currentText = textFactory.createShape(p.x,p.y, Color.WHITE);
	 }

  	  /**
	   * When a key is typed, update the text object, 
	   * if it exists.
	   */
     public void keyTyped(KeyEvent e, ArrayList<Shape> shapes, DrawingCanvas canvas) 
     {
	   Graphics graphics;
		if(currentText != null)
		{
			graphics = canvas.getimageBufferGraphics();
			/* "Undraw" current shape */
			currentText.draw(graphics);
			currentText.appendChar(e.getKeyChar());
			/* Draw updated shape */ 
			currentText.draw(graphics);
			 
			 canvas.repaint();
		}
   }
     
   /**
    * When the text tool is deselected, if text exists finalize it,
    * and add it to the list of drawable items.
    */
    public void deselected(DrawingCanvas canvas) {
		
	   finalizeText(canvas);
		
	}

    /**
	 * Add a text object to list of objects if it exists, and has data.
	 */
    private void finalizeText(DrawingCanvas canvas)
	{
		
		if(currentText != null &&
				currentText.text.length() > 0)
		{
			
		Graphics graphics = canvas.getimageBufferGraphics();
		
	    /* Save the object's color to match the pen's color */
		currentText.set_MainColor(canvas.getpenColor());
	   
	    /* Draw final "permanent" object */
		currentText.draw(graphics);
	    canvas.repaint();   
	
	    /* Add shape to list maintained by the controller */
	    canvas.session.currentState.currentShapes().add(currentText);
		}
		currentText = null;
	}

    
    /**
	 * Does nothing
	 */
     public void mouseReleased(Point p,ArrayList<Shape> shapes, DrawingCanvas canvas) { }
	 public void mouseDragged(Point p, ArrayList<Shape> shapes, DrawingCanvas canvas) { }
	 public void keyReleased(KeyEvent e, ArrayList<Shape> shapes, DrawingCanvas canvas) { }
	 public void keyPressed(KeyEvent e, ArrayList<Shape> shapes, DrawingCanvas canvas)  { }

}
