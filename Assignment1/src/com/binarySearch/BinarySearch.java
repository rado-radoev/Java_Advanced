package com.binarySearch;

import java.security.SecureRandom;
import java.util.Arrays;


public class BinarySearch {
	
	private static int[] nums = new int[(int)Math.pow(2, 30)];

	public static int binarySearch(int[] a, int key) { 
	    int low = 0; 
	    int high = a.length - 1; 

	    while (low <= high) { 
	    		int mid = low + ((high - low) / 2);
	    		System.out.println("Middle is: " + mid);
	    		int midVal = a[mid]; 
	
    			if (midVal < key) {low = mid + 1;} 
		    else if (midVal > key) {high = mid - 1;} 
		    else return mid; // key found 
	    } 
	    return -1;  // key not found. 
	} 
	
	public static void main(String[] args) {
		
		// Get current size of heap in bytes
		long heapSize = Runtime.getRuntime().totalMemory(); 
		System.out.println("Total Memory: " + formatSize(heapSize));

		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		System.out.println("Max Heap Size: " + formatSize(heapMaxSize));
		 // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
		long heapFreeSize = Runtime.getRuntime().freeMemory(); 
		System.out.println("Heap free size: " + formatSize(heapFreeSize));
		
		final long startTime = System.currentTimeMillis();
		
		//int[] nums = new int[(int)Math.pow(2, 30)];
		//SecureRandom generator = new SecureRandom();
		//Arrays.parallelSetAll(nums, i -> generator.nextInt());
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) );
		
		System.out.println("Done");
		nums[0] = 88;
//		
		
		System.out.println(binarySearch(nums, 88));
	}
	
	public static String formatSize(long v) {
	    if (v < 1024) return v + " B";
	    int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
	    return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
	}
}
