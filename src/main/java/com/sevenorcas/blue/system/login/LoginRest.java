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
import com.sevenorcas.blue.system.base.JsonRes;

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
	
	@EJB
	private LoginSrv service;
	
	@POST
	@Path("web")
	public JsonRes loginWeb(@Context HttpServletRequest httpRequest, LoginJsonReq req) {
		
		UserEnt user = service.getUserAndValidate(req.u, req.p, req.o);
		
		//Invalid user id
		if (user == null) {
			return new JsonRes().setError("invuid");			
		}
		//Login can't be validated
		if (!user.isValid()) {
			return new JsonRes().setError(user.getInvalidMessage());	
		}
		
				
		//Success! Set parameters for client to open web gui
		HttpSession s = httpRequest.getSession(true);
		
		ClientSession u = new ClientSession()
				.setOrgNr(user.getOrg())
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
		return new JsonRes().setData(j);
    }

	
	
}
