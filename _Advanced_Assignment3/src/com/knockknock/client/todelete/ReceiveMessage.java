package com.knockknock.client.todelete;

import java.io.BufferedReader;
import java.io.PrintWriter;

class ReceiveMessage implements Runnable {
	
	private BufferedReader in;
    private BufferedReader stdIn;
    private PrintWriter out;
	private String fromServer;
	
	public ReceiveMessage(BufferedReader in, BufferedReader stdIn, PrintWriter out, String fromServer) {
		super();
		this.in = in;
		this.stdIn = stdIn;
		this.out = out;
		this.fromServer = fromServer;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
}
