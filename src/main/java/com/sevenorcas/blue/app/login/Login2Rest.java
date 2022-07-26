package com.sevenorcas.blue.app.login;

import java.util.Hashtable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.sevenorcas.blue.system.login.LoginCache;
import com.sevenorcas.blue.system.login.ClientSession;

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

	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	@EJB
	private LoginCache cache;
	
	/**
	 * Initialise a successful web login
	 * @param httpRequest
	 * @return
	 */
	@SkipAuthorisation
	@GET
	@Path("init-web")
    public Login2JsonRes login2Web(@Context HttpServletRequest httpRequest,
    		@QueryParam("SessionID") String sid) {

		try {
			ClientSession clientSes = cache.getSessionAndRemove(sid);
			HttpSession httpSes = httpRequest.getSession(true);
			
			//Get user sessions or create a new list (if new login)
			Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)httpSes.getAttribute(CLIENT_SESSIONS);
			if (clientSessions == null) {
				clientSessions = new Hashtable<>();
				httpSes.setAttribute(CLIENT_SESSIONS, clientSessions);
			}
			
			//Store the new user session 
			Integer nextSes = clientSessions.size();
			clientSessions.put(nextSes, clientSes.setSessionNr(nextSes));
			
			
			//Return the base usn number for the client to use in all coms
			Login2JsonRes r = new Login2JsonRes();
			r.b = CLIENT_SESSION_NR + clientSes.getSessionNr() + "/";
			r.o = clientSes.getOrgNr();
			r.l = clientSes.getLang();
			r.u ="admin";
			return r;
		
		//Not a valid attempt
		} catch (Exception e) {
			//TODO log me
			return null;
			
		}

    }
	
}
