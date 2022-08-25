package com.sevenorcas.blue.system.lang;

import com.sevenorcas.blue.system.base.BaseDto;

/**
* Created 28 July '22
* 
* Language Data Transfer Object
* 
* [Licence]
* @author John Stewart
*/

public class LangDto extends BaseDto<LangDto> {

    private String descr;
    private boolean defaultValue;
	
	public LangJsonRes toJSon() {
		LangJsonRes j = new LangJsonRes();
		super.toJSon(j);
		j.descr = descr;
		j.dvalue = defaultValue;
		return j;
	}

	public String getDescr() {
		return descr;
	}
	public LangDto setDescr(String descr) {
		this.descr = descr;
		return this;
	}
	public boolean isDefaultValue() {
		return defaultValue;
	}
	public LangDto setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
		
	
	
}
