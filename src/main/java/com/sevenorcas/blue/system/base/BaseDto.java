package com.sevenorcas.blue.system.base;

/**
* Created 22 July 2022
* 
* Base object for Data Transfer Objects to extend
* 
* [Licence]
* @author John Stewart
*/

public class BaseDto <T> {
	private Long id;
	private Integer org;
    private String code;
    private String encoded;
    private Boolean active;
	
 
    /**
     * Set standard fields in JSon object
     * @param j
     */
    protected void toJSon(BaseJsonRes j) {
		j.i = id;
		j.c = code;
		j.o = org;
	}
    
    public Long getId() {
		return id;
	}
	public T setId(Long _id) {
		this.id = _id;
		return (T)this;
	}
	public Integer getOrg() {
		return org;
	}
	public T setOrg(Integer _org) {
		this.org = _org;
		return (T)this;
	}
	public String getCode() {
		return code;
	}
	public T setCode(String _code) {
		this.code = _code;
		return (T)this;
	}
	public String getEncoded() {
		return encoded;
	}
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
	public T setActive(Boolean _active) {
		this.active = _active;
		return (T)this;
	}
    
    
}
