package com.sevenorcas.blue.system.base;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
* Create July 2022
* 
* Base Entity Object for actual entities to extend
* 
* [Licence]
* @author John Stewart
*/

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEnt implements Serializable {
	
	@Id  
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Integer org;
    private String code;
    private Date created;
    private String encoded;
    @Column(name="encoded_flag")
    private Integer encodedFlag;
    private Boolean active;
    
    public BaseEnt () {
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOrg() {
		return org;
	}
	public void setOrg(Integer org) {
		this.org = org;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getEncoded() {
		return encoded;
	}
	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}
	public Integer getEncodedFlag() {
		return encodedFlag;
	}
	public void setEncodedFlag(Integer encoded_flag) {
		this.encodedFlag = encoded_flag;
	}
	public boolean isActive() {
		return active != null && active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
    
}
