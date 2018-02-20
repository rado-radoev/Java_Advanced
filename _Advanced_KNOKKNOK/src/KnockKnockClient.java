
import java.io.*;
import java.net.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Class that creates a GUI to interact with the KnockKnock Server. Allows the 
 * user to receive and send messages from and to the knock knock server.
 * 
 * @author Humberto
 *
 */
public class KnockKnockClient
{
	public static int totalClients = 0;	// keep track of total number of clients
	private JokeState jokeState = JokeState.WAITING;
	
	private JTextArea clientOutput;
	private JScrollPane clientOutputPane;
	private JTextField clientInput;
	private JLabel clientInputLabel;
	private JButton clientRespond;
	private JButton clientClose;
	private JPanel contentPane;
	private JFrame display;
	
	private String host = KnockKnockConstants.HOST;
	private int port = KnockKnockConstants.PORT;
    private Socket kkSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    
    private String toServer = null;
	private String fromServer = null;
	private String correctInput = null;
	
	
    /**
     * This method constructs the GUI for the client in a seperate utilities thread. 
     */
	public KnockKnockClient()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				createGUI();
			}
		});
		totalClients++;
	}
	
    private void createGUI()
    {
    	// create text area for output
    	clientOutput = new JTextArea("Connected to server...Click Start Jokes to begin",15,10);
    	clientOutput.setEditable(false);
    	clientOutput.setLineWrap(true);
    	clientOutputPane = new JScrollPane(clientOutput);
    	clientOutputPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	// create text area for client input
    	clientInput = new JTextField("",5);
    	clientInput.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e)
    		{
    				communicateWithServer();
    		}
    	});
    	
    	// create label for input
    	clientInputLabel = new JLabel("Enter Response:");
    	clientInputLabel.setVerticalAlignment(SwingConstants.CENTER);
    	clientInputLabel.setHorizontalAlignment(SwingConstants.CENTER);

    	// create response button
    	clientRespond = new JButton("Start Jokes ");
    	clientRespond.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e)
    		{
    			if(jokeState == JokeState.WAITING)
    				communicateWithServer();
    			else
    				appendTextPlusScroll("\n>>SERVER: Jokes already started!");
    		}
    	});
    	
    	// create client close button
    	clientClose = new JButton("Close Client");
    	clientClose.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e)
    		{
    			closeCommunications();
    		}
    	});
    	
    	//add to components to panel
    	contentPane = new JPanel();
    	GridBagLayout layout = new GridBagLayout();
    	GridBagConstraints c = new GridBagConstraints();
    	contentPane.setLayout(layout);
    	
    	c.gridwidth = 4;
    	c.gridheight = 1;
    	c.insets =  new Insets(6,6,6,6);
    	c.fill = GridBagConstraints.BOTH;
    	c.weightx = 1;
    	c.gridx =0;
    	c.gridy = 0;
    	layout.setConstraints(clientOutputPane, c);
    	contentPane.add(clientOutputPane);
    	
    	c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(6,6,0,3);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		layout.setConstraints(clientInputLabel, c);
		contentPane.add(clientInputLabel);
		
    	c.gridwidth = 3;
		c.gridheight = 1;
		c.ipadx = 75;
		c.insets = new Insets(6,3,0,6);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 1;
		layout.setConstraints(clientInput, c);
		contentPane.add(clientInput);
		
    	c.gridwidth = 2;
		c.gridheight = 1;
		c.ipadx = 50;
		c.insets = new Insets(0,6,3,3);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(clientRespond, c);
		contentPane.add(clientRespond);
		
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipadx = 50;
		c.insets = new Insets(0,3,3,6);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 2;
		c.gridy = 2;
		layout.setConstraints(clientClose, c);
		contentPane.add(clientClose);
    	
    	// add to frame
		display = new JFrame("KnockKnockClient");
		display.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) 
			{
				closeCommunications();
			}
		});
		
		display.add(contentPane);
		display.pack();
		display.setResizable(false);
		display.setVisible(true);
		
		// make sure we open communications for immediate ability to communicate w/ server
		openCommunications();
    }
    
    /**
     * Grabs the socket to the server based on a set port number and host. Proceeds to
     * define the input and output streams from the established socket connection.
     */
    private void openCommunications()
    {
        try 
        {
            kkSocket = new Socket(host, port);	// this is when we actually connect to server
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        }
        catch (UnknownHostException e) 
        {
            System.err.println("Don't know about host: " + host + " .");
            System.exit(1);
        } 
        catch (IOException e) 
        {
            System.err.println("Couldn't get I/O for the connection to:  " + host + " .");
            System.exit(1);
        }    
    }
    
    /**
     * Attempts to close all input and output streams. Additionally attempts to close the 
     * socket that was used to create input and output streams.
     */
    private void closeCommunications()
    {
    	try
    	{
    		display.setVisible(false);
    		display.dispose();
    		if(out != null)
    			out.close();
    		if(in != null)
    			in.close();
    		if(kkSocket != null)
    			kkSocket.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error occured while closing client: ");    		
    		e.printStackTrace();
    		System.exit(1);	//don't want to continue if sockets don't close properly
    	}
    	totalClients--;
    }
    
    /**
     * Handles the progression of the current knock knock joke. Works in association with
     * KnockKnockProtocol to only progress if the right conditions are met (i.e. only 
     * progress throughout the joke if the user responds correctly to the output).
     */
    private void communicateWithServer()
    {
    	// process based on jokeState
    	switch(jokeState)
    	{
    		case WAITING:
    			try
    			{
    				fromServer = in.readLine();
    				appendTextPlusScroll("\n>>SERVER: " + fromServer);
        			jokeState = JokeState.SENTKNOCK;
    			}
    			catch(IOException e)
    			{
    				System.out.println("Error occured recieving KNOCK");
    			}
    			break;
    			
    		case SENTKNOCK:
    			try
    			{
    				processSentKnock();
    			}
    			catch(IOException e)
    			{
    				System.out.println("Error occured receiving/sending SENTKNOCK");
    			}
    			break;
    			
    		case SENTCLUE:
    			try
    			{
    				processSentClue();
    			}
    			catch(IOException e)
    			{
    				System.out.println("Error occured receiving/sending SENTCLUE");
    			}
    			break;
    			
    		case ANOTHER:
    			try
    			{
    				processAnother();
    			}
    			catch(IOException e)
    			{
    				System.out.println("Error occured receiving/sending ANOTHER");
    			}
    			break;		
    			
    		case UNREACHABLE:
    			appendTextPlusScroll("\n>>SERVER: No Longer connected. Reconnect through a different client.");
    			clientInput.setText("");
    			break;
    	}
    }

    /**
     * Handles joke progression if the state is equal to SENTKNOCK.
     * @throws IOException
     */
    private void processSentKnock() throws IOException
    {
		// get input from client...send to server...recieve new input from server
		toServer = clientInput.getText();
		out.println(toServer);
		fromServer = in.readLine();
		
		// append text
		appendTextPlusScroll("\n>>CLIENT: " + toServer + "\n>>SERVER: " + fromServer);
		
		// determine if we move to next state
		if(toServer.equalsIgnoreCase("Who's There?"))
		{
			jokeState = JokeState.SENTCLUE;
			correctInput = fromServer + " who?";
			clientInput.setText("");
		}
    }
    
    /**
     * Handles joke progression if the state is equal to SENTCLUE.
     * @throws IOException
     */
    private void processSentClue() throws IOException
    {
		// get input from client...send to server
		toServer = clientInput.getText();
		out.println(toServer);
		fromServer = in.readLine();
		
		// append text and get from server...append text
		appendTextPlusScroll("\n>>CLIENT: " + toServer + "\n>>SERVER: " + fromServer);
		
		// determine if we move to next state
		if(toServer.equalsIgnoreCase(correctInput))
		{
			jokeState = JokeState.ANOTHER;
			clientInput.setText("");
		}
		else
		{
			jokeState = JokeState.SENTKNOCK;
		}
    }
    
    /**
     * Handles joke progression if the state is equal to ANOTHER.
     * @throws IOException
     */
    private void processAnother() throws IOException
    {
		// get input from client...send to server
		toServer = clientInput.getText();
		
		// determine if we move to next state
		if(toServer.equalsIgnoreCase("n"))
		{
			jokeState = JokeState.UNREACHABLE;
			out.println(toServer);
			fromServer = in.readLine();
			appendTextPlusScroll("\n\n>>SERVER: " + fromServer);
			clientInput.setText("");
		}
		else if (toServer.equalsIgnoreCase("y"))
		{
			out.println(toServer);
			fromServer = in.readLine();
			appendTextPlusScroll("\n\n>>SERVER: " + fromServer);
			jokeState = JokeState.SENTKNOCK;
			clientInput.setText("");
		}
		else
		{
			appendTextPlusScroll("\n>>SERVER: Do you want another joke? (y/n)" );
		}
    }
    
    /*
	 * Functional method to adjust caret position to the end of the text (i.e.
	 * a form of auto-scrolling for the client).
	 */
    private void appendTextPlusScroll(String text)
	{
		clientOutput.setText(clientOutput.getText() + text);
	}  
}
