package com.sevenorcas.blue.system.base;

public class JsonRes {
	public Boolean valid = true;
	public Object data;
	public Integer returnCode;
	public String error = "";
	public String errorDetail = "";
	
	public JsonRes setData(Object data) {
		this.data = data;
		return this;
	}
	public JsonRes setError(String err) {
		return setError(err, null);
	}
	public JsonRes setError(String err, String detail) {
		valid = false;
		this.error = err;
		this.errorDetail = detail;
		return this;
	}
	public JsonRes setReturnCode (Integer code) {
		this.returnCode = code;
		return this;
	}
}
