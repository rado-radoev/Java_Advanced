package com.knockknock.server;

import java.io.IOException;
import java.net.ServerSocket;

public class KKMultiServer {
	
	ServerSocket serverSocket = null;
    boolean listening = true;
    
    
    public static void main(String[] args) throws IOException {
        KKMultiServer server = new KKMultiServer();
        
        server.startServer();
    }
    
    public void startServer() {
        setServerSocket(4444);
        
        
       while (listening) {
            try {
                new KKMultiServerThread(serverSocket.accept()).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
       }
    }
    
    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public int getServerPort() {
    		return serverSocket.getLocalPort();
    }
    
    private void setServerSocket(int port) {
        System.out.println("Server starting...");
        try {
        		serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port: " + getServerPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port:" + getServerPort() + " .");
        }
    }
}