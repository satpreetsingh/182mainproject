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
	MenuBarView(ArrayList<ToolController> actions) 
	{
		JMenu toolMenu = new JMenu("Tool");
		
		for(int i = 0; i < actions.size(); i++)
		{
			Action a = (Action) actions.get(i);
			toolMenu.add(a);
		}
    
		add(toolMenu);
  
	}
  
} 

