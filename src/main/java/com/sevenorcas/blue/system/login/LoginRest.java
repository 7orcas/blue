package com.sevenorcas.blue.system.login;

import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
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
	public LoginJson login(@Context HttpServletRequest httpRequest, LogonJsonReq req) {
		
		HttpSession s = httpRequest.getSession(true);
		BaseOrg org = new BaseOrg();
		Random rand = new Random();
		org.setOrg(rand.nextInt(5000));
		s.setAttribute("blur.org", org);
				
		LoginJson j = new LoginJson();
		j.WebClientMainUrl = appProperties.get("WebClientMainUrl");
		j.SessionID = s.getId();
		
		cache.put(s.getId(), "t");
		
		return j;
    }

	
}
