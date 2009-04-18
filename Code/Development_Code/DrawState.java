import java.io.Serializable;
import java.util.ArrayList;


/**
 * Manages the DrawState for a Session.
 * @author bmhelppi
 *
 */
public class DrawState implements Serializable{

	private ArrayList <Shape> currentShapes;
	private Shape lastSelected;
	
	/**
	 * Create a new instance of DrawState.
	 */
	public DrawState ()
	{
		currentShapes = new ArrayList <Shape>();
	}
	
	/**
	 * Provide the currentList of shapes.
	 * @return Returns currentShapes.
	 */
	public ArrayList<Shape> currentShapes()
	{
		return currentShapes;
	}
	
	/**
	 * Set the value of the last selected shape.
	 * s must be a shape currently in this class.
	 * @param s Shape to select.
	 */
	public void setLastSelected(Shape s)
	{
		for(int i = 0; i < this.currentShapes.size(); i++)
		{
			if(s == this.lastSelected)
			{
				this.lastSelected = s;
			}
		}
		
	}
	
	/**
	 * Return the last selected shape.
	 * @return Returns last selected shape.
	 */
	public Shape lastSelected()
	{
		return this.lastSelected;
	}
	
	public void clearLastSelectedObject()
	{
		this.lastSelected = null;
	}
	
}
