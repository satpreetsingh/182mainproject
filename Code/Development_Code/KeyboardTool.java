import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * A generic interface for tools that use the keyboard to interface w/ the canvas.
 * @author bmhelppi
 *
 */

import java.io.Serializable;


public interface KeyboardTool extends Tool, Serializable
{

	
	/**
	 * Accept a keyPressed event.
	 * @param keyPressed The key.
	 * @param shapes Current list of shapes on the canvas.
	 */
  void keyPressed(char keyPressed, ArrayList<Shape> shapes, DrawingCanvas canvas);
  
  /**
   * Accept a keyReleased event
   * @param keyPress The key.
   * @param shapes Current list of shapes on the canvas.
   */
  void keyReleased(char keyPress, ArrayList<Shape> shapes, DrawingCanvas canvas);
  
  /**
   * Accept a keyTyped event.
   * @param keyPressed The key
   * @param shapes Current list of shapes on the canvas.
   */
  void keyTyped(char keyPressed, ArrayList<Shape> shapes, DrawingCanvas canvas);
  
}
