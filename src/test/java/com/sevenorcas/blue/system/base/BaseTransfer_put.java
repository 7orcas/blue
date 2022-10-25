package com.sevenorcas.blue.system.base;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.SConfig;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.TRole;
import com.sevenorcas.blue.system.role.ent.EntRole;

/**
 * Base data transfer bean test - put method.
 * Created 25.10.2022
 * [Licence]
 * @author John Stewart
 */
public class BaseTransfer_put extends BaseTest {

	private BaseTransfer dao;
	private SConfig configSrv;
	private EntityConfig config;
	private CallObject callObject;
	private TRole roleT;
	
	@Before
	public void setup() throws Exception {
		dao = setupEJBs(new BaseTransfer());
		roleT = setupEJBs(new TRole());
		configSrv = setupEJBs(new SConfig());
		config = configSrv.getConfig(getCallObject(), EntRole.class.getCanonicalName());
		callObject = getCallObject();
		setTestData();
	}
	
	@Test
	public void put_newAndDelete () throws Exception {
		EntRole ent = configNewEnt(new EntRole())
		   .setDelete();
		dao.put(ent, config, callObject);
		assertTrue(true);
	}
	
	
	
	/**
	 * Predefined test records
	 */
	public EntRole getRole() throws Exception {
		List<EntRole> list = roleT.roleList(getCallObject(), null);
		for (int i=0;i<list.size();i++) {
			if (list.get(i).code.equals("TestRole")) {
				return list.get(i);
			}
		}
		return null;
	}
	
	
}
