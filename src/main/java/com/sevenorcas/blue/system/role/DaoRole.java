package com.sevenorcas.blue.system.role;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
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
		
		String sql = "SELECT " + BASE_LIST_FIELDS_SQL
				+ "FROM " + tableName(EntRole.class, " ");
		
		if (parms.isActiveOnly()) {
			sql += "WHERE active = true ";
		}
		sql += "ORDER BY code ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<EntRole> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			EntRole ent = new EntRole();
			list.add(ent);
			addBaseListFields(ent, i, r);
		}
		
		return list;
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
