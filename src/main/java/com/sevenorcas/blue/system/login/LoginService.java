package com.sevenorcas.blue.system.login;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import com.sevenorcas.blue.system.AppProperties;


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
public class LoginService {
	
	private AppProperties appProperties = AppProperties.getInstance();

	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	@POST
	@Path("")
	public String login(JSONObject userDetails) {
    	//return (new JSONObject ("{\"WebClientMainUrl\":\"" + appProperties.get("WebClientMainUrl") + "\"}")).toString();
		//return "{\"WebClientMainUrl\":\"" + appProperties.get("WebClientMainUrl") + "\"}";
		return appProperties.get("WebClientMainUrl");
    }

	@GET
	@Path("")
    public String loginX() {
    	return "GET Hello God and all!: ";
    }
	
}
