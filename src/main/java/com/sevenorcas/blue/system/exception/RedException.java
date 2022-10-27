package com.sevenorcas.blue.system.exception;

import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

public class RedException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public RedException (String detail, Exception ex, CallObject c) {
		super(ex.getMessage());
		setDetail(detail);
		setOrginalException(ex);
		setCallObject(c);
		logMe();
		emailMe();
		stackTrace(Thread.currentThread().getStackTrace());
	}
	
	public RedException (String message, String detail) {
		super(message);
		setDetail(detail);
		logMe();
		emailMe();
		stackTrace(Thread.currentThread().getStackTrace());
	}
	
}
