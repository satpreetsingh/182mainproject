import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.EventListener;

public class ToolController extends AbstractAction {

  protected DrawingCanvas canvas;
  protected Tool tool;
 
  /* ------------------------------------------------------- */	  
  
  public ToolController(String name, Icon icon, String tip,
		        DrawingCanvas c, Tool t) {
    super(name, icon);
    tool = t;
    putValue(Action.DEFAULT, icon);
    putValue(Action.SHORT_DESCRIPTION, tip);
    setEnabled(tool != null);
    canvas = c;
    
  } 
  /* ------------------------------------------------------- */	
  
  public void actionPerformed(ActionEvent e) {
    canvas.setcurrentTool(tool);
  }
  /* ------------------------------------------------------- */	
  
}
