package _Advanced_Multithreading;

import java.util.*;

public class NamedConsumer implements Runnable {
	Producer producer;
	String name;
	
	NamedConsumer(String name, Producer producer) {
		this.producer = producer;
		this.name = name;
	}
	
	@Override
	public void run() {
		while (true) {
			String message = producer.getMessage();
			System.out.println(name + " got message: " + message);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) { 	}
		}
	}
	
	public static void main (String[] args) {
		Producer producer = new Producer();
		new Thread(producer).start();
		
		NamedConsumer consumer = new NamedConsumer("One", producer);
		new Thread(consumer).start();
		consumer = new NamedConsumer("Two", producer);
		new Thread(consumer).start();
	}
}
