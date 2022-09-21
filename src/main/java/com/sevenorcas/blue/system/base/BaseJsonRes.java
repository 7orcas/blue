package com.sevenorcas.blue.system.base;

/**
* Created 22 July 2022
* 
* Base JSON Object for actual module JSON classes to extend
* 
* [Licence]
* @author John Stewart
*/

public class BaseJsonRes {
	public Long id;
	public String code;
	public Integer orgNr;
	public Boolean active;
	
	public void initialise (BaseEnt ent) {
		id = ent.getId();
		code = ent.getCode();
		orgNr = ent.getOrgNr();
		active = ent.isActive();
	}
	
}
