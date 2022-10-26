package com.sevenorcas.blue.system;

/**
 * Logger to allow unit testing 
 * Created 26.10.2022
 * [Licence]
 * @author John Stewart
 */
@SuppressWarnings("serial")
public class LoggerTest extends org.jboss.logging.Logger {

	public String errorMessage;
	public Exception exception;
	
	public LoggerTest (String message) {
		super(message);
	}

	
	public void error (String message) {
		this.errorMessage = message;
	}
	
	public void error (String message, Exception e) {
		this.errorMessage = message;
		this.exception = e;
	}
	
	@Override
	public boolean isEnabled(Level arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void doLog(Level arg0, String arg1, Object arg2, Object[] arg3, Throwable arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doLogf(Level arg0, String arg1, String arg2, Object[] arg3, Throwable arg4) {
		// TODO Auto-generated method stub
		
	}
	

}
