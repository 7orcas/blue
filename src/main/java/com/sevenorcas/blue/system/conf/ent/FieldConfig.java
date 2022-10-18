package com.sevenorcas.blue.system.conf.ent;

import javax.json.bind.annotation.JsonbTransient;

import com.sevenorcas.blue.system.conf.ConfigurationI;

/**
 * Configuration for a field.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */

public class FieldConfig implements ConfigurationI {
	
	public String name;
	/** For String, Integer, Double fields */
	public Double max = null; 
	/** For String, Integer, Double fields */
	public Double min = null; 
	public Integer nullState = NULLABLE;
	public Integer unique = NON_UNIQUE;

	@JsonbTransient
	public Boolean unused = false;
	
	public FieldConfig(String name) {
		this.name = name;
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
	
	public FieldConfig uniqueOrg() {
		unique = ORG_UNIQUE;	
		return this;
	}

	public boolean isUnique() {
		return unique != null && unique != NON_UNIQUE;
	}
	
	public boolean isUniqueOrg() {
		return unique != null && unique == ORG_UNIQUE;
	}
	
	public FieldConfig uniqueDB() {
		unique = DB_UNIQUE;	
		return this;
	}

	public boolean isUniqueDB() {
		return unique != null && unique == DB_UNIQUE;
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
}
