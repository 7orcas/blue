package com.sevenorcas.blue.system.conf;

import java.lang.reflect.Method;

import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
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
public class SrvConfig extends BaseSrv {

//	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
//	final static private String STRING_CLASS = "java.lang.String"; 
	
	
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
		return e.toJSon();
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
		return (EntityConfig)m.invoke(null, callObj.getOrg());
    }
	
	
//ToDo DELETE	
//	/**
//	 * Return an classes configuration - recursive
//	 * Note @Field annotation overwrites @Column 
//	 * 
//	 * @param organisation number
//	 * @param class
//	 * @param configuration object
//	 * @return
//	 * @throws Exception
//	 */
//	private void getConfig(
//			EntOrg org,
//			Class<?> clazz,
//			EntityConfig config) throws Exception {
//		
//		
//		
//		if (clazz.getSuperclass() != null) {
//			getConfig(org, clazz.getSuperclass(), config);
//		}
//		
//		//Get configurations as per annotated fields 
//		for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
//			FieldConfig fc = null;
//
//			for (Annotation anno : f.getDeclaredAnnotations()) {
//				if (anno instanceof javax.persistence.Column) {
//					javax.persistence.Column a = (javax.persistence.Column)anno;
//					fc = fieldConfig(fc, f);
//					fc.nonNull = !a.nullable();
//					
//					if (isSameNonNull(f.getType().getCanonicalName(), STRING_CLASS)
//							&& (fc.max == null || fc.max == -1D)) {
//						fc.max = Double.parseDouble("" + a.length());	
//					}
//					
//				}
//				if (anno instanceof com.sevenorcas.blue.system.annotation.Field_DEL) {
//					com.sevenorcas.blue.system.annotation.Field_DEL a = (com.sevenorcas.blue.system.annotation.Field_DEL)anno;
//					fc = fieldConfig(fc, f);
//					fc.min = Double.parseDouble("" + a.min());
//					fc.max = Double.parseDouble("" + a.max());
//				}
//				
//			}
//
//			if (fc != null) {
//				config.fields.add(fc);
//			}
//		}
//
//		
//		//Get configurations as per the <code>getConfigOverride</code> method
//		for (Method m : clazz.getDeclaredMethods()) {
//			for (Annotation anno : m.getDeclaredAnnotations()) {
//				if (anno instanceof FieldOverride_DEL) {
//					try {
//						m.invoke(null, org, config);
//					} catch (Exception e) {
//						log.error(e);
//					}
//					
//				}
//			}			
//		}
//		
//
//    }
//	
//	private FieldConfig fieldConfig (FieldConfig f, java.lang.reflect.Field field) {
//		if (f == null) {
//			f = new FieldConfig();
//			f.name = field.getName();
//		}
//		return f;
//	}
	
	
}
