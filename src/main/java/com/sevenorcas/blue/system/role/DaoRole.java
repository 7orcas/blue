package com.sevenorcas.blue.system.role;

import java.lang.invoke.MethodHandles;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.role.ent.EntRolePermission;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* 
* Data access methods for role data
* 
* Created 05.10.22
* [Licence]
* @author John Stewart
*/

@Stateless
public class DaoRole extends BaseDao {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<EntRole> roleList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " + prefix("r", BASE_LIST_FIELDS_SQL) + ",p.id AS idX ,p.permission_id "
				+ "FROM " + tableName(EntRole.class, " AS r ")
				+ "LEFT JOIN " + tableName(EntRolePermission.class, " AS p ON p.role_id = r.id ")
				;
		
		if (parms.isActiveOnly()) {
			sql += "WHERE active = true ";
		}
		sql += "ORDER BY code ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		Hashtable<Long, EntRole> list = new Hashtable<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			Long id = r.getLong(i, "id");
			EntRole ent = null;
			
			if (list.containsKey(id)) {
				ent = list.get(id);	
			}
			else {
				ent = new EntRole();	
				addBaseListFields(ent, i, r);
				list.put(id, ent);
			}
			
			EntRolePermission p = new EntRolePermission();
			p.setId(r.getLong(i, "idX"))
			 .setPermissionId(r.getLong(i, "permission_id"));
			
			ent.add(p);
		}
		
		return hashtableToList(list);
    }
	
	
	/**
     * Return the <code>EntRole</code> entity 
     * @param entity id
     * @return
     */
    public EntRole getRole (
    		Long id) throws Exception {
    	return em.find(EntRole.class, id);
	}
	
    
    /**
     * Persist the <code>EntRole</code> entity 
     * @param entity to persist
     * @return new id
     */
    public Long persistRole (EntRole ent) throws Exception {
    	return persist(ent);
	}
    
    /**
     * Merge selected fields and return the <code>EntRole</code> entity 
     * @param entity
     * @return
     */
    public EntRole mergeRole (
    		EntRole ent) throws Exception {
    	
    	EntRole mergedEnt = getRole(ent.getId()); 
    	
    	mergedEnt.setCode(ent.getCode())
    			 .setDescr(ent.getDescr())
    			 .setActive(ent.isActive());
    
    	update(mergedEnt);
    	
    	return mergedEnt;
	}
    
    
    /**
     * Delete the <code>EntRole</code> entity 
     * @param entity id
     * @return
     */
    public void deleteRole (
    		Long id) throws Exception {
    	EntRole ent = getRole(id);
    	em.remove(ent);
	}
    
}
