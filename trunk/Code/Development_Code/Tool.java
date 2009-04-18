import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create a generic interface for tools.
 * 
 * @author bmhelppi
 *
 */
public interface Tool extends Serializable {
  
	/**
	 * Any instance of the Tool interface must handle mousePressed events.
	 * @param p Point where mousePress occurred.
	 * @param currentShapes ArrayList of drawable shapes in the current context.
	 * @param canvas Canvas to draw on.
	 */
  void mousePressed(Point p, ArrayList<Shape> currentShapes, DrawingCanvas canvas);
 
  /**
   * Any instance of the Tool interface must handle mouseReleased events.
   * @param p Point where the event occurred.
   * @param currentShapes ArrayList of drawable shapes in the current context.
   * @param canvas Canvas to draw on.
   */
  void mouseReleased(Point p, ArrayList<Shape> currentShapes, DrawingCanvas canvas);
  
  /**
   * Any instance of the Tool interface must handle mouseDragged events.
   * @param P Point where the event occurred.
   * @param currentShapes ArrayList of drawable shapes in the current context.
   * @param canvas Canvas to draw on.
   */
  void mouseDragged(Point p, ArrayList<Shape> currentShapes, DrawingCanvas canvas);
  
  /**
   * Tool is no longer selected, any tools that need to finalize objects,
   * or do other handling should do so implementing this function.
   */
  void deselected(DrawingCanvas canvas);
  
}
