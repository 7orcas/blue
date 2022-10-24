package com.sevenorcas.blue.system.lifecycle;

import java.util.Hashtable;

import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.org.ent.EntOrg;

public class CallObject {

	private ClientSession ses;
	private EntOrg org;	
	
	Hashtable<String, Object> objects = new Hashtable<>();
	
	public CallObject (String encoding) {
		//do some decoding....
	}
	
	
	
	public ClientSession getClientSession() {
		return ses;
	}
	public CallObject setClientSession(ClientSession ses) {
		this.ses = ses;
		return this;
	}


	public Integer getOrgNr() {
		return org.getOrgNr();
	}
	public EntOrg getOrg() {
		return org;
	}
	public CallObject setOrg(EntOrg org) {
		this.org = org;
		return this;
	}


	public CallObject put (String key, Object obj) {
		objects.put(key, obj);
		return this;
	}
	public Object get (String key) {
		return objects.get(key);
	}

	//TODO
	public String getLang() {
		return ses.getLang() != null? ses.getLang() : "en";
	}
	
	/**
	 * Convenience method to get the user id
	 * @return
	 */
	public Long getUserId() {
		return ses.getUserId();
	}
}
