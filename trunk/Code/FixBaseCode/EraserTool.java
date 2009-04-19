import java.awt.event.*;
import java.util.ArrayList;

/**
 * Implements an erasing tool, which will delete objects drawn on the canvas.
 * @author bmhelppi, jjtrapan
 *
 */

/* DO NOT rename this class, DrawingCanvas.java depends upon it (line 99) */
public class EraserTool implements Tool {
   
  protected DrawingCanvas canvas;

  /* ------------------------------------------------------- */
  /**
   * Create a new instance of the drawing tool.
   * @param c Canvas tool will interact with.
   */
  
  public EraserTool(DrawingCanvas c) {
   canvas = c;
   
  }
  /* ------------------------------------------------------- */  
  /**
	* Dragging mouse does nothing for this class.
   */
  public void mouseDragged(MouseEvent e, ArrayList<Shape> currentShapes) {
  }
  /* ------------------------------------------------------- */
  /**
   * When mouse pressed, check list of shapes, and if one
   * is found, delete it, and redraw the canvas.
   */
  public void mousePressed(MouseEvent e, ArrayList<Shape> currentShapes) {
    Shape shape;
    for (int i =0; i < currentShapes.size(); i++) {
      shape = currentShapes.get(i);
      if (shape.near(e.getX(), e.getY())) {
        currentShapes.remove(i);
        i = currentShapes.size();
        canvas.refresh();
      }
    }
  }
  /* ------------------------------------------------------- */
  /**
   * This event does nothing for this tool.
   */
  public void mouseReleased(MouseEvent e, ArrayList<Shape> currentShapes) {
  }
  /* ------------------------------------------------------- */	
  /**
   * This event does nothing for this tool.
   */
  public void deselected() {
  }
  /* ------------------------------------------------------- */
	
}
