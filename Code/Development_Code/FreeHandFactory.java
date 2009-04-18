import java.awt.Color;
import java.io.Serializable;

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
	 * @return Returns an instantiated shape.
	 */
  public FreeHandObject createFreeHand(int x, int y, Color c){
		FreeHandObject o = new FreeHandObject(x,y,c);
		return o;
  }
}
