package com.sevenorcas.blue.system.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.field.validation.Validation;

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
@SequenceGenerator(name="zzz_id_seq", sequenceName="zzz_id_seq", allocationSize=1)
public class UserEnt extends BaseEnt<UserEnt> {

	private static final long serialVersionUID = 1L;
	
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="zzz_id_seq")
	private Long id;
	
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
    private Boolean validUser;
	@Transient
    private String inValidMessage;
	@Transient
    private Integer org;
	
	public UserEnt () {
		
	}
	
	/**
     * Is <b>this</b> a valid entity?
     * @return
     */
    protected void validate (Validation validation) { }
	
    public Long getId() {
		return id;
	}
	public UserEnt setId(Long id) {
		this.id = id;
		return this;
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
	public boolean containsOrg(Integer org) {
		String [] s = orgs != null? orgs.split(",") : new String [] {""};
		for (String o : s) {
			if (org != null && o.equals(org.toString())) {
				this.org = org;
				return true;
			}
		}
		return false;
	}
	public void setDefaultOrg() {
		try {
			String [] s = orgs != null? orgs.split(",") : new String [] {""};
			org = Integer.parseInt(s[0]);
		} catch (Exception x) {
		}
	}
	public Integer getOrgNr() {
		return org;
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

	public Boolean isValidUser() {
		return validUser != null && validUser;
	}
	public UserEnt setValidUser() {
		this.validUser = true;
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
