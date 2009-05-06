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
	ToolBarView(ArrayList<ToolController> actions) 
    {
    	super(VERTICAL);
    	for(int i = 0; i < actions.size(); i++)
    	{
    		Action a = (Action)actions.get(i);
    		this.add(a);
    	}
    } 
    
} 
  
