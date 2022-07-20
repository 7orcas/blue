package com.sevenorcas.blue.system.lifecycle;

import java.util.Hashtable;

import com.sevenorcas.blue.system.base.BaseOrg;

public class CallObject {

	private BaseOrg org;
	private String lang;
	
	
	Hashtable<String, Object> objects = new Hashtable<>();
	
	public CallObject (String encoding) {
		lang = "en";
		//do some decoding....
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


	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
}
