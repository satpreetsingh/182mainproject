import java.awt.Color;
import java.awt.Font;


public class TextFactory {

	protected Font defaultFont = new Font("Serif", Font.BOLD, 24);
	
	
	/* ------------------------------------------------------- */	
	
	public Text createShape
	(int x,
	 int y,
	 Color c)
	{
		Text textShape = new Text(x,y,c,defaultFont);
		return textShape;
	}
	/* ------------------------------------------------------- */	
	
	public Text createShape
	(int x,
	 int y,
	 Color c, 
	 Font f)
	{
		Text textShape = new Text(x, y, c, f);
		return textShape;
	}
	/* ------------------------------------------------------- */	
	
}
