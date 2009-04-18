import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

/**
 * The Rectangle class implements methods necessary
 * to manipulate lines, extending the TwoPointShape abstract class.
 * @author jjtrapan
 *
 */

public class Oval extends TwoPointShape
{
	
	    /* ------------------------------------------------------- */
		/**
		 * Create a new instance of a oval.
		 * @param x
		 * @param y
		 * @param c
		 */
		
	    public Oval(int x, int y, Color c) {
			super(x, y, c);
		}
	    /* ------------------------------------------------------- */
		/**
		 * Draws a Oval object.
		 */
		
		void draw(Graphics g) {
	    int shapeX;
	    int shapeY;
	    int shapeWidth;
	    int shapeHeight;
	    
	    if (this.origin.x <= this.end.x) {
	      shapeX = (int)this.origin.x;
	      shapeWidth = (int)(this.end.x-this.origin.x)+1;
	    }
	    else {
	      shapeX = (int)this.end.x;
	      shapeWidth = (int)(this.origin.x-this.end.x)+1;
	    }
	    
	    if (this.origin.y <= this.end.y) {
	      shapeY = (int)this.origin.y;
	      shapeHeight = (int)(this.end.y-this.origin.y)+1;
	    }
	    else {
	      shapeY = (int)this.end.y;
	      shapeHeight = (int)(this.origin.y-this.end.y)+1;
	    }
	    
	   		g.setColor(shapeColor);
			g.drawOval (
					shapeX, 
					shapeY, 
					shapeWidth, 
					shapeHeight);
		}
		/* ------------------------------------------------------- */
		/** 
		 * Draw four resize boxes for this shape. 
		 */
		
		void drawHighlightBoxes(Graphics g) {
			
			/* Set the color for the drag resize boxes */
			g.setColor(Color.black);
			
			/* Draw a small rectangle at each corner */
			g.fillRect ((int)this.origin.x - 5, (int)this.origin.y - 5, 10, 10);
			g.fillRect ((int)this.end.x - 5, (int)this.end.y - 5, 10, 10);			
			g.fillRect ((int)this.origin.x - 5, (int)this.end.y - 5, 10, 10);
			g.fillRect ((int)this.end.x - 5, (int)this.origin.y - 5, 10, 10);
			
		}		
		/* ------------------------------------------------------- */
		/**
		 * Determine if the interior area of a Oval is selected.
		 */
		
		boolean interior(int x, int y)
		{
			boolean finalResult;
			
			finalResult = (ShapeMath.bracket(this.origin.x, this.end.x, x, 0) &&
					       ShapeMath.bracket(this.origin.y, this.end.y, y, 0));
				
			return finalResult;
		}
    	/* ------------------------------------------------------- */
		/**
		 * Determine if the exterior area of a Oval is selected.
		 */

		boolean exterior(int x, int y)
		{
			boolean result = false;
			if (ShapeMath.bracket(this.origin.x, this.end.x, x, nearTol))
			{
				/**
				 * This point is somewhere close on the x axis, make sure its close to an edge on y axis.
				 */
				if((Math.abs(y- (this.end.y)) < nearTol) ||
					(Math.abs(y- (this.origin.y)) < nearTol))
				{
					result = true;
				}
			}
			
			return result;
		
		}
		/* ------------------------------------------------------- */	

}