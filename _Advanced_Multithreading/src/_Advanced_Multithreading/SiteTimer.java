package _Advanced_Multithreading;

import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.IOException;

public class SiteTimer {

	CyclicBarrier barrier;
	List<Result> results = new ArrayList<Result>();
	
	private class Result implements Comparable<Result> {
		Long time;
		String site;
		Result(long time, String site) {
			this.time = time;
			this.site = site;
		}
		
		public int compareTo(Result r) {return time.compareTo(r.time);}
	}
	
	static long timeConnect( String site ) {
		long start = System.currentTimeMillis();
		try {
			new URL( site).openConnection().connect();
		} catch (IOException e) {
			return -1;
		}
		return System.currentTimeMillis() - start;
	}
	
	void showResults() {
		Collections.sort(results);
		for (Result result : results) {
			System.out.printf("%-30.30s : %d%n", result.site, result.time);
		}
	}
	
	public void start (String[] args) {
		Runnable showResultAction = new Runnable() {
			
			@Override
			public void run() {
				showResults();
				results.clear();
			}
		};
		barrier = new CyclicBarrier(args.length, showResultAction);
		
		for (final String site : args) {
			new Thread() {
				public void run() {
					while (true) {
						long time = timeConnect(site);
						results.add(new Result(time, site));
						try {
							barrier.await();
						} catch (BrokenBarrierException | InterruptedException e) {
							return;
						}
					}
				}
			}.start();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new SiteTimer().start(args);
	}
	
}
























