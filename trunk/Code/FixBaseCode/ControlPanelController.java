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
 
  //JJT ADDED
  public void itemStateChanged(ItemEvent e)  {
    if (e.getStateChange() == ItemEvent.SELECTED){ 
    	   	
    	if(e.getSource().toString().contains("comboColor")) { 
    		canvas.setpenColor(itemToColor(e.getItem()));   
    	}
    	else if(e.getSource().toString().contains("comboType")) {
    		canvas.setShapeType(itemToType(e.getItem()));
    	}
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
  
  protected int itemToType(Object item) {
  
	/* By default, we always return 1 (draw outline) */     
	if("solid".equals(item)) {
	  return 2;	
	}
	else {
	  return 1;	
	}
	
	
  }
  /* ------------------------------------------------------- */
  
  
	  
} 
