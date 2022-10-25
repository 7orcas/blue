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
import com.sevenorcas.blue.system.sql.SqlUpdate;
/**
 * Base data transfer bean test - compare time stamps.
 * Created 25.10.2022
 * [Licence]
 * @author John Stewart
 */
public class BaseTransfer_compareTimeStamp extends BaseTest {

	static private String ROLE = "TestRole";
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
		dao.compareTimeStamp(getRole(), config, errors);
		assertTrue(!errors.hasErrors());
	}
	
	@Test
	public void compareTimeStamp_changed () throws Exception {
		ValidationErrors errors = new ValidationErrors();
		EntRole ent = getRole();
		dao.updateTimestampUserid(ent, 1L);
		dao.compareTimeStamp(ent, config, errors);
		assertTrue(errors.getErrors().get(0).type == VAL_ERROR_TS_DIFF);
	}
	
	/**
	 * Predefined test records
	 */
	public void setTestData() throws Exception {
		setupDataSource();
		
		String sql = "DELETE FROM " + BaseUtil.tableName(EntRole.class, null) + " WHERE code = '" +  ROLE + "' AND org_nr = " + ORG_NR;  
		SqlUpdate.executeQuery(null, sql, null);
		
		Long id = dao.nextTempId();
		sql = "INSERT INTO " + BaseUtil.tableName(EntRole.class, null) + " (id, code, org_nr, updated, updated_userid) "
				+ "VALUES (" + id + ", '" +  ROLE + "', " + ORG_NR + ", current_timestamp, 1)";
		SqlUpdate.executeQuery(null, sql, null);
	}
	
	/**
	 * Predefined test records
	 */
	public EntRole getRole() throws Exception {
		List<EntRole> list = roleT.roleList(getCallObject(), null);
		for (int i=0;i<list.size();i++) {
			if (list.get(i).code.equals(ROLE)) {
				return list.get(i);
			}
		}
		return null;
	}
	
	
}
