package com.knockknock.orig;

import java.io.IOException;

public class ServerStart {

	public static void main(String[] args) throws InterruptedException, IOException {
		KKMultiServer s = new KKMultiServer();
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					s.setListening(true);
					s.start();
					System.out.println("Test");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		t.start();
		System.out.println("Thrad alive: " + t.isAlive());

		Thread.sleep(30);
		
		s.setListening(false);
	}

}
