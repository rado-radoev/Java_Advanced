package com.knockknock.server;

import java.net.*;

import com.sun.swing.internal.plaf.synth.resources.synth_pt_BR;

import java.io.*;

public class KKMultiServer implements Runnable {
	
    ServerSocket serverSocket;
    boolean listening;
	
    @Override
	public void run() {

        System.out.println("Server starting...");

        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server started");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        setListening(true);
        while (isListening())
			try {
				new KKMultiServerThread(serverSocket.accept()).start();
				System.out.println("waiting for new connection");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        try {
			serverSocket.close();
			System.out.println("Socket closed");
		} catch (IOException e) {
			System.out.println("Socket closed");
		}	
	}
	
    public void closeServer() {
        try {
        		if (serverSocket != null && !serverSocket.isClosed())
        			serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Server socket canno be closed. Probably closed already.");
        }
    }
	
	public synchronized boolean isListening() {
		return listening;
	}

	public synchronized void setListening(Boolean listening) {
			this.listening = listening;
	}
}