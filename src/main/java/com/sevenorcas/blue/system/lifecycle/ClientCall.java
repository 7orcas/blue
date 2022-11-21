package com.sevenorcas.blue.system.lifecycle;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpSession;

import com.sevenorcas.blue.system.login.ent.ClientSession;

/**
 * Object to pass on the <code>CLIENT_SESSION</code> in a call
 *  
 * [Licence]
 * Created August '22
 * @author John Stewart
 */

@RequestScoped
public class ClientCall {
	private ClientSession clientSession;
	private HttpSession httpSession;
	
	public ClientSession getClientSession() {
		return clientSession;
	}
	
	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void set(HttpSession ses, ClientSession cs) {
		this.httpSession = ses;
		this.clientSession = cs;
	}
	
}
