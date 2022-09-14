package com.sevenorcas.blue.system.org;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
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
	
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	
	public List<OrgDto> orgList(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT " + BASE_FIELDS_SQL
				+ "dvalue "
				+ "FROM cntrl.org ";
		
		if (parms.isActiveOnly()) {
			sql += "WHERE active = true ";
		}
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<OrgDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			OrgDto d = new OrgDto();
			list.add(d);
			
			d.setId(r.getLong(i, "id"))
			 .setOrgNr(r.getInteger(i, "org"))
			 .setCode(r.getString(i, "code"))
			 .setDvalue(r.getBoolean(i, "dvalue"));
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
	
}
