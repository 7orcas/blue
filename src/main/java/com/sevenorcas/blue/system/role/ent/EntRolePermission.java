package com.sevenorcas.blue.system.role.ent;

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
import com.sevenorcas.blue.system.role.json.JsonPermission;

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
	@SequenceGenerator(name="role_permission_id_seq", sequenceName="role_permission_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="role_permission_id_seq")
	private Long id;
	
	private String crud;
	
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
	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
    
	public JsonPermission toJSon() {
		JsonPermission j = super.toJSon(new JsonPermission());
		j.crud = crud;
		return j;
	}

	public String getCrud() {
		return crud;
	}

	public EntRolePermission setCrud(String crud) {
		this.crud = crud;
		return this;
	}
	
	

	
	
}
