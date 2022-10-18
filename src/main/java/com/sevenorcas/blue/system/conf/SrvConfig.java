package com.sevenorcas.blue.system.conf;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
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
public class SrvConfig extends BaseSrv implements ConfigurationI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
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
	
	/**
     * Validate the entity according to it's configuration
     * @param entity
     * @param entity configuration
     * @param object to load errors into
     * @throws Exception
     */
    public <T extends BaseEnt<T>> void validate(T ent, EntityConfig config, ValidationErrors errors) throws Exception {
    	if (ent.isDelete()) {
			return;
		}
    	validate(ent, config, errors, ent.entClass());
    }
	
	
	/**
     * Validate the entity according to it's configuration
     * @param entity
     * @param entity configuration
     * @param object to load errors into
     * @throws Exception
     */
    private <T extends BaseEnt<T>> void validate(T ent, EntityConfig config, ValidationErrors errors, Class<?> clazz) throws Exception {
    	
    	try {
    		if (clazz.getName().equals("java.lang.Object")) {
    			return;
    		}
    		
    		if (clazz.getSuperclass() != null) {
    			validate(ent, config, errors, clazz.getSuperclass());
    		}
    		
    		for (FieldConfig fc : config.list()) {

    			if (fc.isUnused()) continue;
    			
    			Object value = null;
    			try {
    				Field field = clazz.getDeclaredField(fc.name);
    				field.setAccessible(true);
    				value = field.get(ent);
    			} catch ( NoSuchFieldException ex) {
    			    // field doesn't exist
    				continue;
    			}
    			Integer errorType = null;
    			
    			
    			if (value == null) {
    				if (fc.isNonNull()) errorType = VAL_ERROR_NULL_VALUE;
    				else if (fc.isMin()) errorType = VAL_ERROR_MIN_VALUE;
    				
    			}
    			else {
    				if (fc.isMin()) {
    					
    					if (value instanceof String && value.toString().length() < fc.getMinInteger()) {
    						errorType = VAL_ERROR_MIN_VALUE;    						
    					}
    					if (value instanceof Integer && (Integer)value < fc.getMinInteger()) {
    						errorType = VAL_ERROR_MIN_VALUE;    						
    					}
    					if (value instanceof Double && (Double)value < fc.getMin()) {
    						errorType = VAL_ERROR_MIN_VALUE;    						
    					}
    				}
    				
    				if (fc.isMax()) {
    					
    					if (value instanceof String && value.toString().length() > fc.getMaxInteger()) {
    						errorType = VAL_ERROR_MAX_VALUE;    						
    					}
    					if (value instanceof Integer && (Integer)value > fc.getMaxInteger()) {
    						errorType = VAL_ERROR_MAX_VALUE;    						
    					}
    					if (value instanceof Double && (Double)value > fc.getMax()) {
    						errorType = VAL_ERROR_MAX_VALUE;    						
    					}
    				}
	    		}
    			
    			//Validate uniqueness
    			if (errorType == null && fc.isUnique()) {
    				Integer orgNr = null;
    				
    				if (fc.isUniqueOrg()) {
    					orgNr = ent.getOrgNr();	
    				}
    				
    				
    				
    			}
    			
    			
    			
    			if (errorType != null) {
	    			errors.add(new ValidationError(errorType)
			    			.setEntityId(ent.getId())
			    			.setCode(ent.getCode())	
			    			.setField(fc.name)
			    			);
    			}
    		}
    			    	
    	} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
		
    }
	
    

    
	
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
//	private void getListOfFields(
//			EntOrg org,
//			Class<?> clazz,
//			EntityConfig config) throws Exception {
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
