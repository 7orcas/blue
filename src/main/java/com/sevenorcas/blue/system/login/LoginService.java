package com.sevenorcas.blue.system.login;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

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
    public String login(
    		@Context HttpServletRequest httpRequest,
			@QueryParam("org") Integer nr,
			@QueryParam("u")  String user,
			@QueryParam("p")  String pass
    		) {
    	
        return "POST Hello " + user + ", and hello God and all!: ";
    }

	@GET
	@Path("")
    public String loginX() {
    	return "GET Hello God and all!: ";
    }
	
}
