package com.advanced_java.exceptions.test;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.advanced_java.exceptions.test.RunTests.ExceptionTest;

import junit.framework.Test;

public class ExceptionsTest1 {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Test {
		
	}
}

class RunTests {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface ExceptionTest {
		Class<? extends Exception> value();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface ExceptionTest2 {
		Class<? extends Exception>[] value();
	}
	
	public static void main(String[] args) throws Exception {
		int tests = 0;
		int passed = 0;
		Class testClass = Class.forName(args[0]);
		for (Method m : testClass.getMethods()) {
			if (m.isAnnotationPresent(ExceptionTest.class)) {
				tests++;
				try {
					m.invoke(null);
					System.out.printf("Test %s failed: no exception%n", m);
				} catch (InvocationTargetException wrappedExc) {
					Throwable exc = wrappedExc.getCause();
					Class<? extends Exception> excType = 
							m.getAnnotation(ExceptionTest.class).value();
//					int oldPassed = passed;
//					for (Class<? extends Exception> excType : excTypes) {
//						if (excType.isInstance(exc)) {
//							passed++;
//							break;
//						}
//					}
//						if (passed == oldPassed)
//							System.out.printf("Test %s failed: %s %n", m, exc);
//					
					
					if (excType.isInstance(exc)) {
						passed++;
					} else {
						System.out.printf("Test %s failed: expected %s, got %s%n", m, excType.getName(), exc);
					}
				} catch (Exception exc) {
					System.out.println("INVALID @Test: " + m);
				}
			}
		}
				
	}
	

	public static void main1(String[] args) throws Exception {
		int tests = 0;
		int passed = 0;
		Class testClass = Class.forName(args[0]);
		for (Method m : testClass.getMethods()) {
 			if (m.isAnnotationPresent((Class<? extends Annotation>) Test.class)) {
 				tests++;
 				try {
 					m.invoke(null);
 					passed++;
 				} catch (InvocationTargetException wrappedExec) {
 					Throwable exc = wrappedExec.getCause();
 					System.out.println(m + " failed: " + exc);
 				} catch (Exception exc) {
 					System.out.println("INVALID @Test: " + m);
 				}
 			}
		}
		System.out.printf("Passed : %d, Failed: %d", passed, tests-passed);
	}
}

class Sample2 {
	@ExceptionTest(ArithmeticException.class)
	public static void m1() { // Should pass
		int i = 0;
		i = i /i;
	}
	
	@ExceptionTest(ArithmeticException.class) 
	public static void m2() { // should fail
		int[] a = new int[0];
		int i = a[1];
	}
}