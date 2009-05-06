import java.awt.Color;
import java.io.Serializable;
import java.util.UUID;

/**
 * A class for creating FreeHandObject shapes.
 * @author ben
 *
 */
public class FreeHandFactory implements Serializable {

	/**
	 * Create a new FreeHandObject shape.
	 * @param x X coordinate of new shape.
	 * @param y Y coordinate of new shape.
	 * @param c Color of new shape.
	 * @param uniqueId TODO
	 * @return Returns an instantiated shape.
	 */
  public FreeHandObject createFreeHand(int x, int y, Color c, boolean IsOutline, UUID uniqueId){
		FreeHandObject o = new FreeHandObject(x,y,c,IsOutline, uniqueId);
		return o;
  }
}
