package com.sevenorcas.blue.system.conf.ent;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.json.JsonValErrors;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.util.JsonResponseI;

public class ValidationErrors implements JsonResponseI {
	private List<ValidationError> errors;
	
	public ValidationErrors() {
		errors = new ArrayList<>();
	}
	
	public boolean hasErrors() {
		return errors.size()>0;
	}
	
	public ValidationErrors add(ValidationError error) {
		errors.add(error);
		return this;
	}
	
	public ValidationErrors setLabels(UtilLabel util) {
		for (ValidationError e : errors) {
			e.setLabels(util);
		}	
		return this;
	}
	
	public JsonRes toJSon() {
		JsonValErrors j = new JsonValErrors();
		j.errors = new ArrayList<>();
		for (int i=0;i<errors.size();i++) {
			ValidationError e = errors.get(i);
			j.errors.add(e.toJSon(Long.parseLong(""+i)));
		}

		return new JsonRes()
			.setData(j)
			.setReturnCode(JS_VALIDATION_ERRORS);
	}

	public final List<ValidationError> getErrors() {
		return errors;
	}
	
}
