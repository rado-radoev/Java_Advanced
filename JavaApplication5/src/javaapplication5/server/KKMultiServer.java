package javaapplication5.server;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KKMultiServer {
    
    ServerSocket serverSocket = null;
    boolean listening = true;
    
    
    public static void main(String[] args) throws IOException {
        KKMultiServer server = new KKMultiServer();
        
        server.startServer();
    }
    
    public void startServer() {
        System.out.println("Server starting...");
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server started. Listening on port: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        
        
       while (listening)
            try {
                new KKMultiServerThread(serverSocket.accept()).start();
            } catch (IOException ex) {
                Logger.getLogger(KKMultiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(KKMultiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
