package com.sevenorcas.blue.system.lang.ent;

import com.sevenorcas.blue.system.base.BaseDto;
import com.sevenorcas.blue.system.lang.json.JResLang;

/**
* Created 28 July '22
* 
* Language Data Transfer Object
* 
* [Licence]
* @author John Stewart
*/

public class DtoLang extends BaseDto<DtoLang> {

    private String descr;
    private boolean defaultValue;
	
	public JResLang toJSon() {
		JResLang j = new JResLang();
		super.toJSon(j);
		j.descr = descr;
		j.dvalue = defaultValue;
		return j;
	}

	public String getDescr() {
		return descr;
	}
	public DtoLang setDescr(String descr) {
		this.descr = descr;
		return this;
	}
	public boolean isDefaultValue() {
		return defaultValue;
	}
	public DtoLang setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
		
	
	
}
