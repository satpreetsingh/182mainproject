import java.awt.Color;
import java.io.Serializable;
import java.util.UUID;

/**
 * Implements the TwoPointShapeFactory, to create Rectangle objects.
 * @author bmhelppi
 *
 */
public class DynamicTwoPointShapeFactory implements TwoPointShapeFactory, Serializable{


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
//		TwoPointShape dynamicShape = new TwoPointShape(xOne, yOne, c, IsOutline, uniqueId);
//		// TODO This needs a different constructor to be called 
//		return dynamicShape;
		
	}

}
