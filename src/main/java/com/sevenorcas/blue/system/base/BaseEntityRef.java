package com.sevenorcas.blue.system.base;

import javax.persistence.MappedSuperclass;

import com.sevenorcas.blue.app.ref.ent.JsonCountry;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * Base Reference entity 
 * 
 * Created 02.12.22 
 * [Licence]
 * @author John Stewart
 */

@MappedSuperclass
abstract public class BaseEntityRef <T> extends BaseEntity<BaseEntityRef<T>> {
	static final private long serialVersionUID = 1L;
	
	private Integer sort;
	private Boolean dvalue;
	
	public BaseEntityRef() {}
	
	protected void init(BaseEntityRef<?> ent) {
		ent.orgNr = orgNr;
		ent.code = code;
		ent.descr = descr;
		ent.updated = updated;
		ent.updatedUserId = updatedUserId;
		ent.encoded = encoded;
		ent.encodedFlag = encodedFlag;
		ent.active = active;
		ent.sort = sort;
		ent.dvalue = dvalue;
	}
	
	abstract public <J extends BaseJsonRef> J toJson(EntOrg org, boolean fullEntity) throws Exception;
	
	/**
     * Set standard fields in JSon object
     * @param j
     */
    protected <J extends BaseJsonRef> J toJson(J j) {
    	((BaseJsonRes)j).initialise(this);
    	j.initialise(this);
		return j;
	}
	
	public Integer getSort() {
		return sort;
	}
	@SuppressWarnings("unchecked")
	public T setSort(Integer sort) {
		this.sort = sort;
		return (T)this;
	}
	
	public boolean isDvalue() {
		return dvalue != null && dvalue;
	}
	@SuppressWarnings("unchecked")
	public T setDvalue(Boolean dvalue) {
		this.dvalue = dvalue;
		return (T)this;
	}
	public Boolean getDvalue() {
		return dvalue;
	}
	
}
