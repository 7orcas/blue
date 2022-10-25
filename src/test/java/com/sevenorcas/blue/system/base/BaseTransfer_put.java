package com.sevenorcas.blue.system.base;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.role.ent.EntRole;

/**
 * Base data transfer bean test - put method.
 * Created 25.10.2022
 * [Licence]
 * @author John Stewart
 */
public class BaseTransfer_put extends BaseTransfer_ {

	@Before
	public void setup() throws Exception {
		super.setup();
	}
	
	@Test
	public void put_NewAndDelete () throws Exception {
		EntRole ent = configEntNew(new EntRole())
		   .setDelete();
		baseTransfer.put(ent, config, callObject);
		assertTrue(true);
	}
	
	@Test
	public void put_Delete () throws Exception {
		EntRole ent = getRole(ROLE_DELETE)
		   .setDelete();
		ent = baseTransfer.put(ent, config, callObject);
		assertTrue(ent.getCode().endsWith("-remove"));
	}
	
	@Test
	public void put_Merge () throws Exception {
		EntRole ent = getRole(ROLE);
		EntityConfig configX = configSrv.getConfig(getCallObject(), EntRole.class.getCanonicalName());
		configX.put(new FieldConfig ("descr").unused());
		ent.setCode("123")
		   .setDescr("456");
		
		ent = baseTransfer.put(ent, configX, callObject);
		assertTrue(ent.getCode().equals("123"));
		assertTrue(ent.getDescr() == null);
	}
	
	
}
