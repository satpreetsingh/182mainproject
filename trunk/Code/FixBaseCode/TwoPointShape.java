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
	
	/**
	 * Set the second point.
	 * @param x Second point x coordinate.
	 * @param y Second point y coordinate.
	 */
	 void setSecondPoint(double x, double y) {
		 this.end.x = x;
		 this.end.y = y;
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
			
		 
	 }
	 
	 /**
	  * Resize the shape.
	  */
	 void resize(Point2D.Double point, int deltaX, int deltaY)
	 {
		 Point2D.Double pointOne = pickAnchor(point);
		 double x1,x2,y1,y2;
		 
		 if (pointOne == this.origin)
		 {
			 x1 = this.origin.x;
			 y1 = this.origin.y;
			 
			 x2 = this.end.x;
			 y2 = this.end.y;
			 this.end = scale(x1,x2,y1,y2,deltaX, deltaY);
		 }
		 else
		 {
			 x2 = this.origin.x;
			 y2 = this.origin.y;
			 
			 x1 = this.end.x;
			 y1 = this.end.y;
			 this.origin = scale(x1,x2,y1,y2,deltaX, deltaY);
		 }	 
	 }

	 
	 
	 Point2D.Double scale(double x1,double x2,double y1,double y2, int deltaX, int deltaY)
	 {
		 Point2D.Double result = new Point2D.Double();
		 double distance = Point2D.distance(x1, y1, x2, y2);
		 double change = deltaX + deltaY;
		 
		 if (distance > this.floatTol)
		 {
		 double xRatio = Math.abs((x1 - x2) / distance);
		 double yRatio = Math.abs((y1 - y2) / distance);
		 
		 	if (Math.abs(xRatio) > this.floatTol)
		 	{
		 		result.x = x2 + xRatio * change;
		 	}
		 	else
		 	{
		 		result.x = x2;
		 	}
			if (Math.abs(yRatio) > this.floatTol)
		 	{
		 		result.y = y2 + yRatio * change;
		 	}
		 	else
		 	{
		 		result.y = y2;
		 	}
		 }
		 else
		 {
			 result.setLocation(x2,y2);
		 }
		 
		 return result;
		 
	 }
	 
	 Point2D.Double pickAnchor (Point2D.Double p)
	 {
			Point2D.Double result;
			
			double dist1, dist2;
			dist1 = this.origin.distance(p);
			dist2 = this.end.distance(p);
			
			
			if (dist1 < dist2)
			{
				result = origin;
			}
			else
			{
				result = end;
			}
			return result;
		}
	 
}
