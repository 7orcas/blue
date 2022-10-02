package com.sevenorcas.blue.system.base;

import com.sevenorcas.blue.system.util.JsonResponseI;

public class JsonRes implements JsonResponseI{
	public Object data;
	public Integer returnCode = JS_OK;  
	public String error = "";
	public String errorDetail = "";
	
	public JsonRes setData(Object data) {
		this.data = data;
		return this;
	}
	public JsonRes setError(String err) {
		return setError(err, "");
	}
	public JsonRes setError(String err, String detail) {
		returnCode = JS_ERROR;
		this.error = err;
		this.errorDetail = detail;
		return this;
	}
	
	/**
	 * Code is defined in JsonResponseI
	 * @param code
	 * @return
	 */
	public JsonRes setReturnCode (Integer code) {
		this.returnCode = code;
		return this;
	}
}
