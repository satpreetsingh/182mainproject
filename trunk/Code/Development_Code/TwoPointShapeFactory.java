import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

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
	 * @param uniqueId TODO
	 * @param type int object type (outline,solid).
	 * @return Returns an instantiated TwoPointShape.
	 */
	public Object createShape
	(int xOne,
	 int yOne,
	 int xTwo,
	 int yTwo,
	 Color c, 
	 boolean IsOutline, UUID uniqueId);

}
