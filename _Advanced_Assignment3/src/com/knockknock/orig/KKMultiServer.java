package com.knockknock.orig;

import java.net.*;
import java.io.*;

public class KKMultiServer implements Runnable {
	
    ServerSocket serverSocket = null;
    boolean listening = true;
	
	public void run() {

        System.out.println("Server starting...");

        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server started");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (listening)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
