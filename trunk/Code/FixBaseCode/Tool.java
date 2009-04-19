import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Create a generic interface for tools.
 * 
 * @author bmhelppi
 *
 */
public interface Tool {
  
	/**
	 * Any instance of the Tool interface must handle mousePressed events.
	 * @param e The event.
	 * @param currentShapes ArrayList of drawable shapes in the current context.
	 */
  void mousePressed(MouseEvent e, ArrayList<Shape> currentShapes);
 
  /**
   * Any instance of the Tool interface must handle mouseReleased events.
   * @param e The event.
   * @param currentShapes ArrayList of drawable shapes in the current context.
   */
  void mouseReleased(MouseEvent e, ArrayList<Shape> currentShapes);
  
  /**
   * Any instance of the Tool interface must handle mouseDragged events.
   * @param e The event.
   * @param currentShapes ArrayList of drawable shapes in the current context.
   */
  void mouseDragged(MouseEvent e, ArrayList<Shape> currentShapes);
  
  /**
   * Tool is no longer selected, any tools that need to finalize objects,
   * or do other handling should do so implementing this function.
   */
  void deselected();
  
}
