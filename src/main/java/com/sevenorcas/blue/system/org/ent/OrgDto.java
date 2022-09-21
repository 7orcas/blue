package com.sevenorcas.blue.system.org.ent;

import com.sevenorcas.blue.system.base.BaseDto;
import com.sevenorcas.blue.system.org.json.OrgJson;

/**
* Created 22 July 2022
* 
* Data Transfer Object for organisation data
* 
* [Licence]
* @author John Stewart
*/

public class OrgDto extends BaseDto<OrgDto> {
	
	private boolean dvalue;
	
	/**
	 * JSon is a cut-down object (ie minimal fields)
	 * @return
	 */
	public OrgJson toJSon() {
		OrgJson j = new OrgJson();
		super.toJSon(j);
		j.dvalue = dvalue;
		return j;
	}
	
	public boolean isDvalue() {
		return dvalue;
	}
	public OrgDto setDvalue(boolean dvalue) {
		this.dvalue = dvalue;
		return this;
	}
	
	
}
