import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MenuBarView extends JMenuBar {
  
  MenuBarView(ToolList actions) {
    JMenu toolMenu = new JMenu("Tool");
    ToolListIterator iter = actions.iterator();

    while(iter.hasNext()) {
      Action a = (Action) iter.next();
      toolMenu.add(a);
    }
    
    add(toolMenu);
  
  }
  
} 

