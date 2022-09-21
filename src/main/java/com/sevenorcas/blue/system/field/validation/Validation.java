package com.sevenorcas.blue.system.field.validation;

import java.util.ArrayList;
import java.util.List;

/**
* Base Entity Validation Object
* Stores errors for the entity by field and error type 
*  
* Create 21.09.2022
* [Licence]
* @author John Stewart
*/
public class Validation {

	private Long entityId;
	private List<FieldValidation> validationErrors;
	
	public Validation (Long entityId) {
		this.entityId = entityId;
		validationErrors = new ArrayList<>();
	}
	
	
	public Long getEntityId() {
		return entityId;
	}

	public List<FieldValidation> getErrors() {
		return validationErrors;
	}

	public Validation add(String fieldName, Integer error) {
		validationErrors.add(new FieldValidation(fieldName, error));
		return this;
	} 

	public boolean isValid() {
		return validationErrors == null || validationErrors.size() == 0; 
	}
}
