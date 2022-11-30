package com.sevenorcas.blue.system.org.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.base.BaseUtil;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.field.Encode;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;

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

	static private final long serialVersionUID = 1L;
	static private Integer DEFAULT_LOGIN_ATTEMPTS = AppProperties.getInstance().getInteger("LoginAttemptsDefault");

	@Id  
	@SequenceGenerator(name="org_id_seq", sequenceName="cntrl.org_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="org_id_seq")
	private Long id;
	
	private Boolean dvalue;
	
	@Transient private Integer maxLoginAttempts;
	
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
		Encode encode = encoder();
		maxLoginAttempts = encode.get("attempts", DEFAULT_LOGIN_ATTEMPTS);
	}
	
	public JsonOrg toJSon(EntOrg org) throws Exception {
		decode();
		JsonOrg j = super.toJson(new JsonOrg());
		j.dvalue = dvalue;
		j.maxLoginAttempts = maxLoginAttempts;
		
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

	public Integer getMaxLoginAttempts() {
		return maxLoginAttempts;
	}
	public Integer getMaxLoginAttemptsIncludeDefault() {
		return maxLoginAttempts != null? maxLoginAttempts : DEFAULT_LOGIN_ATTEMPTS;
	}
	public EntOrg setMaxLoginAttempts(Integer loginAttempts) {
		this.maxLoginAttempts = loginAttempts;
		return this;
	}
	/**
	 * Have the maximum login attempts been exceeded?
	 * @return
	 */
	public boolean isMaxLoginAttempts(Integer loginAttempts) throws Exception {
		if (!decoded) {
			throw new RedException(IntHardCodeLangKey.LK_UNKNOWN_ERROR, "EntOrg has not been decoded");
		}
		
		loginAttempts = BaseUtil.nonNull(loginAttempts);
		Integer max = getMaxLoginAttemptsIncludeDefault();
		return max != null && loginAttempts > max;
	}
	
	
	
}
