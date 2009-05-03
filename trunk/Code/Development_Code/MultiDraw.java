import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventListener;
import javax.swing.*;

import java.net.*;

public class MultiDraw extends JApplet {

	private boolean isSlave;
	
	private DrawingCanvas canvas;
	private ControlPanelView controlPanel;
	private ControlPanelController controlPanelController;

	private ToolBarView toolBar;
	private MenuBarView menuBar;
	private ArrayList<ToolController> tools;
	private ChatPanelView chatPanelView;
    private ChatPanelController chatPanelController;
    
    private static InitialWindowView initialwindow;
	private MenuBarController menuBarController;
	

	/**
	 * Create a new instance of MultiDraw class.
	 */
	public MultiDraw(SessionManager sessionMgr) 
	{ 
			
		controlPanelController = new ControlPanelController();
		getContentPane().setLayout(new BorderLayout());
		canvas = new DrawingCanvas();
		getContentPane().add(canvas, BorderLayout.CENTER);
	    chatPanelView = new ChatPanelView();
	    chatPanelController = new ChatPanelController(chatPanelView);
	    getContentPane().add(chatPanelView,BorderLayout.EAST);
		controlPanel = new ControlPanelView(controlPanelController);
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
		tools = createTools();
		toolBar = new ToolBarView(tools);
		
		getContentPane().add(toolBar, BorderLayout.WEST);
		
		menuBar = new MenuBarView(canvas, tools);

		
		menuBarController = new MenuBarController(canvas, menuBar);
		menuBar.AddController(menuBarController);
		
	  	getContentPane().add (menuBar, BorderLayout.NORTH);
	  	
	  	
		isSlave = initialwindow.getSlaveMaster();
	    Member tempMember = new Member(initialwindow.getUserName());
		Session tempSession;
		if (isSlave == false)
		{
		    tempSession = SessionUtils.buildSession(tempMember,canvas, tools,chatPanelView, controlPanel);
		}
		else
		{
            tempSession = SessionUtils.buildSession(tempMember, canvas, initialwindow.getIP(), initialwindow.getPort(), tools,chatPanelView, controlPanel);
		}

	  	
	  	
        sessionMgr.addSessionChangeListenObject(canvas);	
        sessionMgr.addSessionChangeListenObject(chatPanelView);
        sessionMgr.addSessionChangeListenObject(chatPanelController);
        sessionMgr.addSessionChangeListenObject(controlPanel);
        sessionMgr.addSessionChangeListenObject(controlPanelController);
        sessionMgr.addSessionChangeListenObject(menuBarController);
     	sessionMgr.addNewSession(tempSession);
        
	}
 

  

	
	/* Configure tool list used for ToolBar and MenuBar construction */
  
	protected ArrayList<ToolController> createTools() {
	    ArrayList<ToolController> actions = new ArrayList<ToolController>();
	
	    actions.add(
	        new ToolController("Freehand",
    		new ImageIcon("freehand.jpg"),
  	        "freehand drawing tool",
  	        canvas,
	  		new FreehandTool(canvas, new FreeHandFactory(), "Freehand")));
	
	    actions.add(
	  		new ToolController("Line",
			new ImageIcon("line.jpg"),
	  		"Line drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new LineFactory(), "Line")));
	  
	    actions.add(
	  		new ToolController("Rectangle",
			new ImageIcon("rectangle.jpg"),
	  		"Rectangle drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new RectangleFactory(), "Rectangle")));
	  		
	    actions.add(
  	        new ToolController("Oval",
    		new ImageIcon("oval.jpg"),
	  		"Oval drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new OvalFactory(), "Oval")));
	    actions.add(
	  		new ToolController("Text",
			new ImageIcon("text.jpg"),
	  		"text drawing tool",
          	canvas,
	  		new TextTool(new TextFactory(),"Text")));		
	    actions.add(
	  		new ToolController("Eraser",
			new ImageIcon("eraser.jpg"),
	  		"Eraser drawing tool",
	  		canvas,
	  		new EraserTool("Eraser")));
	    
	  
	    /* Create a select tool to select other objects */
	    actions.add(
	      		new ToolController("Select",
  				new ImageIcon("select.jpg"),
	      		"Select tool",
	      		canvas,
	      		new SelectorTool("Select")));
	    
	    return actions;
  }
  
  
    
    /**
   * Create a new instance of MultiDraw, as an application.
   * @param args Not used.
   */
  public static void main(String[] args) {
	 
	  ShutdownInterface sessionManger = new SessionManager();
	  ShutdownHandler shutDown = new ShutdownHandler(sessionManger);
	  
	  Runtime.getRuntime().addShutdownHook(shutDown);
	  
	  sessionManger.start();
	  initialwindow = new InitialWindowView((SessionManager)sessionManger);
	 
  }
  
  
}

  
  
  
  
  
  
