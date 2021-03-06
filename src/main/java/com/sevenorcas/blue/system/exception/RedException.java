package com.sevenorcas.blue.system.exception;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

public class RedException extends BaseException {

	private static final long serialVersionUID = 1L;

	public RedException (String message) {
		super(message);
		logMe();
		notifyMe();
		stackTrace(Thread.currentThread().getStackTrace());
	}
	
}
