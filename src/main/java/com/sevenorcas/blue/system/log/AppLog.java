package com.sevenorcas.blue.system.log;

import java.lang.invoke.MethodHandles;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.mail.SMailI;

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
		BaseException x = null;
		if (e instanceof BaseException) {
			x = (BaseException)e;
			e = x.getOriginalException();
		}
		
		//Log
		if (x == null || x.isLog()) {
			String detail = e.getMessage();
			if (detail == null) {
				detail = e.getClass().getCanonicalName();
			}
			
			log.error("App Exception:" + detail + (message != null? " message:" + message : ""));
			log.error(e.getMessage(), e);
			x.logMe(false);
		}
		
		//Email
		if (x != null && x.isEmail()) {
			try {
				Context initialContext = new InitialContext();
				SMailI mail = (SMailI)initialContext.lookup("java:module/SMail"); //java:module/SMail!com.sevenorcas.blue.system.mail.SMailI
				mail.send(x);
				x.emailMe(false);
			} catch (Exception xx) {
				
			}
		}
		
	}
	
	
}

