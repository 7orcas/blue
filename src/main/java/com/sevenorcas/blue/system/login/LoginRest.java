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
import com.sevenorcas.blue.system.cache.Manager;
import com.sevenorcas.blue.system.org.BaseOrg;




/**
 * @ToDo
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
	private Manager m;
	
	@POST
	@Path("web")
	public LoginJson login(@Context HttpServletRequest httpRequest, LogonJsonReq req) {
    	//return (new JSONObject ("{\"WebClientMainUrl\":\"" + appProperties.get("WebClientMainUrl") + "\"}")).toString();
		//return "{\"WebClientMainUrl\":\"" + appProperties.get("WebClientMainUrl") + "\"}";
		
		// create session if does not already exist
		HttpSession s = httpRequest.getSession(true);
		BaseOrg org = new BaseOrg();
		Random rand = new Random();
		org.setOrg(rand.nextInt(5000));
		s.setAttribute("blur.org", org);
System.out.println(">>>LoginService.login User=" + req.u + ", pw=" + req.p + ", Session org=" + org.getOrg());		
		
		LoginJson j = new LoginJson();
		j.WebClientMainUrl = appProperties.get("WebClientMainUrl");
		j.SessionID = s.getId();
		
//		Manager m = new Manager();
		m.setKey("login");
		m.setValue(s.getId());
		m.save();
		
		return j;
    }

	
}
