package com.sevenorcas.blue.system.lang;

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

import com.sevenorcas.blue.system.base.BaseOrg;
import com.sevenorcas.blue.system.java.CallObject;
import com.sevenorcas.blue.system.rest.BaseRest;

/**
 * 
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/lang")
@Produces({"application/json"})
@Consumes({"application/json"})
public class LangRest extends BaseRest {

	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	@GET
	@Path("value")
    public LangJsonRes langValue(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("key") String key,
    		@QueryParam ("id") Long id) {
		
		LangJsonRes r = new LangJsonRes();
		
    	return r;
    }
	
	@GET
	@Path("pack")
    public LangJsonRes langPackage(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("key") String key,
    		@QueryParam ("id") Long id) {
		
		LangJsonRes r = new LangJsonRes();
			
		
    	return r;
    }

	
	@POST
	@Path("update")
    public LangJsonRes langUpdate(
    		@QueryParam ("co") CallObject callObj, 
    		LangJsonReq key) {
		
		LangJsonRes r = new LangJsonRes();
				
		
    	return r;
    }
	
	
}
