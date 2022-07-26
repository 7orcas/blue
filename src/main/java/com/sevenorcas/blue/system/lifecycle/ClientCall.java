package com.sevenorcas.blue.system.lifecycle;

import javax.enterprise.context.RequestScoped;

/**
 * Object to pass on the CLIENT_SESSION_NR
 *  
 * [Licence]
 * @author John Stewart
 */

@RequestScoped
public class ClientCall {
	private Integer clientSessionNr;

	public Integer getClientSessionNr() {
		return clientSessionNr;
	}

	public void setClientSessionNr(Integer nr) {
		this.clientSessionNr = nr;
	}
	
}
