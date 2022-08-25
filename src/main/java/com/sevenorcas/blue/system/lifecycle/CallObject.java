package com.sevenorcas.blue.system.lifecycle;

import java.util.Hashtable;

import com.sevenorcas.blue.system.base.BaseOrg;
import com.sevenorcas.blue.system.login.ClientSession;

public class CallObject {

	private ClientSession ses;
	private BaseOrg org;	
	
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



	public BaseOrg getOrg() {
		return org;
	}
	public void setOrg(BaseOrg org) {
		this.org = org;
	}


	public void put (String key, Object obj) {
		objects.put(key, obj);
	}
	public Object get (String key) {
		return objects.get(key);
	}

	//TODO
	public String getLang() {
		return ses.getLang() != null? ses.getLang() : "en";
	}
	
	
}
