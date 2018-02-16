package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;

public class KKMultiServer {
    
    boolean listening = true;
    
    
    public static void main(String[] args) throws IOException {
        KKMultiServer server = new KKMultiServer();
        
        server.startServer();
    }
    
    public void startServer() {
        System.out.println("Server starting...");
        try (ServerSocket serverSocket = new ServerSocket(4444) )
        {
            System.out.println("Server started. Listening on port: " + serverSocket.getLocalPort());
            
            while (listening) {
                new KKMultiServerThread(serverSocket.accept()).start();
            }
       }            
	   catch (IOException e) {
	       System.err.println("Could not listen on port: 4444.");
	       System.exit(-1);
	   }     
    }
}
