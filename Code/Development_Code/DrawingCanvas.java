import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.*;
import java.util.EventListener;

/**
 * DrawingCanvas is the actual drawing surface.
 * @authors bmhelppi, jjtrapan
 *
 */
public class DrawingCanvas extends JComponent implements Serializable, SessionListener
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
    		  session.processSetMainColor(session.currentState.lastSelected(), c, false);
    	  }
    	  
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
      		  session.processSetShapeFill(session.currentState.lastSelected(), isoutline, false);
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
    				session.processDeleteShape(session.currentState.lastSelected(), false);
    			}
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
	    	ArrayList <Object> drawableShapes = session.currentState.currentShapes();
	    	if (drawableShapes != null)
	    	{
	    		for (int i = 0 ; i < drawableShapes.size(); i++)
	    		{
	    			
	    			/* Attempt to cast as a shape and call the static method */
	    			try{
	    				((Shape)drawableShapes.get(i)).draw(imageBufferGraphics);
	    			}catch(Exception e){
	    				
	    				/* Try to call the shape draw method through reflection */
	    				try {
	    					
	    					 Method mi = drawableShapes.get(i).getClass().getMethod("draw", Graphics.class );
	    					 Object argsArray[] = {imageBufferGraphics};
	    					 mi.invoke(drawableShapes.get(i), argsArray);
	    				
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
	    }
	    
	    repaint();
	    canvasWidth = width;
	    canvasHeight = height;
  }

  /**
   * Refresh the canvas.
   */
  public void refresh() {
	   
	  if(imageBufferGraphics != null)
	  {
		   /* Set the new rectangle color to white */
		   imageBufferGraphics.setColor(Color.WHITE);
		   imageBufferGraphics.setXORMode(Color.WHITE);
		   
		   imageBufferGraphics.fillRect(0, 0, canvasWidth, canvasHeight);
		   imageBufferGraphics.setColor(penColor); 
		   paint(imageBufferGraphics);
		   
		   /* Redraw each of the shapes on the buffer */
		   if (session != null)
		   {
			   ArrayList <Object> drawableShapes = session.currentState.currentShapes();
		   
			   if (drawableShapes != null) 
			   {
				   for (int i = 0 ; i < drawableShapes.size(); i++)
				   {
		    		
					   
					   /* Attempt to cast as a shape and call the static method */
		    			try{
		    				((Shape)drawableShapes.get(i)).draw(imageBufferGraphics);
		    			}catch(Exception e){
		    			
		    				/* Try to call the shape draw method through reflection */
		    				try {
		    					
		    					 Method mi = drawableShapes.get(i).getClass().getMethod("draw", Graphics.class );
		    					 Object argsArray[] = {imageBufferGraphics};
		    					 mi.invoke(drawableShapes.get(i), argsArray);
		    				
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
		    }
		   
		   /* Repaint the canvas */
		   repaint();
	  }
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


    
    
    
	/**
	 * Save the session to a text file
	 * @param filename String determines where to save the object list.
	 */
    public void doSave(String filename) throws java.io.NotSerializableException{
    	
        try {
        	
        	
        	Output.processMessage("Creating File/Object output stream...", Constants.Message_Type.info);

            /* Create a file output stream and a object output stream.  
             * This will have the object list written to it */
        	FileOutputStream fileOut = new FileOutputStream(filename);

            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            Output.processMessage("Writing ArrayList Object...", Constants.Message_Type.info);

            /* Loop through the list of objects */
            for (int index = 0; index < session.currentState.currentShapes().size(); index++) {
            	out.writeObject(session.currentState.currentShapes().get(index));
            }
            

            Output.processMessage("Closing all output streams...", Constants.Message_Type.info);
            out.flush();
            out.close();
            fileOut.flush();
            fileOut.close();

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
    
    

    /**
     * Loads the contents of a previously serialized object from a file called
     * HTExample.ser.
     */
    public void doLoad(String filename) throws FileNotFoundException, IOException {
          
    	
    	/* Clear off the object list */
    	session.processClearObjects(false);
    	
    	refresh();
    	
    	/* Set the input streams to the user selected file name */
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);

        boolean FoundEOF = false;
        
        try{
        	
        	while (!FoundEOF) {
        		
        		/* Read in the objects from the serialized text file */
        		session.currentState.currentShapes().add((Shape)in.readObject());
        	}
        }catch(EOFException  e){
        	Output.processMessage("EOF found for file: " + filename, Constants.Message_Type.debug);
        }catch (ClassNotFoundException e) {
        	e.printStackTrace();
        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        session.setBaseline(session.currentState, false);

        
        in.close();
        fileIn.close();
            
   
    }

	public Session getSession() {
		return session;
	}

	public void setSession(Session s) {
		session = s;
		this.refresh();
		
	    mouseController = new MouseController(session);
	    addMouseListener((MouseListener)(mouseController));
	    addMouseMotionListener((MouseMotionListener)(mouseController));
	    
	    keyController = new KeyboardController(session);
	    addKeyListener((KeyListener)(keyController));
   
		
	}
    
    
      
}
