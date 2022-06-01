package com.sevenorcas.blue.system.exception;

public class RedException extends BaseException {

	private static final long serialVersionUID = 1L;

	public RedException (String message) {
		super(message);
		logMe();
		notifyMe();
		stackTrace(Thread.currentThread().getStackTrace());
	}
	
}
