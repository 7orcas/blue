package com.sevenorcas.blue.system.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Base Reference entity 
 * 
 * Created 02.12.22 
 * [Licence]
 * @author John Stewart
 */

@MappedSuperclass
@Table(name="reference")
public class BaseEntityRef <T> extends BaseEntity<BaseEntityRef<T>> {
	static final private long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="reference_id_seq", sequenceName="reference_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="reference_id_seq")
	private Long id;	

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
	
	public Long getId() {
		return id;
	}
	@SuppressWarnings({ "rawtypes" })
	public BaseEntityRef setId(Long id) {
		this.id = id;
		return this;
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
