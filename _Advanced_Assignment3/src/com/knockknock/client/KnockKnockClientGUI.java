package com.knockknock.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;

import com.knockknock.orig.KnockKnockClient;


public class KnockKnockClientGUI extends JFrame implements Runnable
{
    private String fromServer;
    private String fromUser;
    private BufferedReader in = null;
    
    private ExecutorService pool;

    private JTextArea chatTextArea;
    private JTextField userInputTextField;
    private JScrollPane chatScrollPane;
    private JPanel mainPanel;
    private JButton sendButton;
    private JPanel bottomPanel;
    private PrintWriter out;

    public void init() 
    {
        Socket kkSocket = null;
        String hostName = "";

        try {
        	hostName = InetAddress.getLocalHost().getHostName();
            kkSocket = new Socket(hostName, 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName);
            System.exit(1);
        }

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
        setSize(350, 250);
        setVisible(true); 

        new Thread(new ReceiveMessage(in, fromServer, chatTextArea)).start();;
     }   
    
    private void getUserInput() {
    	fromUser = userInputTextField.getText() + "\n";
        if (fromUser != null) 
        {
            chatTextArea.append(fromUser);
            out.println(fromUser);
            userInputTextField.setText("");
        }
    }
    
    private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			getUserInput();
		}
    	
    }
    
    public ExecutorService getThreadPool() {
    		return pool;
    }

    @Override
	public void run() {
		init();
		
	}
}
