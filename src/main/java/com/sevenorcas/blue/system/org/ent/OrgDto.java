package com.sevenorcas.blue.system.org.ent;

import com.sevenorcas.blue.system.base.BaseDto;
import com.sevenorcas.blue.system.org.json.OrgJsonRes;

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
	 * List JSon is a cutdown object (ie minimal fields)
	 * @return
	 */
	public OrgJsonRes toJSon() {
		OrgJsonRes j = new OrgJsonRes();
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
