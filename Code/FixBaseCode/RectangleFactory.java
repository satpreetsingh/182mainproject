import java.awt.Color;

/**
 * Implements the TwoPointShapeFactory, to create Rectangle objects.
 * @author bmhelppi
 *
 */
public class RectangleFactory implements TwoPointShapeFactory{

	/* ------------------------------------------------------- */
	@Override

	public TwoPointShape createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c,
	 int type) 
	{

		Rectangle newRect = new Rectangle(xOne, yOne, c, type);
		
		return newRect;
		
	}
  /* ------------------------------------------------------- */

}
