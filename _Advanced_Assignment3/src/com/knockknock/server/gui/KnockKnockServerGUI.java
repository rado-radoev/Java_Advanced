package com.knockknock.server.gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.knockknock.server.KKMultiServer;

public class KnockKnockServerGUI extends JFrame {

	private final JPanel mainPanel;
	private final JButton startServer;
	private final JButton stopServer;
	private final JLabel statusLabel;
	private final JPanel buttonPanel;
	private final BorderLayout mainLayout;
	private final FlowLayout buttonLayout;
	
	private KKMultiServer server;
	
	public KnockKnockServerGUI() {
		mainLayout = new BorderLayout();
		mainPanel = new JPanel(mainLayout);
		
		buttonLayout = new FlowLayout(FlowLayout.CENTER, 5, 1);
		ButtonHandler buttonHandler = new ButtonHandler();
		startServer = new JButton("Start server");
		startServer.addActionListener(buttonHandler);
		stopServer = new JButton("Stop server");
		stopServer.addActionListener(buttonHandler);
		buttonPanel = new JPanel(buttonLayout);
		

		
		
		
		statusLabel = new JLabel("The status will be shown here: ");
		
		
		
	}
	
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startServer) {
				server.startServer();
				startServer.setEnabled(false);
				statusLabel.setText("Server started. Listening on port: "  + server.getServerPort());
			}
			else if (e.getSource() == stopServer) {
				server.closeServer();
				startServer.setEnabled(true);
				stopServer.setEnabled(true);
				statusLabel.setText("Servet stopped");
			}
		}
		
	}
	
}
