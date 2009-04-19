import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;


/**
 * Implements a selector tool for selecting, and manipulating objects.
 * @authors bmhelppi, jjtrapan
 *
 */
public class SelectorTool implements Tool{

	protected DrawingCanvas canvas;
	protected Shape shapeOfInterest = null;
	protected Point2D.Double point; 
	
	protected int selectionStyle = ShapeMath.neither;
	protected Point2D.Double anchor;
	protected Point2D.Double eventPoint;
	protected Shape previousSelectedObject;
	protected Shape PrepreviousSelectedObject;
	
	/**
	 * Create a new instance of a selector tool.
	 * @param c Canvas tool will ask shapes to draw themselves on.
	 */
	public SelectorTool(DrawingCanvas c)
	{
		canvas = c;
		point = new Point2D.Double();
		anchor = new Point2D.Double();
		eventPoint = new Point2D.Double();
	}
	
	@Override
	/**
	 * When the mouse is pressed, highlight the first shape that 
	 * is near the point if one is, and consider it for future shape manipulations.
	 */
	public void mousePressed(MouseEvent e, ArrayList<Shape> currentShapes) {
		
		/* Declare local vars to start the list with the latest object */
		int LastListPos;
		boolean found = false;
		
		Graphics graphics = canvas.getimageBufferGraphics();
			
		if (shapeOfInterest != null)
		{
			/**
			 * If a prior shape was selected, dump that one back.
			 * This also means if you click the same shape, it will be
			 * unhighlighted, and then re-highlighted, but since
			 * graphics are buffered, shouldn't be noticeable.
			 */
			shapeOfInterest.draw(graphics);
			LastListPos = shapeOfInterest.ListPos;
		}
		else 
		{
			LastListPos = 0;
		}
		
		shapeOfInterest = null;
		
		/* Search the list and find the first object that is 'near' the mouse click */
		if (currentShapes != null) {
			
			for (int i = LastListPos; i < currentShapes.size(); i++) {
				
				if (currentShapes.get(i).near
						(e.getPoint().x,
						 e.getPoint().y)) {
					
					/* If we selected the same object twice, but not three times, set it as the focus shape */
					if (previousSelectedObject == currentShapes.get(i)){
						if ((PrepreviousSelectedObject != currentShapes.get(i)) || 
						    (found == true)) {
							found = true;
							shapeOfInterest = currentShapes.get(i);
							shapeOfInterest.ListPos = i;
							i = currentShapes.size();	
						}	
					}
					
					/* If we have not found a near object, rescan the object list from 0 */
					else { 		
						found = true;
						shapeOfInterest = currentShapes.get(i);
						shapeOfInterest.ListPos = i;
						i = currentShapes.size();
					}	

				} 
				
				/*  If we have started from the mid section of the list and nothing has
				      been found, then start from the beginning */
				if ((found == false) && 
					(i == currentShapes.size() - 1)){ 
					
					/* Set to negative one since the for loop will increment it to 0 */
					i = -1; 
					found = true;
				}

			} /* For loop */
		}
		
		
		if (shapeOfInterest != null)
		{
			
			/* Save the shape's original position */
			point.setLocation(e.getX(), e.getY());
			
			
			/* Set the color to light gray */
			graphics.setXORMode(Color.lightGray);
			
			/* Redraw the shape */
			shapeOfInterest.draw(graphics);
			

			
  			/* Draw selection rectangles */
			shapeOfInterest.drawHighlightBoxes(graphics);

			/* Iterate the previously selected objects */
			PrepreviousSelectedObject = previousSelectedObject;
			previousSelectedObject = shapeOfInterest;
						
			/* Determine which part of the object the user clicked */
			if(shapeOfInterest.exterior(e.getX(), e.getY()))
			{
				this.selectionStyle = ShapeMath.exterior;
				eventPoint.x = e.getX();
				eventPoint.y = e.getY();
				this.anchor = shapeOfInterest.pickAnchor(eventPoint);
		
			}
			else
			{
				this.selectionStyle = ShapeMath.interior;
			}
			
			/* Pass back the last object to the canvas */
			if (canvas.session != null)
			{
				if(canvas.session.currentState.lastSelected() != null)
				{
					canvas.session.clearSelection();
				}
			}
  			
			canvas.session.selectShape(shapeOfInterest);
			
		}	
    	else 
    	{ 
    	  /* Clear the canvas's last selected object */	
	      canvas.session.clearSelection();
	      
	  	 /* Set the previous selected objects to null */
		  PrepreviousSelectedObject = previousSelectedObject;
		  previousSelectedObject = null;
			
		}
		
        /* Clear the canvas and repaint each listed object */
		canvas.refresh();
		
	}
	/* ------------------------------------------------------- */
	
	@Override
	public void mouseDragged(MouseEvent e, ArrayList<Shape> currentShapes) {
		
		int deltaX, deltaY;
		
		
		/* Set the previous selected objects to null */
		previousSelectedObject = null;
		PrepreviousSelectedObject = null;
		
		if (shapeOfInterest != null) {
			
			Graphics graphics = canvas.getimageBufferGraphics();
		
          
            
            
            deltaX = e.getX() - (int)this.point.x;
  			deltaY = e.getY() - (int)this.point.y;
  			
			/* Resize the object */ 
  			if(this.selectionStyle == ShapeMath.exterior)
  			{
  				shapeOfInterest.resize(anchor, deltaX, deltaY);
  			}
  			
  			/* Drag the object */
  			else
  			{
  				shapeOfInterest.move(deltaX, deltaY);
  			}

  			/* Clear off the old canvas and redraw all the shapes */
  			canvas.refresh();
  			
			/* Save the shape's latest position */
			this.point.setLocation(e.getX(), e.getY());
			
			
			
			/* Set the XOR mode to light gray */
			graphics.setXORMode(Color.lightGray);
			


			
			/* Redraw the shape */
			shapeOfInterest.draw(graphics);
						
  	
			/* Draw selection rectangles */
			shapeOfInterest.drawHighlightBoxes(graphics);
	
			/* Repaint the canvas to visually select/unselect the object */ 
	 		canvas.repaint();	


					
			
		}
		
	}
	/* ------------------------------------------------------- */
	
	@Override
	public void mouseReleased(MouseEvent e, ArrayList<Shape> currentShapes) {
		
		if (shapeOfInterest != null){
			
			/* Paint a white rectangle over the canvas, and redraw all shapes from list */
			canvas.refresh();
						
			/* Set the selected object to gray color and redraw */
			Graphics graphics = canvas.getimageBufferGraphics();
			
  			graphics.setXORMode(Color.lightGray);  			
		
			/* Redraw the shape */ 
			shapeOfInterest.draw(graphics);

			/* Draw selection rectangles */
			shapeOfInterest.drawHighlightBoxes(graphics);	
			
		}		
	}

    /* ------------------------------------------------------- */	
	/**
	 * When deselected is invoked on the selector tool,
	 * if an object is currently selected, deselect it.
	 */
	
	public void deselected() {
				
		Graphics graphics = canvas.getimageBufferGraphics();
		
		if (shapeOfInterest != null)
		{
			shapeOfInterest.draw(graphics);
			shapeOfInterest = null;
			
			/* Reset the previous selected objects to null */
			previousSelectedObject = null;
			PrepreviousSelectedObject = null;
			
			
			/*  Paint a white rectangle over the canvas, and redraw all shapes from list */
			canvas.refresh();	
		}
		
	}
	/* ------------------------------------------------------- */


}
