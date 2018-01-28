package com.binarySearch;

import java.util.Arrays;

public class BinarySearch {
	
	private static int[] arr = new int[200];
	
	public static int binarySearch2(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		
		while (low <= high) {
			int mid = low + ((high - low) / 2); 
			int midVal = a[mid];
	
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key) 
				high = mid - 1;
			else
				return mid; // key found
		}
		
		return 100; // key not found
	}

	public static int binarySearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		
		while (low <= high) {
			int mid = (low + high) / 2;
			//int mid = low + ((high - low) / 2); 
			int midVal = a[mid];
	
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key) 
				high = mid - 1;
			else
				return mid; // key found
		}
		
		return 100; // key not found
	}
	
	public static void main(String[] args) {
		

		arr[1] = 1;
		Arrays.sort(arr);
		
//		for (int i = 0; i < arr.length; i++) {
//			System.out.println("Index: " + i + " Value: " + arr[i]);
//		}
		
		System.out.println(binarySearch(arr, 1));
	}
}
