package com.sevenorcas.blue.system.conf;


import javax.ejb.Local;

import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.lifecycle.CallObject;

@Local
public interface SrvConfig {
	public JsonRes getConfigJson(CallObject callObj, String entity) throws Exception;
	public EntityConfig getConfig(CallObject callObj, String entity) throws Exception;
	public EntityConfig getConfig(CallObject callObj, Class<?> clazz) throws Exception;
}

