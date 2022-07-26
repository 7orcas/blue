package com.sevenorcas.blue.system.login;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseOrg;

/**
 * Part 1 of the login process 
 * If successful the client is sent the baseURL
 * The session id is used by the client to return to part 2, ie a valid key will create a new session 
 * 
 * Note this bean doesn't implement the BaseRest so the RestAuthorisation Interceptor is not called
 * 
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/login")
@Produces({"application/json"})
@Consumes({"application/json"})
public class LoginRest {
	
	private AppProperties appProperties = AppProperties.getInstance();

	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	@EJB
	private LoginCache cache;
	
	@POST
	@Path("web")
	public LoginJsonRes login(@Context HttpServletRequest httpRequest, LoginJsonReq req) {
		
		HttpSession s = httpRequest.getSession(true);
		
		//Test login attempt and if successful create a session object
		ClientSession u = new ClientSession()
				.setOrgNr(req.o)
				.setLang(req.l);
		
		LoginJsonRes j = new LoginJsonRes();
		j.s = s.getId();
		j.b = appProperties.get("BaseUrl");
		j.i = appProperties.get("WebLoginInitUrl");
		
		if (appProperties.is("DevelopmentMode")) {
			j.m = appProperties.get("WebClientMainUrl-CORS");	
		}
		else {
			j.m = appProperties.get("WebClientMainUrl");
		}
				
		cache.put(s.getId(), u);
		
System.out.println("login 1 org_nr=" + u.getOrgNr() +
		", userid=" + req.u +
		", pw=" + req.p + 
		", Session id=" + s.getId());		
		return j;
    }

	/**
	 * TODO delete
	 * @return
	 */
	@SkipAuthorisation
	@GET
	@Path("ping")
	public String login() {
		
System.out.println("login ping");		
		return "ok";
    }
	
	
}
