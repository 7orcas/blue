package com.sevenorcas.blue.system.user.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Validation;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.user.jsonDELETE.JsonUserRole_DELETE;

/**
 * User-Role join entity 
 * 
 * TODO document this bean
 * 
 * Created July '22
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="zzz_role", schema="cntrl")
public class EntUserRole extends BaseEntity<EntUserRole>{
	
	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name="zzz_role_id_seq", sequenceName="cntrl.zzz_role_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="zzz_role_id_seq")
	private Long id;

	/** Header foreign key */
	@ManyToOne
	@JoinColumn(name="zzz_id")
	private EntUser entUser;
	
	/** Parent */     
	@Column(name="zzz_id",insertable=false,updatable=false)
	private Long userId;
	
	/** Foreign Key */
	@Column(name="role_id")
	private Long roleId;

	@Transient
	private EntRole entRole;
	
	/**
     * Override field configurations
     */
	static public EntityConfig getConfig (EntOrg org) throws Exception {
		return BaseEntity.getConfig(org)
				.put(new FieldConfig("orgNr").min(0))
				.put(new FieldConfig("code").unused())
				.put(new FieldConfig("descr").unused())
				.put(new FieldConfig("roleId").uniqueInParent());
    }
	
	public EntUserRole () {
	}

	public EntUser getEntUser() {
		return entUser;
	}
	public EntUserRole setEntUser(EntUser entUser) {
		this.entUser = entUser;
		return this;
	}
	
    public Long getId() {
		return id;
	}
	public EntUserRole setId(Long id) {
		this.id = id;
		return this;
	}

	public JsonUserRole_DELETE toJSon() {
		JsonUserRole_DELETE j = super.toJSon(new JsonUserRole_DELETE());
		j.roleId = roleId;
		if (entRole != null) {
			j.code = entRole.getCode();
			j.descr = entRole.getDescr();
			j.orgNr = entRole.getOrgNr();
		}
		return j;
	}
	
	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
	
	public Long getUserId() {
		return userId;
	}
	public EntUserRole setUserId(Long userId) {
		this.userId = userId;
		return this;
	}
	public Long getRoleId() {
		return roleId;
	}
	public EntUserRole setRoleId(Long roleId) {
		this.roleId = roleId;
		return this;
	}

	public EntRole getEntRole() {
		return entRole;
	}
	public EntUserRole setEntRole(EntRole entRole) {
		this.entRole = entRole;
		return this;
	}
	
}
