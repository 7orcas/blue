package com.sevenorcas.blue.app.login;

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

import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.login.LoginCache;

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
	@GET
	@Path("init-web")
    public Login2JsonRes login2Web(@Context HttpServletRequest httpRequest,
    		@QueryParam("SessionID") String sid) {

		HttpSession ses = httpRequest.getSession(false);
		
		if (ses == null && sid != null) {
			Integer orgNr = cache.getOrgAndRemove(sid);
			if (orgNr != null) {
				ses = httpRequest.getSession(true);
				ses.setAttribute("org_nr", orgNr);
System.out.println("login2Web org_nr=" + orgNr  + ", NEW session_id=" + ses.getId());
			}
		}
		
		if (ses != null) {
			Login2JsonRes r = new Login2JsonRes();
			r.org = (Integer)ses.getAttribute("org_nr");
			r.lang = "en";
			r.ugroup ="admin";
System.out.println("login2Web org_nr=" + r.org + ", Session id=" + ses.getId());
			return r;
		}

System.out.println("login2Web -> no valid session");
    	return null;
    }
	
}
