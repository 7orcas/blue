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
public class EntPermission extends BaseEntity<EntPermission> {

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
								if (ch != '*' && ch != 'C' && ch != 'R' && ch != 'U' && ch != 'D' && ch != '-') {
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
    	crud = crud == null? "" : crud;
    	if (crud.trim().equals("*")) {
    		crud = "*";
    		return;
    	}
    	
    	boolean C = crud.toUpperCase().contains("C");
    	boolean R = crud.toUpperCase().contains("R");
    	boolean U = crud.toUpperCase().contains("U");
    	boolean D = crud.toUpperCase().contains("D");
    	if (C && R && U && D) {
    		crud = "*";
    		return;
    	}
    	crud = (C?"C":"-")
    			+(R?"R":"-")
    			+(U?"U":"-")
    			+(D?"D":"-");
    }
    
    /**
     * Add the passed in crud settings to <code>this</code> entity
     * @param crud
     */
    public void combine(String crud) {
    	if (this.crud.equals("*")) return;
    	if (crud.equals("*")) {
    		this.crud = crud;
    		return;
    	}
    	if (crud.toUpperCase().contains("C")) this.crud += "C"; 
    	if (crud.toUpperCase().contains("R")) this.crud += "R";
    	if (crud.toUpperCase().contains("U")) this.crud += "U";
    	if (crud.toUpperCase().contains("D")) this.crud += "D";
    	formatCrud();
    }
    
	public JsonPermission toJSon() {
		JsonPermission j = super.toJson(new JsonPermission());
		j.crud = crud;
		return j;
	}

	public JsonUrlPermission toJsonUrlPermission() {
		JsonUrlPermission j = new JsonUrlPermission();
		j.url = code; 
		j.crud = crud;
		return j;
	}
	
	public String getCrud() {
		return crud;
	}
	public EntPermission setCrud(String crud) {
		this.crud = crud;
		return this;
	}
	
	/**
	 * Permission URL is stored in the <code>code</code> field
	 * @return
	 */
	public String getUrl() {
		return code;
	}
	
	
}
