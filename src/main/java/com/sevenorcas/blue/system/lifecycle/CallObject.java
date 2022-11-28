package com.sevenorcas.blue.system.lifecycle;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;

import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.ent.EntPermission;

/**
 * Wrapper object for a client call
 *  
 * Create July '22
 * [Licence]
 * @author John Stewart
 */
public class CallObject {

	private ClientSession clientSession;
	private HttpSession httpSession;
	private EntOrg org;	
	private EntPermission perm;
	
	Hashtable<String, Object> objects = new Hashtable<>();
	
	public CallObject (String encoding) {
		//do some decoding....
	}
	
	
	
	public ClientSession getClientSession() {
		return clientSession;
	}
	public CallObject setClientSession(ClientSession ses) {
		this.clientSession = ses;
		return this;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}
	public CallObject setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
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
	
	public EntPermission getPermission() {
		return perm;
	}
	public CallObject setPermission(EntPermission perm) {
		this.perm = perm;
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
		return clientSession.getLang() != null? clientSession.getLang() : "en";
	}
	
	/**
	 * Convenience method to get the user id
	 * @return
	 */
	public Long getUserId() {
		return clientSession.getUserId();
	}
}
