package com.sevenorcas.blue.system.lifecycle;

import javax.enterprise.context.RequestScoped;

import com.sevenorcas.blue.system.login.ClientSession;

/**
 * Object to pass on the CLIENT_SESSION in a call
 *  
 * [Licence]
 * @author John Stewart
 */

@RequestScoped
public class ClientCall {
	private ClientSession clientSession;

	public ClientSession getClientSession() {
		return clientSession;
	}

	public void setClientSession(ClientSession cs) {
		this.clientSession = cs;
	}
	
}
