import java.awt.Color;

/**
 * Implements the TwoPointShapeFactory, to create Oval objects.
 * @author jjtrapan
 * @author ben
 *
 */

public class OvalFactory implements TwoPointShapeFactory{

	/**
	 * Create a new Oval from a generic TwoPointShape.
	 */
	public TwoPointShape createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c,
	 boolean IsOutline) 
	{
		Oval newOval = new Oval(xOne, yOne, c, IsOutline);
		return newOval;
		
	}

}
