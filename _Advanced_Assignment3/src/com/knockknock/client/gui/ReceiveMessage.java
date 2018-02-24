package com.knockknock.client.gui;

import java.io.BufferedReader;

public class ReceiveMessage implements Runnable {

	private BufferedReader in;
	private String fromServer;
	private javax.swing.JTextArea chatTextArea;
	
	
	public ReceiveMessage(BufferedReader in, String fromServer, javax.swing.JTextArea chatTextArea) {
		super();
		this.in = in;
		this.fromServer = fromServer;
		this.chatTextArea = chatTextArea;
	}

	@Override
    public void run()
    {
        try
        {
            while ((fromServer = in.readLine()) != null) {
            String str = "Server : " + fromServer + "\n";
            chatTextArea.append(str);
            chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());

            if (fromServer.equals("Bye."))
                break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
