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
	public Double max = -1D;
	public Double min = -1D;
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

	public FieldConfig uniqueDB() {
		unique = DB_UNIQUE;	
		return this;
	}

	
	public FieldConfig min(Integer min) {
		this.min = min != null? Double.parseDouble(min.toString()) : -1D;
		return this;
	}

	public FieldConfig min(Double min) {
		this.min = min != null? min : -1D;
		return this;
	}

	public FieldConfig max(Integer max) {
		this.max = max != null? Double.parseDouble(max.toString()) : -1D;
		return this;
	}


	public FieldConfig max(Double max) {
		this.max = max != null? max : -1D;
		return this;
	}
}
