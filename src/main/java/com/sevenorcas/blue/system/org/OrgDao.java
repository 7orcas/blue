package com.sevenorcas.blue.system.org;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.app.Label;
import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;

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
		
		String sql = "SELECT id, org, code, dvalue " +
				     "FROM cntrl.org ";
		
		if (parms.isActiveOnly()) {
			sql += "WHERE active = true ";
		}
		
		List<Object[]> r = SqlExecute.executeQuery(parms, sql, log);
		List<OrgDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			OrgDto d = new OrgDto();
			list.add(d);
			Object[] row = r.get(i);
			
			d.setId((Long)row[0])
			 .setOrg((Integer)row[1])
			 .setCode((String)row[2])
			 .setDefaultValue((Boolean)row[3])
			 ;
		}
		
		return list;
    }
	
	
	
	
}
