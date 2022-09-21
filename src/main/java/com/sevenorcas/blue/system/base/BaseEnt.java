package com.sevenorcas.blue.system.base;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.field.validation.Validation;
import com.sevenorcas.blue.system.field.validation.ValidationI;

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
	
	@Id  
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="org")
	private Integer orgNr;
    private String code;
    private Date created;
    private String encoded;
    @Column(name="encoded_flag")
    private Integer encodedFlag;
    private Boolean active;
    
    @Transient
    private Boolean delete;
    
    @Transient
    private Validation validation;
    
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
    	validation = new Validation(id);
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
    protected void toJSon(BaseJsonRes j) {
		j.id = id;
		j.code = code;
		j.orgNr = orgNr;
		j.active = active;
	}
    
    
    public BaseEnt () {
    }
    
	public Long getId() {
		return id;
	}
	@SuppressWarnings("unchecked")
	public T setId(Long id) {
		this.id = id;
		return (T)this;
	}
	public boolean isValidId() {
		return id != null & id > 0L;
	}
	public boolean isNew() {
		return id != null & id < 0L;
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
	
	public Date getCreated() {
		return created;
	}
	@SuppressWarnings("unchecked")
	public T setCreated(Date created) {
		this.created = created;
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
    
}
