package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import com.knockknock.constants.KKServerConst;

public class KKMultiServer implements Runnable {
    private boolean listening;
    private IOException thrownException;
    
    private KKMultiServer() { }

	private static class Initializer {
		static final KKMultiServer INSTANCE = new KKMultiServer(); 
	}
	
	public static KKMultiServer getInstance() {
		return Initializer.INSTANCE;
	}
	
    @Override
    public void run() {      
        try(ServerSocket serverSocket = new ServerSocket(KKServerConst.PORT.getValue())) { 
        		serverSocket.setSoTimeout(KKServerConst.TIMEOUT.getValue());

            while (isListening()) {
	            	try {
	            		new KKMultiServerThread(serverSocket.accept()).start();
	            	} catch (SocketTimeoutException ste) { } 
            		catch (IOException ioe) {
            			throwException(ioe);
            		}
            }
            
            serverSocket.close();
            
        } catch (IOException ioe) {
        		setListening(false);
            throwException(ioe);
        }
    }
    
    private void throwException(IOException ioe) {
    		this.thrownException = ioe;
    }
    
    public synchronized IOException thrownException() {
    		return thrownException;
    }
    
    public synchronized void setListening(Boolean listening) {
    	this.listening = listening;
    }
    
    public synchronized boolean isListening() {
    	return listening;
    }
        
}