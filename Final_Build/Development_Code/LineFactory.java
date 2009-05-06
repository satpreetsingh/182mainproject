import java.awt.Color;
import java.io.Serializable;
import java.util.UUID;

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
	 boolean IsOutline, UUID uniqueId) 
	{

		Line newLine = new Line(xOne, yOne, c, IsOutline, uniqueId);
		newLine.setSecondPoint(xTwo, yTwo);
		
		return newLine;
		
	}
	
}
