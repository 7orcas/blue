package com.sevenorcas.blue.system.conf;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* 
* Data access methods for validation
* 
* Created 19.10.22
* [Licence]
* @author John Stewart
*/

@Stateless
public class DaoValidation extends BaseDao {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<Object> fields(
    		String field,
    		Integer orgNr,
    		String constraint,
    		Class<?> clazz) throws Exception {
		
		SqlParm parms = validateParms(null);
		
		String sql = "SELECT " 
					+ field + " AS x "
				+ "FROM " + tableName(clazz, " ");
		
		if (orgNr != null) {
			sql += "WHERE org_nr = " + orgNr + " ";
		}
		if (!constraint.isEmpty()) {
			sql += "GROUP BY " + field + "," + constraint + " ";	
		}
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<Object> fields = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			fields.add(r.getObject(i, "x"));
		}
		
		return fields;
    }	
    
}
