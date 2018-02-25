package com.knockknock.orig.todelete;


import java.net.*;
import java.io.*;

public class KKMultiServer {
    ServerSocket serverSocket;
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

		serverSocket.close();
        System.out.println("Server socket connection is closed");
    }
    
    public synchronized void setListening(Boolean listening) {
    	this.listening = listening;
    }
    
    public synchronized boolean isListening() {
    	return listening;
    }
}

