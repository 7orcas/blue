package com.sevenorcas.blue.system.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.ConfigurationI;
import com.sevenorcas.blue.system.conf.SrvConfig;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.role.ent.EntRole;

/**
 * Configuration Module service bean test.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */


public class SrvConfigTest extends BaseTest implements ConfigurationI {

	private SrvConfig service;
	
	@Before
	public void setup() throws Exception {
		service = new SrvConfig();
		setupEJBs(service);
	}
	
//	@Test
	public void getConfig () {
		try {
			EntityConfig conf = service.getConfig (getCallObject(), "system.org.ent.EntOrg");
			
			for (FieldConfig f : conf.list()) {
				System.out.println(f.name 
						+ " f.min=" + f.min
						+ " f.max=" + f.max
						+ " f.nonNull=" + f.nullState
						+ " f.unique=" + f.unique
						);
			}
			
			
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
	@Test
	public void validate () {
		try {
			EntityConfig conf = service.getConfig (getCallObject(), EntPermission.class.getCanonicalName());
			
			FieldConfig fc = new FieldConfig("descr");
			fc.min = 10D;
			conf.put(fc);
			
			fc = new FieldConfig("encoded");
			fc.min = 1D;
			conf.put(fc);
			
			EntPermission ent = new EntPermission();
			ent.setCode("123456789012345678901234567890");
			ent.setDescr("12345");
			ValidationErrors errors = new ValidationErrors();
			
			service.validate (ent, conf, errors);
			
			boolean errorNull = false,
					errorMin = false,
					errorMinNull = false,
					errorMax = false;
			
			for (ValidationError err : errors.getErrors()) {
				if (err.field.equals("crud") && err.type == VAL_ERROR_NULL_VALUE) errorNull = true;
				if (err.field.equals("code") && err.type == VAL_ERROR_MAX_VALUE) errorMax = true;
				if (err.field.equals("descr") && err.type == VAL_ERROR_MIN_VALUE) errorMin = true;
				if (err.field.equals("encoded") && err.type == VAL_ERROR_MIN_VALUE) errorMinNull = true;
			}
			
			System.out.println("Null=" + errorNull + " "
					+ "Max=" + errorMax + " "
					+ "Min=" + errorMin + " "
					+ "MinNull=" + errorMinNull + " "
					);
			
			assertTrue(errorNull && errorMin && errorMinNull && errorMax);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
		
	}
	
}
