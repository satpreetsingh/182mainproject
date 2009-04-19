import java.awt.Color;


public class FreeHandFactory {

  /* ------------------------------------------------------- */	
  public FreeHandObject createFreeHand(int x, int y, Color c, int type){
		FreeHandObject o = new FreeHandObject(x,y,c,type);
		return o;
  }
  /* ------------------------------------------------------- */
}
