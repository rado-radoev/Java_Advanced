package com.knockknock.exceptions;

import javax.swing.JOptionPane;

/**
* <h1>Exception Handler</h1> <br>* <strong>NOT USED</strong>
* <p>Class that handles exceptions and displays in JOptionPane
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
* 
*/
public class ExceptionHandler {
	
	/**
	 * Method to handle exceptions
	 * @param exp the exception to handle
	 */
	public static void handleException(Exception exp) {
		displayExceptionMessage(exp);

	}
	
	public static void handleException(String message) {
		displayExceptionMessage(message);
	}
	
	/**
	 * Method to display exception message and cause in JOptionPane
	 * @param exp the exception to handle
	 */
	private static void displayExceptionMessage(Exception exp) {
		javax.swing.JOptionPane.showMessageDialog(null, 
				exp.getMessage(), 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Method to display exception message with custom message
	 * @param message the custom message to display
	 */
	private static void displayExceptionMessage(String message) {
		javax.swing.JOptionPane.showMessageDialog(null, 
				message, 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
	}
}
