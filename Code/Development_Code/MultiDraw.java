import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;
import javax.swing.*;
import java.net.*;

public class MultiDraw extends JApplet  {

	private boolean isMaster = false;
	
	
	
	private SessionManager sessionMgr;
	protected DrawingCanvas canvas;
	protected ControlPanelView controlPanel;
	protected ToolBarView toolBar;
	protected MenuBarView menuBar;
	protected ToolList toolList;
	protected boolean isApplet = false;

  /* Constructors  */
  
	/**
	 * Create a new instance of MultiDraw class.
	 */
	public MultiDraw(boolean isApplet) 
	{ 
		this.isApplet = isApplet;
		if (!isApplet) 
		{
			init();
		}
	}
  
	/**
	 *  Create a new instance of MultiDraw class.
	 */
	public MultiDraw() 
	{
		/* invoked as Applet */
		this(true);
	}
  
	/**
	 * Initialize class.
	 */
	public void init() 
	{
	  sessionMgr = new SessionManager();
	  
    getContentPane().setLayout(new BorderLayout());
    canvas = createDrawingCanvas();
    getContentPane().add(canvas, BorderLayout.CENTER);
    controlPanel = createControlPanelView();
    getContentPane().add(controlPanel, BorderLayout.SOUTH);
    toolList = createToolList();
    toolBar = createToolBarView(toolList);
    getContentPane().add(toolBar, BorderLayout.WEST);
    menuBar = createMenuBarView(toolList);
    getContentPane().add (menuBar, BorderLayout.NORTH);
    
    
    /**
	   * TODO: REMOVE LATER
	   * This is temp code, it automatically creates a new session,
	   * so that Object draw functionality will work.
	   */
	  String tempName;
    if(isMaster)
    {
    	tempName = "Ben_Master";
    }
    else
    {
    	tempName = "Ben_Slave";
    }
    	Member temp = new Member(tempName);
	  
	  Session tempAuto;
	  if (isMaster)
	  {
		  tempAuto = ServerUtils.buildSession(temp,canvas);
	  }
	  else
	  {
		  tempAuto = ServerUtils.buildSession(temp, canvas, "192.168.1.3", 3000);
		  //TODO:
	  }
	  sessionMgr.addNewSession(tempAuto);
	  canvas.updateSession(tempAuto);
	  sessionMgr.start();
  }

	
	/* Factory methods  */
  
	protected DrawingCanvas createDrawingCanvas() {
		return new DrawingCanvas();
	}
  
	protected ControlPanelView createControlPanelView() {
		return new ControlPanelView(canvas); 
	}
 
	protected ToolBarView createToolBarView(ToolList toolList) {
		return new ToolBarView(toolList);
	}
  
	protected MenuBarView createMenuBarView(ToolList toolList) {
		return new MenuBarView(toolList);
	}

	
	/* Configure tool list used for ToolBar and MenuBar construction */
  
	protected ToolList createToolList() {
	    ToolList actions = new ToolList();
	
	    actions.add(
	                new ToolController("Freehand",
	  	        getImageIcon("freehand.jpg"),
	  	        "freehand drawing tool",
	  	        canvas,
	  		new FreehandTool(canvas, new FreeHandFactory())));
	
	    actions.add(
	  		new ToolController("Line",
	  		getImageIcon("line.jpg"),
	  		"Line drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new LineFactory())));
	  
	    actions.add(
	  		new ToolController("Rectangle",
	  		getImageIcon("rectangle.jpg"),
	  		"Rectangle drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new RectangleFactory())));
	  		
	    actions.add(
	  	        new ToolController("Oval",
	  	        getImageIcon("oval.jpg"),
	  		"Oval drawing tool",
	  		canvas,
	  		new TwoPointShapeTool(new OvalFactory())));
	    actions.add(
	  		new ToolController("Text",
	  		getImageIcon("text.jpg"),
	  		"text drawing tool",
	          	canvas,
	  		new TextTool(new TextFactory())));		
	    actions.add(
	  		new ToolController("Eraser",
	  		getImageIcon("eraser.jpg"),
	  		"Eraser drawing tool",
	  		canvas,
	  		new EraserTool()));
	    
	  
	    /* Create a select tool to select other objects */
	    actions.add(
	      		new ToolController("Select",
	      		getImageIcon("select.jpg"),
	      		"Select tool",
	      		canvas,
	      		new SelectorTool()));
	    
	    return actions;
  }
  
  protected ImageIcon getImageIcon(String fileName) {
    if(isApplet) 
    {
      try {
        URL url = new URL(getCodeBase(), fileName);
        return new ImageIcon(url);
      }
      catch(MalformedURLException e) {
        return null;
      }
    }
    else {
      return new ImageIcon(fileName);
    }
  }

  /**
   * Create a new instance of MultiDraw, as an application.
   * @param args Not used.
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setTitle("MultiDraw Development Build");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(new MultiDraw(false),
			      BorderLayout.CENTER);
    frame.addWindowListener(new AppCloser());
    frame.pack();
    frame.setSize(600, 600);
    frame.setVisible(true);
  }

  
  /* Inner class AppCLoser for terminating application  */
  /* when Close Window button of frame is clicked       */
  static class AppCloser extends WindowAdapter  {
    public void windowClosing(WindowEvent e) {
       System.exit(0);
    }
  }
  
}