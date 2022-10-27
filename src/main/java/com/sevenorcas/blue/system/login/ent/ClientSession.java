package com.sevenorcas.blue.system.login.ent;

import com.sevenorcas.blue.system.ApplicationI;

/**
 * Client session object to store settings (for the session)
 * 
 * Created August '22
 * [Licence]
 * @author John Stewart
 */

public class ClientSession implements ApplicationI {

	private Long userId;
	private Integer sessionNr;
	private Integer orgNr;
	private String lang;
	private String user;

	public ClientSession (Long userId, String user, Integer orgNr, String lang) {
		this.userId = userId;
		this.user = user;
		this.orgNr = orgNr;
		this.lang = lang;
	}
	
	public ClientSession setSessionNr(Integer sessionNr) {
		this.sessionNr = sessionNr;
		return this;
	}

	public Long getUserId() {
		return userId;
	}
	public String getUser() {
		return user;
	}
	public Integer getSessionNr() {
		return sessionNr;
	}
	public Integer getOrgNr() {
		return orgNr;
	}
	public String getLang() {
		return lang;
	}
	
	public String getUrlSegment() {
		return CLIENT_SESSION_NR + sessionNr + "/";
	}
	
	
}
