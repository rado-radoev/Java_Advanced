package com.knockknock.protocol;

/**
* <h1>Knock Knock Protocol States</h1>
* Enum containing predefined server-client communication states
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public enum State {

	WAITING (0),
	SENTKNOCKKNOCK (1),
	SENTCLUE (2),
	ANOTHER (3);
	
	private final int stateCode;
	
	State (int stateCode) {
		this.stateCode = stateCode;
	}
	
	public int getStateCode() {
		return this.stateCode;
	}
}
