package com.knockknock.client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;

import javax.swing.*;


public class KnockKnockClientGUI extends JFrame
{
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

    public void init() throws IOException 
    {
        Socket kkSocket = null;
        //BufferedReader in = null;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            kkSocket = new Socket(InetAddress.getLocalHost().getHostName(), 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + InetAddress.getLocalHost().getHostName());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + InetAddress.getLocalHost().getHostName());
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

         
        Thread receiveMessage = new Thread(new ReceiveChat(in, stdIn, out));    
        receiveMessage.start();
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

     private class ReceiveChat implements Runnable
     {
        private BufferedReader in;
        private BufferedReader stdIn;
        private PrintWriter out;

        public ReceiveChat(BufferedReader in, BufferedReader stdIn, PrintWriter out)
        {
            this.in = in;
            this.stdIn = stdIn;
            this.out = out;
        }

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
                
                dispose();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
     }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                KnockKnockClientGUI client = new KnockKnockClientGUI();
                try
                {
                    client.init();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
