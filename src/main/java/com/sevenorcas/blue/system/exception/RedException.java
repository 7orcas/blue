package com.sevenorcas.blue.system.exception;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

public class RedException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public RedException (String detail, Exception ex) {
		super(ex.getMessage(), detail, ex);
		logMe();
		notifyMe();
		stackTrace(Thread.currentThread().getStackTrace());
	}
	
	public RedException (String message, String detail) {
		super(message, detail);
		logMe();
		notifyMe();
		stackTrace(Thread.currentThread().getStackTrace());
	}
	
}
