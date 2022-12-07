package com.sevenorcas.blue.app.ref;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.poi.ss.formula.functions.T;
import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
 * Data access methods for reference data
 * 
 * Created 02.12.22
 * [Licence]
 * @author John Stewart
 */

@Stateless
public class TRef extends BaseTransfer implements TRefI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	/**
	 * Simple Reference list
	 */
	public List<? extends BaseEntityRef<?>> list(
    		CallObject callObj,
    		SqlParm parms,
    		Class<? extends BaseEntityRef<?>> T) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT t1.sort, t1.dvalue, " 
					+ prefixAs("t1", BASE_ENTITY_FIELDS_SQL) 
				+ "FROM " + tableName(T, " AS t1 ")
				;
		
		if (parms.isActiveOnly()) {
			sql += "WHERE " + prefix("t1", "active") + " = true ";
		}
		sql += "ORDER BY t1.sort, t1.code ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<BaseEntityRef<?>> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			BaseEntityRef<?> ent = T.getDeclaredConstructor().newInstance();
			list.add(ent);
			
//			BaseEntityRef ref = (BaseEntityRef)ent;
			addBaseListFields(ent, i, r, "t1");
			ent.setSort(r.getInteger(i, "sort")); 
			ent.setDvalue(r.getBoolean(i, "dvalue"));
			
		}
		
		return list;
    }

	
}
