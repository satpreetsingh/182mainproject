import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Implements a freehand drawing segment, which really consists of a bunch of lines.
 * @author bmhelppi, jjtrapan
 *
 */
public class FreeHandObject extends Shape{

	
	private ArrayList <Line> segments;
	private Color color;
	private boolean IsOutline;
	
	
	/**
	 * Create a new FreeHandObject.
	 * @param x X point for origin.
	 * @param y Y point for origin.
 	 * @param c Color of object.
	 */
	public FreeHandObject(int x, int y, Color c, boolean isoutline, UUID uniqueId)
	{
		super(uniqueId);
		Line newLine = new Line(x,y,c,isoutline, uniqueId);
		segments = new ArrayList<Line>();
		color = c;
		IsOutline = isoutline;
		
		segments.add(newLine);
	}

	/**
	 * Draw FreeHandObject
	 */
	public void draw(Graphics g) 
	{
		for(int i = 0; i < segments.size(); i++)
		{
			segments.get(i).draw(g);
		}
	}
	
	/**
	 * Freehand shape doesn't draw its own boxes.
	 */
	public void drawHighlightBoxes(Graphics g) 
	{
		
	}

	/**
	 * Return true if a point is near first/last piece of FreeHandObject.
	 */
	boolean exterior(int x, int y) 
	{
		
		Line firstLine = null;
		Line lastLine = null;
		boolean boolResult = false;
		
		firstLine = segments.get(0);
		if (segments.size() > 0)
		{
			lastLine = segments.get(segments.size() - 1);
		}
		if(firstLine != null)
		{
			boolResult = ((Math.abs(x - firstLine.origin.x) < nearTol) &&
			(Math.abs(y - firstLine.origin.y) < nearTol));
		}
		if (boolResult == false && 
				lastLine != null)
		{
			boolResult = ((Math.abs(x - lastLine.end.x) < nearTol) &&
					(Math.abs(y - lastLine.end.y) < nearTol));
		}
		return boolResult;
	}

	
	/**
	 * Return true if a point is near any part of a FreeHandObject segment.
	 */
	boolean interior(int x, int y) 
	{
		boolean output = false;
		for(int i = 0; i < segments.size(); i++)
		{
			output = segments.get(i).interior(x, y);
			if(output)
			{
				i = segments.size() + 1;
			}
			
		}
		return output;
	}

	/**
	 * Move a FreeHandObject segment.
	 */
	void move(int x, int y) {
		Line temp;
		for(int i = 0; i < segments.size(); i++)
		{
			temp = segments.get(i);
			temp.move(x, y);
		}
		
	}
	
	/**
	 * Always picks first point as origin.
	 */
	Double pickAnchor(Double p) 
	{
		return segments.get(0).origin;
	}
	
	/**
	 * Not currently effective.
	 */
	void resize(Double point, int deltaX, int deltaY) 
	{
		/* Advanced program logic needed */
	}

	
	/**
	 * Add another piece to freehand.
	 * Will be from last piece to point P.
	 * @param p End point.
	 * 
	 */
	public void appendSegment(Point p)
	{
		Line prior = segments.get(segments.size() - 1);
		
		Line l = new Line
		((int)prior.end.x, 
		 (int)prior.end.y, 
		 color,
		 IsOutline,
		 UUID.randomUUID());
		l.setSecondPoint(p.x, p.y);
		segments.add(l);
	}

	/**
	 * Set color of Freehand object.
	 */
	public void set_MainColor(Color c)
	{
		Line l;
		for(int i = 0; i < segments.size(); i++)
		{
			l = segments.get(i);
			l.set_MainColor(c);
		}
	}
	
}
