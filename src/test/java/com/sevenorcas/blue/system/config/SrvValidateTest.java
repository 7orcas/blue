package com.sevenorcas.blue.system.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.ConfigurationI;
import com.sevenorcas.blue.system.conf.SrvConfig;
import com.sevenorcas.blue.system.conf.SrvValidate;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.role.ent.EntPermission;

/**
 * Configuration Module service bean test.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */


public class SrvValidateTest extends BaseTest implements ConfigurationI {

	private SrvConfig configSrv;
	private SrvValidate valSrv;
	
	@Before
	public void setup() throws Exception {
		configSrv = new SrvConfig();
		setupEJBs(configSrv);
		valSrv = new SrvValidate();
		setupEJBs(valSrv);
	}
	
	@Test
	public void validate () {
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
			   .setConfig(conf)
			   .setOrgNr(0)
			   .setId(-1L);
			
			//Force duplicate error
			for (int i=0;i<3;i++) {
				ent = new EntPermission();
				list.add(ent);
				ent.setCode("duplicate")
				   .setDescr("1234567890")
				   .setEncoded("abc")
				   .setConfig(conf)
				   .setOrgNr(0)
				   .setId(-1L);
			}
			
			ent = new EntPermission();
			list.add(ent);
			ent.setCode("lang") //Existing record in database
			   .setDescr("1234567890")
			   .setEncoded("abc")
			   .setConfig(conf)
			   .setOrgNr(0)
			   .setId(-1L);
			
			ValidationErrors errors = valSrv.validate (list, errors);
			errors.setLabels(null); //Sets the error codes
			
			boolean errorNull = false,
					errorMin = false,
					errorMinNull = false,
					errorMax = false,
					errorUniqueNew = false;
			
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
			
			assertTrue(errorNull && errorMin && errorMinNull && errorMax && errorUniqueNew);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
		
	}
	
}
