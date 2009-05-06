


/**
 * Does some common math functions for shapes.
 * @author ben
 *
 */
public class ShapeMath 
{

	static int interior = 0;
	static int exterior = 1;
	static int neither = 2;
	
	
	/**
	 * Determine if a # is between two other #s, and a given tolerance.
	 * @param p1 Bracket one
	 * @param p2 Bracket two
	 * @param test Test point.
	 * @param tol Tolerance.
	 */
	public static boolean bracket(double p1, double p2, double test, double tol)
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
}
