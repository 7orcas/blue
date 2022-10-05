package com.sevenorcas.blue.system.role;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* 
* Data access methods for organisation data
* 
* Created 05.10.22
* [Licence]
* @author John Stewart
*/

@Stateless
public class DaoPermission extends BaseDao {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<EntPermission> permissionList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " + BASE_LIST_FIELDS_SQL
				+ ", crud "
				+ "FROM " + tableName(EntPermission.class, " ");
		
		if (parms.isActiveOnly()) {
			sql += "WHERE active = true ";
		}
		sql += "ORDER BY code ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<EntPermission> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			EntPermission ent = new EntPermission();
			list.add(ent);
			addBaseListFields(ent, i, r);
			ent.setCrud(r.getString(i, "crud", ""));
		}
		
		return list;
    }
	
	
	/**
     * Return the <code>EntPermission</code> entity 
     * @param entity id
     * @return
     */
    public EntPermission getPermission (
    		Long id) throws Exception {
    	return em.find(EntPermission.class, id);
	}
	
    
    /**
     * Persist the <code>EntPermission</code> entity 
     * @param entity to persist
     * @return new id
     */
    public Long persistPermission (EntPermission ent) throws Exception {
    	return persist(ent);
	}
    
    /**
     * Merge selected fields and return the <code>EntPermission</code> entity 
     * @param entity
     * @return
     */
    public EntPermission mergePermission (
    		EntPermission ent) throws Exception {
    	
    	EntPermission mergedEnt = getPermission(ent.getId()); 
    	
    	mergedEnt.setCode(ent.getCode())
    	   .setActive(ent.isActive())
    	   .setCrud(ent.getCrud());
    
    	update(mergedEnt);
    	
    	return mergedEnt;
	}
    
    
    /**
     * Delete the <code>EntPermission</code> entity 
     * @param entity id
     * @return
     */
    public void deletePermission (
    		Long id) throws Exception {
    	EntPermission ent = getPermission(id);
    	em.remove(ent);
	}
    
}
