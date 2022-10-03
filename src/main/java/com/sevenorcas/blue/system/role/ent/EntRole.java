package com.sevenorcas.blue.system.role.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.field.validationDEL.Validation;

/**
 * Role entity 
 * Roles determine a users's authorisation to access methods
 * 
 * Created Sept '22
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="role", schema="cntrl")
@SequenceGenerator(name="role_id_seq", sequenceName="role_id_seq", allocationSize=1)
public class EntRole extends BaseEnt<EntRole>{
	private static final long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="role_id_seq", sequenceName="role_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="role_id_seq")
	private Long id;
	
	public EntRole () {
	}

    public Long getId() {
		return id;
	}
	public EntRole setId(Long id) {
		this.id = id;
		return this;
	}

	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
}
