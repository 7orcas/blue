package com.sevenorcas.blue.system.exception;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* Application exception object to hold various parameters once an exception is thrown
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

abstract public class BaseException extends Exception{

	private static final long serialVersionUID = 1L;
	private String detail;
	private boolean logMe = true;
	private boolean emailMe = false;
	private CallObject callObject;
	private StackTraceElement[] stack;
	private Exception orginalException;
	private Timestamp timestamp;
	
	public BaseException (String message) {
		super (message);
		LocalDateTime d = LocalDateTime.now();
		timestamp = Timestamp.valueOf(d);
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public void logMe() {
		logMe(true);
	}

	public void logMe(boolean logMe) {
		this.logMe = logMe;
	}
	
	public void emailMe() {
		emailMe(true);
	}
	
	public void emailMe(boolean email) {
		this.emailMe = email;
	}

	public void setCallObject(CallObject callObject) {
		this.callObject = callObject;
	}
	
	protected void stackTrace(StackTraceElement[] s) { 
		stack = s;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setOrginalException(Exception orginalException) {
		this.orginalException = orginalException;
	}

	public boolean isLog() {
		return logMe;
	}
	
	public boolean isEmail() {
		return emailMe;
	}
	
	public boolean isStackTrace() {
		return stack != null;
	}
	
	public CallObject getCallObject() {
		return callObject;
	}

	public Exception getOriginalException() {
		return orginalException;
	}
	
	public final Timestamp getTimestamp() {
		return timestamp;
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
