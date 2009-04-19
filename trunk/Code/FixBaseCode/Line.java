import java.awt.*;
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
}
