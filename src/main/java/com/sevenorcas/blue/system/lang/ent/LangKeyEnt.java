package com.sevenorcas.blue.system.lang.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.field.validation.Validation;

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
@SequenceGenerator(name="lang_key_id_seq", sequenceName="lang_key_id_seq", allocationSize=1)
public class LangKeyEnt extends BaseEnt<LangKeyEnt> {

	private static final long serialVersionUID = 1L;

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="lang_key_id_seq")
	private Long id;
	
	public LangKeyEnt () {
		super();
	}
	
    public Long getId() {
		return id;
	}
	public LangKeyEnt setId(Long id) {
		this.id = id;
		return this;
	}

	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
}
