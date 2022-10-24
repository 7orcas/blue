package com.sevenorcas.blue.system.lifecycle;

import javax.enterprise.context.RequestScoped;

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

	public ClientSession getClientSession() {
		return clientSession;
	}

	public void setClientSession(ClientSession cs) {
		this.clientSession = cs;
	}
	
}
