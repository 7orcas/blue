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
	private Long _id;
	private Integer _org;
    private String _code;
    private String _encoded;
    private Boolean _active;
	
 
    /**
     * Set standard fields in JSon object
     * @param j
     */
    protected void toJSon(BaseJsonRes j) {
		j._i = _id;
		j._c = _code;
		j._o = _org;
	}
    
    public Long getId() {
		return _id;
	}
	public T setId(Long _id) {
		this._id = _id;
		return (T)this;
	}
	public Integer getOrg() {
		return _org;
	}
	public T setOrg(Integer _org) {
		this._org = _org;
		return (T)this;
	}
	public String getCode() {
		return _code;
	}
	public T setCode(String _code) {
		this._code = _code;
		return (T)this;
	}
	public String getEncoded() {
		return _encoded;
	}
	public T setEncoded(String _encoded) {
		this._encoded = _encoded;
		return (T)this;
	}
	public Boolean isActive() {
		return _active;
	}
	public T setActive() {
		return setActive(true);
	}
	public T setActive(Boolean _active) {
		this._active = _active;
		return (T)this;
	}
    
    
}
