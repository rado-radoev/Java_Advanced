package com.knockknock.server;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import com.knockknock.client.KnockKnockClientGUI;
import com.sun.glass.events.KeyEvent;

/**
* <h1>Knock Knock Server Graphical User Interface</h1>
* Class that creates a Server Start/Stop Graphical User Interface.
* </br>New clients can be started from this GUI, only when a server is running.
* 
* 
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class KKServerGUI extends JFrame {
	
	private static final long serialVersionUID = 6995715762008339632L;
	
	private final JPanel mainPanel;
	private final JButton startServer;
	private final JButton stopServer;
	private final JLabel statusLabel;
	private final JPanel buttonPanel;
	private final BorderLayout mainLayout;
	private final FlowLayout buttonLayout;
	private final JMenuBar menuBar;
	private final JMenu fileMenu;
	private final JMenu helpMenu;
	private final JMenuItem client;
	
	private final ExecutorService pool;
	private KKMultiServer server;
	
	public KKServerGUI() {
		super("Knock Knock Server");
		 
		// Create new server instance and start it
		server = KKMultiServer.getInstance();
		pool = Executors.newFixedThreadPool(KKServerConst.MAXTHREADS.getValue());

		// Build the GUI
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
		
		statusLabel = new JLabel("Server is: " + (server.isListening() ? "running" : "stopped"));
		
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(statusLabel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		client = new JMenuItem("Client");
		client.setMnemonic(KeyEvent.VK_C);
		client.setToolTipText("Start new client");
		client.setEnabled(false);
		// Client can only be started once the server is running
		client.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
					
						@Override
						public void run() {
							new KnockKnockClientGUI().run();
						}
					}).start();
			}
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setToolTipText("Exit");
		exit.addActionListener((ActionEvent event) -> {
			pool.shutdownNow();
			System.exit(0);
			});
		
		fileMenu.add(client);
		fileMenu.add(exit);
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem about = new JMenuItem("About");
		about.setMnemonic(KeyEvent.VK_A);
		about.setToolTipText("About");
		about.addActionListener((ActionEvent event) -> {
			JOptionPane.showMessageDialog(this, 
					"Radoslav Radoev - Java Programming IV : \nAdvanced Java Programming Structures");
			});
		
		helpMenu.add(about);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
		
	}
	
	/**
	* <h1>Button Handler</h1>
	* Inner class that handles button clicks
	* 
	* <p> In case server is started "Server Start" button is turned inactive
	* Else the "Server Stop" buttons is inactive
	* 
	* @author  Radoslav Radoev
	* @version %I%, %G%
	* @since   02/25/2018
	*/
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startServer) {
				
				// if server is not active already, turn it on and start it
				if (!server.isListening()) {
					server.setListening(true);
				}
				
				pool.execute(server);
				
				// If the server is still not running display the user an error message.
				if (!server.isListening()) {
	        		javax.swing.JOptionPane.showMessageDialog(null,
	        				"Servet cannot be started", 
	        				"Server error",
	        				JOptionPane.ERROR_MESSAGE);
				} else { // If the server is running, change the status label and toggle on/off buttons accordingly
					statusLabel.setText("Server is running");	
					startServer.setEnabled(false);
					stopServer.setEnabled(true);
					client.setEnabled(true);
				}
			}
			// if server is going to be stopped, turn it off, update label,
			// toggle buttons accordingly. Thread will not be shutdown in case
			// server needs to be restart
			else if (e.getSource() == stopServer) {
				server.setListening(false);
				statusLabel.setText("Server is stopped");
				startServer.setEnabled(true);
				stopServer.setEnabled(false);
			}
		}
		
	}
	
	/**
	 * Method that returns the current executor service
	 * @return an ExecutorService instance of the current thread pool
	 */
	public ExecutorService getExecutionThreadPool() {
		return pool;
	}
}









