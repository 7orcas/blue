package com.sevenorcas.blue.system.conf;

import java.lang.reflect.Method;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * Configuration Module service bean.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */
@Stateless
public class SConfig extends BaseService implements SConfigI {

//	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB private TValidationI dao;
	
	
	/**
	 * Return an entity's configuration
	 * 
	 * @param callObj
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public JsonRes getConfigJson(
			CallObject callObj,
			String entity) throws Exception {
		EntityConfig e = getConfig(callObj, entity);
		JsonRes j = e.toJSon();
		return j;
    }
   
	/**
	 * Return an entity's configuration
	 * 
	 * @param callObj
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public EntityConfig getConfig(
			CallObject callObj,
			String entity) throws Exception {
		
		Class<?> clazz = null;
		try {
			//Derive entity's class
			clazz = Class.forName(CLASS_PATH_PREFIX + entity);
			
		} catch (Exception e) {
			//Full path
			clazz = Class.forName(entity);
		}
		return getConfig(callObj, clazz);
    }

	/**
	 * Return an entity's configuration
	 * 
	 * @param callObj
	 * @param class
	 * @return
	 * @throws Exception
	 */
	public EntityConfig getConfig(
			CallObject callObj,
			Class<?> clazz) throws Exception {
		
		Method m = clazz.getMethod("getConfig", EntOrg.class);
		EntityConfig c = (EntityConfig)m.invoke(null, callObj.getOrg());
		c.tableName = tableName(clazz, "");
		return c;
    }
	
}
