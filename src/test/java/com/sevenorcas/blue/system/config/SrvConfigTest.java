package com.sevenorcas.blue.system.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.SrvConfig;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;

/**
 * Configuration Module service bean test.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */


public class SrvConfigTest extends BaseTest {

	private SrvConfig service;
	
	@Before
	public void setup() throws Exception {
		service = new SrvConfig();
		setupEJBs(service);
	}
	
	@Test
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
	
}
