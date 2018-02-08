import java.io.*;
import java.net.*;

          public class Reverse {
              public static void main(String[] args) throws Exception {

                  if (args.length != 1) {
                      System.err.println("Usage:  java Reverse "
                                         + "string_to_reverse");
                      System.exit(1);
                  }

                  String stringToReverse = URLEncoder.encode(args[0]);

                  URL url = new URL("http://java.sun.com/cgi-bin/backwards");
                  URLConnection connection = url.openConnection();
                  connection.setDoOutput(true);

                 System.out.println("doInput = " + connection.getDoInput() );

                  PrintWriter out = new PrintWriter(
                                        connection.getOutputStream());
                  out.println("string=" + stringToReverse);
                  out.close();
                  
                  System.out.println("doInput = " + connection.getDoInput() );
                  
                  BufferedReader in = new BufferedReader(
                                          new InputStreamReader(
                                          connection.getInputStream()));
                  String inputLine;

                  while ((inputLine = in.readLine()) != null)
                      System.out.println(inputLine);

                  in.close();
                  
                  System.in.read();                                    
              }
          }

          
/*  
URLEncoder method:

To convert a String, each character is examined in turn: 

     The ASCII characters 'a' through 'z', 'A' through 'Z', and '0' through '9' remain the same. 
     The space character ' ' is converted into a plus sign '+'. 
     All other characters are converted into the 3-character string "%xy", where xy is the two-digit
     hexadecimal representation of the lower 8-bits of the character. 

*/

