package com.sevenorcas.blue.system.login;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

/**
 * 
 * [Licence]
 * @author John Stewart
 */


@Stateless
@Path("/login")
@Produces({"application/json"})
@Consumes({"application/json"})
public class LoginService {

	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	@POST
	@Path("")
	public String login(JSONObject userDetails) {
    	return "POST Hello , and hello God and all!: " + userDetails.getString("u");
    }

	@GET
	@Path("")
    public String loginX() {
    	return "GET Hello God and all!: ";
    }
	
}
