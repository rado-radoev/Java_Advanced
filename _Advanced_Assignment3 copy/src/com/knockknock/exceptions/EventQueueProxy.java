package com.knockknock.exceptions;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

public class EventQueueProxy extends EventQueue {
	
	protected void dispatchEvent (AWTEvent newEvent) {
		try {
			super.dispatchEvent(newEvent);
		} catch (RuntimeException | Error e) {
			JOptionPane.showMessageDialog(null, "Oops something went wrong...");
		}
	}
	
	public static void captureUncaughtException() {
		EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		queue.push(new EventQueueProxy());
	}

}
