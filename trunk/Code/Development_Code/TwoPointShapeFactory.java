import java.awt.*;
import java.io.Serializable;

/**
 * An interface defining a class for creating TwoPointShape objects.
 * @author bmhelppi
 *
 */
interface TwoPointShapeFactory extends Serializable{

	/**
	 * Creates an instance of a TwoPointShape.
	 * @param xOne X position.
	 * @param yOne Y position.
	 * @param xTwo Second x position.
	 * @param yTwo Second y position.
	 * @param c C base color.	 
	 * @param type int object type (outline,solid).
	 * @return Returns an instantiated TwoPointShape.
	 */
	public TwoPointShape createShape
	(int xOne,
	 int yOne,
	 int xTwo,
	 int yTwo,
	 Color c, 
	 int type);

}
