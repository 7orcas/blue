package com.sevenorcas.blue.system.lang.ent;

import com.sevenorcas.blue.system.base.BaseDto;

/**
* Created July '22
* 
* Label Data Transfer Object
* 
* [Licence]
* @author John Stewart
*/

public class DtoLabel extends BaseDto<DtoLabel> {

    private String label;
	private Long langKeyId;

	
	public JResLabel toJSon() {
		JResLabel j = new JResLabel();
		super.toJSon(j);
		j.label = label;
		j.keyId = langKeyId;
		return j;
	}
	
	public String getLabel() {
		return label;
	}
	public DtoLabel setLabel(String label) {
		this.label = label;
		return this;
	}

	public Long getLangKeyId() {
		return langKeyId;
	}
	public DtoLabel setLangKeyId(Long id_lang_key) {
		this.langKeyId = id_lang_key;
		return this;
	}
	
}
