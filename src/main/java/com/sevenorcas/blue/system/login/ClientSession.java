package com.sevenorcas.blue.system.login;

/**
 * Client session object to store settings (for the session)
 * 
 * [Licence]
 * @author John Stewart
 */

public class ClientSession {

	private Integer sessionNr;
	private Integer orgNr;
	private String lang;
	
	
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
	
	
}
