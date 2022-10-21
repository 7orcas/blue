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

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.base.BaseService;
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
public class SValidateImp extends BaseService implements SValidate {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB private TValidation dao;
	
	
	/**
     * Validate the entities according to their configuration
     * @param List of entities
     * @throws Exception
     */
    public <T extends BaseEntity<T>> ValidationErrors validate(List<T> list, EntityConfig config) throws Exception {
    	ValidationErrors errors = new ValidationErrors();
    	validate(list, null, null, config, errors);
    	return errors;
    }
	
	/**
     * Validate the entities (who have parents) according to their configuration
     * @param List of entities
     * @param Entity Configuration object
     * @param Parent code
     * @param Parent id 
     * @param Validation error object
     * @throws Exception
     */
    public <T extends BaseEntity<T>> void validate(List<T> list, String parentCode, Long parentId, EntityConfig config, ValidationErrors errors) throws Exception {

    	Hashtable<String, List<FieldX>> uniqueFields = new Hashtable<>();
    	
    	//Test time stamp conflicts
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
    		BaseEntity<?> ent = values.get(0).ent;
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
    		findDuplicates (field, parentCode, valuesX.toArray(), errors, VAL_ERROR_NON_UNIQUE_NEW);

            //Test database values
    		if (parentColumn == null || !isNewId(parentId)) {
	            List<Object> dbFields = dao.fields(
	            		columnName, 
	            		fc.isUniqueIgnoreOrgNr()? null : ent.getOrgNr(), 
	            		parentColumn, 
	            		parentId,
	            		ent.getId(),
	            		ent.entClass());
	            for (Object f : dbFields) {
	    			valuesU.add(f);
	    		}            
	            findDuplicates (field, parentCode, valuesU.toArray(), errors, VAL_ERROR_NON_UNIQUE_DB);            
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
    private <T extends BaseEntity<T>> void validateFields(T ent, 
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
    						fieldX.columnName = getAnnotationColumnName(clazz, fc.name);
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
    private void findDuplicates (String field, String parentCode, Object[] values, ValidationErrors errors, int errorType) throws Exception {
    
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
        	String c = v.next();
        	errors.add(new ValidationError(errorType)
        			.setCode(parentCode != null? parentCode : c)	
        			.setField(field)
        			);
        }
    }
    
    
    /**
     * Store unique values with ids to be tested
     */
    private class FieldX {
    	public BaseEntity<?> ent;
    	public Object value;
    	public FieldConfig fieldConfig;
    	public String columnName;
    	public String parentColumn;
    }
    
    
    private String getAnnotationColumnName(Class<?> clazz, String field) throws Exception {
    	return getAnnotationColumnName(clazz, field, null);
    }
    
    private String getAnnotationColumnName(
			Class<?> clazz,
			String field,
			String name) throws Exception {
			
		if (name != null) {
			return name;
		}
    	
		if (clazz.getSuperclass() != null) {
			name = getAnnotationColumnName(clazz.getSuperclass(), field, name);
		}
		
		//Get configurations as per annotated fields 
		for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
			if (f.getName().equals(field)) {
				for (Annotation anno : f.getDeclaredAnnotations()) {
					if (anno instanceof javax.persistence.Column) {
						javax.persistence.Column a = (javax.persistence.Column)anno;
						name = a.name();
					}
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
 	
}

