package com.knockknock.client.gui;

 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	
	private KnockKnockClient kkClient;
	
	public KnockKnockClientGUI() {
		super("Knock Knock Client");
		
		kkClient = new KnockKnockClient();
		
		mainPanel = new JPanel(new BorderLayout());
		
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		
		chatTextAreaScrollPane = new JScrollPane(chatTextArea);
		chatTextAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(chatTextAreaScrollPane, BorderLayout.CENTER);
		
		sendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
		userInputTextField = new JTextField(10);
		userInputTextField.requestFocusInWindow();
		userInputTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chatTextArea.append("\nClient: " + userInputTextField.getText());
				kkClient.readClientInput(userInputTextField.getText() + "\n");
			}
		});
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chatTextArea.append("\nClient: " + userInputTextField.getText());
				kkClient.readClientInput(userInputTextField.getText() + "\n");
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
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new KnockKnockClientGUI();
            }
        });
    }	
}
