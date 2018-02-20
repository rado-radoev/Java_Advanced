package com.knockknock.orig;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.KeyManagementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KnockKnockServerGUI extends JFrame {
	
	// TO DO: ADD MENU TO ADD CLIENTS
	// EACH NEW CLIENT WILL BE RECORDED IN A LABEL
	// KEEP TRACK OF RUNNING CLIENTS
	
	// FIX WHILE LOOP IN KKMULTI THRED - IT IS LOCKING UP THE GUI 

	private static final long serialVersionUID = 6995715762008339632L;
	private final JPanel mainPanel;
	private final JButton startServer;
	private final JButton stopServer;
	private final JLabel statusLabel;
	private final JPanel buttonPanel;
	private final BorderLayout mainLayout;
	private final FlowLayout buttonLayout;
	private final ExecutorService pool;
	
	private KKMultiServer server;
	
	public KnockKnockServerGUI() {
		super("Knock Knock Server");
		 
		server = new KKMultiServer();
		pool = Executors.newCachedThreadPool();
		
		mainLayout = new BorderLayout();
		mainPanel = new JPanel(mainLayout);
		
		buttonLayout = new FlowLayout(FlowLayout.CENTER, 5, 1);
		ButtonHandler buttonHandler = new ButtonHandler();
		startServer = new JButton("Start server");
		startServer.addActionListener(buttonHandler);
		stopServer = new JButton("Stop server");
		stopServer.addActionListener(buttonHandler);
		buttonPanel = new JPanel(buttonLayout);
		
		buttonPanel.add(startServer);
		buttonPanel.add(stopServer);
		
		statusLabel = new JLabel("The status will be shown here: ");
		
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(statusLabel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
	}
	
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startServer) {
				
				pool.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
							server.start();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				startServer.setEnabled(false);
				stopServer.setEnabled(true);
			}
			else if (e.getSource() == stopServer) {
				try {
					server.listening = false;
					pool.awaitTermination(2, TimeUnit.SECONDS);
					server.serverSocket.close();
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				startServer.setEnabled(true);
				stopServer.setEnabled(false);
				statusLabel.setText("");
				statusLabel.setText("Server stopped");
			}
		}
		
	}
	
	public static void main(String[] args) {
		KnockKnockServerGUI gui = new KnockKnockServerGUI();
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(450, 350);
		gui.setVisible(true);
	}
	
}









