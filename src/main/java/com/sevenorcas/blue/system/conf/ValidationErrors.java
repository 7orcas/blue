package com.sevenorcas.blue.system.conf;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrors {
	public List<ValidationError> list;
	
	public ValidationErrors() {
		list = new ArrayList<>();
	}
	
	public boolean hasErrors() {
		return list.size()>0;
	}
	
	public ValidationErrors add(Long id, String code, String error) {
		list.add(new ValidationError(id, code, error));
		return this;
	}
}
