package com.sevenorcas.blue.system.org.ent;

import com.sevenorcas.blue.system.base.BaseDto;
import com.sevenorcas.blue.system.org.json.JsonOrg;

/**
* Created 22 July 2022
* 
* Data Transfer Object for organisation data
* 
* [Licence]
* @author John Stewart
*/

public class DtoOrg extends BaseDto<DtoOrg> {
	
	private boolean dvalue;
	
	/**
	 * JSon is a cut-down object (ie minimal fields)
	 * @return
	 */
	public JsonOrg toJSon() {
		JsonOrg j = new JsonOrg();
		super.toJSon(j);
		j.dvalue = dvalue;
		return j;
	}
	
	public boolean isDvalue() {
		return dvalue;
	}
	public DtoOrg setDvalue(boolean dvalue) {
		this.dvalue = dvalue;
		return this;
	}
	
	
}
