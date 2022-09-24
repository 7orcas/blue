package com.sevenorcas.blue.system.lang.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.field.validation.Validation;

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
@SequenceGenerator(name="lang_label_id_seq", sequenceName="lang_label_id_seq", allocationSize=1)
public class LangLabelEnt extends BaseEnt<LangLabelEnt> {

	private static final long serialVersionUID = 1L;

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="lang_label_id_seq")
	private Long id;
	
	@Column(name="id_lang_key")
	private Long idLangKey;

	@Transient
	private String langKey;
	
	private String lang;
	
	public LangLabelEnt () {
		super();
	}

    public Long getId() {
		return id;
	}
	public LangLabelEnt setId(Long id) {
		this.id = id;
		return this;
	}

	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
	
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
