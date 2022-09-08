package com.sevenorcas.blue.system;

public interface ApplicationI {

	//PATHs used in web.xml
	final static public String APPLICATION_PATH = "rest-api-x"; 
	static public String UPLOAD_PATH = "upload";
	static public String ERROR_PATH = "error";
	static public String LOGIN_PATH = "blue-login";
	
	static public String CLIENT_SESSIONS = "client-sessions";
	static public String CLIENT_SESSION_NR = "client-nr";
	
	static public Integer MAX_LOGIN_ATTEMPTS = 3;
}
