package com.sevenorcas.blue.system.login;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.org.SOrgI;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
* Initial Login module
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

@Stateless
public class SLogin extends BaseService implements SLoginI {

	@EJB private TLoginI dao;
	@EJB private SOrgI orgService;
	
	/**
	 * Test the given parameters to return a valid user object (assuming they are valid).
	 * To be valid the user must:
	 * - supply a valid userid and password
	 * - not exceed the maximum login attempts (once exceeded the user is effectively shut out)
	 * - they are allowed to access the passed in org (unless the passed in org is null, then the user default is used)
	 *  
	 * @param userid
	 * @param pw
	 * @param orgNr
	 * @return User object with valid flag (or null if no valid user id) 
	 */
	public EntUser getUserAndValidate (String userid, String pw, Integer orgNr) {
		
		EntUser user = dao.getUser (userid); 
		
		//Validate user
		if (user != null) {
			user.incrementAttempts();
	
			//Determine the orgNr for this login
			if (orgNr == null) {
				user.setDefaultOrg();
			}
			else if (user.containsOrg(orgNr)){
				user.setOrgNrLogin(orgNr);
			}
			
			if (!user.isOrgNrLoginValid()) {
				user.setInvalidMessage("invorg");
				return user;	
			}
			
			//Test attempts has not exceed maximum
			try {
				EntOrg org = orgService.getOrgCache(user.getOrgNrLogin());
				if (org.isMaxLoginAttempts(user.getAttempts())) {
					user.setInvalidMessage("maxatt");
					return user;
				}
			} catch (Exception x) {}
			
			if (!user.getPassword().equals(pw)) {
				user.setInvalidMessage("invpw");
				return user;	
			}
			
			if (!user.isActive()) {
				user.setInvalidMessage("inarec");
				return user;	
			}
			
			user.setValidUser()
			    .setAttempts(0)
			    .setLastlogin(Timestamp.valueOf(LocalDateTime.now()));
		}
		
		return user;
	}

	/**
	 * Detached the entity from JPA
	 * @param EntUser
	 * @param ent
	 */
	public void detach (EntUser ent) throws Exception {
		dao.detach(ent);
	}
	
	/**
	 * Return userid for the passed in user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getUserid (Long userId) throws Exception {
		return dao.getUserid(userId, new SqlParm());
	}
	
	
	/**
	 * Return list of roles for the passed in user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<String> getUserRoles (Long userId) throws Exception {
		return dao.getUserRoles(userId, new SqlParm().setActiveOnly());
	}
	
	/**
	 * Return comma separated list of roles for the passed in user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getUserRolesAsString (Long userId)  throws Exception {
		List<String> roles = getUserRoles(userId);
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<roles.size();i++) {
			sb.append((sb.length()>0?",":"") + roles.get(i));
		}
		return sb.toString();
	}
	
	/**
     * Persist the user entity 
     * @param entity
     * @return
     */
    public EntUser persistAfterLogin (EntUser ent) throws Exception {
    	return dao.persistAfterLogin(ent);
	}
}
