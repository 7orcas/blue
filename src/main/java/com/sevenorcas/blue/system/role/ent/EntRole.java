package com.sevenorcas.blue.system.role.ent;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.json.JsonRole;

/**
 * Role entity 
 * Roles determine a users's authorisation to access REST methods
 * 
 * Created Sept '22
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="role", schema="cntrl")
public class EntRole extends BaseEnt<EntRole>{
	
	private static final long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="role_id_seq", sequenceName="cntrl.role_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="role_id_seq")
	private Long id;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="entRole")
	private List <EntRolePermission> permissions = new ArrayList<>(); 

	/**
	 * Override field configurations
	 */
	static public EntityConfig getConfig (EntOrg org) {
		return BaseEnt.getConfig(org)
				.put(new FieldConfig("orgNr").min(0))
				;
	}
	
	public EntRole () {
	}

	public JsonRole toJSon() {
		JsonRole j = super.toJSon(new JsonRole());
		if (permissions != null) {
			j.permissions = new ArrayList<>();
			for (EntRolePermission p : permissions) {
				j.permissions.add(p.toJSon());
			}
		}		
		return j;
	}
		
	public Long getId() {
		return id;
	}
	public EntRole setId(Long id) {
		this.id = id;
		return this;
	}
		
    public List<EntRolePermission> getPermissions() {
		return permissions;
	}
    public EntRole setPermissions(List<EntRolePermission> permissions) {
		this.permissions = permissions;
		return this;
	}
    public EntRole add(EntRolePermission p) {
		if (permissions == null) {
			permissions = new ArrayList<>();
		}
		permissions.add(p);
		return this;
	}	
    
}
