package com.sevenorcas.blue.system.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Validation;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * User-Role join entity 
 * 
 * TODO document this bean
 * 
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="zzz_role", schema="cntrl")
public class EntUserRole extends BaseEnt<EntUserRole>{
	
	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name="zzz_role_id_seq", sequenceName="cntrl.zzz_role_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="zzz_role_id_seq")
	private Long id;

	
	@Column(name="zzz_id")
	private Long userId;
	@Column(name="role_id")
	private Long roleId;
	
	/**
     * Override field configurations
     */
	static public EntityConfig getConfig (EntOrg org) {
		return BaseEnt.getConfig(org)
    	    .put(new FieldConfig("code").unused())
    	    .put(new FieldConfig("descr").unused());
    }
	
	public EntUserRole () {
	}

    public Long getId() {
		return id;
	}
	public EntUserRole setId(Long id) {
		this.id = id;
		return this;
	}

	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
	
}
