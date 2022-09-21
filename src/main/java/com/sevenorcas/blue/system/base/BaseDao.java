package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.DaoAroundInvoke;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* Created Jul '22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({DaoAroundInvoke.class})
public class BaseDao extends BaseUtil {
	
	/** standard fields in tables **/
	final static protected String BASE_LIST_FIELDS_SQL = " id,org,code,active ";
	
	/** standard fields in tables **/
	final static protected String BASE_ENTITY_FIELDS_SQL = " id,org,code,created,encoded,encoded_flag,active ";
	
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	public EntityManager getEntityManager() { 	
		return em;
	}
	
	
	/**
	 * Ensure a valid SQL Parameter object is used
	 * @param parms
	 * @return
	 */
	static protected SqlParm validateParms (SqlParm parms) {
		if (parms == null) {
			return new SqlParm ();
		}
		return parms;
	}
	
	/**
	 * Populate the standard list fields
	 * @param dto
	 * @param index
	 * @param result set
	 * @throws Exception
	 */
	static protected <T extends BaseDto<T>> void addBaseListFields(T dto, Integer index, SqlResultSet r) throws Exception {
		dto.setId(r.getLong(index, "id")) 
		   .setOrgNr(r.getInteger(index, "org")) 
		   .setCode(r.getString(index, "code")) 
		   .setActive(r.getBoolean(index, "active"));
	}
	
	
	/**
	 * Return the entities database table name
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	static protected String tableName (Class<?> clazz, String suffix) throws Exception {
		suffix = suffix == null? "" : suffix;
		if (clazz.isAnnotationPresent(Table.class)) {
			Table table = clazz.getAnnotation(Table.class);
			return table.schema() + "." + table.name() + suffix;
		}
		throw new RedException (LK_UNKNOWN_ERROR, "Invalid table annonation on class : " + clazz.getCanonicalName());
	}
	
	
}
