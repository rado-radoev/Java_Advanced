package com.knockknock.application;

import com.knockknock.message.reader.MessageLoader;
import com.knockknock.server.KKServerGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* <h1>Knock Knock</h1>
* The Knock Knock program implements an application that
* allows the user to start a local server that accepts client
* connections and displays knock knock jokes. 
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class Application {
	
	/**
	 * Main entry point for the Knock Knock application
	 * Method creates the entry point GUI and loads the jokes
	 * from the provided text files.
	 * @param args Unused
	 */
	public static void main(String[] args) {
		
		ExecutorService executors = Executors.newCachedThreadPool();		
		executors.execute(new Runnable() {
			
			// Server start GUI
			@Override
			public void run() {
		
				KKServerGUI gui = new KKServerGUI();

				gui.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent event) {
						if (event.getWindow() == gui) {
							gui.getExecutionThreadPool().shutdownNow();
							executors.shutdownNow();
							 System.exit(0);
						}
					}
				});
		
				gui.setSize(200, 200);
				gui.setLocationRelativeTo(null);
				gui.setVisible(true);
			}
		});

		// Load jokes from clues.txt and answers.txt
		executors.execute(new Runnable() {
			
			@Override
			public void run() {
				MessageLoader.getInstance();
			}
		});
	}

}
