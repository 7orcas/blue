package com.sevenorcas.blue.system.org;

import com.sevenorcas.blue.system.base.BaseDto;

/**
* Created 22 July 2022
* 
* Data Transfer Object for organisation data
* 
* [Licence]
* @author John Stewart
*/

public class OrgDto extends BaseDto<OrgDto> {
	
	private boolean defaultValue;
	
	public OrgJsonRes toJSon() {
		OrgJsonRes j = new OrgJsonRes();
		super.toJSon(j);
		j.d = defaultValue;
		return j;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}
	public OrgDto setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
	
}
