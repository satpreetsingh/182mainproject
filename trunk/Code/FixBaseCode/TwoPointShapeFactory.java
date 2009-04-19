import java.awt.*;

/**
 * An interface defining a class for creating TwoPointShape objects.
 * @author bmhelppi
 *
 */
interface TwoPointShapeFactory {

	/**
	 * Creates an instance of a TwoPointShape.
	 * @param xOne X position.
	 * @param yOne Y position.
	 * @param xTwo Second x position.
	 * @param yTwo Second y position.
	 * @param c C base color.
	 * @param int type determines how the object will be drawn.
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
