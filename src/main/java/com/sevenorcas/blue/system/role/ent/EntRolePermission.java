package com.sevenorcas.blue.system.role.ent;

import java.util.ArrayList;

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

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.role.json.JsonRole;
import com.sevenorcas.blue.system.role.json.JsonRolePermission;

/**
* Role-Permission join entity
* 
* [Licence]
* Created 03.10.22
* @author John Stewart
*/

@Entity
@Table(name="role_permission", schema="cntrl")
public class EntRolePermission extends BaseEnt<EntRolePermission> {

	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name="role_permission_id_seq", sequenceName="cntrl.role_permission_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="role_permission_id_seq")
	private Long id;
	
	/** Header foreign key */
	@ManyToOne
	@JoinColumn(name="role_id")
	private EntRole entRole;
	
	/** Parent */     
	@Column(insertable=false,updatable=false)
	private Long role_id;
	
	@Column(name="permission_id")
	private Long permissionId;
	
	@Transient
	private EntPermission entPermission;
	
	public EntRolePermission () {
		super();
	}

	public Long getId() {
		return id;
	}
	public EntRolePermission setId(Long id) {
		this.id = id;
		return this;
	}
	
	public JsonRolePermission toJSon() {
		JsonRolePermission j = super.toJSon(new JsonRolePermission());
		j.permission_id = permissionId;
		if (entPermission != null) {
			j.code = entPermission.getCode();
			j.descr = entPermission.getDescr();
			j.crud = entPermission.getCrud();
		}
		return j;
	}
	
	
//	
//	/**
//     * Is <b>this</b> a valid entity?
//     * @return
//     */
//    protected void validate (Validation validationx) { }


//	public EntRole getEntRole() {
//		return entRole;
//	}
//	public EntRolePermission setEntRole(EntRole entRole) {
//		this.entRole = entRole;
//		return this;
//	}

	public Long getRoleId() {
		return role_id;
	}
	public EntRolePermission setRoleId(Long role_id) {
		this.role_id = role_id;
		return this;
	}

	public Long getPermissionId() {
		return permissionId;
	}
	public EntRolePermission setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
		return this;
	}

	public EntPermission getEntPermission() {
		return entPermission;
	}
	public EntRolePermission setEntPermission(EntPermission entPermission) {
		this.entPermission = entPermission;
		return this;
	}
	
	
}
