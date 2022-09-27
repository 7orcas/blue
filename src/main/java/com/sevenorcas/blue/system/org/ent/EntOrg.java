package com.sevenorcas.blue.system.org.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.annotation.FieldOverride;
import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.conf.EntityConfig;
import com.sevenorcas.blue.system.conf.FieldConfig;
import com.sevenorcas.blue.system.field.validationDEL.Validation;
import com.sevenorcas.blue.system.org.json.JsonOrg;

/**
* Organisation Entity
* 
* [Licence]
* Created 14.09.22
* @author John Stewart
*/

@Entity
@Table(name="org", schema="cntrl")
public class EntOrg extends BaseEnt<EntOrg> {

	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name="org_id_seq", sequenceName="org_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="org_id_seq")
	private Long id;
	
	
	private Boolean dvalue;
	
	public EntOrg () {
		super();
	}

	public Long getId() {
		return id;
	}
	public EntOrg setId(Long id) {
		this.id = id;
		return this;
	}
	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
    /**
     * Override field configurations
     * @param current org
     * @param config
     */
    @FieldOverride
    static public void getConfigOverride (EntOrg org, EntityConfig config) {
		for (int i=0;i<config.fields.size();i++) {
			FieldConfig f = config.fields.get(i);
			switch (f.name) {
				case "code":
					f.max = 21D;
					break;
			}
		}
    }
    
    
	public JsonOrg toJSon() {
		JsonOrg j = new JsonOrg();
		super.toJSon(j);
		j.dvalue = dvalue;
		return j;
	}
	
	public Boolean getDvalue() {
		return dvalue;
	}
	public EntOrg setDvalue(Boolean dvalue) {
		this.dvalue = dvalue;
		return this;
	}

	
	
}
