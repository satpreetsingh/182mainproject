import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;


/**
 * Implements a selector tool for selecting, and manipulating objects.
 * @authors bmhelppi, jjtrapan
 *
 */
public class SelectorTool implements Tool
{
	private String name;
	protected Object shapeOfInterest = null;
	protected Point2D.Double point; 
	
	protected int selectionStyle = ShapeMath.neither;
	protected Point2D.Double anchor;
	protected Point2D.Double eventPoint;
	protected Object previousSelectedObject = null;
	protected Object PrepreviousSelectedObject = null;
	
	/**
	 * Create a new instance of a selector tool.
	 * @param c Canvas tool will ask shapes to draw themselves on.
	 */
	public SelectorTool(String name)
	{
		this.name = name;
		point = new Point2D.Double();
		anchor = new Point2D.Double();
		eventPoint = new Point2D.Double();
	}
	
	
	/**
	 * When the mouse is pressed, highlight the first shape that 
	 * is near the point if one is, and consider it for future shape manipulations.
	 */
	public void mousePressed(Point p,ArrayList<Object> currentShapes, DrawingCanvas canvas,boolean fill,UUID uniqueId) 
	{
		/* Declare local vars to start the list with the latest object */
		int LastListPos = 0;
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
			 try{
				 /* First attempt to draw the shape from a static class */
				 ((Shape) shapeOfInterest).draw(graphics);
				 
				 	 
				 
			 }catch(Exception e){
			
				/* Otherwise try to draw the shape from a dynamically loaded class */ 
				try {
					
					 Method mi = shapeOfInterest.getClass().getMethod("draw", Graphics.class );
					 Object argsArray[] = {graphics};
					 mi.invoke(shapeOfInterest, argsArray);

			 	} catch (SecurityException e1) {
				 	e1.printStackTrace();
			 	} catch (NoSuchMethodException e2) {
					e2.printStackTrace();
				} catch (IllegalArgumentException e3) {
					e3.printStackTrace();
				} catch (IllegalAccessException e4) {
					e4.printStackTrace();
				} catch (InvocationTargetException e5) {
					e5.printStackTrace();
				}	 
			 }			

			 try { 
				 LastListPos = ((Shape) shapeOfInterest).ListPos;
			 }catch(Exception e){
				 
				 Field fe = null;
				try {
					fe = shapeOfInterest.getClass().getField("ListPos");
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 try {
					LastListPos = fe.getInt(shapeOfInterest);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  
			 }
			
		}
		else 
		{
			LastListPos = 0;
		}
		
		shapeOfInterest = null;
		
		/* Search the list and find the first object that is 'near' the mouse click */
		if (currentShapes != null) 
		{
			
			for (int i = LastListPos; i < currentShapes.size(); i++) 
			{
				
				/* Attempt to perform the object lookup statically */
				try{
					if (((Shape)currentShapes.get(i)).near(p.x,p.y)) {
					
						/* If we selected the same object twice, but not three times, set it as the focus shape */
						if (previousSelectedObject == currentShapes.get(i))
						{
							if ((PrepreviousSelectedObject != currentShapes.get(i)) || 
							    (found == true)) {
								found = true;
								shapeOfInterest = currentShapes.get(i);
								((Shape)shapeOfInterest).ListPos = i;
								i = currentShapes.size();	
							}	
						}
						
						/* If we have not found a near object, rescan the object list from 0 */
						else 
						{ 		
							found = true;
							shapeOfInterest = currentShapes.get(i);
							((Shape)shapeOfInterest).ListPos = i;
							i = currentShapes.size();
						}	
	
					} 
				}catch(Exception e){
					
					
					Method mi = null;
					try {
						mi = currentShapes.get(i).getClass().getMethod("near", int.class, int.class);
					} catch (SecurityException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (NoSuchMethodException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					Object argsArray[] = {p.x,p.y};
	
					 
					try {
						if (mi.invoke(shapeOfInterest, argsArray).toString().equals("true")) {
							
							/* If we selected the same object twice, but not three times, set it as the focus shape */
							if (previousSelectedObject == currentShapes.get(i))
							{
								if ((PrepreviousSelectedObject != currentShapes.get(i)) || 
								    (found == true)) {
									
									found = true;
									
									shapeOfInterest = currentShapes.get(i);
									
									Field fe = shapeOfInterest.getClass().getField("ListPos");
									fe.setInt(shapeOfInterest, i);
									
									i = currentShapes.size();	
								}	
							}
							
							/* If we have not found a near object, rescan the object list from 0 */
							else 
							{ 		
								found = true;
								shapeOfInterest = currentShapes.get(i);
								
								
								Field fe2 = null;
								try {
									fe2 = shapeOfInterest.getClass().getField("ListPos");
								} catch (SecurityException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								} catch (NoSuchFieldException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								try {
									fe2.setInt(shapeOfInterest, i);
								} catch (IllegalArgumentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IllegalAccessException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}	
								i = currentShapes.size();
							}	

						}
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchFieldException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 	
				}
				
				
				
				
				/*  If we have started from the mid section of the list and nothing has
				      been found, then start from the beginning */
				if ((found == false) && 
					(i == currentShapes.size() - 1))
				{ 
					
					/* Set to negative one since the for loop will increment it to 0 */
					i = -1; 
					found = true;
				}

			} /* For loop */
		}
		
		
		if (shapeOfInterest != null)
		{
			
			/* Save the shape's original position */
			point.setLocation(p.x,p.y);
			
			
			/* Set the color to light gray */
			graphics.setXORMode(Color.lightGray);
			

			/* Redraw the shape */ 
			 try{
				 /* First attempt to draw the shape from a static class */
				 ((Shape)shapeOfInterest).draw(graphics);
				 ((Shape)shapeOfInterest).drawHighlightBoxes(graphics);
				 
			 }catch(Exception e){
			
				/* Otherwise try to draw the shape from a dynamically loaded class */ 
				try {
					
					 Method mi = shapeOfInterest.getClass().getMethod("draw", Graphics.class );
					 Object argsArray[] = {graphics};
					 mi.invoke(shapeOfInterest, argsArray);

					 Method mi2 = shapeOfInterest.getClass().getMethod("drawHighlightBoxes", Graphics.class );
					 Object argsArray2[] = {graphics};
					 mi2.invoke(shapeOfInterest, argsArray2); 
					 
			 	} catch (SecurityException e1) {
				 	e1.printStackTrace();
			 	} catch (NoSuchMethodException e2) {
					e2.printStackTrace();
				} catch (IllegalArgumentException e3) {
					e3.printStackTrace();
				} catch (IllegalAccessException e4) {
					e4.printStackTrace();
				} catch (InvocationTargetException e5) {
					e5.printStackTrace();
				}	 
			 }
			 
			 

			/* Iterate the previously selected objects */
			PrepreviousSelectedObject = previousSelectedObject;
			previousSelectedObject = shapeOfInterest;
						
			
			
			/* Determine which part of the object the user clicked */
			try{
				
				/* Try to accessing a static method */
				if(((Shape)shapeOfInterest).exterior(p.x,p.y)) {
					this.selectionStyle = ShapeMath.exterior;
					eventPoint.x = p.x;
					eventPoint.y = p.y;
					this.anchor = ((Shape)shapeOfInterest).pickAnchor(eventPoint);
		
				}
				else {
					this.selectionStyle = ShapeMath.interior;
				}
			}catch(Exception e){
				
				
				
				Method mi = null;
				try {
					mi = shapeOfInterest.getClass().getMethod("exterior", int.class, int.class);
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Object argsArray[] = {p.x,p.y};
				
					 
					 
			    /* Otherwise try to access a dynamic method */
				try {
					if(mi.invoke(shapeOfInterest, argsArray).toString().equals("true")) {
						
						this.selectionStyle = ShapeMath.exterior;
						eventPoint.x = p.x;
						eventPoint.y = p.y;
						
						Method mi2 = shapeOfInterest.getClass().getMethod("pickAnchor", Point2D.Double.class );
						Object argsArray2[] = {eventPoint};
						this.anchor = (Double) mi2.invoke(shapeOfInterest, argsArray);					
						

					}
					else {
						this.selectionStyle = ShapeMath.interior;
					}
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
			
			
			
			
			/* Pass back the last object to the canvas */
			if (canvas.session != null)
			{
				if(canvas.session.currentState.lastSelected() != null)
				{
					canvas.session.processClearSelectedObject(false);
				}
			}
  			
			canvas.session.processSelectShape(shapeOfInterest, false);
			
		}	
    	else 
    	{ 
    		/* Clear the canvas's last selected object */	
    		canvas.session.processClearSelectedObject(false);
	      
	  	 	/* Set the previous selected objects to null */
    		PrepreviousSelectedObject = previousSelectedObject;
		 	 previousSelectedObject = null;
			
		}
		
        /* Clear the canvas and repaint each listed object */
		canvas.refresh();
		
	}
	
	
	
	
	/**
	 * Processes mouseDragged events.
	 */
	public void mouseDragged(Point p, ArrayList<Object> currentShapes, DrawingCanvas canvas) 
	{
		
		int deltaX, deltaY;
		
		
		/* Set the previous selected objects to null */
		previousSelectedObject = null;
		PrepreviousSelectedObject = null;
		
		if (shapeOfInterest != null) 
		{
			
			Graphics graphics = canvas.getimageBufferGraphics();
            
            deltaX = p.x - (int)this.point.x;
  			deltaY = p.y - (int)this.point.y;
  			
			/* Resize the object */ 
  			if(this.selectionStyle == ShapeMath.exterior)
  			{

  				/* Redraw the shape */ 
  				 try{
  					 /* First attempt to resize the shape from a static class */
  					 ((Shape)shapeOfInterest).resize(anchor, deltaX, deltaY);
 
  				 }catch(Exception e){
  				
  					/* Otherwise try to resize the shape from a dynamically loaded class */ 
  					try {
  						
  						 Method mi = shapeOfInterest.getClass().getMethod("resize", Point2D.Double.class, int.class, int.class);
  						 Object argsArray[] = {anchor, deltaX, deltaY};
  						 mi.invoke(shapeOfInterest, argsArray);
  						 
  				 	} catch (SecurityException e1) {
  					 	e1.printStackTrace();
  				 	} catch (NoSuchMethodException e2) {
  						e2.printStackTrace();
  					} catch (IllegalArgumentException e3) {
  						e3.printStackTrace();
  					} catch (IllegalAccessException e4) {
  						e4.printStackTrace();
  					} catch (InvocationTargetException e5) {
  						e5.printStackTrace();
  					}	 
  				 }
  				  				
  				
  				
  			}
  			
  			/* Drag the object */
  			else
  			{
  	
  				/* Move the shape */ 
  				 try{
  					 
  					 /* First attempt to move the shape from a static class */
  					 ((Shape)shapeOfInterest).move(deltaX, deltaY);  					 
  		
  				 }catch(Exception e){
  				
  					/* Otherwise try to move the shape from a dynamically loaded class */ 
  					try {
  						
  						 Method mi = shapeOfInterest.getClass().getMethod("move", int.class, int.class);
  						 Object argsArray[] = {deltaX, deltaY};
  						 mi.invoke(shapeOfInterest, argsArray);
  						 
  				 	} catch (SecurityException e1) {
  					 	e1.printStackTrace();
  				 	} catch (NoSuchMethodException e2) {
  						e2.printStackTrace();
  					} catch (IllegalArgumentException e3) {
  						e3.printStackTrace();
  					} catch (IllegalAccessException e4) {
  						e4.printStackTrace();
  					} catch (InvocationTargetException e5) {
  						e5.printStackTrace();
  					}	 
  				 }
  				 
  				 
  				 
  			}

  			/* Clear off the old canvas and redraw all the shapes */
  			canvas.refresh();
  			
			/* Save the shape's latest position */
			this.point.setLocation(p.x,p.y);
			
			/* Set the XOR mode to light gray */
			graphics.setXORMode(Color.lightGray);
			
			
			
			/* Redraw the shape */ 
			 try{
				 /* First attempt to draw the shape from a static class */
				 ((Shape)shapeOfInterest).draw(graphics);
				 ((Shape)shapeOfInterest).drawHighlightBoxes(graphics);
				 
			 }catch(Exception e){
			
				/* Otherwise try to draw the shape from a dynamically loaded class */ 
				try {
					
					 Method mi = shapeOfInterest.getClass().getMethod("draw", Graphics.class );
					 Object argsArray[] = {graphics};
					 mi.invoke(shapeOfInterest, argsArray);

					 Method mi2 = shapeOfInterest.getClass().getMethod("drawHighlightBoxes", Graphics.class );
					 Object argsArray2[] = {graphics};
					 mi2.invoke(shapeOfInterest, argsArray2); 
					 
			 	} catch (SecurityException e1) {
				 	e1.printStackTrace();
			 	} catch (NoSuchMethodException e2) {
					e2.printStackTrace();
				} catch (IllegalArgumentException e3) {
					e3.printStackTrace();
				} catch (IllegalAccessException e4) {
					e4.printStackTrace();
				} catch (InvocationTargetException e5) {
					e5.printStackTrace();
				}	 
			 }
			 
			 
			/* Repaint the canvas to visually select/unselect the object */ 
	 		canvas.repaint();	
			
		}
		
	}

	public void mouseReleased(Point point, ArrayList<Object> currentShapes, DrawingCanvas canvas,Color finalColor, boolean filled) 
	{
		
		if (shapeOfInterest != null)
		{
			
			/* Paint a white rectangle over the canvas, and redraw all shapes from list */
			canvas.refresh();
						
			/* Set the selected object to gray color and redraw */
			Graphics graphics = canvas.getimageBufferGraphics();
			
  			graphics.setXORMode(Color.lightGray);  			
		
  			
  			
			/* Redraw the shape */ 
			 try{
				 /* First attempt to draw the shape from a static class */
				 ((Shape)shapeOfInterest).draw(graphics);
				 ((Shape)shapeOfInterest).drawHighlightBoxes(graphics);
				 
			 }catch(Exception e){
			
				/* Otherwise try to draw the shape from a dynamically loaded class */ 
				try {
					
					 Method mi = shapeOfInterest.getClass().getMethod("draw", Graphics.class );
					 Object argsArray[] = {graphics};
					 mi.invoke(shapeOfInterest, argsArray);

					 Method mi2 = shapeOfInterest.getClass().getMethod("drawHighlightBoxes", Graphics.class );
					 Object argsArray2[] = {graphics};
					 mi2.invoke(shapeOfInterest, argsArray2); 
					 
			 	} catch (SecurityException e1) {
				 	e1.printStackTrace();
			 	} catch (NoSuchMethodException e2) {
					e2.printStackTrace();
				} catch (IllegalArgumentException e3) {
					e3.printStackTrace();
				} catch (IllegalAccessException e4) {
					e4.printStackTrace();
				} catch (InvocationTargetException e5) {
					e5.printStackTrace();
				}	 
			 }
			

			
			 
			
		}		
	}

	/**
	 * When deselected is invoked on the selector tool,
	 * if an object is currently selected, deselect it.
	 */
	public void deselected(DrawingCanvas canvas) {
				
		Graphics graphics = canvas.getimageBufferGraphics();
		
		if (shapeOfInterest != null)
		{
			
			
			 try{
				 /* First attempt to draw the shape from a static class */
				 ((Shape)shapeOfInterest).draw(graphics);
			 }catch(Exception e){
			
				/* Otherwise try to draw the shape from a dynamically loaded class */ 
				try {
					
					 Method mi = shapeOfInterest.getClass().getMethod("draw", Graphics.class );
					 Object argsArray[] = {graphics};
					 mi.invoke(shapeOfInterest, argsArray);
				
			 	} catch (SecurityException e1) {
				 	e1.printStackTrace();
			 	} catch (NoSuchMethodException e2) {
					e2.printStackTrace();
				} catch (IllegalArgumentException e3) {
					e3.printStackTrace();
				} catch (IllegalAccessException e4) {
					e4.printStackTrace();
				} catch (InvocationTargetException e5) {
					e5.printStackTrace();
				}	 
			 }
			
			
			shapeOfInterest = null;
			
			/* Reset the previous selected objects to null */
			previousSelectedObject = null;
			PrepreviousSelectedObject = null;
			
			
			/*  Paint a white rectangle over the canvas, and redraw all shapes from list */
			canvas.refresh();	
		}
		
	}


	public String toolName() 
	{
		return this.name;
	}


}
