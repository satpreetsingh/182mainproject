import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventListener;
import javax.swing.*;

import java.net.*;

public class MultiDraw extends JApplet  {

	private boolean isSlave;
	
	
	private SessionManager sessionMgr;
	protected DrawingCanvas canvas;
	protected ControlPanelView controlPanel;
	protected static InitialWindowView initialwindow;
	protected ToolBarView toolBar;
	protected MenuBarView menuBar;
	protected ArrayList<ToolController> tools;

	
	

  /* Constructors  */
  
	/**
	 * Create a new instance of MultiDraw class.
	 */
	public MultiDraw() 
	{ 
		

		
		  sessionMgr = new SessionManager();
		  
		    getContentPane().setLayout(new BorderLayout());
		    canvas = createDrawingCanvas();
		    getContentPane().add(canvas, BorderLayout.CENTER);
		    controlPanel = createControlPanelView();
		    getContentPane().add(controlPanel, BorderLayout.SOUTH);
		    tools = createTools();
		    toolBar = createToolBarView(tools);
		    getContentPane().add(toolBar, BorderLayout.WEST);
		    menuBar = createMenuBarView(tools);
		    getContentPane().add (menuBar, BorderLayout.NORTH);
		    
		    
		    /**
			   * TODO: REMOVE LATER
			   * This is temp code, it automatically creates a new session,
			   * so that Object draw functionality will work.
			   */
			String tempName;
			
			isSlave = initialwindow.getSlaveMaster();
	
		    Member temp = new Member(initialwindow.getUserName());
			  
			  Session tempAuto;
			  if (isSlave == false)
			  {
				  tempAuto = SessionUtils.buildSession(temp,canvas, tools);
			  }
			  else
			  {
				  tempAuto = SessionUtils.buildSession(temp, canvas, initialwindow.getIP(), initialwindow.getPort(), tools);
			  }
			  sessionMgr.addNewSession(tempAuto);
			  canvas.updateSession(tempAuto);
			  controlPanel.updateSession(tempAuto);
			  
			  sessionMgr.start();

	}


	
	/* Factory methods  */
 
	protected static InitialWindowView createInitialWindowView() {
		return new InitialWindowView();
	}	
	
	protected DrawingCanvas createDrawingCanvas() {
		return new DrawingCanvas();
	}
  
	protected ControlPanelView createControlPanelView() {
		return new ControlPanelView(null); 
	}
 
	protected ToolBarView createToolBarView(ArrayList<ToolController> toolList) {
		return new ToolBarView(toolList);
	}
  
	protected MenuBarView createMenuBarView(ArrayList<ToolController> toolList) {
		return new MenuBarView(canvas, toolList);
	}

	
	/* Configure tool list used for ToolBar and MenuBar construction */
  
	protected ArrayList<ToolController> createTools() {
	    ArrayList<ToolController> actions = new ArrayList();
	
	    actions.add(
	        new ToolController("Freehand",
	  	        getImageIcon("freehand.jpg"),
	  	        "freehand drawing tool",
	  	        canvas,
	  		new FreehandTool(canvas, new FreeHandFactory(), "Freehand")));
	
	    actions.add(
	  		new ToolController("Line",
	  		getImageIcon("line.jpg"),
	  		"Line drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new LineFactory(), "Line")));
	  
	    actions.add(
	  		new ToolController("Rectangle",
	  		getImageIcon("rectangle.jpg"),
	  		"Rectangle drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new RectangleFactory(), "Rectangle")));
	  		
	    actions.add(
	  	        new ToolController("Oval",
	  	        getImageIcon("oval.jpg"),
	  		"Oval drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new OvalFactory(), "Oval")));
	    actions.add(
	  		new ToolController("Text",
	  		getImageIcon("text.jpg"),
	  		"text drawing tool",
	          	canvas,
	  		new TextTool(new TextFactory(),"Text")));		
	    actions.add(
	  		new ToolController("Eraser",
	  		getImageIcon("eraser.jpg"),
	  		"Eraser drawing tool",
	  		canvas,
	  		new EraserTool("Eraser")));
	    
	  
	    /* Create a select tool to select other objects */
	    actions.add(
	      		new ToolController("Select",
	      		getImageIcon("select.jpg"),
	      		"Select tool",
	      		canvas,
	      		new SelectorTool("Select")));
	    
	    return actions;
  }
  
  protected ImageIcon getImageIcon(String fileName) {
      return new ImageIcon(fileName);
  }
  
  
    
    /**
   * Create a new instance of MultiDraw, as an application.
   * @param args Not used.
   */
  public static void main(String[] args) {
    
	  initialwindow = createInitialWindowView();

  }

  
}

  
  
  
  
  
  
