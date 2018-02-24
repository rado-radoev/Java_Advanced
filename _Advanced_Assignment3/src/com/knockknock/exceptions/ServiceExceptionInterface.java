package com.knockknock.exceptions;

public interface ServiceExceptionInterface {
	
	public void throwException(Throwable t);
	public Throwable thrownException( ) throws ServiceException;
}
