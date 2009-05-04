import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.UUID;

public class MyOnePointShape extends Object implements Serializable
{
	Point startPoint;
	Color objColor;

	public MyOnePointShape() {
//		this.startPoint;
		this.objColor = Color.black;
	}

	public MyOnePointShape(Point p, Color c) {
//		this.startPoint = p;
		this.objColor = c;
	}

	void draw(Graphics g) { ; } 
	
	void setStartPoint(Point p) {
//		this.startPoint = p;
	}
}


