package com.sevenorcas.blue.system.conf;

import java.sql.Timestamp;

/**
 * Error object generated when persisting / merging of entity
 *  
 * [Licence]
 * Created July '22
 * @author John Stewart
 */
public class ValidationError {
	public Long entityId;
	public String code;

	public Timestamp updated;
	
	public Long updatedUserId;
	public String updatedUser;
	
	public String errorLangKey;
	public String actionLangKey;
	
	public ValidationError() {
	}

	public ValidationError setActionLangKey(String actionLangKey) {
		this.actionLangKey = actionLangKey;
		return this;
	}

	public ValidationError setCode(String code) {
		this.code = code;
		return this;
	}

	public ValidationError setEntityId(Long entityId) {
		this.entityId = entityId;
		return this;
	}
	
	public ValidationError setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
		return this;
	}

	public ValidationError setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
		return this;
	}

	public ValidationError setUpdated(Timestamp updated) {
		this.updated = updated;
		return this;
	}

	public ValidationError setErrorLangKey(String errorLangKey) {
		this.errorLangKey = errorLangKey;
		return this;
	}
	
	
	
}
