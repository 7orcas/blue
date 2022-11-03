package com.sevenorcas.blue.system.conf.ent;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;

import com.sevenorcas.blue.system.base.JsonRes;

/**
 * Configuration for a field.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */

public class FieldConfig implements ConfigurationI {
	
	public String field;
	/** For String, Integer, Double fields */
	public Double max = null; 
	/** For String, Integer, Double fields */
	public Double min = null; 
	public Integer nullState = NULLABLE;
	
	/** unique field within org_nr */
	public Boolean uniqueOrgNr = null;

	/** unique field within parent */
	public Boolean uniqueParent = null;

	@JsonbTransient
	public Boolean uniqueIgnoreOrgNr = null;

	@JsonbTransient
	public Boolean unused = false;
	
	@JsonbTransient
	public ValidationCallbackI callback = null;
	
	@JsonbTransient
	public List<ForeignKey> foreignKeys = null;
	
	public FieldConfig(String field) {
		this.field = field;
	}

	public JsonFieldConfig toJSon() throws Exception {
		JsonFieldConfig j = new JsonFieldConfig(); 
		j.field = field;
		j.max = max;
		j.min = min;
		j.nullState = isNonNull();
		j.uniqueParent = isUniqueInParent();
		j.uniqueOrgNr = isUniqueInOrg();
		return j;
	}
	
	public FieldConfig unused() {
		unused = true;
		return this;
	}

	public boolean isUnused() {
		return unused != null && unused;
	}
	
	public FieldConfig nonNull() {
		nullState = NON_NULL;	
		return this;
	}

	public boolean isNonNull() {
		return nullState != null && nullState != NULLABLE;
	}
	
	public boolean isUnique() {
		return isUniqueInOrg()
			|| isUniqueInParent()
			|| isUniqueIgnoreOrgNr();
	}
	
	/**
	 * Sets the field as unique within the org_nr
	 */
	public FieldConfig uniqueInOrg() {
		uniqueOrgNr = true;	
		nullState = NON_NULL;
		return this;
	}
	public boolean isUniqueInOrg() {
		return uniqueOrgNr != null && uniqueOrgNr;
	}

	/**
	 * Sets the field as unique within the parent
	 */
	public FieldConfig uniqueInParent() {
		uniqueParent = true;	
		nullState = NON_NULL;
		return this;
	}
	public boolean isUniqueInParent() {
		return uniqueParent != null;
	}

	/**
	 * Sets the field as unique within the database
	 */
	public FieldConfig uniqueIgnoreOrgNr() {
		uniqueIgnoreOrgNr = true;
		nullState = NON_NULL;
		return this;
	}
	
	public boolean isUniqueIgnoreOrgNr() {
		return uniqueIgnoreOrgNr != null && uniqueIgnoreOrgNr;
	}

	public boolean isMin() {
		return min != null;
	}
	
	public Integer getMinInteger() {
		return min.intValue();
	}
	
	public Double getMin() {
		return min;
	}

	public FieldConfig min(Integer min) {
		this.min = min != null? Double.parseDouble(min.toString()) : -1D;
		return this;
	}

	public FieldConfig min(Double min) {
		this.min = min;
		return this;
	}

	public boolean isMax() {
		return max != null;
	}

	public Integer getMaxInteger() {
		return max.intValue();
	}
	
	public Double getMax() {
		return max;
	}

	public FieldConfig max(Integer max) {
		this.max = max != null? Double.parseDouble(max.toString()) : -1D;
		return this;
	}
	public FieldConfig max(Double max) {
		this.max = max;
		return this;
	}
	
	public FieldConfig callback (ValidationCallbackI callback) {
		this.callback = callback;
		return this;
	}
	public boolean isCallback() {
		return callback != null;
	}
	public ValidationCallbackI getCallback() {
		return callback;
	}
	
	public FieldConfig add(ForeignKey fk) {
		if (foreignKeys == null) {
			foreignKeys = new ArrayList<>();
		}
		fk.field = this;
		foreignKeys.add(fk);
		return this;
	}
	public boolean isForeignKey() {
		return foreignKeys != null;
	}
	public List<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}	
}
