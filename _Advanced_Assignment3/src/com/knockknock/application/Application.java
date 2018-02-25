package com.knockknock.application;

import com.knockknock.message.reader.MessageLoader;
import com.knockknock.server.KnockKnockServerGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
	
	public static void main(String[] args) {
		
		ExecutorService executors = Executors.newCachedThreadPool();
			
		executors.execute(new Runnable() {
			
			@Override
			public void run() {
		
				KnockKnockServerGUI gui = new KnockKnockServerGUI();

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


		executors.execute(new Runnable() {
			
			@Override
			public void run() {
				MessageLoader.getInstance();
			}
		});
	}

}
