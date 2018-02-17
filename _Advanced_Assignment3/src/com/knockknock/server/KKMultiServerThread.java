package com.knockknock.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.knockknock.protocol.KnockKnockProtocol;

public class KKMultiServerThread implements Runnable {
    private Socket socket = null;

    public KKMultiServerThread(Socket socket) {
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
        		ex.printStackTrace();
        }

    }
}
