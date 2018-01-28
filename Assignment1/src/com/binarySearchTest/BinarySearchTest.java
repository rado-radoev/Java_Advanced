package com.binarySearchTest;

import static org.junit.Assert.*;
import org.junit.Test;

import com.binarySearch.BinarySearch;

public class BinarySearchTest {

	
	@Test
	 public void testBinarySearchBug(){
		final int ERROR = 0;
	          
	    int[] vec = new int[Integer.MAX_VALUE / 2 + 2];
	    vec[vec.length - 1] = 1;
	    
	  assertEquals(1073741824, BinarySearch.binarySearch(vec, 1), ERROR);
	 }
	
	@Test
	 public void testBinarySearchBugSuccess(){
		final int ERROR = 0;
	          
	    int[] vec = new int[Integer.MAX_VALUE / 2 + 2];
	    vec[vec.length - 1] = 1;
	    
	  assertEquals(1073741824, BinarySearch.binarySearch2(vec, 1), ERROR);
	 }

}
