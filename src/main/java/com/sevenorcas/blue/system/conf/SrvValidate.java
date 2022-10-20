package com.sevenorcas.blue.system.conf;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseEnt;
import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;

/**
 * Entity Validation Module service bean.
 * 
 * Created 19.10.2022
 * [Licence]
 * @author John Stewart
 */

@Stateless
public class SrvValidate extends BaseSrv {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB
	private DaoValidation dao;
	
	
	/**
     * Validate the entities according to their configuration
     * @param List of entities
     * @throws Exception
     */
    public <T extends BaseEnt<T>> ValidationErrors validate(List<T> list, EntityConfig config) throws Exception {
    	ValidationErrors errors = new ValidationErrors();
    	validate(list, null, config, errors);
    	return errors;
    }
	
	/**
     * Validate the entities (who have parents) according to their configuration
     * @param List of entities
     * @param Entity Configuration object
     * @param Parent id 
     * @param Validation error object
     * @throws Exception
     */
    public <T extends BaseEnt<T>> void validate(List<T> list, Long parentId, EntityConfig config, ValidationErrors errors) throws Exception {

    	Hashtable<String, List<FieldX>> uniqueFields = new Hashtable<>();
    	
    	//Test timestamp conflicts
    	for (T ent : list) {
    		dao.compareTimeStamp(ent, config, errors);
    	}
    	
    	//Test fields
    	for (T ent : list) {
	    	if (ent.isDelete()) {
				continue;
			}
	    	validateFields(ent, config, ent.entClass(), errors, uniqueFields);
    	}
    	
    	//Test uniqueness
    	Enumeration<String> fields = uniqueFields.keys();
    	while (fields.hasMoreElements()) {
    		String field = fields.nextElement();
    		List<FieldX> values = uniqueFields.get(field);
    		
    		FieldConfig fc = values.get(0).fieldConfig;
    		BaseEnt<?> ent = values.get(0).ent;
    		String parentColumn = values.get(0).parentColumn;
    		String columnName = values.get(0).columnName;
    		columnName = columnName != null? columnName : field;
    		
    		List<Object> valuesX = new ArrayList<>();
    		List<Object> valuesU = new ArrayList<>();
    		for (FieldX f : values) {
    			valuesX.add(f.value);
    			if (!valuesU.contains(f.value)) valuesU.add(f.value);
    		}
    		
    		//Test values in list
    		findDuplicates (field, valuesX.toArray(), errors, VAL_ERROR_NON_UNIQUE_NEW);

            //Test database values
    		if (parentColumn == null || !isNewId(parentId)) {
	            List<Object> dbFields = dao.fields(
	            		columnName, 
	            		fc.isUniqueIgnoreOrgNr()? null : ent.getOrgNr(), 
	            		parentColumn, 
	            		parentId,
	            		ent.entClass());
	            for (Object f : dbFields) {
	    			valuesU.add(f);
	    		}            
	            findDuplicates (field, valuesU.toArray(), errors, VAL_ERROR_NON_UNIQUE_DB);            
    		}
    	}
    }
	
	
	/**
     * Validate the entity according to it's configuration
     * @param entity
     * @param entity configuration
     * @param object to load errors into
     * @param list of unique field values to test later
     * @throws Exception
     */
    private <T extends BaseEnt<T>> void validateFields(T ent, 
    		EntityConfig config, 
    		Class<?> clazz, 
    		ValidationErrors errors, 
    		Hashtable<String, List<FieldX>> uniqueFields) throws Exception {
    	
    	try {
    		if (clazz.getName().equals("java.lang.Object")) {
    			return;
    		}
    		
    		if (clazz.getSuperclass() != null) {
    			validateFields(ent, config, clazz.getSuperclass(), errors, uniqueFields);
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

    				//Accumulate uniqueness fields
    				if (fc.isUnique()) {
    					FieldX fieldX = new FieldX();
    					
    					List<FieldX> fields = uniqueFields.get(fc.name);
    					if (fields == null) {
    						fields = new ArrayList<>();
    						uniqueFields.put(fc.name, fields);
    						
    						//Populate first element with constants
    						fieldX.ent = ent;
    						fieldX.fieldConfig = fc;	
    						fieldX.columnName = getAnnotationColumnName(clazz);
    						fieldX.parentColumn = getAnnotationJoinColumnName(clazz);
    					}
    					fieldX.value = value;
    					fields.add(fieldX);
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
	
    /**
     * Find duplicate values and add to errors
     */
    private void findDuplicates (String field, Object[] values, ValidationErrors errors, int errorType) throws Exception {
    
    	Set<String> dupL = new HashSet<String>();
		
    	for (int i=0;i<values.length;i++) {
			for (int j=i+1;j<values.length;j++) {
				if (isSameNonNull(values[i], values[j])) {
					dupL.add(values[i].toString());
				}
			}
		}
		
		Iterator<String> v = dupL.iterator();
        while (v.hasNext()) {
        	errors.add(new ValidationError(errorType)
        			.setCode(v.next())	
        			.setField(field)
        			);
        }
    }
    
    
    /**
     * Store unique values with ids to be tested
     */
    private class FieldX {
    	public BaseEnt<?> ent;
    	public Object value;
    	public FieldConfig fieldConfig;
    	public String columnName;
    	public String parentColumn;
    }
    
    
    private String getAnnotationColumnName(Class<?> clazz) throws Exception {
    	return getAnnotationColumnName(clazz, null);
    }
    
    private String getAnnotationColumnName(
			Class<?> clazz,
			String name) throws Exception {
			
		if (name != null) {
			return name;
		}
    	
		if (clazz.getSuperclass() != null) {
			name = getAnnotationColumnName(clazz.getSuperclass(), name);
		}
		
		//Get configurations as per annotated fields 
		for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
			for (Annotation anno : f.getDeclaredAnnotations()) {
				if (anno instanceof javax.persistence.Column) {
					javax.persistence.Column a = (javax.persistence.Column)anno;
					name = a.name();
				}
			}
		}
		return name;
    }
    
    private String getAnnotationJoinColumnName(Class<?> clazz) throws Exception {
    	return getAnnotationJoinColumnName(clazz, null);
    }
    
    private String getAnnotationJoinColumnName(
			Class<?> clazz,
			String name) throws Exception {
			
		if (name != null) {
			return name;
		}
    	
		if (clazz.getSuperclass() != null) {
			name = getAnnotationColumnName(clazz.getSuperclass(), name);
		}
		
		//Get configurations as per annotated fields 
		for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
			for (Annotation anno : f.getDeclaredAnnotations()) {
				if (anno instanceof javax.persistence.JoinColumn) {
					javax.persistence.JoinColumn a = (javax.persistence.JoinColumn)anno;
					name = a.name();
				}
			}
		}
		return name;
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
