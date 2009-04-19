import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.EventListener;


/**
 * ToolController, to handle toolEvents.
 * @author bmhelppi
 *
 */
public class ToolController extends AbstractAction {

  protected DrawingCanvas canvas;
  protected Tool tool;
 
  
  /**
   * Create a toolController.
   * @param name Name of the controller.
   * @param icon Icon of the too.
   * @param tip A tip for when the tool is highlighted.
   * @param c Canvas the tool may be used on.
   * @param t Tool to handle.
   */
  public ToolController(String name, Icon icon, String tip,
		        DrawingCanvas c, Tool t) 
  {
	  super(name, icon);
	  tool = t;
	  putValue(Action.DEFAULT, icon);
	  putValue(Action.SHORT_DESCRIPTION, tip);
	  setEnabled(tool != null);
	  canvas = c;
  } 
  
  /**
   * If something happens to this item, tell the canvas
   * that this is the selected tool.
   */
  public void actionPerformed(ActionEvent e) 
  {
    canvas.setcurrentTool(tool);
  }
  
}
