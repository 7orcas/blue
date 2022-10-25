package com.sevenorcas.blue.system.base;

import java.util.List;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.conf.SConfig;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.role.TRole;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.sql.SqlUpdate;

/**
 * Base data transfer bean test.
 * Created 25.10.2022
 * [Licence]
 * @author John Stewart
 */
public class BaseTransfer_ extends BaseTest {

	static public String ROLE = "TestRole";
	static public String ROLE_DELETE = "TestRoleDelete";
	
	public SConfig configSrv;
	public EntityConfig config;
	public TRole roleT;
	
	public void setup() throws Exception {
		roleT = setupEJBs(new TRole());
		configSrv = setupEJBs(new SConfig());
		config = configSrv.getConfig(getCallObject(), EntRole.class.getCanonicalName());
		setTestData();
	}
	
	/**
	 * Predefined test records
	 */
	public void setTestData() throws Exception {
		setupDataSource();
		
		String sql = "DELETE FROM " + BaseUtil.tableName(EntRole.class, null) + " WHERE code = '" +  ROLE + "' AND org_nr = " + ORG_NR;  
		SqlUpdate.executeQuery(null, sql, null);
		sql = "DELETE FROM " + BaseUtil.tableName(EntRole.class, null) + " WHERE code = '" +  ROLE_DELETE + "' AND org_nr = " + ORG_NR;  
		SqlUpdate.executeQuery(null, sql, null);
			
		sql = "INSERT INTO " + BaseUtil.tableName(EntRole.class, null) + " (id, code, org_nr, updated, updated_userid) "
				+ "VALUES (" + nextTempId() + ", '" +  ROLE + "', " + ORG_NR + ", current_timestamp, 1)";
		SqlUpdate.executeQuery(null, sql, null);
				
		sql = "INSERT INTO " + BaseUtil.tableName(EntRole.class, null) + " (id, code, org_nr, updated, updated_userid) "
				+ "VALUES (" + nextTempId() + ", '" +  ROLE_DELETE + "', " + ORG_NR + ", current_timestamp, 1)";
		SqlUpdate.executeQuery(null, sql, null);
	}
	
	/**
	 * Predefined test records
	 */
	public EntRole getRole(String role) throws Exception {
		List<EntRole> list = roleT.roleList(getCallObject(), null);
		for (int i=0;i<list.size();i++) {
			if (list.get(i).code.equals(role)) {
				return list.get(i);
			}
		}
		return null;
	}
	
}
