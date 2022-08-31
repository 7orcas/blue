package com.sevenorcas.blue.app.login;

import java.util.Hashtable;

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
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.login.ClientSession;
import com.sevenorcas.blue.system.login.LoginCacheDEL;
import com.sevenorcas.blue.system.login.LoginCacheDev;
import com.sevenorcas.blue.system.login.LoginSrv;

/**
 * 
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/login2")
@Produces({"application/json"})
@Consumes({"application/json"})
public class Login2Rest extends BaseRest {

	@EJB
	private LoginSrv service;
	
//	@EJB
//	private LoginCache cache;
	
	//NOT TO BE USED IN PRODUCTION
	@EJB
	private LoginCacheDev cacheDev;

	
	/**
	 * Initialise a successful web login
	 * @param httpRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
//	@SkipAuthorisation
	@GET
	@Path("init-web")
    public JsonRes login2Web(@Context HttpServletRequest httpRequest,
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam("SessionID") String sid) {

		try {
//			ClientSession clientSes = cache.getSessionAndRemove(sid);
			HttpSession httpSes = httpRequest.getSession(true);
			
			//Get user sessions or create a new list (if new login)
			Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)httpSes.getAttribute(CLIENT_SESSIONS);
//			if (clientSessions == null) {
//				clientSessions = new Hashtable<>();
//				httpSes.setAttribute(CLIENT_SESSIONS, clientSessions);
//			}
//			
//			
//			//Store the new user session 
//			Integer nextSes = clientSessions.size();
//			clientSessions.put(nextSes, clientSes.setSessionNr(nextSes));
				
			ClientSession cs = callObj.getClientSession();
			
			//Get User configuration, eg roles
			String userid = service.getUserid(cs.getUserId());
			String roles = service.getUserRolesAsString(cs.getUserId());
			
			//Return the base usn number for the client to use in all coms
			Login2JsonRes r = new Login2JsonRes();
			r.userid = userid; 
			r.clientUrl = cs.getUrlSegment();
			r.orgNr = cs.getOrgNr();
			r.lang = cs.getLang();
			r.roles = roles;
			
System.out.println("NEW CLIENT sid=" + httpSes.getId() + "  passed in sid=" + sid  + "  clientNr=" + cs.getSessionNr());				

			if (appProperties.is("DevelopmentMode")) {
				cacheDev.put(httpRequest.getRemoteHost(), httpSes);
			}
			
			return new JsonRes().setData(r);
		
		//Not a valid attempt
		} catch (Exception e) {
			//TODO log me
			return null;
			
		}

    }
	
}
