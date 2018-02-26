package com.knockknock.client.todelete;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.knockknock.server.KKServerConst;

public class KnockKnockClient_1 extends JFrame {
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String hostName;

    private BufferedReader stdIn;
    private String fromServer;
    private String fromUser;
    
	private final JPanel mainPanel;
	private final JPanel sendPanel;
	private final JButton sendButton;
	private final JTextField userInputTextField;
	private final JTextArea chatTextArea;
	private final JScrollPane chatTextAreaScrollPane;
	private final JLabel statusLabel;
	
	
	public KnockKnockClient_1() {
		super("Knock Knock Client");
		
		mainPanel = new JPanel(new BorderLayout());
		
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		
		chatTextAreaScrollPane = new JScrollPane(chatTextArea);
		chatTextAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(chatTextAreaScrollPane, BorderLayout.CENTER);
		
		sendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getUserInput();
			}
		});
		
		userInputTextField = new JTextField(10);
		userInputTextField.setFocusable(true);
		userInputTextField.requestFocusInWindow();
		userInputTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getUserInput();
			}
		});
		
		statusLabel = new JLabel("Connected");
		sendPanel.add(userInputTextField);
		sendPanel.add(sendButton);
		sendPanel.add(statusLabel);
		
		mainPanel.add(sendPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				new ServerInput(in, stdIn, out);
			}
		});
		
		t.start();
	}
	
	
	public void clientInit() {
	    try {
	    		hostName = Inet4Address.getLocalHost().getHostName();
	        kkSocket = new Socket(hostName, KKServerConst.PORT.getValue());
	        out = new PrintWriter(kkSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
	        stdIn = new BufferedReader(new InputStreamReader(System.in));
	    } catch (UnknownHostException e) {
	        System.err.println("Don't know about host: " + hostName + ".");
	        System.exit(1);
	    } catch (IOException e) {
	        System.err.println("Couldn't get I/O for the connection to: " + hostName + ".");
	        System.exit(1);
	    }
	}
	

	public void getUserInput() {
		fromUser = userInputTextField.getText() + "\n";
		if (fromUser != null) {
			chatTextArea.append(chatTextArea.getText() + "\n" + fromUser);
			out.println(fromUser);
			userInputTextField.setText("");
		}
	}
	
	public void close() {
        try {
			if (out != null)
	        		out.close();
	        if (in != null)
	        		in.close();
	        if (stdIn != null)
	        		stdIn.close();
	        if (kkSocket != null) 
	        		kkSocket.close();
        } catch (IOException ioe) {
        		ioe.printStackTrace();
        }
	}
	
	
	
    public static void main(String[] args) throws IOException {
    		SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					KnockKnockClient_1 client = new KnockKnockClient_1();
					client.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					client.setVisible(true);
					client.setSize(300, 150);
				}
			});
    }
    
	private class ServerInput implements Runnable {
		
		private BufferedReader in;
		private BufferedReader stdIn;
		private PrintWriter out;
		
		public ServerInput(BufferedReader in, BufferedReader stdIn, PrintWriter out) {
			this.in = in;
			this.stdIn = stdIn;
			this.out = out;
					
		}
		
		@Override
		public void run() {
		       try {
				while ((fromServer = in.readLine()) != null) {
					   chatTextArea.append(chatTextArea.getText() + "Server: " + fromServer + "\n");
				        if (fromServer.equalsIgnoreCase("Bye."));
				            break;
				    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
	}
}
