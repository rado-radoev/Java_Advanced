package com.knockknock.client.gui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class KnockKnockClientGUI extends JFrame {

	private final JPanel mainPanel;
	private final JPanel senderPanel;
	private final JButton sendButton;
	private final JTextField textField;
	private final JTextArea textArea;
	private final JLabel statusLabel;
	private final JScrollPane textAreaScrollPane;
	
	public KnockKnockClientGUI() {
		super("Knock Knock Client");
		
		mainPanel = new JPanel(new BorderLayout());
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		textAreaScrollPane = new JScrollPane(textArea);
		mainPanel.add(textAreaScrollPane, BorderLayout.CENTER);
		
		senderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		sendButton = new JButton("Send");
		sendButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// send text
			}
		});
		
		textField = new JTextField(10);
		
		statusLabel = new JLabel("Connected to: SERVER on Port: PORT");
		senderPanel.add(textField);
		senderPanel.add(sendButton);
		senderPanel.add(statusLabel);
		
		mainPanel.add(senderPanel, BorderLayout.SOUTH);
		
		add(mainPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(300, 150);
		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new KnockKnockClientGUI();
            }
        });
    }

	
}
