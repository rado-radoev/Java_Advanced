package com.knockknock.client.todelete;

import java.awt.HeadlessException;
import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class KnockKnockClient extends JFrame implements Runnable {
    
	Socket kkSocket;
    PrintWriter out;
    BufferedReader in;
    String hostName;
    private JTextArea chatTextArea;
    private JTextField userInputTextField;
	
    
    
	public KnockKnockClient(JTextField userInputTextField, JTextArea chatTextArea) {
		super();
		
		this.userInputTextField = userInputTextField;
		this.chatTextArea = chatTextArea;
		
		try {
			hostName = InetAddress.getLocalHost().getHostName();
            kkSocket = new Socket(hostName, 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
    		} catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName + ".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName + ".");
            System.exit(1);
        }
    }


	public JTextField getUserInputTextField() {
		return userInputTextField;
	}


	public void setUserInputTextField(String userInputText) {
		this.userInputTextField.setText(userInputText);
	}


	@Override
	public void run() {
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        try {
	        while ((fromServer = in.readLine()) != null) {
	            chatTextArea.append("\nServer: " + fromServer);
	        		//System.out.println("Server: " + fromServer);
	            if (fromServer.equals("Bye."))
	                break;
			    
	            fromUser = userInputTextField.getText() + "\n";/*stdIn.readLine();*/
		   			if (fromUser != null) {
		                
		   				//System.out.println("Client: " + fromUser);
		                out.println(fromUser);
		   			}
	        }
        } catch (IOException ioe) {
        		ioe.printStackTrace();
        }
        finally {
    		try {
			  out.close();
		      in.close();
		      //stdIn.close();
		      kkSocket.close();
    			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}








