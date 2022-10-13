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
	 * Prepend the table prefix to the fields
	 * @param prefix
	 * @param fields
	 * @return
	 */
	public String prefix (String prefix, String fields) {
		return prefix (prefix, fields, false);
	}
	
	/**
	 * Prepend the table prefix to the fields as append 'AS'
	 * @param prefix
	 * @param fields
	 * @return
	 */
	public String prefixAs (String prefix, String fields) {
		return prefix (prefix, fields, true);
	}
	
	/**
	 * Prepend the table prefix to the fields
	 * @param prefix
	 * @param fields
	 * @param true = append 'AS'
	 * @return
	 */
	public String prefix (String prefix, String fields, boolean as) {
		String p = fields.startsWith(" ")? " " : "",
			   s = fields.endsWith(" ")? " " : "";
		String [] x = fields.trim().split(",");
		StringBuffer sb = new StringBuffer();
		for (String f : x) {
			if (sb.length()>0) sb.append(",");
			sb.append(prefix + "." + f.trim() + (as? " AS "  + prefix + "_" + f.trim() : ""));
		}
		return p + sb.toString() + s;
	}
	
	/**
     * Return the entity 
     * @param entity id
     * @return
     */
    public <T extends BaseEnt<T>> T find (T ent) throws Exception {
    	return em.find(ent.getEntClass(), ent.getId());
	}
	
	/**
     * Persist the entity 
     * @param entity
     * @return
     */
    public <T extends BaseEnt<T>> T persist (T ent) throws Exception {
    	LocalDateTime d = LocalDateTime.now();
    	ent.setUpdated(Timestamp.valueOf(d));
    	em.persist(ent);
    	em.flush();
    	return ent;
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
     * Delete an entity 
     * @param entity
     * @return
     */
    public <T extends BaseEnt<T>> void deleteEntity (T ent) throws Exception {
    	T entX = em.find(ent.getEntClass(), ent.getId());
    	em.remove(entX);
	}
	
    /**
     * Process the entity, CrUD operations
     * @param ent
     * @return
     * @throws Exception
     */
    public <T extends BaseEnt<T>> T put (T ent) throws Exception {
    	
    	if (ent.isNew() && ent.isDelete()) {
    		return ent;
    	}
    	
    	if (ent.isDelete()) {
			deleteEntity(ent);
		}
		else if (ent.isValidId()) {
			merge(ent);
		}
		else if (ent.isNew()){
			Long id = ent.getId();
			ent.setId(null);
			ent = persist(ent);
			ent.setTempId(id);
		}
    	
    	return ent;
    }
    
    /**
     * Merge selected fields and return the <code>Entity</code>  
     * @param entity
     * @return
     */
    public <T extends BaseEnt<T>> T merge(T ent) throws Exception {
    	
    	T mergedEnt = find(ent); 
    
    	//Set relevant fields
    	String nullFields = ent.getNullBaseFields();
    	nullFields = nullFields != null? nullFields : ""; 
    	
    	if (!nullFields.contains("orgNr")) mergedEnt.setOrgNr(ent.getOrgNr());
    	if (!nullFields.contains("code")) mergedEnt.setCode(ent.getCode());
    	if (!nullFields.contains("descr")) mergedEnt.setDescr(ent.getDescr());
    	if (!nullFields.contains("active")) mergedEnt.setActive(ent.isActive());
    	
    	update(mergedEnt);
    	
    	return mergedEnt;
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
		addBaseListFields(ent, index, r, null);
	}
	
	/**
	 * Populate the standard list fields
	 * @param dto
	 * @param index
	 * @param result set
	 * @param table prefix
	 * @throws Exception
	 */
	static protected <T extends BaseEnt<T>> void addBaseListFields(T ent, Integer index, SqlResultSet r, String prefix) throws Exception {
		ent.setId(r.getLong(index, prefixField(prefix, "id"))) 
		   .setOrgNr(r.getInteger(index, prefixField(prefix, "org_nr"))) 
		   .setCode(r.getString(index, prefixField(prefix, "code")))
		   .setDescr(r.getString(index, prefixField(prefix, "descr")))
		   .setActive(r.getBoolean(index, prefixField(prefix, "active")))
		   .setUpdated(r.getTimestamp(index, prefixField(prefix, "updated")));
	}
	
	/**
	 * Format field with table prefix
	 * @param table prefix
	 * @param field
	 * @throws Exception
	 */
	static protected String prefixField(String prefix, String field) throws Exception {
		String p = prefix != null? prefix + "_" : "";
		return p + field;
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
