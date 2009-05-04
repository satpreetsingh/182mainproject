import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;
import java.util.UUID;

public class MyTwoPointShape extends MyOnePointShape implements Serializable
{
	private Point endPoint;

	void setEndPoint(Point p) {
		this.startPoint = p;
	}
}
