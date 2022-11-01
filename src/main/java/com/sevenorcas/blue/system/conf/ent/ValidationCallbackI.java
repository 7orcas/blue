package com.sevenorcas.blue.system.conf.ent;

import com.sevenorcas.blue.system.base.BaseEntity;

/**
 * Validation callback for a field.
 * 
 * Created 01.11.2022
 * [Licence]
 * @author John Stewart
 */
public interface ValidationCallbackI {
	public <T extends BaseEntity<T>> ValidationError validate(T ent) throws Exception;
}
