package _Advanced_Multithreading;

public class TimeSlicingTest {

	public static void main(String[] args) {
		new ShowThread("Foo").start();
		new ShowThread("Bar").start();
	}
	
	static class ShowThread extends Thread {
		String message;
		
		ShowThread(String message) {
			this.message = message;
		}
		
		public void run() {
			while (true)
				System.out.println(message);
		}
	}
}
