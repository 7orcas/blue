package com.sevenorcas.blue.system.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.field.validationDEL.Validation;

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
@SequenceGenerator(name="zzz-role_id_seq", sequenceName="zzz-role_id_seq", allocationSize=1)
public class EntUserRole extends BaseEnt<EntUserRole>{
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="zzz-role_id_seq")
	private Long id;

	@Column(name="id_zzz")
	private Long idUser;
	@Column(name="id_role")
	private Long idRole;
	
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
	
	
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public Long getIdRole() {
		return idRole;
	}
	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}
	
	
	
}
