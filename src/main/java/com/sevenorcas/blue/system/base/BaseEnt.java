package com.sevenorcas.blue.system.base;

import java.sql.Date;

/**
* Create July 2022
* 
* Base Entity Object for actual entities to extend
* 
* [Licence]
* @author John Stewart
*/

public class BaseEnt {
	private Long _id;
	private Integer _org;
    private String _code;
    private Date _created;
    private String _encoded;
    private Integer _encoded_flag;
    
    public BaseEnt () {
    	
    }
    
    
	public Long getId() {
		return _id;
	}
	public void setId(Long _id) {
		this._id = _id;
	}
	public Integer getOrg() {
		return _org;
	}
	public void setOrg(Integer _org) {
		this._org = _org;
	}
	public String getCode() {
		return _code;
	}
	public void setCode(String _code) {
		this._code = _code;
	}
	public Date getCreated() {
		return _created;
	}
	public void setCreated(Date _created) {
		this._created = _created;
	}
	public String getEncoded() {
		return _encoded;
	}
	public void setEncoded(String _encoded) {
		this._encoded = _encoded;
	}
	public Integer getEncodedFlag() {
		return _encoded_flag;
	}
	public void setEncodedFlag(Integer _encoded_flag) {
		this._encoded_flag = _encoded_flag;
	}
    
}
