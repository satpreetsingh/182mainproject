import java.io.Serializable;
import java.util.ArrayList;


/**
 * Manages the DrawState for a Session.
 * @author bmhelppi, jjtrapan
 *
 */
public class DrawState implements Serializable{

	private ArrayList <Object> currentShapes;
	private Object lastSelected;
	
	/**
	 * Create a new instance of DrawState.
	 */
	public DrawState ()
	{
		currentShapes = new ArrayList <Object>();
	}
	
	/**
	 * Provide the currentList of shapes.
	 * @return Returns currentShapes.
	 */
	public ArrayList<Object> currentShapes()
	{
		return currentShapes;
	}
	
	/**
	 * Set the value of the last selected shape.
	 * s must be a shape currently in this class.
	 * @param s Object to select.
	 */
	public void setLastSelected(Object s)
	{
		for(int i = 0; i < this.currentShapes.size(); i++)
		{
			if(s == this.currentShapes.get(i))
			{
				this.lastSelected = s;
			}
		}
		
	}
	
	/**
	 * Return the last selected object.
	 * @return Returns last selected Object.
	 */
	public Object lastSelected()
	{
		return this.lastSelected;
	}
	
	/**
	 * Clear the last selected object.
	 */
	public void clearLastSelectedObject()
	{
		this.lastSelected = null;
	}
	
}
