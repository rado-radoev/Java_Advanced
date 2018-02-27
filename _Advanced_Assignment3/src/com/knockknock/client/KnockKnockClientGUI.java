package com.knockknock.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;

import javax.swing.*;

/**
* <h1>Knock Knock Client GUI</h1>
* Class that impelemnts the Graphical User Interface and the Knock Knock Client
* functionality. The class will create JFrame that will accept user input and
* send it to a local server. Will display the message sent back from the server
* and will wait for another message from the user. 
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class KnockKnockClientGUI extends JFrame implements Runnable
{

    private ExecutorService pool;
	
	private String fromServer;
    private String fromUser;
    private BufferedReader in = null;
    
    private JTextArea chatTextArea;
    private JTextField userInputTextField;
    private JScrollPane chatScrollPane;
    private JPanel mainPanel;
    private JButton sendButton;
    private JPanel bottomPanel;
    private PrintWriter out;

    /**
     * Method that initalizes the client GUI and makes a connection to a local server
     */
    public void init() 
    {
        Socket kkSocket = null;
        String hostName = "";

		// Attempt to connect to the server
        try {
        		hostName = InetAddress.getLocalHost().getHostName();
            kkSocket = new Socket(hostName, 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
        		JOptionPane.showMessageDialog(null, 
        				"Don't know about host: " + hostName, 
        				"File Missing", 
        				JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (IOException e) {
    			JOptionPane.showMessageDialog(null, 
    				"Couldn't get I/O for the connection to: " + hostName, 
    				"File Missing", 
    				JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Create the client GUI
        mainPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new FlowLayout());
        
        Handler handler = new Handler();
        
        chatTextArea = new JTextArea(10, 10);
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatScrollPane = new JScrollPane(chatTextArea);
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(chatScrollPane, BorderLayout.CENTER);
        
        userInputTextField = new JTextField(10);
        userInputTextField.requestFocusInWindow();
        userInputTextField.addActionListener(handler);
        bottomPanel.add(userInputTextField);
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(handler);
        bottomPanel.add(sendButton);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(500, 300);
        setVisible(true); 

        // New listening thread started for each client 
        new Thread(new ReceiveMessage(in, fromServer, chatTextArea)).start();
     }   
    
    /**
     * Method to read text entered from user and send to server
     */
    private void getUserInput() {
    	fromUser = userInputTextField.getText() + "\n";
        if (fromUser != null) 
        {
            chatTextArea.append("Client: " + fromUser);
            out.println(fromUser);
            userInputTextField.setText("");
        }
    }
    
    /**
     * Anonymous Inner class that handles user input into a text field
     * @author Radoslav Radoev
     *
     */
    private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			getUserInput();
		}
    	
    }
    
    /**
     * Method to return an instance of the thread pool
     * @return ExecutorService instance
     */
    public ExecutorService getThreadPool() {
    		return pool;
    }

    // Initialize the Knock Knock Client
    @Override
	public void run() {
		init();
	}
}
