package com.sevenorcas.blue.system.ref;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;
import com.sevenorcas.blue.system.sql.SqlUpdate;

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
	 * @param Call Object
	 * @param Sql parameters
	 * @param reference entity class
	 */
	public List<? extends BaseEntityRef<?>> list(
    		CallObject callObj,
    		SqlParm parms,
    		Class<? extends BaseEntityRef<?>> T) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT t1.sort, t1.dvalue, " 
					+ prefixAs("t1", BASE_ENTITY_FIELDS_SQL) 
				+ "FROM " + tableName(T, " AS t1 ")
				+ "WHERE t1.org_nr = " + callObj.getOrgNr() + " "
				;
		
		if (parms.isActiveOnly()) {
			sql += "AND" + prefix("t1", "active") + " = true ";
		}
		sql += "ORDER BY t1.sort, t1.code ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<BaseEntityRef<?>> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			BaseEntityRef<?> ent = T.getDeclaredConstructor().newInstance();
			list.add(ent);
			
			addBaseListFields(ent, i, r, "t1");
			ent.setSort(r.getInteger(i, "sort")); 
			ent.setDvalue(r.getBoolean(i, "dvalue"));
		}
		
		return list;
    }

	
	/**
	 * Reset default values
	 * @param Call Object
	 * @param reference entity class
	 * @throws Exception
	 */
	public void resetDvalues(CallObject callObj,
			Class<? extends BaseEntityRef<?>> T) throws Exception {
		String sql = "UPDATE " 
				+ tableName(T, " ") 
				+ "SET dvalue = false "
				+ "WHERE org_nr = " + callObj.getOrgNr();
		SqlUpdate.executeQuery(null, sql, log);	
	}
	
}
