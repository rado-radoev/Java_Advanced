package com.knockknock.server.gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import com.knockknock.server.KKMultiServer;
import com.knockknock.server.KKServerConst;

public class KnockKnockServerGUI extends JFrame {
	
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
	
	
	private final ExecutorService pool;
	private KKMultiServer server;
	
	public KnockKnockServerGUI() {
		super("Knock Knock Server");
		 
		server = new KKMultiServer();
		pool = Executors.newFixedThreadPool(KKServerConst.MAXTHREADS.getValue());
		
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
		
	}
	
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startServer) {
				
				if (!server.isListening()) {
					server.setListening(true);
				}
				
				pool.execute(server);
				statusLabel.setText("Server is running");
				
				startServer.setEnabled(false);
				stopServer.setEnabled(true);
				
			}
			else if (e.getSource() == stopServer) {
				server.setListening(false);
				statusLabel.setText("Server is stopped");
				startServer.setEnabled(true);
				stopServer.setEnabled(false);
			}
		}
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
		
				KnockKnockServerGUI gui = new KnockKnockServerGUI();

				gui.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						System.exit(0);
					}
				});
		

				gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				gui.setSize(200, 150);
				gui.setVisible(true);
				
			}
		});
	}
	
}









