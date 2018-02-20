
import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;

import java.util.concurrent.*;

/**
 * KKServerGUI is the main class controlling the KKMultiServer. This class controls the
 * server status and creates clients based on the status of the server. 
 *   
 * @author Humberto Sainz
 *
 */
public class KKMutliServerGUIController extends JFrame 
{
	// auto generated sVUID. required becuase of JFrame
	private static final long serialVersionUID = 9163708173059627375L;
	
	// Fields
	private JTextArea serverOutput;
	private JScrollPane serverPane;
	private JButton toggleServer;
	private JButton createClient;
	private JPanel contentPane;
	
	private final KKMultiServer server;		// server used to control client creation and interaction
	private ExecutorService executorServer;	// to avoid hang up from while loop to accept clients
	private int serverThreadPool = 1;	// run server on separate thread
	
	public KKMutliServerGUIController()
	{
		super("KnockKnockServer");
		server = new KKMultiServer();
		executorServer = Executors.newFixedThreadPool(serverThreadPool);
		createGUI();
		
		//set defaults
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Handles the creation GUI elements and assignment of event action listeners.
	 */
	private void createGUI()
	{
		// setup TextArea
		serverOutput = new JTextArea("Turn server on to begin...", 16, 25);
		serverOutput.setLineWrap(true);
		serverOutput.setEditable(false);
		serverPane = new JScrollPane(serverOutput);
		serverPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// setup button to toggle server status
		toggleServer = new JButton("Turn Server On");
		toggleServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				server.toggleServer();
				if(server.isListening())
				{
					executorServer.execute(server);
					appendTextPlusScroll("\n>> Server On. Accepting clients.");
					((JButton) e.getSource()).setText("Turn Server Off");
				}
				else
				{
					appendTextPlusScroll("\n>> Server Off. Not accepting clients.");
					((JButton) e.getSource()).setText("Turn Server On");
				}
			}
		});

		// setup button to create clients
		createClient = new JButton("Create Client");
		createClient.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(server.isListening() && KnockKnockClient.totalClients < KnockKnockConstants.MAX_CLIENTS)
				{
					new KnockKnockClient();
					appendTextPlusScroll("\nAdded a new client...");
				}
				else
				{
					appendTextPlusScroll("\nCannot add client. Server is not listening or max # of clients reached...");
				}
				
			}
		});
		
		// add components to panel;
		contentPane = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		contentPane.setLayout(layout);
		
		c.gridwidth = 1;
		c.gridheight = 2;
		c.insets = new Insets(5,5,5,2);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(serverPane, c);
		contentPane.add(serverPane);
		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 100;
		c.weightx = 1;
		c.insets = new Insets(5,0,2,5);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		layout.setConstraints(toggleServer, c);
		contentPane.add(toggleServer);
		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 100;
		c.weightx = 1;
		c.insets = new Insets(2,0,5,5);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		layout.setConstraints(createClient, c);
		contentPane.add(createClient);
		
		// add to frame
		this.add(contentPane);
	}
	
	/**
	 * Functional method to adjust caret position to the end of the text (i.e.
	 * a form of auto-scrolling for the server).
	 * 
	 * @param text 
	 */
	private void appendTextPlusScroll(String text)
	{
		serverOutput.setText(serverOutput.getText() + text);
	}
	
	/**
	 * Creates a new instance of KKMultiServerController.
	 */
	public static void main(String[] args)
	{
		new KKMutliServerGUIController();
	}

}
