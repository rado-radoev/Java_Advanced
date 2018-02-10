package _Advanced_Multithreading;

public class TimeSlicingTest {

	public static void main(String[] args) {
		Thread foo = new ShowThread("Foo");
		foo.setPriority(Thread.MIN_PRIORITY);
		foo.start();

		Thread bar = new ShowThread("Bar");
		bar.setPriority(Thread.MAX_PRIORITY);
		bar.start();
	}
	
	static class ShowThread extends Thread {
		String message;
		
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
