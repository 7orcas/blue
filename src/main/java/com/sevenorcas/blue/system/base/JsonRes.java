package com.sevenorcas.blue.system.base;

public class JsonRes {
	public Boolean valid = true;
	public Object data;
	public Object message;
	
	public JsonRes setData(Object data) {
		this.data = data;
		return this;
	}
	public JsonRes setError(String message) {
		valid = false;
		this.message = message;
		return this;
	}
}
