package _Advanced_Multithreading;

import java.util.*;

public class Producer implements Runnable {
	static final int MAXQUEUE = 5;
	private List<String> messages = new ArrayList<String>();
	
	@Override
	public void run() {
		while (true) {
			putMessage();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) { 	}
		}
	}
	
	// called by Producer internally
	private synchronized void putMessage() {
		while (messages.size() >= MAXQUEUE) {
			try {
				wait();
			} catch (InterruptedException e) { }
		}
		
		messages.add(new Date().toString());
		notifyAll();
	}
	
	// called by Consumer externally
	public synchronized String getMessage() {
		while (messages.size() == 0) {
			try {
				notifyAll();
				wait();
			} catch (InterruptedException ie) { }
		}
		
		String message = (String)messages.remove(0);
		notifyAll();
		return message;
	}
	
}
