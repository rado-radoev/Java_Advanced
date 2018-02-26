package com.knockknock.protocol;

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
