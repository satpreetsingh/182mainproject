import java.awt.*;
import java.awt.event.*;

public class ControlPanelController
	implements ActionListener, ItemListener {

  protected DrawingCanvas canvas;
  
  /* ------------------------------------------------------- */
  /* Constructor */
  
  ControlPanelController(DrawingCanvas c) {
    canvas = c;
  }
  /* ------------------------------------------------------- */
  
  public void actionPerformed(ActionEvent e) {
    canvas.clearCanvas();
  }
  /* ------------------------------------------------------- */
  
  public void itemStateChanged(ItemEvent e)  {
    if (e.getStateChange() == ItemEvent.SELECTED) {
      canvas.setpenColor(itemToColor(e.getItem()));
    }
  }
  /* ------------------------------------------------------- */
  
  protected Color itemToColor(Object item) {
    
    if("black".equals(item)) {
      return Color.black;
    }
    else if("blue".equals(item)) {
          return Color.blue;
         }
         else if("green".equals(item)) {
               return Color.green;
              }
              else {
                return Color.red;
              }
  }
  /* ------------------------------------------------------- */
  
} 
