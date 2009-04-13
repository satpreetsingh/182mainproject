import java.awt.event.*;
import java.util.EventListener;
import javax.swing.*;

public class ControlPanelView extends JPanel {
  
	protected DrawingCanvas canvas;
	protected ControlPanelController CPcontroller;
	protected JButton clearButton;
	protected JComboBox comboBox;
  
	
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
	    add(new JLabel("Pen color"));
	    comboBox = new JComboBox();
	    comboBox.addItem("black");
	    comboBox.addItem("blue");
	    comboBox.addItem("green");
	    comboBox.addItem("red");
	    add(comboBox);
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
    comboBox.addItemListener((ItemListener)listener);
  }
  /* ------------------------------------------------------- */
  
}