import java.awt.Color;
import java.io.Serializable;

/**
 * Implements the TwoPointShapeFactory, to create Line objects.
 * @author bmhelppi
 *
 */
public class LineFactory implements TwoPointShapeFactory, Serializable{

	public TwoPointShape createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c) 
	{

		Line newLine = new Line(xOne, yOne, c);
		newLine.setSecondPoint(xTwo, yTwo);
		
		return newLine;
		
	}
	
}
