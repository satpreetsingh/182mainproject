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
 	/* ------------------------------------------------------- */
	
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
	 /* ------------------------------------------------------- */ 
	 
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
  	/* ------------------------------------------------------- */
	
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
	 /* ------------------------------------------------------- */
	 
	 /**
	  * Resize the shape.
	  */
	void resize(Point2D.Double anchor, int deltaX, int deltaY)
	{
			 
			 if (anchor == this.origin)
			 {				 			 
				 this.end = scale(this.origin.x,this.end.x,this.origin.y,this.end.y,deltaX, deltaY);
				 
				 /* Update the sub points */
				 this.endorigin.x = this.end.x;
				 this.originend.y = this.end.y;
			 }
			 else if (anchor == this.endorigin){
				 
				 this.originend = scale(this.endorigin.x,this.originend.x,this.endorigin.y,this.originend.y,deltaX, deltaY);					 
				 
				 /* Update the main endpoints */
				 this.origin.x = this.originend.x;
				 this.end.y = this.originend.y;
				 
			 }
			 else if (anchor == this.originend){
				 this.endorigin = scale(this.originend.x,this.endorigin.x,this.originend.y,this.endorigin.y,deltaX, deltaY);	 
				 
				 /* Update the main endpoints */
				 this.end.x = this.endorigin.x;
				 this.origin.y = this.endorigin.y;
			 }
			 else
			 {
				 this.origin = scale(this.end.x,this.origin.x,this.end.y,this.origin.y,deltaX, deltaY);
				 
				 /* Update the sub points */
				 this.originend.x = this.origin.x;
				 this.endorigin.y = this.origin.y;
				 
			 }	 
	}
	/* ------------------------------------------------------- */
		 
	Point2D.Double scale(double x1,double x2,double y1,double y2, int deltaX, int deltaY)
	{
			 Point2D.Double result = new Point2D.Double();
			 result.x = x2 + deltaX;
			 result.y = y2 + deltaY;		 
			 return result;
			 
	}
 	/* ------------------------------------------------------- */
		
}
