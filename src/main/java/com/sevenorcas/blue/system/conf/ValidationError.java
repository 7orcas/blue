package com.sevenorcas.blue.system.conf;

public class ValidationError {
	public Long id;
	public String code;
	public String error;
	
	public ValidationError(Long id, String code, String error) {
		this.id = id;
		this.code = code;
		this.error = error;
	}
	
}
