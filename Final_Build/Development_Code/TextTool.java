import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * TextTool is an instance of KeyboardTool, capable of creating text objects.
 * @author bmhelppi
 *
 */
public class TextTool implements KeyboardTool {
   
	private String name;
	protected TextFactory textFactory;
	protected Text currentText = null;
  
  
     /**
  	  * Create a new TextTool
  	  * @param f Factory that this tool will use to instantiate objects.
  	  */
  	 public TextTool(TextFactory f, String name) 
  	 {
  		 this.name = name;
  		 textFactory = f;
	 }

  	 /**
	  * When mousePressed received, create a new text object,
	  * finalizing the previous one if it exists.
	  */
  	  public void mousePressed(Point p, 
  			  ArrayList<Shape> shapes, 
  			  DrawingCanvas canvas, 
  			  boolean fill,
  			  UUID uniqueId) {
  		  
		  Graphics graphics = canvas.getimageBufferGraphics();
		  canvas.requestFocus();
		  graphics.setXORMode(Color.lightGray);
		  graphics.setColor(Color.white);
			 
		  finalizeText(canvas);
		  currentText = textFactory.createShape(p.x,p.y, Color.WHITE, uniqueId);
	 }

  	  /**
	   * When a key is typed, update the text object, 
	   * if it exists.
	   */
     public void keyTyped(char keyPressed, ArrayList<Shape> shapes, DrawingCanvas canvas) 
     {
	   Graphics graphics;
		if(currentText != null)
		{
			graphics = canvas.getimageBufferGraphics();
			/* "Undraw" current shape */
			currentText.draw(graphics);
			currentText.appendChar(keyPressed);
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

    public String toolName() 
    {
    	return this.name;
	}
    
    
    /**
	 * Does nothing
	 */
     public void mouseReleased(Point point,ArrayList<Shape> shapes, DrawingCanvas canvas,Color finalColor, boolean filled) { }
	 public void mouseDragged(Point p, ArrayList<Shape> shapes, DrawingCanvas canvas) { }
	 public void keyReleased(char keyPress, ArrayList<Shape> shapes, DrawingCanvas canvas) { }
	 public void keyPressed(char keyPressed, ArrayList<Shape> shapes, DrawingCanvas canvas)  { }

	

}
