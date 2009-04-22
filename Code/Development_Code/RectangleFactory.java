import java.awt.Color;

/**
 * Implements the TwoPointShapeFactory, to create Rectangle objects.
 * @author bmhelppi
 *
 */
public class RectangleFactory implements TwoPointShapeFactory{


	/**
	 * Create a new Rectangle TwoPointShape.
	 */
	public TwoPointShape createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c,
	 boolean IsOutline) 
	{
		Rectangle newRect = new Rectangle(xOne, yOne, c, IsOutline);
		return newRect;
		
	}

}
