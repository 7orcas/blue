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
    private String lang;
    private String code;
    private String encoded;
    private Boolean active;
    
    //Used in excel import
    private Boolean changed;
    private Boolean valid;
    private String importComment;
	
 
    /**
     * Set standard fields in JSon object
     * @param j
     */
    protected void toJSon(BaseJsonRes j) {
		j.id = id;
		j.org = orgNr;
		j.code = code;
		j.active = active;
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
	public String getLang() {
		return lang;
	}
	@SuppressWarnings("unchecked")
	public T setLang(String lang) {
		this.lang = lang;
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
    
	public String getImportComment() {
		return importComment;
	}
	@SuppressWarnings("unchecked")
	public T setImportComment(String comment) {
		this.importComment = comment;
		return (T)this;
	}
	@SuppressWarnings("unchecked")
	public T addImportComment(String comment) {
		if (comment == null) {
			return (T)this;
		}
		this.importComment = this.importComment == null? "" : this.importComment;
		if (this.importComment.length()>0) {
			this.importComment += ";";
		}
		this.importComment += comment;
		return (T)this;
	}
	public boolean isChanged() {
		return changed != null && changed;
	}
	@SuppressWarnings("unchecked")
	public T setChanged() {
		this.changed = true;
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
