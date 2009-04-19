import java.awt.event.*;
import java.util.EventListener;
import javax.swing.*;

public class ControlPanelView extends JPanel {
  
	protected DrawingCanvas canvas;
	protected ControlPanelController CPcontroller;
	protected JButton clearButton;
	protected JComboBox comboType;
	protected JComboBox comboColor;

	
	/* ------------------------------------------------------- */
	/**
	 * Create a new ControlPanelView
	 * @param c Canvas that the panel is linked to.
	 */
	
	public ControlPanelView(DrawingCanvas c) 
	{ 
	    canvas = c;
	    clearButton = new JButton("Clear");
	    add(clearButton);
	    
	    comboType = new JComboBox();
	    
	    /* Make sure the set the name of this component for evaluation in the controller */
	    comboType.setName("comboType");
	    
	    comboType.addItem("outline");
	    comboType.addItem("solid");
	    add(comboType);
	    
	    
	    
	    comboColor = new JComboBox();
	    
	    /* Make sure the set the name of this component for evaluation in the controller */
	    comboColor.setName("comboColor");

	    comboColor.addItem("black");
	    comboColor.addItem("blue");
	    comboColor.addItem("green");
	    comboColor.addItem("red");
	    add(comboColor);
	    
	    ControlPanelController CPcontroller =
	        createControlPanelController();
	    addControlPanelListener(CPcontroller);
  }

  /* ------------------------------------------------------- */
  protected ControlPanelController 
          createControlPanelController() {
      return new ControlPanelController(canvas);
  }
  /* ------------------------------------------------------- */
  
  protected void addControlPanelListener(EventListener listener)  {
    clearButton.addActionListener((ActionListener)listener);
    comboColor.addItemListener((ItemListener)listener);
    comboType.addItemListener((ItemListener)listener);
  }
  /* ------------------------------------------------------- */
  
}