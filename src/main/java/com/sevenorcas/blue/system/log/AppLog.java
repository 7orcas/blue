package com.sevenorcas.blue.system.log;

import java.lang.invoke.MethodHandles;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.exception.BaseException;

/**
 * Application log wrapper
 * Determines actions after an exception is thrown
 * 
 * Created 26.10.2022
 * [Licence]
 * @author John Stewart
 */

public class AppLog {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	static public void info (String message) {
		log.error("App Info:" + message);
	}
	
	static public void error (String message) {
		log.error("App Error:" + message);
	}

	static public void exception (Exception e) {
		exception (null, e);	
	}
	
	static public void exception (String message, Exception e) {
		String detail = e.getMessage();
		if (detail == null) {
			detail = e.getClass().getCanonicalName();
		}
		log.error("App Exception:" + detail + (message != null? " message:" + message : ""));
		log.error(e.getMessage(), e);
		
		if (e instanceof BaseException) {
			BaseException x = (BaseException)e;
			if (x.isNotifiable()) {
				
			}
		}
		
	}
	
	
}

