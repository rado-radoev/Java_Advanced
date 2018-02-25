package com.knockknock.server;

public enum KKServerConst {
	
	PORT ( 4444 ),
	TIMEOUT ( 2000 ),
	MAXTHREADS ( 1 );
	
	private final int constant;
	
	private KKServerConst(int constant) {
		this.constant = constant;
	}
	
	public int getValue() {
		return constant;
	}
}
