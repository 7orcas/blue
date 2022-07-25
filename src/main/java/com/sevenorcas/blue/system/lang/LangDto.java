package com.sevenorcas.blue.system.lang;

import com.sevenorcas.blue.system.base.BaseDto;

/**
* Created July '22
* 
* Language Data Transfer Object
* 
* [Licence]
* @author John Stewart
*/

public class LangDto extends BaseDto<LangDto> {

    private String label;
	private Long _id_lang_key;

	
	public LabelJsonRes toJSon() {
		LabelJsonRes j = new LabelJsonRes();
		super.toJSon(j);
		j.l = label;
		j._id_key = _id_lang_key;
		return j;
	}
	
	public String getLabel() {
		return label;
	}
	public LangDto setLabel(String label) {
		this.label = label;
		return this;
	}

	public Long getIdLangKey() {
		return _id_lang_key;
	}
	public LangDto setIdLangKey(Long _id_lang_key) {
		this._id_lang_key = _id_lang_key;
		return this;
	}
	
	
	
}
