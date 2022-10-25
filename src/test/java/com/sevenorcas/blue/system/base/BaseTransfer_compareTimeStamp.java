package com.sevenorcas.blue.system.base;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.SConfig;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.role.TRole;
import com.sevenorcas.blue.system.role.ent.EntRole;

public class BaseTransfer_compareTimeStamp extends BaseTest {

	private BaseTransfer dao;
	private SConfig configSrv;
	private EntityConfig config;
	private TRole roleT;
	
	@Before
	public void setup() throws Exception {
		dao = setupEJBs(new BaseTransfer());
		roleT = setupEJBs(new TRole());
		configSrv = setupEJBs(new SConfig());
		config = configSrv.getConfig(getCallObject(), EntRole.class.getCanonicalName());
		setTestData();
	}
	
	@Test
	public void compareTimeStamp_new () throws Exception {
		ValidationErrors errors = new ValidationErrors();
		EntRole ent = configNewEnt(new EntRole());
		dao.compareTimeStamp(ent, config, errors);
		assertTrue(!errors.hasErrors());
	}
	
	@Test
	public void compareTimeStamp_unchanged () throws Exception {
		ValidationErrors errors = new ValidationErrors();
		EntRole ent = getRole();
		dao.compareTimeStamp(getRole(), config, errors);
		assertTrue(!errors.hasErrors());
	}
	
	@Test
	public void compareTimeStamp_changed () throws Exception {
		ValidationErrors errors = new ValidationErrors();
		EntRole ent = getRole();
		dao.updateTimestampUserid(ent, 1L);
		dao.compareTimeStamp(getRole(), config, errors);
		assertTrue(!errors.hasErrors());
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
