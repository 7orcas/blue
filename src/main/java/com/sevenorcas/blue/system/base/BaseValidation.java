package com.sevenorcas.blue.system.base;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.field.FieldValidation;

/**
* Base Entity Validation Object
* Stores errors for the entity by field and error type 
*  
* Create 21.09.2022
* [Licence]
* @author John Stewart
*/

public class BaseValidation <T> {

	private List<FieldValidation> errors;
	
	public BaseValidation () {
		errors = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public T add(String fieldName, Integer error) {
		errors.add(new FieldValidation(fieldName, error));
		return (T)this;
	} 
	
}
