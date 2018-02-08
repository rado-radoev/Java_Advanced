import java.net.*;
import java.io.*;

public class URLReader 
{
   public static void main(String[] args) throws Exception 
   {
      URL myJava = new URL("http://www.concentric.net/~Jambaker/java2/homework.html");
      
      BufferedReader in = new BufferedReader(
                          new InputStreamReader(
                              myJava.openStream()));

      String inputLine;

      while ((inputLine = in.readLine()) != null)
         System.out.println(inputLine);

      in.close();
      System.in.read();
   }
}
