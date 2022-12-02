package com.sevenorcas.blue.system.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.json.JSONPropertyIgnore;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.conf.ent.ConfigurationI;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.field.Encode;
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
abstract public class BaseEntity <T> implements Serializable, ApplicationI, ConfigurationI, IdCodeI {
	
	@Transient
    private Long tempId;
	
	@Column(name = "org_nr")
	protected Integer orgNr;
	
	protected String code;
	
	protected String descr;
	
	protected Timestamp updated;
	
	@Column(name = "updated_userid")
	protected Long updatedUserId;
    
	protected String encoded;
    @Column(name = "encoded_flag")
    protected Integer encodedFlag;
    protected Boolean active;
    
    @Transient private Boolean delete;
    @Transient private Boolean valid;
    @Transient protected boolean decoded = false;
    @Transient private Encode encodeObject;
    
    /**
     * Entity / Field configurations.
     * Implementing class can override this method
     */
    static public EntityConfig getConfig (EntOrg org) throws Exception {
    	return new EntityConfig()
    	    .put(new FieldConfig("orgNr").min(1)) 
    	    .put(new FieldConfig("code").max(20).uniqueInOrg())
	    	.put(new FieldConfig("descr").max(30))
    	    .put(new FieldConfig("active").nonNull());
    }
    
    /**
	 * Return the entities database table name
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	static public String tableName (Class<?> clazz) throws Exception {
		return BaseUtil.tableName(clazz, "");
	}
    
    
	/**
     * Set standard fields in JSon object
     * @param j
     */
    protected <J extends BaseJsonRes> J toJson(J j) {
    	j.initialise(this);
		return j;
	}
   
	public BaseEntity () {
    }
    
    @JSONPropertyIgnore
    @SuppressWarnings("unchecked")
    public Class<T> entClass() {
    	return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
    
    /**
	 * The <code>Encode</code> object contains fields that have been encoded in the table
	 */
	public Encode encoder() throws Exception {
		decoded = true;
		encodeObject = new Encode(this).decode(encoded);
		return encodeObject;
	}
    
//DELETE	
//	/**
//	 * The <code>Encode</code> object contains fields that have been encoded in the table
//	 */
//	public void encode() throws Exception {
//		if (encodeObject == null) {
//			throw new RedException(IntHardCodeLangKey.LK_UNKNOWN_ERROR, "Entity has not been decoded");
//		}
//		encoded = encodeObject.encode();
//	}
	
    
	abstract public Long getId();
	abstract public T setId(Long id);
	
	public boolean isValidId() {
		return getId() != null & getId() > 0L;
	}
	public boolean isNew() {
		return getId() != null & getId() < 0L;
	}
	
	/**
	 * Convience to return a boolean primitive
	 * @param value
	 * @return
	 */
	protected boolean is(Boolean value) {
		return value != null && value;
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
	
	public Long getUpdatedUserId() {
		return updatedUserId;
	}
	@SuppressWarnings("unchecked")
	public T setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
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
	
//DELETE	
//	public Integer getEncodedFlag() {
//		return encodedFlag;
//	}
//	@SuppressWarnings("unchecked")
//	public T setEncodedFlag(Integer encoded_flag) {
//		this.encodedFlag = encoded_flag;
//		return (T)this;
//	}
	
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
	public T setDelete() {
		return setDelete(true);
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
