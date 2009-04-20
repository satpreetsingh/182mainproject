import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
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
		
	    public Oval(int x, int y, Color c, int type) {
			super(x, y, c, type);
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
	   		
	   	    /* Draw the outline of a oval on the graphics */
	   		if (this.DrawingType == 1) {
		   		g.drawOval(
						shapeX, 
						shapeY, 
						shapeWidth, 
						shapeHeight);
	   		}
	   	    
	   		/* Draw the solid shape of a oval on the graphics */
	   		else {
		   		g.fillOval(
						shapeX, 
						shapeY, 
						shapeWidth, 
						shapeHeight);
		   		

	   		}
			
	   		
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
		
		Point2D.Double pickAnchor (Point2D.Double p)
		{
				Point2D.Double result;
				
				double dist1, dist2, dist3, dist4;
				dist1 = this.origin.distance(p);
				dist2 = this.end.distance(p);
				dist3 = this.endorigin.distance(p);
				dist4 = this.originend.distance(p);
				
				/* Choose the end that is farther away from the mouseclick */				
				if ((dist4 > dist3) && 
					(dist4 > dist2) && 
					(dist4 > dist1)){
					result = originend;	
				}
				else if ((dist3 > dist4) && 
						(dist3 > dist2) && 
						(dist3 > dist1)){
					result = endorigin;	
				}
				else if ((dist2 > dist4) && 
						(dist2 > dist3) && 
						(dist2 > dist1)){
					result = end;	
				}
				else {
				  result = origin;	
				}
					
					
				return result;
			}
		/* ------------------------------------------------------- */

		
}
