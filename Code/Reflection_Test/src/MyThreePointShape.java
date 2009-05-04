import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;
import java.util.UUID;

public class MyThreePointShape extends MyOnePointShape implements Serializable
{
	private Point midPoint;
	private Point endPoint;

	public MyThreePointShape() {
	}
	
	public MyThreePointShape(Point startP, Point midP, Point endP) {
		this.startPoint = startP;
		this.midPoint = midP;
		this.endPoint = endP;				
	}
	
	

	void setEndPoint(Point p) {
		this.startPoint = p;
	}

	void setMidPoint(Point p) {
		this.midPoint = p;
	}
	
}
