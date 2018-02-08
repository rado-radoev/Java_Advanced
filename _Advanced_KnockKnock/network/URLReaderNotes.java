import java.net.*;
import java.io.*;

public class URLReaderNotes 
{
   public static void main(String[] args) throws Exception 
   {
      //use a String constructor - could also use command line param
      //
      //URL myJava = new URL("http://www.concentric.net/~Jambaker/java2/homework.html");
      //can't have String literals break lines - this won't compile
      //Error message is "String not terminated at end of line"
      //URL myJava = new URL("http://www.concentric.net/~Jambaker/
      //                                    java2/homework.html");
      URL myJava = new URL("http://www.concentric.net/~Jambaker/" +
                                          "java2/homework.html");
      //relative addressing
      URL concentric = new URL("http://www.concentric.net/~Jambaker/");
      URL homework = new URL(concentric, "java2/homework.html");
      URL syllabus = new URL(concentric, "java2/syllabus.html");

      
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
