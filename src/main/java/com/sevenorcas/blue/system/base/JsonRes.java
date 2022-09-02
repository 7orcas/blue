package com.sevenorcas.blue.system.base;

public class JsonRes {
	public Boolean valid = true;
	public Object data;
	public String message = "";
	public String messageDetail = "";
	
	public JsonRes setData(Object data) {
		this.data = data;
		return this;
	}
	public JsonRes setError(String message) {
		return setError(message, null);
	}
	public JsonRes setError(String message, String detail) {
		valid = false;
		this.message = message;
		this.messageDetail = detail;
		return this;

	}
}
