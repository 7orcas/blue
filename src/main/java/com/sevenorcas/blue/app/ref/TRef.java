package com.sevenorcas.blue.app.ref;

import java.lang.invoke.MethodHandles;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.ref.ent.EntReftype;
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
	 * Reference list
	 */
	public List<BaseEntityRef<?>> list(
    		CallObject callObj,
    		SqlParm parms,
    		String reftype) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT t1.sort, t1.dvalue " 
					+ prefixAs("t1", BASE_ENTITY_FIELDS_SQL) 
				+ "FROM " + tableName(BaseEntityRef.class, " AS t1 ")
					+ "INNER JOIN " + tableName(EntReftype.class, "x")
					+ "ON t1.reftype_id = x.id "
				+ "WHERE x.code = '" + reftype + "'"
				;
		
		if (parms.isActiveOnly()) {
			sql += "WHERE " + prefix("t1", "active") + " = true ";
		}
		sql += "ORDER BY t1.sort, t1.code ";
		
		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		Hashtable<Long, BaseEntityRef<?>> list = new Hashtable<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			Long ref_id = r.getLong(i, prefixField("t1", "id"));
			BaseEntityRef<?> ent = null;
			
			if (list.containsKey(ref_id)) {
				ent = list.get(ref_id);	
			}
			else {
				ent = new BaseEntityRef<>();	
				addBaseListFields(ent, i, r, "t1");
				list.put(ref_id, ent);
				ent.setSort(r.getInteger(i, "sort"))
				   .setDvalue(r.getBoolean(i, "dvalue"));
			}
		}
		
		return hashtableToListId(list);
    }

	
}
