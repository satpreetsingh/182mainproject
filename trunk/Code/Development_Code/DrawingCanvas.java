import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.EventListener;

/**
 * DrawingCanvas is the actual drawing surface.
 * @author bmhelppi
 *
 */
public class DrawingCanvas extends JComponent 
{
	
	private MouseController mouseController = null;
	private KeyboardController keyController = null;
	private Image imageBuffer = null;
	private Graphics imageBufferGraphics = null;
	private int canvasWidth = 0;
	private int canvasHeight = 0;
	private Color penColor = Color.black;
    protected boolean IsOutline = true;
	private Tool currentTool = null; 
	protected Session session = null;
    
    /**
     * Create a new DrawingCanvas.
     */
    public DrawingCanvas() 
    {
	    setBackground(Color.white);
	    mouseController = createDrawingCanvasController();
	    addMouseListeneer(mouseController);
	    keyController = createKeyboardController();
	    addKeyboardListener(keyController);
    }
  
    /**
     * Create a MouseController.
     * @return Returns the instantiated controller.
     */
    protected MouseController
               createDrawingCanvasController() {
      return new MouseController(session);
    }
  
    /**
     * Create a keyboard controller.
     * @return Returns the instantiated controller.
     */
    protected KeyboardController createKeyboardController() {
      return new KeyboardController(session);
    }
  
    /**
     * Add mouse listener to the Canvas.
     * @param listener Listener to add.
     */
    protected void addMouseListeneer(EventListener listener) 
    {
    	addMouseListener((MouseListener) listener);
    	addMouseMotionListener((MouseMotionListener) listener);
    }
  
  
    /**
     * Add keyboard listener to the canvas.
     * @param listener Listener to add.
     */
    protected void addKeyboardListener(EventListener listener) {
    	addKeyListener( (KeyListener)listener);
    }
  
  
    /**
     * Updates are done by painting the canvas.
     */
    public void update(Graphics g){
    	paint(g);
    }
  
    /**
     * Paint consists of rendering the image buffer.
     */
    public void paint(Graphics g) {
    	g.drawImage(imageBuffer,0, 0, this);
    }
  
    /**
     * Set the pen color.
     * @param c Color objects will be drawn in.
     */
    public void setpenColor(Color c) {
      penColor = c;
      imageBufferGraphics.setColor(c);
      
      if (session != null)
      {
    	  /* If we have an object that has been selected */
    	  if (session.currentState.lastSelected() != null)
    	  {
    		  session.setMainColor(session.currentState.lastSelected(), c);
    	  }
    	  repaint();
      }
    }  
    
    /**
     * Set the object type.
     * @param int type objects will be drawn in.
     */
    public void setShapeType(boolean isoutline) {
        
    	IsOutline = isoutline;  	
    
        if (session != null)
        {
      	  /* If we have an object that has been selected */
      	  if (session.currentState.lastSelected() != null)
      	  {
      		  session.setMainType(session.currentState.lastSelected(), isoutline);
      	  }
      	  repaint();
        }
      }  
    
    /**
     * Get the current color that the canvas will use for 
     * new/selected objects.
     * @return Current color.
     */
    public Color getpenColor() {
    	return penColor;
    }
  
    /**
     * Get the current type of object that the canvas will use for 
     * new/selected objects.
     * @return int type.
     */
    public boolean getDrawingType() {
    	return IsOutline;
    }
    
    
    /**
     * Update the current tool that will interface with the canvas.
     * Also sends a deselect event to the last tool used if one
     * exists.
     * @param t  Tool that will be used to draw upon the canvas.
     */
    public void setcurrentTool(Tool t)  
    {
    	if (currentTool != null)
    	{
    		currentTool.deselected(this);
    	}
    	currentTool = t;
	  
    	/* If we are calling the erase tool, remove the current object */
    	if(t.getClass().toString().equalsIgnoreCase("class EraserTool")){
		  
    		/*  If we have a selected object */  
    		if (session.currentState.lastSelected() != null)
    		{  
    			if(session != null)
    			{
    				session.deleteShape(session.currentState.lastSelected());
    			}
    			this.refresh();
    		}
	    }	
	  }
  
    /**
     * Get the current tool in use for the canvas.
     * @return Return the current tool.
     */
    public Tool getcurrentTool() 
    {
    	return currentTool;
    }
  	
  
    /**
     * Get the current graphics.
     * @return Returns the imageBufferGraphics.
     */
    public Graphics getimageBufferGraphics() {
    	return imageBufferGraphics;
    }
  
    /**
     * Clean the canvas.
     */
    public void clearCanvas() 
    {
	  if(session != null)
	  {
		  session.clearObjects(false);
	  }
   
	  /* Set the new rectangle color to white */
	  imageBufferGraphics.setColor(Color.WHITE);
	  imageBufferGraphics.setXORMode(Color.WHITE);
   
	   imageBufferGraphics.fillRect(0, 0, canvasWidth, canvasHeight);
	   imageBufferGraphics.setColor(penColor); 
	   paint(imageBufferGraphics);
	   repaint();
  	}
  
    /**
     * Change the canvas size.  Note that this event is not
     * sent to session.
     */
    public void setBounds(int x, int y, int width, int height) 
    {
	    Image newimageBuffer = createImage(width, height);
	    imageBufferGraphics = newimageBuffer.getGraphics();
	    if (imageBuffer != null) 
	    {
	      imageBufferGraphics.drawImage(imageBuffer, 0, 0 ,this);
	    }
	    imageBuffer = newimageBuffer;
	    setpenColor(penColor);
	    super.setBounds(x, y, width, height);
	    
	    if (session != null)
	    {
	    	ArrayList <Shape> drawableShapes = session.currentState.currentShapes();
	    	if (drawableShapes != null)
	    	{
	    		for (int i = 0 ; i < drawableShapes.size(); i++)
	    		{
	    			drawableShapes.get(i).draw(imageBufferGraphics);
	    		}
	    	}
	    }
	    
	    repaint();
	    canvasWidth = width;
	    canvasHeight = height;
  }

  /**
   * Refresh the canvas.
   */
  public void refresh() {
	   
	   /* Set the new rectangle color to white */
	   imageBufferGraphics.setColor(Color.WHITE);
	   imageBufferGraphics.setXORMode(Color.WHITE);
	   
	   imageBufferGraphics.fillRect(0, 0, canvasWidth, canvasHeight);
	   imageBufferGraphics.setColor(penColor); 
	   paint(imageBufferGraphics);
	   
	   /* Redraw each of the shapes on the buffer */
	   if (session != null)
	   {
		   ArrayList <Shape> drawableShapes = session.currentState.currentShapes();
	   
		   if (drawableShapes != null) 
		   {
			   for (int i = 0 ; i < drawableShapes.size(); i++)
			   {
				   drawableShapes.get(i).draw(imageBufferGraphics);
			   }
		   }
	    }
	   
	   /* Repaint the canvas */
	   repaint();
  }
  
	/**
	 * Get the current Session.
	 * @return Return the session.
	 */
	public Session getCurrentSession()
	{
		return session;
	}
	
	/**
	 * Update the current Session
	 * @param s Session that will be used going forward.
	 */
	public void updateSession(Session s)
	{
		session = s;
		this.mouseController.updateSesion(s);
		this.keyController.updateSesion(s);
	}

}
