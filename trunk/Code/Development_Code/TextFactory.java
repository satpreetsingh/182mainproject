import java.awt.Color;
import java.awt.Font;

/**
 * Implements a textFactory for creating text objects.
 * @author ben
 *
 */
public class TextFactory {

	protected Font defaultFont = new Font("Serif", Font.BOLD, 24);
	
	
	/**
	 * Create a default text object.
	 * @param x X position to create at.
	 * @param y Y position to create at.
	 * @param c Color of text.
	 * @return Returns a text shape.
	 */
	public Text createShape
	(int x,
	 int y,
	 Color c)
	{
		Text textShape = new Text(x,y,c,defaultFont);
		return textShape;
	}
	
	/**
	 * Create a default text object.
	 * @param x X position to create at.
	 * @param y Y position to create at.
	 * @param c Color of text.
	 * @param f Font of text.
	 * @return Returns a text shape.
	 */
	public Text createShape
	(int x,
	 int y,
	 Color c, 
	 Font f)
	{
		Text textShape = new Text(x, y, c, f);
		return textShape;
	}
	
}
