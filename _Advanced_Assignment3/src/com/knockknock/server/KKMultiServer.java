package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class KKMultiServer implements Runnable {
	private final int port;
    private boolean listening;
    
    public KKMultiServer() {
    	super();
    	this.port = KKServerConst.PORT.getValue();

    	listening = true;
    }
	
    @Override
    public void run() {

        System.out.println("Server starting...");
        
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started ...");

            //serverSocket.setSoTimeout(KKServerConst.TIMEOUT.getValue());
            try {
	            while (isListening()) {
	    	       new KKMultiServerThread(serverSocket.accept()).start();
	            }
            } catch(SocketTimeoutException e) {
        		/* 
        		 * Time out used to close socket accept() before it is accessed illegally 
        		 * from another client that does not have permission (i.e. after server 
        		 * stops running in the middle of the while loop). If timeout was not used
        		 * a client could connect without the server explicitly creating it.
        		 */            	
            }
              catch (IOException ioe) {
            	  System.out.println("Something happened in while loop of run: ");
            	  ioe.printStackTrace();
              }
            
            serverSocket.close();

        } catch (IOException ioe) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(-1);
        }
    }
    
    public synchronized int getPort() {
    	return port;
    }
    
    public synchronized void setListening(Boolean listening) {
    	this.listening = listening;
    }
    
    public synchronized boolean isListening() {
    	return listening;
    }
        
}