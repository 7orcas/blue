package com.sevenorcas.blue.system.lang;

import com.sevenorcas.blue.system.base.BaseDto;

/**
* Created July '22
* 
* Label Data Transfer Object
* 
* [Licence]
* @author John Stewart
*/

public class LabelDto extends BaseDto<LabelDto> {

    private String label;
	private Long id_lang_key;

	
	public LabelJsonRes toJSon() {
		LabelJsonRes j = new LabelJsonRes();
		super.toJSon(j);
		j.l = label;
		j.id_key = id_lang_key;
		return j;
	}
	
	public String getLabel() {
		return label;
	}
	public LabelDto setLabel(String label) {
		this.label = label;
		return this;
	}

	public Long getIdLangKey() {
		return id_lang_key;
	}
	public LabelDto setIdLangKey(Long id_lang_key) {
		this.id_lang_key = id_lang_key;
		return this;
	}
	
	
	
}
