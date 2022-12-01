package com.sevenorcas.blue.system.login.ent;

/**
* Json Login Response entity 
* Part 1 of login process
* 
* Created July '22
* [Licence]
* @author John Stewart
*/
public class JResLogin {
	public String baseUrl;
	public String uploadUrl;
	public String sessionId;
	public Integer clientNr;
	public String initialisationUrl;
	public String locationHref;
	public Boolean adminLoggedIn;
}
