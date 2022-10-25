package com.sevenorcas.blue.system.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.SValidate;
import com.sevenorcas.blue.system.conf.ent.ConfigurationI;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.role.ent.EntRolePermission;

/**
 * Configuration Module service bean test.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */


public class SValidateTest extends BaseTest implements ConfigurationI {

	private SValidate valSrv;
	private EntityConfig permConfig;
	
	@Before
	public void setup() throws Exception {
		valSrv = setupEJBs(new SValidate());
		permConfig = configSrv.getConfig(callObject, EntRolePermission.class.getCanonicalName());	
	}
	
	/**
	 * Create duplicate entities and test for validation errors
	 */
	@Test
	public void validateUniqueInList () {
		try {
	    	
	    	EntRole ent = configEntNew(new EntRole());
	    	ent.setCode("x");
	    	
			List<EntRolePermission> list = new ArrayList<>();
			for (int i=0;i<3;i++) {
				list.add(configEntNew(new EntRolePermission())
				    .setRoleId(ent.getId())
				    .setPermissionId(1L));
			}
			
			ValidationErrors errors = new ValidationErrors();
			valSrv.validate (list, ent.getCode(), ent.getId(), permConfig, errors);
			checkErrors("EntRolePermission in List", errors);
			boolean result = hasError(errors, "permissionId", VAL_ERROR_NON_UNIQUE_NEW);
			assertTrue(result);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}

	/**
	 * Create duplicate entities and test for validation errors
	 */
	@Test
	public void validateUniqueInDB () {
		try {
	    	
	    	EntRole ent = configEntNew(new EntRole());
	    	ent.setId(1L)
	    	   .setCode("x")
	    	   .setOrgNr(0);
	    	
			List<EntRolePermission> list = new ArrayList<>();
			EntRolePermission perm = configEntNew(new EntRolePermission());
			perm.setRoleId(ent.getId())
				.setOrgNr(ent.getOrgNr())
			    .setPermissionId(1L);
			list.add(perm);

			ValidationErrors errors = new ValidationErrors();			
			valSrv.validate (list, ent.getCode(), ent.getId(), permConfig, errors);
			checkErrors("EntRolePermission in DB", errors);
			boolean result = hasError(errors, "permissionId", VAL_ERROR_NON_UNIQUE_DB);
			assertTrue(result);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}

	
	@Test
	public void validatePermission () {
		try {
			EntityConfig conf = configSrv.getConfig (getCallObject(), EntPermission.class.getCanonicalName());
			
			FieldConfig fc = new FieldConfig("descr");
			fc.min = 10D;
			conf.put(fc);
			
			fc = new FieldConfig("encoded");
			fc.min = 1D;
			conf.put(fc);
			
			List<EntPermission> list = new ArrayList<>();
			
			EntPermission ent = new EntPermission();
			list.add(ent);
			ent.setCode("123456789012345678901234567890")
			   .setDescr("12345")
			   .setOrgNr(0)
			   .setId(-1L);
			
			//Force duplicate error
			for (int i=0;i<3;i++) {
				ent = new EntPermission();
				list.add(ent);
				ent.setCode("duplicate")
				   .setDescr("1234567890")
				   .setEncoded("abc")
				   .setOrgNr(0)
				   .setId(-1L);
			}
			
			ent = new EntPermission();
			list.add(ent);
			ent.setCode("lang") //Existing record in database
			   .setDescr("1234567890")
			   .setEncoded("abc")
			   .setOrgNr(0)
			   .setId(-1L);
			
			ValidationErrors errors = valSrv.validate (list, conf);
			boolean result = checkErrors("EntPermission", errors);
			assertTrue(!result);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}
	
	private boolean hasError(ValidationErrors errors, String field, int type) {
		for (ValidationError err : errors.getErrors()) {
			if (err.field.equals(field) && err.type == type) return true;
		}	
		return false;
	}
	
	private boolean checkErrors(String ent, ValidationErrors errors) {
		
		errors.setLabels(util); //Sets the error codes
		
		boolean errorNull = false,
				errorMin = false,
				errorMinNull = false,
				errorMax = false,
				errorUniqueNew = false;
		
		System.out.println("Errors for " + ent);
		for (ValidationError err : errors.getErrors()) {
			System.out.println(err.toString());
			if (err.field.equals("crud") && err.type == VAL_ERROR_NULL_VALUE) errorNull = true;
			if (err.field.equals("code") && err.type == VAL_ERROR_MAX_VALUE) errorMax = true;
			if (err.field.equals("code") && err.type == VAL_ERROR_NON_UNIQUE_NEW) errorUniqueNew = true;
			if (err.field.equals("descr") && err.type == VAL_ERROR_MIN_VALUE) errorMin = true;
			if (err.field.equals("encoded") && err.type == VAL_ERROR_MIN_VALUE) errorMinNull = true;
		}
		
		System.out.println("Null=" + errorNull + " "
				+ "Max=" + errorMax + " "
				+ "Min=" + errorMin + " "
				+ "MinNull=" + errorMinNull + " "
				+ "UniqueNew=" + errorUniqueNew + " "
				);
		return errorNull && errorMin && errorMinNull && errorMax && errorUniqueNew;
	}
	
}
