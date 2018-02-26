package com.knockknock.server;

import java.net.*;
import javax.swing.JOptionPane;
import com.knockknock.protocol.KnockKnockProtocol;
import java.io.*;



public class KKMultiServerThread implements Runnable {
    private Socket socket;

    public KKMultiServerThread(Socket socket) {
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
				if (inputLine.length() > 0) {
			    		outputLine = kkp.processInput(inputLine);
					out.println(outputLine);
					if (outputLine.equals("Bye"))
					    break;
				}
		    }
		    out.close();
		    in.close();
		    socket.close();
	
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(null, 
    				"Error reading jokes", 
    				"Reading error", 
    				JOptionPane.ERROR_MESSAGE);
		}
    }
}

