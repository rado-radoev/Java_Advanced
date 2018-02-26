package com.knockknock.exceptions;

import javax.swing.JOptionPane;

public class ExceptionHandler {
	
	public static void handleException(Exception exp) {
		displayExceptionMessage(exp);

	}
	
	private static void displayExceptionMessage(Exception exp) {
		javax.swing.JOptionPane.showMessageDialog(null, 
				exp.getMessage(), 
				exp.getCause().toString(), 
				JOptionPane.ERROR_MESSAGE);
	}

}
