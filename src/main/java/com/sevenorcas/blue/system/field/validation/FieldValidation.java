package com.sevenorcas.blue.system.field.validation;

/**
* Field Validation Object
* Stores errors for the field 
* 
* Create 21.09.2022
* [Licence]
* @author John Stewart
*/

public class FieldValidation {

	private String fieldName;
	private Integer errorNr;

	public FieldValidation(String fieldName, Integer errorNr) {
		this.fieldName = fieldName;
		this.errorNr = errorNr;
	}

	public String getFieldName() {
		return fieldName;
	}
	public Integer getErrorNr() {
		return errorNr;
	}
	
}
