import java.awt.Color;


public class FreeHandFactory {

  /* ------------------------------------------------------- */	
  public FreeHandObject createFreeHand(int x, int y, Color c){
		FreeHandObject o = new FreeHandObject(x,y,c);
		return o;
  }
  /* ------------------------------------------------------- */
}
