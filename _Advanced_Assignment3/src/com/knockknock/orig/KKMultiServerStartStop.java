package com.knockknock.orig;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KKMultiServerStartStop {
	
	public static void main (String[] args) throws InterruptedException, IOException {
		KKMultiServer kmms = new KKMultiServer();
		
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(kmms);
		
		
		
		kmms.listening = false;
		exec.shutdown();
		

	}

}
