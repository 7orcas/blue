package com.sevenorcas.blue.system.base;

/**
* Created 22 July 2022
* 
* Base object for Data Transfer Objects to extend
* 
* [Licence]
* @author John Stewart
*/

public class BaseDto <T> implements IdCodeI {
	private Long id;
	private Integer orgNr;
    private String code;
    private String encoded;
    private Boolean active;
	
 
    /**
     * Set standard fields in JSon object
     * @param j
     */
    protected void toJSon(BaseJsonRes j) {
		j.id = id;
		j.code = code;
		j.org = orgNr;
	}
    
    public Long getId() {
		return id;
	}
	@SuppressWarnings({"unchecked"})
	public T setId(Long _id) {
		this.id = _id;
		return (T)this;
	}
	public Integer getOrgNr() {
		return orgNr;
	}
	@SuppressWarnings("unchecked")
	public T setOrgNr(Integer _org) {
		this.orgNr = _org;
		return (T)this;
	}
	public String getCode() {
		return code;
	}
	@SuppressWarnings("unchecked")
	public T setCode(String _code) {
		this.code = _code;
		return (T)this;
	}
	public String getEncoded() {
		return encoded;
	}
	@SuppressWarnings("unchecked")
	public T setEncoded(String _encoded) {
		this.encoded = _encoded;
		return (T)this;
	}
	public Boolean isActive() {
		return active;
	}
	public T setActive() {
		return setActive(true);
	}
	@SuppressWarnings("unchecked")
	public T setActive(Boolean _active) {
		this.active = _active;
		return (T)this;
	}
    
    
}
