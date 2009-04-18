import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Implements an erasing tool, which will delete objects drawn on the canvas.
 * @author bmhelppi, jjtrapan
 *
 */

/* DO NOT rename this class, DrawingCanvas.java depends upon it (line 99) */
public class EraserTool implements Tool {
   

  /**
   * Create a new instance of the Eraser tool.
   */
  public EraserTool() {}

  
  /**
	* Dragging mouse does nothing for this class.
   */
  public void mouseDragged(Point p, ArrayList<Shape> currentShapes,DrawingCanvas canvas) {}

  
  /**
   * When mouse pressed, check list of shapes, and if one
   * is found, delete it, and redraw the canvas.
   */
  public void mousePressed(Point p, ArrayList<Shape> currentShapes, DrawingCanvas canvas) {
    Shape shape;
    for (int i = 0; i < currentShapes.size(); i++) 
    {
      shape = currentShapes.get(i);
      if (shape.near(p.x, p.y)) 
      {
        currentShapes.remove(i);
        i = currentShapes.size();
        canvas.refresh();
      }
    }
  }
  
  
  /**
   * This event does nothing for this tool.
   */
  public void mouseReleased(Point p, ArrayList<Shape> currentShapes,DrawingCanvas canvas) {}

  
  /**
   * This event does nothing for this tool.
   */
  public void deselected(DrawingCanvas canvas) {}
	
}
