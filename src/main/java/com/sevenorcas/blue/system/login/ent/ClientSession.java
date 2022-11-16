package com.sevenorcas.blue.system.login.ent;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * Client session object to store settings (for the session)
 * 
 * Created August '22
 * [Licence]
 * @author John Stewart
 */

public class ClientSession implements ApplicationI {

	private EntUser user;
	private Integer sessionNr;
	private Integer orgNr;
	private String lang;
	
	public ClientSession (EntUser user) {
		this.user = user;
		this.orgNr = user.getOrgNrLogin();
		this.lang = user.getLangLogin();
	}
	
	public ClientSession setSessionNr(Integer sessionNr) {
		this.sessionNr = sessionNr;
		return this;
	}

	public EntUser getUser() {
		return user;
	}
	public Long getUserId() {
		return user.getId();
	}
	public String getUserName() {
		return user.getUserName();
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
