package com.sevenorcas.blue.system.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.ent.ConfigurationI;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.role.ent.EntRolePermission;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * Configuration Module service bean test.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */


public class SConfigTest extends BaseTest implements ConfigurationI {

	@Test
	public void getConfig () {
		try {
			EntityConfig conf = configSrv.getConfig (getCallObject(), EntRolePermission.class.getCanonicalName());
			assertTrue(conf.isUnused("code"));
			assertTrue(conf.isUniqueParent("permissionId"));
			
			conf = configSrv.getConfig (getCallObject(), EntUser.class.getCanonicalName());
			assertTrue(conf.isUniqueIgnoreOrgNr("code"));
			
			for (FieldConfig f : conf.list()) {
				System.out.println(f.field 
						+ " f.min=" + f.min
						+ " f.max=" + f.max
						+ " f.nonNull=" + f.nullState
						+ " f.unique=" + f.isUnique()
						);
			}
			
			
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
