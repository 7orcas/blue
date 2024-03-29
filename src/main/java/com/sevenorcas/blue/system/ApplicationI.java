package com.sevenorcas.blue.system;

/**
 * Application constants
 * Some are duplicated in the web client
 *  
 * Created July '22
 * [Licence]
 * @author John Stewart
 */
public interface ApplicationI {

	//PATHs used in web.xml
	final static public String APPLICATION_PATH  = "rest-api-x"; 
	static public String UPLOAD_PATH             = "upload";
	static public String ERROR_PATH              = "error";
	static public String LOGIN_PATH              = "blue-login";
	
	static public String REST_LOGIN              = "login";
	static public String REST_LOGIN_WEB          = "web";
	
	static public String CLIENT_SESSIONS         = "client-sessions";
	static public String CLIENT_SESSION_NR       = "client-nr";
	static public String NEXT_CLIENT_SESSION_NR  = "next-client-nr";
	static public String CLIENT_URL              = "client-url";
	
	static public Integer BASE_ORG_NR            = 0;
	
	final static public String CLASS_PATH_PREFIX = "com.sevenorcas.blue.";
	
	//Theme's are duplicated in web client (Session.ts)
	static public Integer THEME_LIGHT            = 0;
	static public Integer THEME_DARK             = 1;
	
}
