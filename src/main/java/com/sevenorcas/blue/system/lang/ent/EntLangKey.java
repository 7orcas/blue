package com.sevenorcas.blue.system.lang.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEntity;

/**
* Created July '22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Entity
@Table(name="lang_key", schema="cntrl")
public class EntLangKey extends BaseEntity<EntLangKey> {

	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name="lang_key_id_seq", sequenceName="cntrl.lang_key_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="lang_key_id_seq")
	private Long id;
	
	public EntLangKey () {
		super();
	}
	
    public Long getId() {
		return id;
	}
	public EntLangKey setId(Long id) {
		this.id = id;
		return this;
	}
	
}
