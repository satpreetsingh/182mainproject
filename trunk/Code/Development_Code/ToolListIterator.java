import java.awt.*;
import java.util.*;

public class ToolListIterator {
   
  private Enumeration enumer;
  
  
  /* ------------------------------------------------------- */	
  
  public ToolListIterator(java.util.Vector list) {
    enumer = list.elements();
  }
  /* ------------------------------------------------------- */	
  
  public boolean hasNext() {
    return enumer.hasMoreElements();
  }
  /* ------------------------------------------------------- */	
  
  public Object next() {
    return enumer.nextElement();
  }
  /* ------------------------------------------------------- */	
  
}
