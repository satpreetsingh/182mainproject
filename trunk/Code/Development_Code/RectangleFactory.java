import java.awt.Color;
import java.io.Serializable;
import java.util.UUID;

/**
 * Implements the TwoPointShapeFactory, to create Rectangle objects.
 * @author bmhelppi
 *
 */
public class RectangleFactory implements TwoPointShapeFactory, Serializable{


	/**
	 * Create a new Rectangle TwoPointShape.
	 */
	public TwoPointShape createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c,
	 boolean IsOutline, UUID uniqueId) 
	{
		Rectangle newRect = new Rectangle(xOne, yOne, c, IsOutline, uniqueId);
		return newRect;
		
	}

}
