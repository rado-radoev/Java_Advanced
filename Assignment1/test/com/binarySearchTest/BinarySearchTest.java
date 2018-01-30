package com.binarySearchTest;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.binarySearch.BinarySearch;

/**
 * Testing binary search and disposing a flow with overflowing
 * when large array used.
 * 
 * @author Radoslav Radoev
 * @version     %I%, %G%
 * @since       1.0
 */
public class BinarySearchTest {

	private static int[] arr;

	
	@BeforeClass
	public static void setUp() {
		arr = new int[Integer.MAX_VALUE / 2 + 2];
		arr[arr.length - 1] = 1;
	}
	
	
	@AfterClass
	public static void cleanUp() {
		arr = null;
	}

	@Rule
	public ExpectedException exc = ExpectedException.none();

	@Test
	// Exposes binary search overflow flaw
	public void testBinarySearchBug() {
		exc.expect(java.lang.ArrayIndexOutOfBoundsException.class);
		BinarySearch.binarySearchBug(arr, 1);
	}

	@Test
	// Tests fixed binary search algorithm 
	public void testBinarySearchBugSuccess() {
		assertEquals("Search not overflowing", 1073741824, BinarySearch.binarySearchFix(arr, 1));
	}
	
}