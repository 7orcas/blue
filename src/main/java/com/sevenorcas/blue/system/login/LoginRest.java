package com.sevenorcas.blue.system.login;

import java.util.Random;

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
import com.sevenorcas.blue.system.org.BaseOrg;

/**
 * Part 1 of the login process 
 * If successful the client is sent the mainURL
 * The session id is used by the client to return to part 2, ie a valid key will create a new session (this step is for CORS) 
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
		BaseOrg org = new BaseOrg("");
		Random rand = new Random();
		org.setOrg(rand.nextInt(5000));
		s.setAttribute("org_nr", org.getOrg());
				
		LoginJsonRes j = new LoginJsonRes();
		j.SessionID = s.getId();
		j.WebLoginInitUrl = appProperties.get("WebLoginInitUrl");
		
		if (appProperties.is("DevelopmentMode")) {
			j.WebClientMainUrl = appProperties.get("WebClientMainUrl-CORS");	
		}
		else {
			j.WebClientMainUrl = appProperties.get("WebClientMainUrl");
		}
				
		cache.put(s.getId(), org.getOrg());
		
System.out.println("login 1 org_nr=" + org.getOrg() + ", Session id=" + s.getId());		
		return j;
    }

	@GET
	@Path("ping")
	public String login() {
		
System.out.println("login ping");		
		return "ok";
    }
	
	
}
