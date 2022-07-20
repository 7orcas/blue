package com.sevenorcas.blue.system.lifecycle;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestUser {
	private Integer orgNr;

	public Integer getOrgNr() {
		return orgNr;
	}

	public void setOrgNr(Integer orgNr) {
		this.orgNr = orgNr;
	}
	
}
