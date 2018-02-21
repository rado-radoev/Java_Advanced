package com.knockknock.server;


import java.net.*;

import com.knockknock.protocol.KnockKnockProtocol;

import java.io.*;

public class KKMultiServerThread extends Thread {
    private Socket socket;

    public KKMultiServerThread(Socket socket) {
    	super("KKMultiServerThread");
    	this.socket = socket;
    }

    @Override
    public void run() {

	try ( PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		  BufferedReader in = new BufferedReader(
					    	  new InputStreamReader(
		    					socket.getInputStream()))) {
	
		    String inputLine, outputLine;
		    KnockKnockProtocol kkp = new KnockKnockProtocol();
		    outputLine = kkp.processInput(null);
		    out.println(outputLine);
	
		    while ((inputLine = in.readLine()) != null) {
			outputLine = kkp.processInput(inputLine);
			out.println(outputLine);
			if (outputLine.equals("Bye"))
			    break;
		    }
		    out.close();
		    in.close();
	    	socket.close();
	
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}

