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
import javax.sql.DataSource;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.lifecycle.DaoAroundInvoke;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;
import com.sevenorcas.blue.system.user.EntUser;

/**
* Base data transfer object (sometimes referred to as Dao - Data Access Object).
* Contains common attributes and methods used by transfer objects.
* 
* [Licence]
* Created Jul '22
* @author John Stewart
*/

@Interceptors({DaoAroundInvoke.class})
public class BaseTransfer extends BaseUtil implements BaseTransferI {
	
	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	/** standard fields in tables **/
	final static protected String UPDATED_FIELD = "updated";
	final static protected String UPDATED_USERID = "updated_userid";
	final static protected String BASE_LIST_FIELDS_SQL = " id,org_nr,code,descr,active," + UPDATED_FIELD + " ";
	
	/** standard fields in tables **/
	final static protected String BASE_ENTITY_FIELDS_SQL = " id,org_nr,code,descr,encoded,encoded_flag,active," + UPDATED_FIELD + " ";
	
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
    public <T extends BaseEntity<T>> T find (T ent) throws Exception {
    	return em.find(ent.entClass(), ent.getId());
	}
	
	/**
     * Persist the entity 
     * @param entity
     * @param user id causing the action
     * @return
     */
    public <T extends BaseEntity<T>> T persist (T ent, Long userId) throws Exception {
    	LocalDateTime d = LocalDateTime.now();
    	ent.setUpdated(Timestamp.valueOf(d));
    	em.persist(ent);
    	em.flush();
    	return ent;
	}
	
    /**
     * Update the entities <code>update</code> fields 
     * @param entity
     * @param user id causing the action
     * @return
     */
    public <T extends BaseEntity<T>> void updateTimestampUserid (T ent, Long userId) throws Exception {
    	LocalDateTime d = LocalDateTime.now();
    	ent.setUpdated(Timestamp.valueOf(d))
    	   .setUpdatedUserId(userId);
	}
	
    /**
     * Delete an entity 
     * @param entity
     * @return
     */
    public <T extends BaseEntity<T>> void deleteEntity (T ent) throws Exception {
    	T entX = em.find(ent.entClass(), ent.getId());
    	em.remove(entX);
	}
	
    /**
     * Process the entity, CrUD operations
     * @param entity
     * @param entities configuration
     * @param call object
     * @return
     * @throws Exception
     */
    public <T extends BaseEntity<T>> T put (T ent, EntityConfig config, CallObject callObj) throws Exception {
    	try {
	    	if (ent.isNew() && ent.isDelete()) {
	    		return ent;
	    	}
	    	
	    	if (ent.isDelete()) {
				deleteEntity(ent);
			}
			else if (ent.isValidId()) {
				merge(ent, config, callObj);
			}
			else if (ent.isNew()){
				Long id = ent.getId();
				ent.setId(null);
				nullBaseFields(ent, config);
				ent = persist(ent, callObj.getUserId());
				ent.setTempId(id);
			}
	    	
	    	return ent;
	    	
    	} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
    }
    
    /**
     * Merge selected fields and return the <code>Entity</code>  
     * @param entity
     * @param entities configuration
     * @param call object
     * @return
     */
    public <T extends BaseEntity<T>> T merge(T ent, EntityConfig config, CallObject callObj) throws Exception {
    	try {
	    	T mergedEnt = find(ent); 
	    	if (!config.isUnused("orgNr")) mergedEnt.setOrgNr(ent.getOrgNr());
	    	if (!config.isUnused("code")) mergedEnt.setCode(ent.getCode());
	    	if (!config.isUnused("descr")) mergedEnt.setDescr(ent.getDescr());
	    	if (!config.isUnused("active")) mergedEnt.setActive(ent.isActive());
	    	updateTimestampUserid(mergedEnt, callObj.getUserId());
	    	return mergedEnt;
    	
    	} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
	}
    
    /**
     * If the entities time-stamp is different to the database record then it's already been changed
     * @param entity
     * @param entity configuration
     * @param object to load errors into
     * @throws Exception
     */
    public <T extends BaseEntity<T>> void compareTimeStamp(T ent, EntityConfig config, ValidationErrors errors) throws Exception {
    	if (ent.isNew()){
    		return;
    	}
    	
    	try {
	    	String sql = "SELECT t." + UPDATED_FIELD + ", t." + UPDATED_USERID + ", u." + EntUser.USERID + " "
				+ "FROM " + config.tableName + " as t "
					+ "LEFT JOIN " + tableName(EntUser.class, " AS u ON t." + UPDATED_USERID + " = u.id ")
				+ "WHERE t.id = " + ent.getId();	
	    	
	    	SqlResultSet r = SqlExecute.executeQuery(null, sql, log);
	    	
	    	if (r.size() != 1) {
	    		errors.add(new ValidationError(VAL_ERROR_NO_RECORD)
	    			.setEntityId(ent.getId())
	    			.setCode(ent.getCode())	
	    			);
	    		return;
	    	}
	    	
	    	Timestamp updated = r.getTimestamp(0, UPDATED_FIELD);
	    	
	    	if (updated.compareTo(ent.getUpdated()) != 0) {
	    		errors.add(new ValidationError(VAL_ERROR_TS_DIFF)
	    			.setEntityId(ent.getId())
	    			.setUpdatedUserId(r.getLong(0, UPDATED_USERID))
	    			.setUpdatedUser(r.getString(0, EntUser.USERID))
	    			.setUpdated(updated)
	    			.setCode(ent.getCode())	
	    			);
	    		return;	
	    	}
    	} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
		
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
	static protected <T extends BaseEntity<T>> void addBaseListFields(T ent, Integer index, SqlResultSet r) throws Exception {
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
	static protected <T extends BaseEntity<T>> void addBaseListFields(T ent, Integer index, SqlResultSet r, String prefix) throws Exception {
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
	
}
