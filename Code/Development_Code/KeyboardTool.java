import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * A generic interface for tools that use the keyboard to interface w/ the canvas.
 * @author bmhelppi
 *
 */
public interface KeyboardTool extends Tool{

	
	/**
	 * Accept a keyPressed event.
	 * @param e The event.
	 * @param shapes Current list of shapes on the canvas.
	 */
  void keyPressed(KeyEvent e, ArrayList<Shape> shapes, DrawingCanvas canvas);
  
  /**
   * Accept a keyReleased event
   * @param e The event.
   * @param shapes Current list of shapes on the canvas.
   */
  void keyReleased(KeyEvent e, ArrayList<Shape> shapes, DrawingCanvas canvas);
  
  /**
   * Accept a keyTyped event.
   * TODO: Is this like a mouse click, compared to press?
   * @param e
   * @param shapes Current list of shapes on the canvas.
   */
  void keyTyped(KeyEvent e, ArrayList<Shape> shapes, DrawingCanvas canvas);
}
