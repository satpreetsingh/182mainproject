import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
/**
 * This class implements a Text object,
 * drawing text where it is asked.
 * @author bmhelppi
 *
 */
public class Text extends Shape {

	protected StringBuffer text;
	private Font font;
	
	
	/**
	 * Create a new text object.
	 * @param x X location
	 * @param y Y location
	 * @param c Initial color.
	 * @param f Font to use.
	 * @param t Initial textSize.
	 */
	public Text (int x, int y, Color c, Font f) 
	{
		
		this.origin = new Point2D.Double(x,y);
		shapeColor = c;
		font = f;
		text = new StringBuffer();
	}

	
	/**
	 * Add another character to this text string.
	 * @param c Character to add.
	 */
	public void appendChar(char c)
	{
		text.append(c);
	}

	
	/**
	 * Assign a value to this textString, replacing the prior value.
	 * @param s String to use.
	 */
	public void setText(String s)
	{
		text.delete(0, text.length());
		text.append(s);
		
	}

	/**
	 * Draw the text string.
	 */
	public void draw(Graphics g) {
		g.setColor(shapeColor);
		g.drawString(text.toString(), (int)this.origin.x, (int)this.origin.y);
	}

	
	/** 
	 * Do not draw any resize boxes for this shape. 
	 */
	public void drawHighlightBoxes(Graphics g) 
	{
	  // Do nothing.	
	}

	boolean exterior(int x, int y) 
	{
		//TODO: Improve algorithm
		if((Math.abs(this.origin.x - x) < this.nearTol) &&
			(Math.abs(this.origin.y - y) < this.nearTol))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	boolean interior(int x, int y) 
	{
		//TODO: Improve.
		if((x > this.origin.x) &&
			(Math.abs(this.origin.y -y) < this.nearTol))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	void move(int x, int y) 
	{
		 this.origin.setLocation(this.origin.x + x, this.origin.y + y);	
	}

	void resize(Double point, int deltaX, int deltaY) 
	{
		int delta;
		if((deltaX + deltaY) > 0)
		{
			delta = 1;
		}
		else
		{
			delta = -1;
		}
		Font f = new Font(font.getFontName(), font.getStyle(), 30); //font.getSize() + delta);
		font = f;
	}
	
	
	Double pickAnchor(Double p) 
	{
		return this.origin;
	}

}
