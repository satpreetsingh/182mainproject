import java.awt.Color;

/**
 * Implements the TwoPointShapeFactory, to create Oval objects.
 * @author jjtrapan
 *
 */

public class OvalFactory implements TwoPointShapeFactory{

	@Override
	public TwoPointShape createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c) 
	{

		Oval newOval = new Oval(xOne, yOne, c);
		
		return newOval;
		
	}

}
