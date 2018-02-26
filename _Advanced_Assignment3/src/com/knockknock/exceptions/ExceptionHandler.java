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
	 * Method to handle excceptions
	 * @param exp the exception to handle
	 */
	public static void handleException(Exception exp) {
		displayExceptionMessage(exp);

	}
	
	/**
	 * Method to display exception message and cause in JOptionPane
	 * @param exp the exception to handle
	 */
	private static void displayExceptionMessage(Exception exp) {
		javax.swing.JOptionPane.showMessageDialog(null, 
				exp.getMessage(), 
				exp.getCause().toString(), 
				JOptionPane.ERROR_MESSAGE);
	}

}
