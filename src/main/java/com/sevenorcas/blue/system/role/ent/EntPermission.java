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
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.json.JsonPermission;

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
	static public EntityConfig getConfig (EntOrg org) {
		return BaseEntity.getConfig(org)
			.put(new FieldConfig("org_nr").min(0))
			.put(new FieldConfig("code").uniqueIgnoreOrgNr())
    	    .put(new FieldConfig("crud").nonNull().max(4));
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
    
	public JsonPermission toJSon() {
		JsonPermission j = super.toJSon(new JsonPermission());
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
	
}
