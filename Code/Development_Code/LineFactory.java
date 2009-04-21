import java.awt.Color;
import java.io.Serializable;

/**
 * Implements the TwoPointShapeFactory, to create Line objects.
 * @author bmhelppi
 *
 */
public class LineFactory implements TwoPointShapeFactory, Serializable{

	/**
	 * Create a new Line, from the generic TwoPointShape.
	 */
	public TwoPointShape createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c,
	 int type) 
	{

		Line newLine = new Line(xOne, yOne, c, type);
		newLine.setSecondPoint(xTwo, yTwo);
		
		return newLine;
		
	}
	
}
