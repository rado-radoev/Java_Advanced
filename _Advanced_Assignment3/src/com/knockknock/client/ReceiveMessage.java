package com.knockknock.client;

import java.io.BufferedReader;

/**
* <h1>Receive Message</h1>
* Helper class that is listening for server messages and
* prints the message to JTextArea
* <p>
* <b>Note:</b> GUI needs to exist for the message to be displayed.
* Class does not create the GUI, it only uses a passed, already existing
* JTextArea.
*
* @author  Radoslav Radoev
* @version 1.0
* @since   02/25/2018
*/
public class ReceiveMessage implements Runnable {

	private BufferedReader in;
	private String fromServer;
	private javax.swing.JTextArea chatTextArea;
	
	/**
	 * Class constructor 
	 * @param in BufferedReader to read input from
	 * @param fromServer String that contains the message read form Buffer
	 * @param chatTextArea JTextArea where the message will be printed
	 */
	public ReceiveMessage(BufferedReader in, String fromServer, javax.swing.JTextArea chatTextArea) {
		super();
		this.in = in;
		this.fromServer = fromServer;
		this.chatTextArea = chatTextArea;
	}

	// Read all data in the buffer and display each line in a JTextArea
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
