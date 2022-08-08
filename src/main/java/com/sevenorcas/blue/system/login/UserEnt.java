package com.sevenorcas.blue.system.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.base.BaseEnt;

/**
 * User entity 
 * 
 * TODO document this bean
 * 
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="zzz", schema="cntrl")
public class UserEnt extends BaseEnt {

	private static final long serialVersionUID = 1L;
	
	@Column(name="xxx")
	private String userId;
	@Column(name="yyy")
	private String password;
	private String orgs;
	private Integer attempts;
	
	/**
	 * To be valid, correct password and org and attempts < max
	 */
	@Transient
    private Boolean valid;
	@Transient
    private String inValidMessage;
	
	public UserEnt () {
		
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getOrgs() {
		return orgs;
	}
	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}
	public boolean containsOrg (Integer org) {
		String [] s = orgs != null? orgs.split(",") : new String [] {""};
		for (String o : s) {
			if (o.equals(org.toString())) {
				return true;
			}
		}
		return false;
	}
	
	
	public Integer getAttempts() {
		return attempts;
	}
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	public UserEnt incrementAttempts() {
		attempts++;
		return this;
	}

	public Boolean isValid() {
		return valid != null && valid;
	}
	public UserEnt setValid() {
		this.valid = true;
		return this;
	}

	public String getInvalidMessage() {
		return inValidMessage;
	}
	public UserEnt setInvalidMessage(String inValidMessage) {
		this.inValidMessage = inValidMessage;
		return this;
	}
	
	
	
	
	
}
