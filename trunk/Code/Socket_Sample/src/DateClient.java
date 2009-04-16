import java.io.*;
import java.net.*;
import java.util.*;

public class DateClient {
   public static void main(String argv[]) {
      ObjectOutputStream oos = null;
      ObjectInputStream ois = null;
      Socket socket = null;
      Date date = null;
      try {
        // open a socket connection
          //127.0.0.1 represents the machine on which the Server will run
          //In my case i used my local machine as the server hence the ip address
    	 
        socket = new Socket("127.0.0.1", 3000);
        // open I/O streams for objects
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        // read an object from the server
        date = (Date) ois.readObject();
        System.out.print("The date is: " + date);
        oos.close();
        ois.close();
      } catch(Exception e) {
        System.out.println(e.getMessage());
      }
   }
}
