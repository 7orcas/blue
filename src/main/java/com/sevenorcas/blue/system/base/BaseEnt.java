package com.sevenorcas.blue.system.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.conf.EntityConfig;
import com.sevenorcas.blue.system.conf.FieldConfig;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
* Base Entity Object for actual entities to extend
* 
* Create July 2022
* [Licence]
* @author John Stewart
*/

@SuppressWarnings("serial")
@MappedSuperclass
abstract public class BaseEnt <T> implements Serializable {
	
	@Transient
	private Class<T> clazz;
	
	@Transient
    private Long tempId;
	
	@Column(name = "org_nr")
	protected Integer orgNr;
	
	protected String code;
	
	protected String descr;
	
	protected Timestamp updated;
    
	protected String encoded;
    @Column(name = "encoded_flag")
    protected Integer encodedFlag;
    protected Boolean active;
    
    @Transient
    private Boolean delete;
    
    @Transient
    private Boolean valid;
    
    /**
     * Entity / Field configurations.
     * Implementing class can override this method
     */
    static public EntityConfig getConfig (EntOrg org) {
    	return new EntityConfig()
    	    .put(new FieldConfig("orgNr").nonNull().min(1)) 
    	    .put(new FieldConfig("code").nonNull().max(20).uniqueOrg())
	    	.put(new FieldConfig("descr").max(30))
    	    .put(new FieldConfig("active").nonNull());
    }
    
	
	/**
     * Set standard fields in JSon object
     * @param j
     */
    protected <J extends BaseJsonRes> J toJSon(J j) {
		j.id = getId();
		j.code = code;
		j.descr = descr;
		j.orgNr = orgNr;
		j.updated = updated;
		j.active = active;
		return j;
	}
   
    @SuppressWarnings("unchecked")
	public BaseEnt () {
    	clazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
    
    public Class<T> getEntClass() {
    	return clazz;
    }
    
    
	abstract public Long getId();
	abstract public T setId(Long id);
	
	public boolean isValidId() {
		return getId() != null & getId() > 0L;
	}
	public boolean isNew() {
		return getId() != null & getId() < 0L;
	}
	
	
	public Long getTempId() {
		return tempId;
	}
	@SuppressWarnings("unchecked")
	public T setTempId(Long tempId) {
		this.tempId = tempId;
		return (T)this;
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
