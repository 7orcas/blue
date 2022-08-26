package com.sevenorcas.blue.system.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;

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
public class RoleEnt extends BaseEnt{
	private static final long serialVersionUID = 1L;
	
	public RoleEnt () {
	}
}