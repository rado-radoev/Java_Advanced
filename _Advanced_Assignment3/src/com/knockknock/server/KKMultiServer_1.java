package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class KKMultiServer_1 implements Runnable {
	
	private ServerSocket serverSocket;
    volatile boolean listening = true;
    
    @Override
    public void run() {
    		setServerSocket(4444);
    		
        while (isListening())
	        try {
	    			new KKMultiServerThread(serverSocket.accept()).start();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }

        closeServer();
    }
    
    public boolean isListening() {
    		return listening;
    }
    
    public void setListening(Boolean listening) {
    		this.listening = listening;
    }
    
    public void closeServer() {
        try {
        		if (serverSocket != null && !serverSocket.isClosed())
        			serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Server socket closed.");
        }
    }
    
    
    public int getServerPort() {
    		return serverSocket.getLocalPort();
    }
    
    private void setServerSocket(int port) {
        try {
        		System.out.println("Server starting...");
        		serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port: " + getServerPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port:" + getServerPort() + ".");
        }
    }
}
