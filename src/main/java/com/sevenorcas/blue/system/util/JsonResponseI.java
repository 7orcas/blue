package com.sevenorcas.blue.system.util;

/**
 * Hard coded JSon response values
 * 
 * UPDATE NOTE: Keep in-sync with both React Clients (JsonResponseI enum) 
 *   
 * [Licence]
 * Created July '22
 * @author John Stewart
 */

public interface JsonResponseI {

	final static int JS_OK                = 0;
	final static int JS_ERROR             = -1;
	final static int JS_INVALID           = -2;
	final static int JS_VALIDATION_ERRORS = -3;
	final static int JS_NO_PERMISSION     = -4;
	final static int JS_NO_CHANGE         = 1;
	final static int JS_UPLOADED          = 3;
	
	final static int JS_LOGIN_REDIRECT    = 4;
	final static int JS_LOGGED_OUT        = 5;
}

