package com.sevenorcas.blue.system.base;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.annotation.Field;
import com.sevenorcas.blue.system.field.validationDEL.Validation;
import com.sevenorcas.blue.system.field.validationDEL.ValidationI;

/**
* Base Entity Object for actual entities to extend
* 
* Create July 2022
* [Licence]
* @author John Stewart
*/

@SuppressWarnings("serial")
@MappedSuperclass
abstract public class BaseEnt <T> implements Serializable, ValidationI {
	
	@Field(unique = true, min = 1)
	@Column(name = "org_nr", nullable = false)
	protected Integer orgNr;
	
	@Field(unique = true)
	@Column(nullable = false, length = 30)
    private String code;
	
	@Column(length = 80)
    private String descr;
	
    private Timestamp updated;
    
    private String encoded;
    @Column(name = "encoded_flag")
    private Integer encodedFlag;
    private Boolean active;
    
    @Transient
    private Boolean delete;
    
    @Transient
    private Boolean valid;
    private Validation validation;
    
//    /**
//     * Override for field configurations.
//     * Implementing class needs to override this method
//     */
//    public void getConfigOverride (Integer orgNr, EntityConfig config) {}
    
    
    /**
     * Implementing class can attach invalid fields
     * @param validation
     */
abstract protected void validate(Validation validation);
    
    /**
 * Is <b>this</b> a valid entity?
 * @return
 */
public boolean isValidEntity () {
	validation = new Validation(getId());
	if (orgNr == null) validation.add("orgNr", NON_NULL_FIELD);
if (code == null) validation.add("code", NON_NULL_FIELD);
	validate(validation);
	return validation.isValid();
}

public Validation getValidation() {
	return validation;
}

	/**
     * Set standard fields in JSon object
     * @param j
     */
    protected <J extends BaseJsonRes> J toJSon(J j) {
		j.id = getId();
		j.code = code;
		j.orgNr = orgNr;
		j.updated = updated;
		j.active = active;
		return j;
	}
   
    public BaseEnt () {
    }
    
	abstract public Long getId();
	abstract public T setId(Long id);
	
	public boolean isValidId() {
		return getId() != null & getId() > 0L;
	}
	public boolean isNew() {
		return getId() != null & getId() < 0L;
	}
	
	public Integer getOrgNr() {
		return orgNr;
	}
	@SuppressWarnings("unchecked")
	public T setOrgNr(Integer orgNr) {
		this.orgNr = orgNr;
		return (T)this;
	}
	
	public String getCode() {
		return code;
	}
	@SuppressWarnings("unchecked")
	public T setCode(String code) {
		this.code = code;
		return (T)this;
	}
	
	public String getDescr() {
		return descr;
	}
	@SuppressWarnings("unchecked")
	public T setDescr(String descr) {
		this.descr = descr;
		return (T)this;
	}
		
	public Timestamp getUpdated() {
		return updated;
	}
	@SuppressWarnings("unchecked")
	public T setUpdated(Timestamp updated) {
		this.updated = updated;
		return (T)this;
	}
	
	public String getEncoded() {
		return encoded;
	}
	@SuppressWarnings("unchecked")
	public T setEncoded(String encoded) {
		this.encoded = encoded;
		return (T)this;
	}
	
	public Integer getEncodedFlag() {
		return encodedFlag;
	}
	@SuppressWarnings("unchecked")
	public T setEncodedFlag(Integer encoded_flag) {
		this.encodedFlag = encoded_flag;
		return (T)this;
	}
	
	public boolean isActive() {
		return active != null && active;
	}
	public T setActive() {
		return setActive(true);
	}
	@SuppressWarnings("unchecked")
	public T setActive(Boolean active) {
		this.active = active;
		return (T)this;
	}
	
	public Boolean isDelete() {
		return delete != null && delete;
	}
	@SuppressWarnings("unchecked")
	public T setDelete(Boolean delete) {
		this.delete = delete;
		return (T)this;
	}
    
	public boolean isValid() {
		return valid == null || valid;
	}
	public T setValid() {
		return setValid(true);
	}
	@SuppressWarnings("unchecked")
	public T setValid(Boolean valid) {
		this.valid = valid;
		return (T)this;
	}
	
}
