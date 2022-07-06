package com.sevenorcas.blue.app.home;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

/**
 * 
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/home")
@Produces({"application/json"})
@Consumes({"application/json"})
public class Home {

	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	@GET
	@Path("lang")
    public String languagePack() {
		JSONObject j = new JSONObject("{key: 'x', value: 'lang pack'}");
    	return j.toString();
    }
	
}
