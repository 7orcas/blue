package com.sevenorcas.blue.system.login;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;
import com.sevenorcas.blue.system.user.UserEnt;

/**
* Created July '22
* 
* Data access methods to the Login Module
* TODO Expand Module Description
* 
* [Licence]
* @author John Stewart
*/

@Stateless
public class LoginDao extends BaseDao {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
		
	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	/**
	 * Return the <code>UserEnt</code> entity
	 * @param userid
	 * @return
	 */
	public UserEnt getUser (String userid) {
		try {
			TypedQuery<UserEnt> tq = em.createQuery("FROM com.sevenorcas.blue.system.user.UserEnt WHERE xxx = :userid", UserEnt.class);
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
				+ "FROM cntrl.zzz AS u "
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
				+ "FROM cntrl.role AS r "
				+ "JOIN cntrl.zzz_role AS j ON j.id_role = r.id "
				+ "WHERE j.id_zzz = " + userId;
		
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
