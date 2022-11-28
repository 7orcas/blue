package com.sevenorcas.blue.system.lifecycle;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpSession;

import com.sevenorcas.blue.system.login.ent.ClientSession;

/**
 * Object to pass on the <code>CLIENT_SESSION</code> in a call
 * Also, if an http session is present but not valid then return a login redirect to the client
 *  
 * [Licence]
 * Created August '22
 * @author John Stewart
 */

@RequestScoped
public class ClientCall {
	private ClientSession clientSession;
	private HttpSession httpSession;
	private String clientUrl;
	private Boolean loginRedirect;
	
	public ClientSession getClientSession() {
		return clientSession;
	}
	
	public HttpSession getHttpSession() {
		return httpSession;
	}

	public ClientCall set(HttpSession ses, ClientSession cs) {
		this.httpSession = ses;
		this.clientSession = cs;
		return this;
	}

	public boolean isLoginRedirect() {
		return loginRedirect != null && loginRedirect;
	}
	public ClientCall setLoginRedirect() {
		this.loginRedirect = true;
		return this;
	}

	public String getClientUrl() {
		return clientUrl;
	}
	public ClientCall setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
		return this;
	}
	
	
}
