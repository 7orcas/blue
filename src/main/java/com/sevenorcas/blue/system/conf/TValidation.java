package com.sevenorcas.blue.system.conf;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* 
* Data transfer methods for validation
* 
* Created 19.10.22
* [Licence]
* @author John Stewart
*/

@Stateless
public class TValidation extends BaseTransfer implements TValidationI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	public List<Object> fields(
    		String field,
    		Integer orgNr,
    		String parentColumn,
    		Long parentId,
    		Long entityId,
    		Class<?> clazz) throws Exception {
		
		SqlParm parms = validateParms(null);
		
		String sql = "SELECT " 
					+ field + " AS x "
				+ "FROM " + tableName(clazz, " ");
		
		String sqlx = "";
		
		if (orgNr != null) {
			sqlx += (sqlx.length()>0?"AND":"WHERE") + " org_nr = " + orgNr + " ";
		}
		if (parentColumn != null) {
			sqlx += (sqlx.length()>0?"AND":"WHERE") + " " + parentColumn + " = " + parentId + " ";	
		}
		if (!isNewId(entityId)) {
			sqlx += (sqlx.length()>0?"AND":"WHERE") + " id <> " + entityId + " ";	
		}
		sql += sqlx;
				
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<Object> fields = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			fields.add(r.getObject(i, "x"));
		}
		
		return fields;
    }	
    
}
