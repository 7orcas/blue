package com.sevenorcas.blue.system.role.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.conf.ent.ForeignKey;
import com.sevenorcas.blue.system.conf.ent.ValidationCallbackI;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.login.ent.JsonUrlPermission;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.PermissionI;

/**
* Permission Entity
* Notes:
*  - The code field holds the REST url
* 
* [Licence]
* Created 03.10.22
* @author John Stewart
*/

@Entity
@Table(name="permission", schema="cntrl")
public class EntPermission extends BaseEntity<EntPermission> implements PermissionI{

	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name="permission_id_seq", sequenceName="cntrl.permission_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="permission_id_seq")
	private Long id;
	
	private String crud;
	
	/**
     * Override field configurations
     */
	static public EntityConfig getConfig (EntOrg org) throws Exception {
		return BaseEntity.getConfig(org)
			.put(new FieldConfig("orgNr").min(0))
			.put(new FieldConfig("code").nonNull().max(30))
			.put(new FieldConfig("id").add(new ForeignKey(tableName(EntRolePermission.class), "permission_id", "role")))
			.put(new FieldConfig("crud").nonNull().max(4)
    	    		.callback(new ValidationCallbackI() {
						@Override
						public <T extends BaseEntity<T>> ValidationError validate(T ent) throws Exception {
							EntPermission p = (EntPermission)ent;
							for (int i = 0; p.crud != null && i < p.crud.length(); i++) {
								char ch = p.crud.toUpperCase().charAt(i);
								if (ch != PERM_WILDCARD 
										&& ch != PERM_CREATE 
										&& ch != PERM_READ 
										&& ch != PERM_UPDATE 
										&& ch != PERM_DELETE 
										&& ch != PERM_NONE) {
									return new ValidationError(VAL_ERROR_INVALID_VALUE)
							    			.setEntityId(ent.getId())
							    			.setCode(ent.getCode())	
							    			.setField("crud");
								}
							}
							return null;
						}
    	    		}));
    }
	
	
	public EntPermission () {
		super();
	}

	public Long getId() {
		return id;
	}
	public EntPermission setId(Long id) {
		this.id = id;
		return this;
	}
		
    /**
     * Format the CRUD string
     */
    public void formatCrud() {
    	crud = crud == null? "" : crud.toUpperCase();
    	if (crud.trim().equals(Character.toString(PERM_WILDCARD))) {
    		crud = Character.toString(PERM_WILDCARD);
    		return;
    	}
    	
    	boolean C = contains(crud, PERM_CREATE);
    	boolean R = contains(crud, PERM_READ);
    	boolean U = contains(crud, PERM_UPDATE);
    	boolean D = contains(crud, PERM_DELETE);
    	if (C && R && U && D) {
    		crud = Character.toString(PERM_WILDCARD);
    		return;
    	}
    	
    	crud = add(C, PERM_CREATE);
    	crud += add(R, PERM_READ);
    	crud += add(U, PERM_UPDATE);
    	crud += add(D, PERM_DELETE);
    }
    
    /**
     * Does the crud value contain the permission?
     * @param crud
     * @param perm
     * @return
     */
    private boolean contains (String crud, Character perm) {
    	return crud.contains(Character.toString(perm));
    }
    
    private String add (boolean value, Character perm) {
    	return value? Character.toString(perm) : Character.toString(PERM_NONE); 
    }
    
    /**
     * Add the passed in crud settings to <code>this</code> entity
     * @param crud
     */
    public void combine(String crud) {
    	crud = crud == null? "" : crud.toUpperCase();
    	if (this.crud.equals(Character.toString(PERM_WILDCARD))) return;
    	if (crud.equals(Character.toString(PERM_WILDCARD))) {
    		this.crud = crud;
    		return;
    	}
    	
    	combine(crud, PERM_CREATE);
    	combine(crud, PERM_READ);
    	combine(crud, PERM_UPDATE);
    	combine(crud, PERM_DELETE);
    	
    	formatCrud();
    }
    
    private void combine(String crud, Character perm) {
    	if (contains(crud, perm) && !contains(this.crud, perm)) {
    		this.crud += add(true, perm);
    	}
    }
    
	public JsonPermission toJSon() {
		JsonPermission j = super.toJson(new JsonPermission());
		j.crud = crud;
		return j;
	}

	public JsonUrlPermission toJsonUrlPermission() {
		JsonUrlPermission j = new JsonUrlPermission();
		j.perm = code; 
		j.crud = crud.replaceAll("-", "");
		return j;
	}
	
	public String getCrud() {
		return crud;
	}
	public EntPermission setCrud(String crud) {
		this.crud = crud;
		return this;
	}
	
}
