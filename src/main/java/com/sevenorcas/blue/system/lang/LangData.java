package com.sevenorcas.blue.system.lang;

public class LangData {

	private Long _id;
	private Integer _org;
    private String key;
    private String label;
	private Long _id_lang_key;

	public LangData () {
		
	}

	public LangJsonRes toJSon() {
		LangJsonRes j = new LangJsonRes();
		j._id = _id;
		j.label = label;
		j._code = key;
		j._id_lang_key = _id_lang_key;
		return j;
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

	public String getKey() {
		return key;
	}
	public LangData setKey(String key) {
		this.key = key;
		return this;
	}

	public String getLabel() {
		return label;
	}
	public LangData setLabel(String label) {
		this.label = label;
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
