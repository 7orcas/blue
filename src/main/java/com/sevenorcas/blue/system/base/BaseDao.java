package com.sevenorcas.blue.system.base;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.interceptor.Interceptors;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.DaoAroundInvoke;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

/**
* TODO Module Description
* 
* [Licence]
* Created Jul '22
* @author John Stewart
*/

@Interceptors({DaoAroundInvoke.class})
public class BaseDao extends BaseUtil {
	
	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	/** standard fields in tables **/
	final static protected String BASE_LIST_FIELDS_SQL = " id,org_nr,code,descr,updated,active ";
	
	/** standard fields in tables **/
	final static protected String BASE_ENTITY_FIELDS_SQL = " id,org_nr,code,descr,updated,encoded,encoded_flag,active ";
	
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	public EntityManager getEntityManager() { 	
		return em;
	}
	
	/**
	 * Prepend the prefix to the fields
	 * @param prefix
	 * @param fields
	 * @return
	 */
	public String prefix (String prefix, String fields) {
		String p = fields.startsWith(" ")? " " : "",
			   s = fields.endsWith(" ")? " " : "";
		String [] x = fields.trim().split(",");
		StringBuffer sb = new StringBuffer();
		for (String f : x) {
			if (sb.length()>0) sb.append(",");
			sb.append(prefix + "." + f.trim());
		}
		return p + sb.toString() + s;
	}
	
	/**
     * Persist the entity 
     * @param entity
     * @return
     */
    public <T extends BaseEnt<T>> Long persist (T ent) throws Exception {
    	LocalDateTime d = LocalDateTime.now();
    	ent.setUpdated(Timestamp.valueOf(d));
    	em.persist(ent);
    	em.flush();
    	return ent.getId();
	}
	
    /**
     * Update the entities <code>update</code> field 
     * @param entity
     * @return
     */
    public <T extends BaseEnt<T>> void update (T ent) throws Exception {
    	LocalDateTime d = LocalDateTime.now();
    	ent.setUpdated(Timestamp.valueOf(d));
	}
	
	/**
	 * Clients creating new objects must have unique negative ids
	 * @return
	 * @throws Exception
	 */
	public Long nextTempIdNegative() throws Exception {
		return nextTempId() * -1;
	}
	
	/**
	 * Unique ID
	 * @return
	 * @throws Exception
	 */
	public Long nextTempId() throws Exception {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT NEXTVAL ('sys.temp_id')";
			DataSource ds = (DataSource) new InitialContext().lookup(SqlExecute.DS);
    		conn = ds.getConnection();
    		stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
    	
			// Extract data from result set
			while(rs.next()){
				return rs.getLong(1);
			}
		} finally {
			if (rs != null) rs.close();
    		if (stmt != null) stmt.close();
    		if (conn != null) conn.close();	
		}
		
		return null;
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
		   .setOrgNr(r.getInteger(index, "org_nr")) 
		   .setCode(r.getString(index, "code"))
		   .setDescr(r.getString(index, "descr"))
		   .setActive(r.getBoolean(index, "active"))
		   .setUpdated(r.getTimestamp(index, "updated"))
		   ;
	}
	
	/**
	 * Populate the standard list fields
	 * @param dto
	 * @param index
	 * @param result set
	 * @throws Exception
	 */
	static protected <T extends BaseEnt<T>> void addBaseListFields(T ent, Integer index, SqlResultSet r) throws Exception {
		ent.setId(r.getLong(index, "id")) 
		   .setOrgNr(r.getInteger(index, "org_nr")) 
		   .setCode(r.getString(index, "code"))
		   .setDescr(r.getString(index, "descr"))
		   .setActive(r.getBoolean(index, "active"))
		   .setUpdated(r.getTimestamp(index, "updated"))
		   ;
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
