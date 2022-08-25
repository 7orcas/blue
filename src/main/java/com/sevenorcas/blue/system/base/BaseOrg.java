package com.sevenorcas.blue.system.base;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

public class BaseOrg {

	private Integer nr;

	public BaseOrg(Integer nr) {
		this.nr = nr;
	}

	public BaseOrg (String encoding) {
		//do some decoding....
	}
	
	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}
	
	
}
