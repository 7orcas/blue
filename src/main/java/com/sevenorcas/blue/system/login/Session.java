package com.sevenorcas.blue.system.login;

/**
 * User session object to store settings (for the session)
 * 
 * [Licence]
 * @author John Stewart
 */

public class Session {

	private Integer sessionNr;
	private Integer orgNr;
	private String lang;
	
	
	public Integer getSessionNr() {
		return sessionNr;
	}
	public Session setSessionNr(Integer sessionNr) {
		this.sessionNr = sessionNr;
		return this;
	}
	public Integer getOrgNr() {
		return orgNr;
	}
	public Session setOrgNr(Integer orgNr) {
		this.orgNr = orgNr;
		return this;
	}
	public String getLang() {
		return lang;
	}
	public Session setLang(String lang) {
		this.lang = lang;
		return this;
	}
	
	
}
