package com.binarySearch;

import java.util.Arrays;

public class BinarySearch {
	
	private static char[] arr = new char[(int)Math.pow(2, 31)];

	public static int binarySearch(char[] a, char key) {
		int low = 0;
		int high = a.length - 1;
		
		while (low <= high) {
			int mid = (low + high) >>> 1; 
			char midVal = a[mid];
			
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key) 
				high = mid - 1;
			else
				return mid; // key found
		}	
		return -(low + 1); // key not found
	}
	
	public static void main(String[] args) {
		
		arr[1] = 'c';
		arr[2] = 'Z';
		arr[88] = 'A';
		Arrays.sort(arr);
		
		System.out.println(binarySearch(arr, 'Z'));
	}
}
