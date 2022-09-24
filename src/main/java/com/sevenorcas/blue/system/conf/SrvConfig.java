package com.sevenorcas.blue.system.conf;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
 * Configuration Module service bean.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */

@Stateless
public class SrvConfig extends BaseSrv {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	final static private String STRING_CLASS = "java.lang.String"; 
	
	
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
		return new JsonRes().setData(e);
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
		
		//Derive entity's class
		Class<?> clazz = Class.forName(CLASS_PATH_PREFIX + entity);
		EntityConfig conf = new EntityConfig();
		getConfig(clazz, conf);
		return conf;
    }
	
	/**
	 * Return an classes configuration - recursive
	 * Note @Field annotation overwrites @Column 
	 * 
	 * @param class
	 * @return
	 * @throws Exception
	 */
	private void getConfig(
			Class<?> clazz,
			EntityConfig config) throws Exception {
		
		if (clazz.getSuperclass() != null) {
			getConfig(clazz.getSuperclass(), config);
		}
		
		for (Annotation annotation : clazz.getDeclaredAnnotations()) {
		    //Not yet used
		}
		
		for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
			FieldConfig f = null;

			for (Annotation anno : field.getDeclaredAnnotations()) {
				if (anno instanceof javax.persistence.Column) {
					javax.persistence.Column a = (javax.persistence.Column)anno;
					f = fieldConfig(f, field);
					f.nonNull = !a.nullable();
					
					if (isSameNonNull(field.getType().getCanonicalName(), STRING_CLASS)
							&& (f.max == null || f.max == -1D)) {
						f.max = Double.parseDouble("" + a.length());	
					}
					
				}
				if (anno instanceof com.sevenorcas.blue.system.annotation.Field) {
					com.sevenorcas.blue.system.annotation.Field a = (com.sevenorcas.blue.system.annotation.Field)anno;
					f = fieldConfig(f, field);
					f.min = Double.parseDouble("" + a.min());
					f.max = Double.parseDouble("" + a.max());
				}
				
			}

			if (f != null) {
				config.fields.add(f);
			}
		}
		
    }
	
	private FieldConfig fieldConfig (FieldConfig f, java.lang.reflect.Field field) {
		if (f == null) {
			f = new FieldConfig();
			f.name = field.getName();
		}
		return f;
	}
	
	
}
