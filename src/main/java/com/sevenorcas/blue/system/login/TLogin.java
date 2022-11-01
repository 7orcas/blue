package com.sevenorcas.blue.system.login;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;
import com.sevenorcas.blue.system.user.ent.EntUser;
import com.sevenorcas.blue.system.user.ent.EntUserRole;

/**
* Data access methods to the Login Module
* 
* Created July '22
* [Licence]
* @author John Stewart
*/
@Stateless
public class TLogin extends BaseTransfer implements TLoginI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
		
	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	/**
	 * Return the <code>UserEnt</code> entity
	 * @param userid
	 * @return
	 */
	public EntUser getUser (String userid) {
		try {
			TypedQuery<EntUser> tq = em.createQuery("FROM " + EntUser.class.getCanonicalName() + " WHERE xxx = :userid", EntUser.class);
			return tq.setParameter("userid", userid).getSingleResult();
		} catch (Exception e) {
			log.error("userid=" + userid + " error:" + e);
			return null;
		}
	}
	
	/**
	 * Return userid for the passed in user id
	 * @param userId
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public String getUserid (Long userId,
		SqlParm parms) throws Exception {
	
		parms = validateParms(parms);
	
		String sql;
		sql = "SELECT u.xxx " 
				+ "FROM " + tableName(EntUser.class, " AS u ")
				+ "WHERE u.id = " + userId;
		
		if (parms.isActiveOnly()) {
			sql += "AND u.active = TRUE"; 	
		}

		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
	
		// Extract data from result set
		if (r.size() > 0) {
			return r.getString(0, "xxx");
		}
		
		return null;
	}

	/**
	 * Return list of roles for the passed in user id
	 * @param userId
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public List<String> getUserRoles (Long userId,
		SqlParm parms) throws Exception {
	
		parms = validateParms(parms);
	
		String sql;
		sql = "SELECT r.code " 
				+ "FROM " + tableName(EntRole.class, " AS r ")
				+ "JOIN " + tableName(EntUserRole.class, " AS j ON j.role_id = r.id ")
				+ "WHERE j.zzz_id = " + userId;
		
		if (parms.isActiveOnly()) {
			sql += "AND r.active = TRUE"; 	
		}

		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<String> list = new ArrayList<>();
	
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			list.add(r.getString(i, "code"));
		}
		
		return list;
	}

	
	
	
}
