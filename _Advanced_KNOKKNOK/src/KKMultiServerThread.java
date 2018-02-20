
import java.net.*;
import java.io.*;

/**
 * This class handles the communication between server and client through the
 * use of the KnockKnockProtocol. Input is handled in a separate thread to 
 * allow responsiveness to multiple clients. 
 * 
 * @author Edited by Humberto Sainz
 *
 */
public class KKMultiServerThread extends Thread 
{
    private Socket socket = null;

    public KKMultiServerThread(Socket socket) 
    {
    	super("KKMultiServerThread");
    	this.socket = socket;
    }

    /**
     * Creates input/output streams from the socket that was created by the 
     * server.accept() method when a new client connects. It then begins to 
     * communicate with the client via the output/input streams from that 
     * socket. The communication of the messages is handled via the 
     * KnockKnockProtocol.  
     * 
     */
    public void run() 
    {
    	try 
    	{
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    		BufferedReader in = new BufferedReader(
    				new InputStreamReader(
				    socket.getInputStream()));

    		String inputLine, outputLine;
    		KnockKnockProtocol kkp = new KnockKnockProtocol(); 
    		outputLine = kkp.processInput(null);
    		out.println(outputLine);
    		
    		while ((inputLine = in.readLine()) != null) 
    		{
    			outputLine = kkp.processInput(inputLine);
    			out.println(outputLine);
    			if (outputLine.equals("Bye"))
    				break;
    		}
    		
    		out.close();
    		in.close();
    		socket.close();

    	} 
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    	}
    }
    
} // end class
