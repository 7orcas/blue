package com.sevenorcas.blue.system.org;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.DtoOrg;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* Data access methods for organisation data
* 
* Created July '22
* [Licence]
* @author John Stewart
*/
@Stateless
public class TOrg extends BaseTransfer implements TOrgI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<DtoOrg> orgList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " + BASE_LIST_FIELDS_SQL
				+ ", dvalue "
				+ "FROM " + tableName(EntOrg.class, " ");
		
		if (parms.isActiveOnly()) {
			sql += "WHERE active = true ";
		}
		sql += "ORDER BY org_nr ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<DtoOrg> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			DtoOrg d = new DtoOrg();
			list.add(d);
			addBaseListFields(d, i, r);
			d.setDvalue(r.getBoolean(i, "dvalue", false));
		}
		
		return list;
    }
	
	
//	/**
//     * Return the <code>OrgEnt</code> entity 
//     * @param orgId
//     * @return
//     */
//    public EntOrg getOrg (
//    		Long orgId) throws Exception {
//    	return em.find(EntOrg.class, orgId);
//	}
    
}
