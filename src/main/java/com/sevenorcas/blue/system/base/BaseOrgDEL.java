package com.sevenorcas.blue.system.base;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

public class BaseOrgDEL {

	private Integer nr;

	public BaseOrgDEL(Integer nr) {
		this.nr = nr;
	}

	public BaseOrgDEL (String encoding) {
		//do some decoding....
	}
	
	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}
	
	
}
