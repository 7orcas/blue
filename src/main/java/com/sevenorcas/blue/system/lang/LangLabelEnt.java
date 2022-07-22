package com.sevenorcas.blue.system.lang;

import com.sevenorcas.blue.system.base.BaseEnt;

/**
* Created July '22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

public class LangLabelEnt extends BaseEnt {

	private Long _id_lang_key;
	private String lang;
	
	public LangLabelEnt () {
		super();
	}

	public Long getIdLangKey() {
		return _id_lang_key;
	}
	public void setIdLangKey(Long _id_lang_key) {
		this._id_lang_key = _id_lang_key;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
	
}
