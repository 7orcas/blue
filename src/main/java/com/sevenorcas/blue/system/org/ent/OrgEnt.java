package com.sevenorcas.blue.system.org.ent;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.field.validation.Validation;
import com.sevenorcas.blue.system.org.json.OrgJson;

/**
* Organisation Entity
* 
* [Licence]
* Created 14.09.22
* @author John Stewart
*/

@Entity
@Table(name="org", schema="cntrl")
public class OrgEnt extends BaseEnt<OrgEnt> {

	private static final long serialVersionUID = 1L;
	
	private Boolean dvalue;
	
	public OrgEnt () {
		super();
	}

	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
	public OrgJson toJSon() {
		OrgJson j = new OrgJson();
		super.toJSon(j);
		j.dvalue = dvalue;
		return j;
	}
	
	public Boolean getDvalue() {
		return dvalue;
	}
	public OrgEnt setDvalue(Boolean dvalue) {
		this.dvalue = dvalue;
		return this;
	}

	
	
}
