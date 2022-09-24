package com.sevenorcas.blue.system.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.field.validation.Validation;

/**
 * Role entity 
 * Roles determine a users's authorisation to access methods
 * 
 * TODO document this bean
 * 
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="role", schema="cntrl")
@SequenceGenerator(name="role_id_seq", sequenceName="role_id_seq", allocationSize=1)
public class RoleEnt extends BaseEnt<RoleEnt>{
	private static final long serialVersionUID = 1L;
	
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="role_id_seq")
	private Long id;
	
	public RoleEnt () {
	}

    public Long getId() {
		return id;
	}
	public RoleEnt setId(Long id) {
		this.id = id;
		return this;
	}

	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
}
