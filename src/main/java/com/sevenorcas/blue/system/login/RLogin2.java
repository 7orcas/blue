package com.sevenorcas.blue.system.login;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.field.Encode;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.login.ent.CacheLogin_Dev;
import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.login.ent.JResLogin2;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * Part 2 of the login process
 * Once a user is logged in, then initialise their session
 * 
 * Development Note: If using React client on 3000, a new session will be created here.
 * Therefore transient attributes stored in the initial login http session will be erased.
 *  
 * Created July '22
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/login2")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RLogin2 extends BaseRest {

	@EJB
	private SLoginI service;
	
	//NOT TO BE USED IN PRODUCTION
	@EJB
	private CacheLogin_Dev cacheDev;

	
	/**
	 * Initialise a successful web login
	 * @param httpRequest
	 * @return
	 */
	@SkipAuthorisation
	@GET
	@Path("init-web")
    public JsonRes login2Web(@Context HttpServletRequest httpRequest,
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam("SessionID") String sid) {

		try {
			HttpSession httpSes = httpRequest.getSession(true);
			
			//Get user sessions or create a new list (if new login)
			ClientSession cs = callObj.getClientSession();
			EntUser user = cs.getUser();
			
			//Return the base usn number for the client to use in all coms
			JResLogin2 login = new JResLogin2();
			login.username = user.getUserName();
			login.orgNr = cs.getOrgNr();
			login.lang = cs.getLang();
			login.permissions = user.toJsonUrlPermission(callObj.getOrg());
			login.changePW = cs.getUser().isChangePassword();

			if (user.isDevAdmin()) {
				login.devAdmin = true;
			}
						
			Encode encode = cs.getUser().encoder();
			login.theme = encode.getInteger("theme");
			
			if (appProperties.is("DevelopmentMode")) {
				cacheDev.put(httpRequest.getRemoteHost(), httpSes);
			}
			
			return new JsonRes().setData(login);
		
		//Not a valid attempt
		} catch (Exception e) {
			//TODO log me
			return null;
			
		}

    }
	
}
