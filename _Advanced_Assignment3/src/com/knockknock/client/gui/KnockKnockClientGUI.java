package com.knockknock.client.gui;

 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.knockknock.client.KnockKnockClient;
import com.sun.java.swing.plaf.windows.resources.windows;

public class KnockKnockClientGUI extends JFrame {

	private static final long serialVersionUID = 4379695112985425572L;
	
	private String fromServer;
	private String fromUser;
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;
    BufferedReader stdIn;
    private String hostname; 

	private final JPanel mainPanel;
	private final JPanel sendPanel;
	private final JButton sendButton;
	private final JTextField userInputTextField;
	private final JTextArea chatTextArea;
	private final JScrollPane chatTextAreaScrollPane;
	private final JLabel statusLabel;
	
	public KnockKnockClientGUI() {
		super("Knock Knock Client");
		
		initializeCLient();
		
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

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(300, 150);
		
		this.addWindowListener(new WindowAdapter() {
			public void WindowOpened(WindowEvent e) {
				userInputTextField.requestFocus();
			}
		});
		
		Thread receiveMessage = new Thread(new ReceiveChat(in, stdIn, out));
		receiveMessage.start();
		
	}
	
	private void getUserInput() {
		fromUser = userInputTextField.getText() + "\n";
		if (fromUser != null) {
			chatTextArea.append(chatTextArea.getText() + "\n" + fromUser);
			out.println(fromUser);
			userInputTextField.setText("");
		}
	}
	
	private final void initializeCLient() {
		try {
		    hostname = InetAddress.getLocalHost().getHostName();
	        kkSocket = new Socket(hostname, 4444);
	        out = new PrintWriter(kkSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
	        stdIn = new BufferedReader(new InputStreamReader(System.in));
	    } catch (UnknownHostException e) {
	        System.err.println("Don't know about host: " + hostname + ".");
	        System.exit(1);
	    } catch (IOException e) {
	        System.err.println("Couldn't get I/O for the connection to: " + hostname + ".");
	        System.exit(1);
	    }
	}
	
	private class ReceiveChat implements Runnable {
		
		private BufferedReader in;
		private BufferedReader stdIn;
		private PrintWriter out;
		
		public ReceiveChat(BufferedReader in, BufferedReader stdIn, PrintWriter out) {
			this.in = in;
			this.stdIn = stdIn;
			this.out = out;
		}

		@Override
		public void run() {
			try {
				while ((fromServer = in.readLine()) != null) {
					chatTextArea.append(chatTextArea.getText() + 
							"Server: " +
							fromServer +
							"\n");
					chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
					
					if (fromServer.equalsIgnoreCase("bye."))
						break;
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new KnockKnockClientGUI();
            }
        });
    }	
}
