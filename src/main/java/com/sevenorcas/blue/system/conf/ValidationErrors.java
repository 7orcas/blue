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
	
	public ValidationErrors add(ValidationError error) {
		list.add(error);
		return this;
	}
}
