package com.sevenorcas.blue.system.login;

import com.sevenorcas.blue.system.ApplicationI;

/**
 * Client session object to store settings (for the session)
 * 
 * [Licence]
 * @author John Stewart
 */

public class ClientSession implements ApplicationI {

	private Long userId;
	private Integer sessionNr;
	private Integer orgNr;
	private String lang;

	public ClientSession (Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return userId;
	}
	public Integer getSessionNr() {
		return sessionNr;
	}
	public ClientSession setSessionNr(Integer sessionNr) {
		this.sessionNr = sessionNr;
		return this;
	}
	public Integer getOrgNr() {
		return orgNr;
	}
	public ClientSession setOrgNr(Integer orgNr) {
		this.orgNr = orgNr;
		return this;
	}
	public String getLang() {
		return lang;
	}
	public ClientSession setLang(String lang) {
		this.lang = lang;
		return this;
	}
	
	public String getUrlSegment() {
		return CLIENT_SESSION_NR + sessionNr + "/";
	}
	
	
}
