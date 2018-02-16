package javaapplication5.server;


import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaapplication5.protocol.KnockKnockProtocol;

public class KKMultiServerThread extends Thread {
    private Socket socket = null;

    public KKMultiServerThread(Socket socket) {
	super("KKMultiServerThread");
	this.socket = socket;
    }

    @Override
    public void run() {
        
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

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
                
            //socket.close();
        } catch (IOException ex) {
            Logger.getLogger(KKMultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
