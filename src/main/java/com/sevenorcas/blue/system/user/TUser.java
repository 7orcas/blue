package com.sevenorcas.blue.system.user;

import java.lang.invoke.MethodHandles;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
 * Data access methods for user data
 * 
 * Created 01.11.22
 * [Licence]
 * @author John Stewart
 */

@Stateless
public class TUser extends BaseTransfer implements TUserI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<EntUser> userList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " 
					+ prefixAs("t1", BASE_LIST_FIELDS_SQL) 
					+ "," + prefixAs("t2", BASE_LIST_FIELDS_SQL)
					+ "," + prefixAs("t3", BASE_LIST_FIELDS_SQL)
					+ ", role_id "
				+ "FROM " + tableName(EntUser.class, " AS t1 ")
				+ "LEFT JOIN " + tableName(EntUserRole.class, " AS t2 ON p.zzz_id = t1.id ")
				+ "LEFT JOIN " + tableName(EntRole.class, " AS t3 ON t3.id = t2.role_id ")
				;
		
		if (parms.isActiveOnly()) {
			sql += "WHERE " + prefix("t1", "active") + " = true ";
		}
		sql += "ORDER BY " + prefix("t1", "code");
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		Hashtable<Long, EntUser> list = new Hashtable<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			Long user_id = r.getLong(i, prefixField("t1", "id"));
			EntUser ent = null;
			
			if (list.containsKey(user_id)) {
				ent = list.get(user_id);	
			}
			else {
				ent = new EntUser();	
				addBaseListFields(ent, i, r, "t1");
				list.put(user_id, ent);
			}
			
			Long userrole_id = r.getLong(i, prefixField("t2", "id"));
			if (userrole_id != null) {
				EntUserRole p = new EntUserRole();
				ent.add(p);
				addBaseListFields(p, i, r, "t2");
				p.setRoleId(r.getLong(i, "role_id"));
				
				EntRole m = new EntRole();
				p.setEntRole(m);
				addBaseListFields(m, i, r, "t3");
			}
		}
		
		return hashtableToListId(list);
    }
    
}
