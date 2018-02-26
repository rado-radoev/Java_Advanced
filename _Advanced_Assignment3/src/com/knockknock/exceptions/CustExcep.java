package com.knockknock.exceptions;

/**
* <h1>Custom Exception</h1>
* Class that can create custom exceptions
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class CustExcep extends Exception {

	/**
	 * Method to create exception with custom message
	 * @param message custom message to add to exception
	 */
   public CustExcep (String message) {
        super (message);
    }

   /**
    * Method to create exception with a custom cause
    * @param cause custom cause to add to exception
    */
    public CustExcep (Throwable cause) {
        super (cause);
    }

    /**
     * Method to create exception with a custom cause and message
     * @param message custom message to add to exception
     * @param cause custom cause to add to exception
     */
    public CustExcep (String message, Throwable cause) {
        super (message, cause);
    }
}
