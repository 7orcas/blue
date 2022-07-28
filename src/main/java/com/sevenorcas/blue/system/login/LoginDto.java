package com.sevenorcas.blue.system.login;

import com.sevenorcas.blue.system.base.BaseDto;

/**
* Created 28 July '22
* 
* Login Data Transfer Object
* 
* [Licence]
* @author John Stewart
*/

public class LoginDto extends BaseDto<LoginDto> {

    private String descr;
    private boolean defaultValue;
	
//	public LangJsonRes toJSon() {
//		LangJsonRes j = new LangJsonRes();
//		super.toJSon(j);
//		j.d = descr;
//		j.x = defaultValue;
//		return j;
//	}

	public String getDescr() {
		return descr;
	}
	public LoginDto setDescr(String descr) {
		this.descr = descr;
		return this;
	}
	public boolean isDefaultValue() {
		return defaultValue;
	}
	public LoginDto setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
		
	
	
}
