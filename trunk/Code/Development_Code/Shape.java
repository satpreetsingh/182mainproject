import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Abstract interface for all Shapes 
 * that may be manipulated by MultiDraw.
 * @author bmhelppi, jjtrapan
 *
 */
public abstract class Shape extends Object implements Serializable
{
	  /* Temp, define a common tolerance for nearness, or other such operations */
	  int nearTol = 10;
	  double floatTol = 0.0001;
	  int ListPos;
	  Point2D.Double origin;
	  Color shapeColor;
	
	
	  /**
		 * Requires a class implementing this class
		 * to define how to draw itself.
		 * @param g Graphics context for drawing.
		 */
	  abstract void draw(Graphics g);
	  
	  /**
	   * This class defines how each object's
	   * resize boxes will be drawn,
	   */
	  abstract void drawHighlightBoxes(Graphics g);
	  	  
	  /**
	   * Indicate if an event at this position
	   * would be considered inside this shape.
	   * @param x X co-ordinate.
	   * @param y Y co-ordinate.
	   * @return Returns true if event is 'inside' the shape.
	   */
		abstract boolean interior(int x, int y);
		
		/**
		 * Indicate if an event at this position
		 * would be considered at the edges of the shape.
		 * @param x X co-ordinate.
		 * @param y Y co-ordinate.
		 * @return Returns true if the x,y values are at the 
		 * edge of this shape.
		 */
		abstract boolean exterior(int x, int y);
		
		/**
		 * Combines interior/exterior methods to indicate
		 * if a position is near the object.
		 * @param x X position
		 * @param y Y Position
		 * @return Returns true if interior/exterior returns true,
		 * false otherwise.
		 */
		boolean near(int x, int y)
		{
			return (interior(x,y) || exterior(x,y));
		}

		/**
		 * Set base position of this object.
		 * @param (int) d  X position (start).
		 * @param e  Y position (start).
		 * @param f X position (end).
		 * @param g Y position (end).
		 */
		void set_Position(double d, double e) {
			origin.x = (int) d;
			origin.y = (int)e;
	    }

		/**
		 * Set the primary color of the shape.
		 * @param c
		 */
		void set_MainColor(Color c)
		{
			shapeColor = c;
		}
		
		/**
		 * If asked, the object can return a point that will be considered the 
		 * anchor.  This becomes important when resizing, and one or more points
		 * need to stay fixed.
		 * @param p Point of interest, shape can use this to decide how
		 * it will pick an anchor.
		 * @return Returns an anchor point.
		 */
		abstract Point2D.Double pickAnchor (Point2D.Double p);
		
		/**
		 * Each shape will implement a method to resize.
		 * @param point Anchor point.
		 * @param deltaX Change in X.
		 * @param deltaY Change in Y.
		 */
		abstract void resize(Point2D.Double point, int deltaX, int deltaY);
		
		
		/**
		 * Each shape will implement a move method.
		 * @param x Change in X.
		 * @param y Change in Y.
		 */
		abstract void move(int x, int y);

}