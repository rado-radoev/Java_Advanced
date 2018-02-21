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

import com.knockknock.client.KnockKnockClient;

public class KnockKnockClientGUI extends JFrame {

	private static final long serialVersionUID = 4379695112985425572L;

	private final JPanel mainPanel;
	private final JPanel sendPanel;
	private final JButton sendButton;
	private final JTextField userInputTextField;
	private final JTextArea chatTextArea;
	private final JScrollPane chatTextAreaScrollPane;
	private final JLabel statusLabel;
	private final KnockKnockClient client;
	
	public KnockKnockClientGUI() {
		super("Knock Knock Client");
		
		mainPanel = new JPanel(new BorderLayout());
		client = new KnockKnockClient();
		
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		
		chatTextAreaScrollPane = new JScrollPane(chatTextArea);
		mainPanel.add(chatTextAreaScrollPane, BorderLayout.CENTER);
		
		sendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		sendButton = new JButton("Send");
		sendButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// send text
			}
		});
		
		userInputTextField = new JTextField(10);
		
		statusLabel = new JLabel("Connected");
		sendPanel.add(userInputTextField);
		sendPanel.add(sendButton);
		sendPanel.add(statusLabel);
		
		mainPanel.add(sendPanel, BorderLayout.SOUTH);
		
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
