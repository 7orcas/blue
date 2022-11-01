package com.sevenorcas.blue.system.conf.ent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.conf.json.JValError;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;
import com.sevenorcas.blue.system.lang.LangLabelI;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
 * Error object generated when persisting / merging of entity
 *  
 * [Licence]
 * Created July '22
 * @author John Stewart
 */
public class ValidationError implements ConfigurationI, IntHardCodeLangKey, LangLabelI {
	public int type;
	
	public Long entityId;
	public String code;
	public String field;
	
	public Timestamp updated;
	
	public Long updatedUserId;
	public String updatedUser;
	
	public String error;
	public List<String> errorLangKeyParams;
	public String action;
	
	public ValidationError(int type) {
		this.type = type;
	}

	/**
	 * Apply langKeys to errors and actions.
	 * - The field name is always encoded as %1 in the language label
	 * - Subsequent codes are encoded as %n in the language label
	 * 
	 * @param util
	 * @return
	 */
	public ValidationError setLabels(UtilLabel util) {
		String e, a, p2 = "";
		switch (type) {
			case VAL_ERROR_NO_RECORD:
				e = LK_VAL_ERROR_NO_RECORD;
				a = LK_VAL_ERROR_RELOAD;
				break;

			case VAL_ERROR_TS_DIFF:
				e = LK_VAL_ERROR_TS_DIFF;
				a = LK_VAL_ERROR_RELOAD;
				break;

			case VAL_ERROR_NULL_VALUE:
				e = LK_VAL_ERROR_FIELD_NULL;
				a = LK_VAL_ENTER_VALUE;
				break;
				
			case VAL_ERROR_MIN_VALUE:
			case VAL_ERROR_MAX_VALUE:
			case VAL_ERROR_INVALID_VALUE:
				e = LK_VAL_ERROR_FIELD_VALUE;
				a = LK_VAL_ENTER_VALUE;
				break;
				
			case VAL_ERROR_CANT_DELETE:
				e = LK_VAL_ERROR_CANT_DELETE;
				if (errorLangKeyParams != null) {
					p2 = errorLangKeyParams.get(0).toUpperCase();
				}
				a = LK_VAL_REMOVE_FK;
				break;		
				
			case VAL_ERROR_NON_UNIQUE_NEW:
			case VAL_ERROR_NON_UNIQUE_DB:
				e = LK_VAL_ERROR_FIELD_UNIQUE;
				a = LK_VAL_ENTER_VALUE;
				break;
				
			default:
				e = "?";
				a = LK_UNKNOWN_ERROR;	
		}

		if (util != null) {
			String x = field != null? field : code;
			x = x != null? x : "";
			e = util.getLabel(e + LABEL_APPEND + x);
			a = util.getLabel(a + LABEL_APPEND + x);
			if (p2 != null) {
				e = e.replace("%2", p2);
				a = a.replace("%2", p2);
			}
		}
		
		setError(e);
		setAction(a);
		
		return this;
	}

	
	public JValError toJSon(Long id) {
		JValError j = new JValError();
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
		return "type=" + type 
			+ " code='" + code + "'"
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
	
	public ValidationError addErrorLangKeyParams(String param) {
		if (errorLangKeyParams == null) {
			errorLangKeyParams = new ArrayList<>();
		}
		errorLangKeyParams.add(param);
		return this;
	} 
	
	
	
}
