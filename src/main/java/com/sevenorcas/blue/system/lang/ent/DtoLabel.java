package com.sevenorcas.blue.system.lang.ent;

import com.sevenorcas.blue.system.base.BaseDto;
import com.sevenorcas.blue.system.lang.json.JResLabel;

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
	private Long id_lang_key;

	
	public JResLabel toJSon() {
		JResLabel j = new JResLabel();
		super.toJSon(j);
		j.label = label;
		j.id_key = id_lang_key;
		return j;
	}
	
	public String getLabel() {
		return label;
	}
	public DtoLabel setLabel(String label) {
		this.label = label;
		return this;
	}

	public Long getIdLangKey() {
		return id_lang_key;
	}
	public DtoLabel setIdLangKey(Long id_lang_key) {
		this.id_lang_key = id_lang_key;
		return this;
	}
	
}
