import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * Implements the TwoPointShapeFactory, to create Rectangle objects.
 * @author satpreet, jjtrapan
 *
 */
public class DynamicTwoPointShapeFactory implements TwoPointShapeFactory, Serializable{

	Class TwoPointShapeClass;
	Constructor[] cstr;

	public DynamicTwoPointShapeFactory(Class TwoPointShapeClass) {
		this.TwoPointShapeClass = TwoPointShapeClass;
		this.cstr = TwoPointShapeClass.getDeclaredConstructors();
	}
	/**
	 * Create a new Rectangle TwoPointShape.
	 */
	public Object createShape
	(int xOne, 
	 int yOne, 
	 int xTwo, 
	 int yTwo,
	 Color c,
	 boolean IsOutline, UUID uniqueId) 
	{
		Object dynamicShape = null;

		
		try {
			dynamicShape = cstr[0].newInstance(xOne, yOne, c, IsOutline, uniqueId);
		} catch (Exception e) {
			e.printStackTrace();
		} 
				
		return dynamicShape;
		
	}

}
