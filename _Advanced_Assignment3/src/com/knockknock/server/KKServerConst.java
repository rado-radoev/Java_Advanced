package com.knockknock.server;

/**
* <h1>Knock Knock Server Constant Parameters</h1>
*
* Enum containing server side constant parameters
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public enum KKServerConst {
	
	PORT ( 4444 ),
	TIMEOUT ( 2000 ),
	MAXTHREADS ( 1 );
	
	private final int constant;
	
	/**
	 * Class Constructor accepting contant as integer value 
	 * @param constant
	 */
	private KKServerConst(int constant) {
		this.constant = constant;
	}
	
	/**
	 * Method to return constant value
	 * @return
	 */
	public int getValue() {
		return constant;
	}
}
