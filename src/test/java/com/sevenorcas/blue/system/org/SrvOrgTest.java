package com.sevenorcas.blue.system.org;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * Organisation Module service bean test.
 * 
 * Created 28.09.2022
 * [Licence]
 * @author John Stewart
 */


public class SrvOrgTest extends BaseTest {

	private SOrg service;
	
	@Before
	public void setup() throws Exception {
		service = new SOrg();
		setupEJBs(service);
	}
	
	@Test
	public void newOrg () {
		try {
			EntOrg o = service.newOrg(getCallObject());
			System.out.println("id=" + o.getId() 
					+ " active=" + o.isActive()
					+ " dvalue=" + o.getDvalue()
					);
			
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
