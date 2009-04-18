import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * Implements a MenuBar.
 * @author ben
 *
 */
public class MenuBarView extends JMenuBar 
{
  
	/**
	 * Create a new MenuBar.
	 * @param actions Actions the Menubar can perform.
	 */
	MenuBarView(ToolList actions) 
	{
		JMenu toolMenu = new JMenu("Tool");
		ToolListIterator iter = actions.iterator();

		while(iter.hasNext()) 
		{
			Action a = (Action) iter.next();
			toolMenu.add(a);
		}	
    
		add(toolMenu);
  
	}
  
} 

