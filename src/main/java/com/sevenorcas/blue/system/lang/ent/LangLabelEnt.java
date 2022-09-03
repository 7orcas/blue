package com.sevenorcas.blue.system.lang.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.base.BaseEnt;

/**
* Created July '22
* Language label entity
* The LangKey entity is the parent reference for this entity 
* 
* [Licence]
* @author John Stewart
*/

@Entity
@Table(name="lang_label", schema="cntrl")
public class LangLabelEnt extends BaseEnt {

	private static final long serialVersionUID = 1L;
	
	@Column(name="id_lang_key")
	private Long idLangKey;

	@Transient
	private String langKey;
	
	private String lang;
	
	public LangLabelEnt () {
		super();
	}

	public Long getIdLangKey() {
		return idLangKey;
	}
	public void setIdLangKey(Long idLangKey) {
		this.idLangKey = idLangKey;
	}

	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLangKey() {
		return langKey;
	}
	public void setLangKey(String key) {
		this.langKey = key;
	}

	
}
