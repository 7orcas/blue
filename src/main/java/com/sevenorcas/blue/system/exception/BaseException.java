package com.sevenorcas.blue.system.exception;

import java.util.ArrayList;

abstract public class BaseException extends Exception{

	private static final long serialVersionUID = 1L;
	private boolean logMe = false;
	private boolean notifyMe = false;
	private StackTraceElement[] stack;
	
	public BaseException (String message) {
		super (message);
	}

	public BaseException logMe() {
		logMe = true;
		return this;
	}

	public BaseException notifyMe() {
		notifyMe = true;
		return this;
	}
	
	protected BaseException stackTrace(StackTraceElement[] s) { 
		stack = s;
		return this;	
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
