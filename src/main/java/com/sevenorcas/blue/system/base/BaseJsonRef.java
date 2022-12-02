package com.sevenorcas.blue.system.base;

/**
* Base JSON Object for actual module JSON reference classes to extend
* 
* Created 02.12.2022
* [Licence]
* @author John Stewart
*/
public class BaseJsonRef extends BaseJsonRes {
	public Integer sort;
	public Boolean dvalue;
	
	public void initialise (BaseEntityRef<?> ent) {
		sort = ent.getSort();
		dvalue = ent.getDvalue();
	}
	
}
