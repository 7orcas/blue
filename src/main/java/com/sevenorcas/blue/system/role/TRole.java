package com.sevenorcas.blue.system.role;

import java.lang.invoke.MethodHandles;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.role.ent.EntRolePermission;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
 * Data access methods for role data
 * 
 * Created 05.10.22
 * [Licence]
 * @author John Stewart
 */

@Stateless
public class TRole extends BaseTransfer implements TRoleI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<EntRole> roleList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " 
					+ prefixAs("r", BASE_LIST_FIELDS_SQL) 
					+ "," + prefixAs("p", BASE_LIST_FIELDS_SQL)
					+ "," + prefixAs("m", BASE_LIST_FIELDS_SQL)
					+ ", permission_id, crud "
				+ "FROM " + tableName(EntRole.class, " AS r ")
				+ "LEFT JOIN " + tableName(EntRolePermission.class, " AS p ON p.role_id = r.id ")
				+ "LEFT JOIN " + tableName(EntPermission.class, " AS m ON m.id = p.permission_id ")
				;
		
		if (parms.isActiveOnly()) {
			sql += "WHERE " + prefix("p", "active") + " = true ";
		}
		sql += "ORDER BY " + prefix("p", "code");
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		Hashtable<Long, EntRole> list = new Hashtable<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			Long role_id = r.getLong(i, prefixField("r", "id"));
			EntRole ent = null;
			
			if (list.containsKey(role_id)) {
				ent = list.get(role_id);	
			}
			else {
				ent = new EntRole();	
				addBaseListFields(ent, i, r, "r");
				list.put(role_id, ent);
			}
			
			Long rolep_id = r.getLong(i, prefixField("p", "id"));
			if (rolep_id != null) {
				EntRolePermission p = new EntRolePermission();
				ent.add(p);
				addBaseListFields(p, i, r, "p");
				p.setPermissionId(r.getLong(i, "permission_id"));
				
				EntPermission m = new EntPermission();
				p.setEntPermission(m);
				addBaseListFields(m, i, r, "m");
				m.setCrud(r.getString(i, "crud"));
			}
		}
		
		return hashtableToListId(list);
    }
    
}
