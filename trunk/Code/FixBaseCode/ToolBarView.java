import java.awt.*;
import java.util.*;
import javax.swing.*;
  
  public class ToolBarView extends JToolBar {
  
    ToolBarView(ToolList actions) {
    super(VERTICAL);
    ToolListIterator iter = actions.iterator();
    
    while(iter.hasNext()) {
      Action a = (Action) iter.next();
      this.add(a);
    }
  } 
    
} 
  