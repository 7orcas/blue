package com.sevenorcas.blue.system.base;

import javax.persistence.MappedSuperclass;

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
	@SuppressWarnings({ "rawtypes" })
	public BaseEntityRef setSort(Integer sort) {
		this.sort = sort;
		return this;
	}
	
	public boolean isDvalue() {
		return dvalue != null && dvalue;
	}
	@SuppressWarnings({ "rawtypes" })
	public BaseEntityRef setDvalue(Boolean dvalue) {
		this.dvalue = dvalue;
		return this;
	}
	public Boolean getDvalue() {
		return dvalue;
	}
	
}
