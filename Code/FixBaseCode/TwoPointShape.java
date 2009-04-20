import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Creates an abstract class for shapes that
 * are defined by two points.
 * @authors bmhelppi, jjtrapan
 *
 */
public abstract class TwoPointShape extends Shape
{
	 
	Point2D.Double end;
	
	/* endorigin.x is same as end.x; endorigin.y is the same as origin.y */
	/* These points serve as secondary locations for the bases of anchor/resizing for 2D shapes */
	Point2D.Double endorigin;
	Point2D.Double originend;
	
	
	/**
	 * Set the second point,regarded as the end point.
	 * This module also sets the third and forth points.
	 * These are based off of the first and second points.
	 * @param x Second point x coordinate.
	 * @param y Second point y coordinate.
	 */
	 void setSecondPoint(double x, double y) {
		 this.end.x = x;
		 this.end.y = y;
		 
		 this.endorigin.x = x;
		 this.endorigin.y = this.origin.y;
		 this.originend.x = this.origin.x;
		 this.originend.y = y;
		 
	 }
	 
	 /**
	  * Create a new instance of a two point shape.
	  * @param x X origin for two point shape.
	  * @param y Y origin for two point shape.
	  * @param c C initial color of the shape.
	  */
	 public TwoPointShape(int x, int y, Color c, int type) {
		 this.origin = new Point2D.Double(x,y);
		 this.end = new Point2D.Double(x,y);
		 this.endorigin = new Point2D.Double(x,y);
		 this.originend = new Point2D.Double(x,y);
		 this.shapeColor = c;
		 this.DrawingType = type;
	 }
	 
	 /**
	  * Move the shape.
	  */
	 void move(int x, int y)
	 {
		 this.origin.setLocation(this.origin.x + x, this.origin.y + y);
		 this.end.setLocation(this.end.x + x, this.end.y + y);
		 
		  /* Update the non-endpoint points */	
		 this.endorigin.x = this.end.x + x;
	     this.endorigin.y = this.origin.y + y;	
		 this.originend.x = this.origin.x + x;
	     this.originend.y = this.end.y + y;		 
	 }
		
}
