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
	
	public UserEnt getUser (String userid, String pw, Integer org) {
		
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
			if (!user.containsOrg(org)) {
				user.setInvalidMessage("invorg");
				return user;	
			}

			user.setValid()
			    .setAttempts(0);
		}
		
		return user;
	}

	
}
