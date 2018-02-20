package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;

import com.knockknock.server.KKMultiServerThread;

public class KKMultiServer {
    volatile ServerSocket serverSocket;
    boolean listening;
	
    public void start() throws IOException {

        System.out.println("Server starting...");
        
        try {
            serverSocket = new ServerSocket(4444);
            
            System.out.println("Server started ...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (isListening()) {
        	System.out.println("listening: " + listening);
	       new KKMultiServerThread(serverSocket.accept()).start();
        }

        closeSocketConnection();
        
    }
    
    public synchronized void setListening(Boolean listening) {
    	this.listening = listening;
    }
    
    public synchronized boolean isListening() {
    	return listening;
    }
    
    public ServerSocket getServerSocket() {
    	return serverSocket;
    }
    
    public synchronized void closeSocketConnection() {
    	try {
			serverSocket.close();
			System.out.println("Server socket connection is closed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}