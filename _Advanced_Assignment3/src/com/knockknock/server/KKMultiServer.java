package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import com.knockknock.constants.KKServerConst;

public class KKMultiServer implements Runnable {
	private final int port;
    private boolean listening;
    
    public KKMultiServer() {
	    	super();
	    	this.port = KKServerConst.PORT.getValue();
    }
	
    @Override
    public void run() {      
        try(ServerSocket serverSocket = new ServerSocket(port)) { 
        		serverSocket.setSoTimeout(KKServerConst.TIMEOUT.getValue());

            while (isListening()) {
	            	try {
	            		new KKMultiServerThread(serverSocket.accept()).start();
	            	} catch (SocketTimeoutException ste) { } 
            		catch (IOException ioe) {
	            	  ioe.printStackTrace();
	              }
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