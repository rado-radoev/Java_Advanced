package _Advanced_Multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorHttpd {
	
	ExecutorService executor = Executors.newFixedThreadPool(3);
	
	public void start (int port) throws IOException {
		final ServerSocket ss = new ServerSocket(port);
		
		while (!executor.isShutdown()) {
			executor.submit(new TinyHttpdConnection(ss.accept()));
		}
		
	}
	
	public void shutdown() throws InterruptedException {
		executor.shutdown();
		executor.awaitTermination(30, TimeUnit.SECONDS);
		executor.shutdownNow();
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		new ExecutorHttpd().start(Integer.parseInt(args[0]));
	}

}


class TinyHttpdConnection<T> implements Callable<T> {

	public TinyHttpdConnection(Socket accept) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public T call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
