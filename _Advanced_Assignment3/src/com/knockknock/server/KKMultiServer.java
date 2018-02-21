package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;

public class KKMultiServer implements Runnable {
	private final int port;
    private boolean listening;
    
    public KKMultiServer(int... port) {
    	super();
    	if (port.length > 1 || port[0] <= 1024) 
    		throw new IllegalArgumentException("Select port in range 1025 - 65535");
    	
    	if (port == null)
    		this.port = KKServerConstants.PORT.getValue();
    	else 
    		this.port = port[0];
    		
    }
	
    @Override
    public void run() {

        System.out.println("Server starting...");
        
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started ...");

    		/* 
    		 * Time out used to close socket accept() before it is accessed illegally 
    		 * from another client that does not have permission (i.e. after server 
    		 * stops running in the middle of the while loop). If timeout was not used
    		 * a client could connect without the server explicitly creating it.
    		 */
            serverSocket.setSoTimeout(KKServerConstants.TIMEOUT.getValue());
            
            while (isListening()) {
    	       new KKMultiServerThread(serverSocket.accept()).start();
            }

        } catch (IOException ioe) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(-1);
        }
    }
    
    
    public synchronized void setListening(Boolean listening) {
    	this.listening = listening;
    }
    
    public synchronized boolean isListening() {
    	return listening;
    }
        
}