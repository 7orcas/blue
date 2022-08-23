package com.sevenorcas.blue.system.base;

import com.sevenorcas.blue.system.lang.LangLabelEnt;

/**
* Created 22 July 2022
* 
* Base JSON Object for actual module JSON classes to extend
* 
* [Licence]
* @author John Stewart
*/

public class BaseJsonRes {
	public Long i;
	public String c;
	public Integer o;
	public Boolean a;
	
	public void initialise (BaseEnt ent) {
		i = ent.getId();
		c = ent.getCode();
		o = ent.getOrg();
		a = ent.isActive();
	}
	
}
