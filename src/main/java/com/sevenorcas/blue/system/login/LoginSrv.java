package com.sevenorcas.blue.system.login;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseSrv;

/**
* Created July '22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Stateless
public class LoginSrv extends BaseSrv {

	@EJB
	private LoginDao dao;
	
	/**
	 * Test the given parameters to return a valid user object (assuming they are valid).
	 * To be valid the user must:
	 * - supply a valid userid and password
	 * - not exceed the maximum login attempts (once exceeded the user is effectively shut out)
	 * - they are allowed to access the passed in org (unless the passed in org is null, then the user default is used)
	 *  
	 * @param userid
	 * @param pw
	 * @param org
	 * @return User object with valid flag (or null if no valid user id) 
	 */
	public UserEnt getUserAndValidate (String userid, String pw, Integer org) {
		
		UserEnt user = dao.getUser (userid); 
		
		//Validate user
		if (user != null) {
			user.incrementAttempts();
			
			if (user.getAttempts() > MAX_LOGIN_ATTEMPTS) {
				user.setInvalidMessage("maxatt");
				return user;
			}
			if (!user.getPassword().equals(pw)) {
				user.setInvalidMessage("invpw");
				return user;	
			}
			
			if (org == null) {
				user.setDefaultOrg();
			}
			else {
				user.containsOrg(org);
			}

			if (user.getOrg() == null) {
				user.setInvalidMessage("invorg");
				return user;	
			}
			
			user.setValid()
			    .setAttempts(0);
		}
		
		return user;
	}

	
}
