package com.sevenorcas.blue.system.conf;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* REST interface for entity configuration
* 
* Created 14.10.22
* [Licence]
* @author John Stewart
*/

@Stateless
@Path("/config")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RestConfig extends BaseRest {

	@EJB
	private SrvConfig service;

	
	/**
	 * Return an entity's configuration
	 * 
	 * @param callObj
	 * @param org id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("entity")
    public JsonRes getConfig(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("entity") String entity) throws Exception {
		if (entity == null) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR, "Invalid entity");
		}
		return service.getConfigJson(callObj, entity);
    }
		
}
