package com.sevenorcas.blue.system.org;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.base.BaseDto;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.OrgDto;
import com.sevenorcas.blue.system.org.ent.OrgEnt;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* Created July '22
* 
* Data access methods for organisation data
* 
* [Licence]
* @author John Stewart
*/

@Stateless
public class OrgDao extends BaseDao {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<OrgDto> orgList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " + BASE_LIST_FIELDS_SQL
				+ ", dvalue "
				+ "FROM " + tableName(OrgEnt.class, " ");
		
		if (parms.isActiveOnly()) {
			sql += "WHERE active = true ";
		}
		sql += "ORDER BY org ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<OrgDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			OrgDto d = new OrgDto();
			list.add(d);
			addBaseListFields(d, i, r);
			d.setDvalue(r.getBoolean(i, "dvalue", false));
		}
		
		return list;
    }
	
	
	/**
     * Return the <code>OrgEnt</code> entity 
     * @param orgId
     * @return
     */
    public OrgEnt getOrg (
    		Long orgId) throws Exception {
    	return em.find(OrgEnt.class, orgId);
	}
	
    
    /**
     * Persist the <code>OrgEnt</code> entity 
     * @param org entity
     * @return
     */
    public void persistOrg (
    		OrgEnt org) throws Exception {
    	em.persist(org);
	}
    
    /**
     * Merge selected fields and return the <code>OrgEnt</code> entity 
     * @param org entity
     * @return
     */
    public OrgEnt mergeOrg (
    		OrgEnt org) throws Exception {
    	
    	OrgEnt ent = getOrg(org.getId()); 
    	
    	ent.setCode(org.getCode())
    	   .setActive(org.isActive())
    	   .setDvalue(org.getDvalue());
    	
    	return ent;
	}
    
    
    /**
     * Delete the <code>OrgEnt</code> entity 
     * @param org entity id
     * @return
     */
    public void deleteOrg (
    		Long id) throws Exception {
    	OrgEnt ent = getOrg(id);
    	em.remove(ent);
	}
    
}
