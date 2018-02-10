package _Advanced_Multithreading;

public class TimeSlicingTest {

	public static void main(String[] args) {
		Thread foo = new ShowThread("Foo");
		foo.setPriority(Thread.MIN_PRIORITY);
		foo.start();

		Thread bar = new ShowThread("Bar");
		bar.setPriority(Thread.MAX_PRIORITY);
		bar.start();
		
		// catch uncought exceptions
		bar.setUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println(t + " threw exception: " + e);
				
			}
		});
	}
	
	static class ShowThread extends Thread {
		private volatile String message;
		String getMessage() {
			String result = message;
			
			if (result == null) { // First check (no locking)
				synchronized (this) {
					result = message;
					if (result == null) { // Second check (with locking)
						message = result = "";
					}
				}
			}
			
			return result;
		}
		
		ShowThread(String message) {
			this.message = message;
		}
		
		public void run() {
			while (true) {
				System.out.println(message);
				yield();
			}
		}
	}
}
