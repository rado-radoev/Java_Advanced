package com.knockknock.server;

public enum KKServerConstants {
	
	PORT ( 4444 ),
	TIMEOUT ( 1000 ),
	MAXTHREADS ( 1 );
	
	private final int constant;
	
	private KKServerConstants(int constant) {
		this.constant = constant;
	}
	
	public int getValue() {
		return constant;
	}
}
