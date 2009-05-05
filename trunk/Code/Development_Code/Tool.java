import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Create a generic interface for tools.
 * 
 * @author bmhelppi
 *
 */
public interface Tool extends Serializable {
  
	/**
	 * Get the name of this tool.
	 * @return Returns a string with this tools name.
	 */
	public String toolName();
	
	/**
	 * Any instance of the Tool interface must handle mousePressed events.
	 * @param p Point where mousePress occurred.
	 * @param currentShapes ArrayList of drawable shapes in the current context.
	 * @param canvas Canvas to draw on.
	 * @param fill TODO
	 */
	public void mousePressed(Point p,
			ArrayList<Object> currentShapes, 
			DrawingCanvas canvas, 
			boolean fill,
			UUID uniqueId);
 
	/**
     * Any instance of the Tool interface must handle mouseReleased events.
     * @param point Point where the event occurred.
	 * @param currentShapes ArrayList of drawable shapes in the current context.
	 * @param canvas Canvas to draw on.
	 * @param finalColor The color the shape should be.
	 * @param filled TODO
     */
	public void mouseReleased(Point point, ArrayList<Object> currentShapes, DrawingCanvas canvas, Color finalColor, boolean filled);
  
	/**
     * Any instance of the Tool interface must handle mouseDragged events.
     * @param P Point where the event occurred.
     * @param currentShapes ArrayList of drawable shapes in the current context.
     * @param canvas Canvas to draw on.
     */
	public void mouseDragged(Point p, ArrayList<Object> currentShapes, DrawingCanvas canvas);
  
	/**
     * Tool is no longer selected, any tools that need to finalize objects,
    * or do other handling should do so implementing this function.
     */
	public void deselected(DrawingCanvas canvas);
  
}
