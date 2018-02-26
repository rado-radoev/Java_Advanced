package com.knockknock.exceptions;

public class ServiceException extends Throwable {

    public ServiceException() {}  
    
    public ServiceException(String message) {
    		super(message); 
	}  
    
    public ServiceException(Throwable cause) { 
    		super(cause); 
	}  
    
    public ServiceException(String message, Throwable cause) {
    		super(message, cause); 
    	}
}
