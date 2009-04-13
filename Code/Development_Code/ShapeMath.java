
import java.awt.*;
import java.awt.geom.Point2D;
public class ShapeMath 
{

	static int interior = 0;
	static int exterior = 1;
	static int neither = 2;
	
	
	/* ------------------------------------------------------- */	
	static boolean bracket(double p1, double p2, double test, double tol)
	{
		boolean result;
		if(p1 > p2)
		{
			if ((test > p2 && test < p1) ||
			   (Math.abs(p1 - test) <= tol) ||
			   (Math.abs(p2 - test) <= tol))
			{
				result = true;
			}
			else
			{
				result = false;
			}
				
		}
		else
		{
			if ((test > p1 && test < p2) ||
					   (Math.abs(p1 - test) <= tol) ||
					   (Math.abs(p2 - test) <= tol))
			{
				result = true;
			}
			else
			{
				result = false;
			}
		}
		return result;
	}
	/* ------------------------------------------------------- */	
}
