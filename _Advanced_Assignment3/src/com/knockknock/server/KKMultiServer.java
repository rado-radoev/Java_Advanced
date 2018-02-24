package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.knockknock.constants.KKServerConst;

public class KKMultiServer implements Runnable {
    private boolean listening;
    private IOException thrownException;
    private ExecutorService pool;
    
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

        		pool = Executors.newCachedThreadPool();
        		
            while (isListening()) {
	            	try {
	            		pool.execute(new KKMultiServerThread(serverSocket.accept()));
	            	} catch (SocketTimeoutException ste) { } 
            		catch (IOException ioe) {
            			throwException(ioe);
            		}
            }
            
            serverSocket.close();
            pool.shutdownNow();
            
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