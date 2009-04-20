import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * The line class implements methods necessary
 * to manipulate lines, extending the TwoPointShape abstract class.
 * @authors bmhelppi, jjtrapan
 *
 */
public class Line extends TwoPointShape
{
	
	  /* ------------------------------------------------------- */
	  /* Constructor */ 
	  
	  public Line(int x, int y, Color c, int type) {
			super(x, y, c, type);

	  }
	  /* ------------------------------------------------------- */
	  
	  /**
	   * Draws a line object.
	   */
	  
	  void draw(Graphics g) {
		  
        g.setColor(shapeColor);
        g.drawLine((int)this.origin.x, (int)this.origin.y, (int)this.end.x, (int)this.end.y);
	  }
	  /* ------------------------------------------------------- */ 
	  /** 
	   * Draw two resize boxes at each end 
	   * of this shape. 
	   */
	   
	   void drawHighlightBoxes(Graphics g) {
			
		 /* Set the color for the drag resize boxes */
		 g.setColor(Color.black);
			
		 /* Draw a small rectangle at each end point */
	 	 g.fillRect ((int)this.origin.x - 5, (int)this.origin.y - 5, 10, 10);
		 g.fillRect ((int)this.end.x - 5, (int)this.end.y - 5, 10, 10);
						
	   }
	   /* ------------------------------------------------------- */		
       /**
		* Determine if the interior area of a line is selected.
	    */
		
	    boolean interior(int x, int y) {
	    	
			double ratio;
			double yProposed;
			boolean finalResult = false;
			boolean xOk = false;
			
			/*  If the user drew the line from right to left, and the x is in tolerance */
			if((this.end.x < this.origin.x) &&
			  (x > this.end.x - nearTol && x < this.origin.x + nearTol))
			{
				xOk = true;
				ratio = 1.0 - ((this.origin.x - x) / (this.origin.x - this.end.x ));
				if((this.end.y < this.origin.y) &&
				(y > this.end.y && y < this.origin.y))
				{
					yProposed = this.end.y + (this.origin.y - this.end.y) * ratio;
				}
				else
				{
					yProposed = this.origin.y + (this.end.y - this.origin.y) * ratio;
				}
				
				
				finalResult =(Math.abs(yProposed - y) < nearTol);
					
			}
			
			/* If the object was drawn from left to right and click was in the tolerance of the object */
			else if (x < this.end.x + nearTol && x > this.origin.x - nearTol)
			{
				xOk = true;
				ratio = 1.0 - ((this.end.x - x) / (this.end.x - this.origin.x ));
				if((this.end.y < this.origin.y) &&
				(y > this.end.y && y < this.origin.y))
				{
					yProposed = this.end.y + (this.origin.y - this.end.y) * ratio;
				}
				else
				{
					yProposed = this.origin.y + (this.end.y - this.origin.y) * ratio;
				}
				finalResult =(Math.abs(yProposed - y) < nearTol);
			}
			else if (Math.abs(x -this.end.x) < nearTol){
				xOk = true;
			}
			
			if (finalResult == false &&
				xOk)
			{
				if (this.end.y > this.origin.y)
				{ 
					finalResult = y > this.origin.y && y < this.end.y;
				}
				else
				{
					finalResult = y < this.origin.y && y > this.end.y;
				}
			}
			return finalResult;
		}
	    /* ------------------------------------------------------- */
		/**
		 * Determine if the exterior area of a line is selected.
		 */
	    
		boolean exterior(int x, int y)
		{
			return ( 
					 (  (Math.abs(x - this.origin.x) < nearTol) && (Math.abs(y - this.origin.y) < nearTol)  ) 
			         ||
			         (  (Math.abs(x- this.end.x) < nearTol) && (Math.abs(y- this.end.y) < nearTol)  )
			       );
		
		}
		/* ------------------------------------------------------- */
 	    /**
		 * Resize the shape.
		 */
		void resize(Point2D.Double anchor, int deltaX, int deltaY)
		{
			 
			 double x1,x2,y1,y2;
			 
			 
			 if (anchor == this.origin)
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
		/* ------------------------------------------------------- */
		 
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
		/* ------------------------------------------------------- */
 
		Point2D.Double pickAnchor (Point2D.Double p)
		{
				Point2D.Double result;
				
				double dist1, dist2;
				dist1 = this.origin.distance(p);
				dist2 = this.end.distance(p);
				
				/* Choose the end that is farther away from the mouseclick */
				if (dist1 < dist2)
				{
					result = end;
				}
				else
				{
					result = origin;
				}
				return result;
			}
		/* ------------------------------------------------------- */

		
}
