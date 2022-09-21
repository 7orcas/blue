package com.sevenorcas.blue.system.lang.ent;

import javax.persistence.Entity;
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
public class LangKeyEnt extends BaseEnt {

	private static final long serialVersionUID = 1L;
	
	public LangKeyEnt () {
		super();
	}
	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
}
