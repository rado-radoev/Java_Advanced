
import java.net.*;
import java.io.*;

/**
 * This class should be called to begin the KKServer threads that accept new clients.
 * KKMultiServer starts a new KKMultiServerThread for each client that serverSocket 
 * accepts. 
 *  
 * @author Java Tutorials 
 * @author Humberto Sainz
 *
 */
public class KKMultiServer implements Runnable
{
	private ServerSocket serverSocket;
	private int timeOut = KnockKnockConstants.TIMEOUT;
    private int port = KnockKnockConstants.PORT;	//
    private boolean listening = false;
    
    /**
     * This run() begins threads to accept new clients so long as KKMultiServer is
     * listening (i.e. listening = true). Server does not close until it is 
     * explicitly called by some other class (i.e. toggleServer()). When server is 
     * no longer listening it simply does not accept any more clients. The method 
     * then proceeds to close the socket to finish this run() method.  
     * 
     */
    @Override
	public void run()
	{
        try 
        {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(timeOut);
            
            while (isListening()) 
            {
            	try
            	{
            		new KKMultiServerThread(serverSocket.accept()).start();
            	}
        	
            	catch(SocketTimeoutException e)	
            	{
            		/* 
            		 * Time out used to close socket accept() before it is accessed illegally 
            		 * from another client that does not have permission (i.e. after server 
            		 * stops running in the middle of the while loop). If timeout was not used
            		 * a client could connect without the server explicitly creating it.
            		 */
            	}
            	
            	catch(IOException e)
            	{
            		System.out.println("Something happened in while loop of run: ");
            		System.out.print(e);           		
            	}
            }
        }
        
        catch (IOException e) 
        {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(-1);
        }
        
        finally
        {
        	closeServer();
        }
    }
	
	/**
	 * Toggles the server between a running state and a non running state. This 
	 * determines whether or not the server should continue to accept new clients 
	 * or skip over the code to accept new clients and finish the run() method. 
	 */
	public void toggleServer()
	{
		if(listening)
		{
			listening = false;
		}
		
		else
		{
			listening = true;
		}
	}
	
	/**
	 * Determines if the server is listening for new clients.
	 * 
	 * @return listening parameter controlling whether or not the server is listening
	 */
    public boolean isListening()
	{
		return listening;
	}
    
	/**
	 * Shuts down the server socket. Should be called for a smooth, clean exit from the
	 * run() method accepting new clients. 
	 */
	private void closeServer() 
	{
		try
		{
			serverSocket.close();
		}
		catch(IOException e)
		{
			System.out.println("Something happened in close(). Unable to close server Properly. "
					+ "Exiting: ");
    		e.printStackTrace();
    		System.exit(1);	// if server socket is messed up we should probably just quit.
		}
	}

}// end class
