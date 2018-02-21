package com.knockknock.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.knockknock.server.KKServerConst;

public class KnockKnockClient {
	
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String hostname; 
    
	public KnockKnockClient() {
		init();
    }
	
	public void init() {
    	try {
	    	setHostName(InetAddress.getLocalHost().getHostName());
	    	
            kkSocket = new Socket(hostname, KKServerConst.PORT.getValue());
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + getHostName() + ".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + getHostName() + ".");
            System.exit(1);
        }
	}
	
    public String getHostName() {
		return hostname;
	}

	public final void setHostName(String hostName) {
		this.hostname = hostName;
	}
	
	public void readValue() {
		
		if (kkSocket.isClosed()) {
			try {
				throw new SocketException("Socket is closed");
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		
        String fromServer;
        String fromUser;

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
			while ((fromServer = in.readLine()) != null) {
			    System.out.println("Server: " + fromServer);
			    if (fromServer.equals("Bye."))
			        break;
			    
			   fromUser = stdIn.readLine();
			    if (fromUser != null) {
			            System.out.println("Client: " + fromUser);
			            out.println(fromUser);
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
    public void close() {
	    try {
	    	if (out != null)
	    		out.close();	
			if (in != null)
				in.close();
			if (kkSocket != null)
				kkSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    	
    }
	

    public static void main(String[] args) {
    		 KnockKnockClient client = new KnockKnockClient();
    		 
    		 client.readValue();
    		 client.close();
    	             
    }
    

}
