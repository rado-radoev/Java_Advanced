package com.knockknock.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class KnockKnockClient {
	
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String laptopName; 
    
	public KnockKnockClient() {
	    	try {
    		    setLaptopName(InetAddress.getLocalHost().getHostName());
            kkSocket = new Socket(laptopName, 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + getLaptopName() + ".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + getLaptopName() + ".");
            System.exit(1);
        }
    }
	
    public String getLaptopName() {
		return laptopName;
	}

	public final void setLaptopName(String laptopName) {
		this.laptopName = laptopName;
	}
	
	public void readValue() {
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
	
    public void closeCleanup() {
	    try {
	    		out.close();
			in.close();
			kkSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    	
    }
	

    public static void main(String[] args) {
    		 KnockKnockClient client = new KnockKnockClient();
    		 
    		 client.readValue();
    		 client.closeCleanup();
    	             
    }
    

}
