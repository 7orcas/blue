package com.sevenorcas.blue.system.user;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.base.IdCodeI;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.role.ent.EntRolePermission;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;
import com.sevenorcas.blue.system.user.ent.EntUser;
import com.sevenorcas.blue.system.user.ent.EntUserRole;

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
	
	/**
	 * Cut down user entity
	 * ToDo refactor: don't need hashtable
	 */
	public List<EntUser> userList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " 
					+ prefixAs("t1", BASE_LIST_FIELDS_SQL) 
					+ ", " + EntUser.USERNAME + ", attempts "
				+ "FROM " + tableName(EntUser.class, " AS t1 ")
				;
		
		if (parms.isActiveOnly()) {
			sql += "WHERE " + prefix("t1", "active") + " = true ";
		}
		sql += "ORDER BY " + prefix("t1", EntUser.USERNAME);
		
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
				ent.setUserName(r.getString(i, EntUser.USERNAME))
				   .setAttempts(r.getInteger(i, "attempts"));
			}
		}
		
		return hashtableToListId(list);
    }

	/**
	 * Get a user's permission list
	 * Process CRUD values for duplicate urls
	 * 
	 * @param callObj
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public List<EntPermission> permissionList(
    		CallObject callObj,
    		SqlParm parms,
    		EntUser user) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT p.id, p.code, p.crud "
				+ "FROM " + tableName(EntUserRole.class, "r")
					+ "INNER JOIN " + tableName(EntRolePermission.class, "x")
						+ "ON r.role_id = x.role_id "
						+ "AND x.active = true "
					+ "INNER JOIN " + tableName(EntPermission.class, "p")
						+ "ON p.id = x.permission_id "
						+ "AND p.active = true "
					+ "WHERE " + prefix("r", EntUserRole.USER_ID) + " = " + user.getId() + " "
						+ "AND r.active = true "
					+ "ORDER BY p.code ";

		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<EntPermission> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			String url = r.getString(i, "code");
			String crud = r.getString(i, "crud");
			
			EntPermission ent = findByCode (url, list);
		
			if (ent == null) {
				ent = new EntPermission()
				   .setId(r.getLong(i, "id") * -1)
				   .setCode(url)
				   .setCrud(crud)
				   .setActive();
				list.add(ent);
			}
			//Consolidate
			else {
				ent.combine(crud);
			}
		}
		
		return list;
    }
	
}
