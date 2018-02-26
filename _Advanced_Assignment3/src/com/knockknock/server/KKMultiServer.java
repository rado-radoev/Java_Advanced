package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import com.knockknock.exceptions.CustExcep;
import com.knockknock.exceptions.ExceptionHandler;

/**
* <h1>Knock Knock Server</h1>
* Class that creates a Server Socket instance listening on a specific port.
* Creates new thread for each connection. 
* 
* <p> Class can only be instantiated once. Multiple instances are not allowed.
* 
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class KKMultiServer implements Runnable {
    private boolean listening;
    private ExecutorService pool;
    
    private KKMultiServer() { }

	private static class Initializer {
		static final KKMultiServer INSTANCE = new KKMultiServer(); 
	}
	
	public static KKMultiServer getInstance() {
		return Initializer.INSTANCE;
	}
	
	// Start new server listening on specific port
    @Override
    public void run() {      
        try(ServerSocket serverSocket = new ServerSocket(KKServerConst.PORT.getValue())) { 
        		serverSocket.setSoTimeout(KKServerConst.TIMEOUT.getValue());

        		pool = Executors.newCachedThreadPool();

        		// Listen for new connections and when new connection attempt is made, start communication
        		// in separate thread
            while (isListening()) {
	            	try {
	            		pool.execute(new KKMultiServerThread(serverSocket.accept()));
	            	} catch (SocketTimeoutException ste) { } 
            		catch (IOException ioe) {
                		javax.swing.JOptionPane.showMessageDialog(null,
                				"Server cannot accept new client connections", 
                				"Server error",
                				JOptionPane.ERROR_MESSAGE);
            		}
            }
            
            // Gracefully shutdown server socket and close all opened client threads.
            serverSocket.close();
            pool.shutdownNow();
            
        } catch (IOException ioe) {
        		javax.swing.JOptionPane.showMessageDialog(null,
        				"Servet cannot be started. Port " + KKServerConst.PORT.getValue() + " may already be in use", 
        				"Server error",
        				JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
    * Method to set if server is listening for new connections or not
    * @param listening boolean value enabling or disabling the server
    * to listen for new connection
    */
    public synchronized void setListening(Boolean listening) {
    	this.listening = listening;
    }
    
    /**
     * Method to return if server is listening or not for new connections
     * @return boolean value showing if server is listening for new connections or not
     */
    public synchronized boolean isListening() {
    	return listening;
    }
        
}