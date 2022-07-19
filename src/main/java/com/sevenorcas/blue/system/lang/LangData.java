package com.sevenorcas.blue.system.lang;

public class LangData {

	private Long _id;
	private Integer _org;
    private String _code;
    private String lang;
	private Long _id_lang_key;

	public LangData () {
		
	}

	public Long getId() {
		return _id;
	}
	public LangData setId(Long _id) {
		this._id = _id;
		return this;
	}

	public Integer getOrg() {
		return _org;
	}
	public LangData setOrg(Integer _org) {
		this._org = _org;
		return this;
	}

	public String getCode() {
		return _code;
	}
	public LangData setCode(String _code) {
		this._code = _code;
		return this;
	}

	public String getLang() {
		return lang;
	}
	public LangData setLang(String lang) {
		this.lang = lang;
		return this;
	}

	public Long getIdLangKey() {
		return _id_lang_key;
	}

	public LangData setIdLangKey(Long _id_lang_key) {
		this._id_lang_key = _id_lang_key;
		return this;
	}
	
	
	
}
