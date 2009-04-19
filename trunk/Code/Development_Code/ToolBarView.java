import java.awt.*;
import java.util.*;
import javax.swing.*;
  
/**
 * A Tool bar implementation.
 * @author bmhelppi
 *
 */
 public class ToolBarView extends JToolBar 
 {
  
    /**
     * Create a ToolBar.
     * @param actions List of items on the ToolBar.
     */
	ToolBarView(ToolList actions) 
    {
    	super(VERTICAL);
    	ToolListIterator iter = actions.iterator();
    
    	while(iter.hasNext()) 
    	{
    		Action a = (Action) iter.next();
    		this.add(a);
    	}
    } 
    
} 
  