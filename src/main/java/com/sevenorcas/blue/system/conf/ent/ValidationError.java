package com.sevenorcas.blue.system.conf.ent;

import java.sql.Timestamp;

import com.sevenorcas.blue.system.conf.ConfigurationI;
import com.sevenorcas.blue.system.conf.json.JsonValError;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
 * Error object generated when persisting / merging of entity
 *  
 * [Licence]
 * Created July '22
 * @author John Stewart
 */
public class ValidationError implements ConfigurationI, IntHardCodeLangKey {
	public int type;
	
	public Long entityId;
	public String code;
	public String field;

	public Timestamp updated;
	
	public Long updatedUserId;
	public String updatedUser;
	
	public String error;
	public String action;
	
	public ValidationError(int type) {
		this.type = type;
	}

	public ValidationError setLabels(UtilLabel util) {
		String e, a;
		switch (type) {
			case VAL_ERROR_NO_RECORD:
				e = LK_VAL_ERROR_NO_RECORD;
				a = LK_VAL_ERROR_RELOAD;
				break;

			case VAL_ERROR_TS_DIFF:
				e = LK_VAL_ERROR_TS_DIFF;
				a = LK_VAL_ERROR_RELOAD;
				break;
			
			default:
				e = "?";
				a = LK_UNKNOWN_ERROR;	
		}

		setError(util.getLabel(e));
		setAction(util.getLabel(a));
		
		return this;
	}

	
	public JsonValError toJSon(Long id) {
		JsonValError j = new JsonValError();
		j.id = id;
		j.entityId = entityId;
		j.code = code;
		j.descr = error;
		j.action = action;
		j.updated = updated;
		j.updatedUser = updatedUser;
		return j;
	}
	
	public String toString() {
		return "code='" + code + "'"
			+ " field='" + field + "'"
			+ " error='" + error + "'"
			;
	}
	
	public ValidationError setAction(String action) {
		this.action = action;
		return this;
	}

	public ValidationError setCode(String code) {
		this.code = code;
		return this;
	}
	
	public ValidationError setField(String field) {
		this.field = field;
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

	public ValidationError setError(String error) {
		this.error = error;
		return this;
	}
	
	
	
}
