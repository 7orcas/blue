package com.sevenorcas.blue.system.org.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.field.Encode;

/**
* Organisation Entity
* 
* 
* [Licence]
* Created 14.09.22
* @author John Stewart
*/

@Entity
@Table(name="org", schema="cntrl")
public class EntOrg extends BaseEntity<EntOrg> {

	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name="org_id_seq", sequenceName="cntrl.org_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="org_id_seq")
	private Long id;
	
	private Boolean dvalue;
	
	@Transient private Integer loginAttempts;
	
	public EntOrg () {
	}

	public Long getId() {
		return id;
	}
	public EntOrg setId(Long id) {
		this.id = id;
		return this;
	}
	
	/**
     * Override field configurations
     */
	static public EntityConfig getConfig (EntOrg org) throws Exception {
		return BaseEntity.getConfig(org)
			.put(new FieldConfig("orgNr").min(1).uniqueIgnoreOrgNr())
    	    .put(new FieldConfig("code").max(20).uniqueIgnoreOrgNr());
    }

	/**
	 * Decode the encoded field
	 */
	public void decode() throws Exception {
		//Encoded fields
		Encode encode = new Encode().decode(encoded);
		loginAttempts = encode.getInteger("attempts");
		
	}
	
	public JsonOrg toJSon(EntOrg org) throws Exception {
		decode();
		JsonOrg j = super.toJSon(new JsonOrg());
		j.dvalue = dvalue;
		j.loginAttempts = loginAttempts;
		
		return j;
	}
	
	public boolean isDvalue() {
		return dvalue != null && dvalue;
	}
	public EntOrg setDvalue(Boolean dvalue) {
		this.dvalue = dvalue;
		return this;
	}
	public final Boolean getDvalue() {
		return dvalue;
	}

	public Integer getLoginAttempts() {
		return loginAttempts;
	}
	public EntOrg setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
		return this;
	}
	
	
	
	
}
