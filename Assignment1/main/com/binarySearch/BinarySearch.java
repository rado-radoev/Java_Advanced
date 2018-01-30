package com.binarySearch;

/**
 * Utility class demonstrating a flaw in 
 * the divide and conquer algorithms. Bug is represented
 * in the binary search algorithm.
 * 
 * @author Radoslav Radoev
 * @version     %I%, %G%
 * @since       1.0
 */
public class BinarySearch {
	
	/**
	 * Searches for a number in a <strong>sorted</strong> array.
	 * Value will be returned in all cases, even if the number
	 * is not found
	 * <p>
	 * Demonstrates an overflow bug fixed.
	 * <p>
	 * No exceptions checking has been implemented.
	 * @param a the array to search
	 * @param key the element to search for
	 * @return the <code>index</code> of the element if found;
	 * 		   lowest <code>element</code> if not found;
	 */
	public static int binarySearchFix(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		
		while (low <= high) {
			int mid = (low + high) >>> 1; // overflow bug fixed
			int midVal = a[mid];
	
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key) 
				high = mid - 1;
			else
				return mid; // key found
		}
		
		return -(low + 1); // key not found
	}

	/**
	 * Searches for a number in a <strong>sorted</strong> array.
	 * Value will be returned in all cases, even if the number
	 * is not found
	 * <p>
	 * Demonstrates an overflow bug.
	 * <p>
	 * No exceptions checking has been implemented.
	 * @param a the array to search
	 * @param key the element to search for
	 * @return the <code>index</code> of the element if found;
	 * 		   lowest <code>element</code> if not found;
	 */
	public static int binarySearchBug(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		
		while (low <= high) {
			int mid = (low + high) / 2;  // overflow bug
			int midVal = a[mid];
	
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key) 
				high = mid - 1;
			else
				return mid; // key found
		}
		
		return -(low + 1); // key not found
	}

}
