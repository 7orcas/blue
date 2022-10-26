package com.sevenorcas.blue.system.exception;

import java.util.ArrayList;

/**
* Application exception object to manager actions once an exception is thrown
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

abstract public class BaseException extends Exception{

	private static final long serialVersionUID = 1L;
	private String detail;
	private boolean logMe = true;
	private boolean notifyMe = false;
	private StackTraceElement[] stack;
	private Exception exception;
	
	public BaseException (String message, String detail) {
		super (message);
		this.detail = detail;
	}

	public BaseException (String message, String detail, Exception ex) {
		super (message);
		this.detail = detail;
		this.exception = ex;
	}
	
	public BaseException logMe() {
		logMe = true;
		return this;
	}

	public BaseException notifyMe() {
		notifyMe(true);
		return this;
	}
	
	public BaseException notifyMe(boolean notifyMe) {
		this.notifyMe = notifyMe;
		return this;
	}
	
	protected BaseException stackTrace(StackTraceElement[] s) { 
		stack = s;
		return this;	
	}
	
	public String getDetail() {
		return detail;
	}

	public Exception getOriginalException() {
		return exception;
	}

	public boolean isLog() {
		return logMe;
	}
	
	public boolean isNotifiable() {
		return notifyMe;
	}
	
	public boolean isStackTrace() {
		return stack != null;
	}
	
	/**
	 * Return the stack trace at the third line (ignores the Thread and Exception Constructor)
	 * @return
	 */
	public String [] stackTraceToString() {
		if (!isStackTrace()) return null;
		
		ArrayList<String> l = new ArrayList<>();
		
		for (int i = 2; stack != null && i < stack.length; i++) {
			l.add(stack[i].toString());
		}
		
		if (l.size() == 0) return new String [0];
		
		String [] s = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			s[i] = l.get(i);
		}
		
		return s;
	}
	
	public void sysoutStackTrace() {
		
		
		
	}
}
