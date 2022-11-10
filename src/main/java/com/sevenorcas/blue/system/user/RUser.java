package com.sevenorcas.blue.system.user;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.ent.EntUser;
import com.sevenorcas.blue.system.user.ent.JsonUserConfig;

/**
* REST interface for Users
* 
* Created 01.11.22
* [Licence]
* @author John Stewart
*/

@Stateless
@Path("/user")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RUser extends BaseRest {

	@EJB private SUserI service;
		
	/**
	 * Return users list
	 * 
	 * @param callObj
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("list")
    public JsonRes list(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.userListJson(callObj, new SqlParm());
    }
	
	/**
	 * Return a user entity
	 * 
	 * @param callObj
	 * @param entity id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("get")
    public JsonRes get(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("id") Long id) throws Exception {
		if (id == null) {
			return new JsonRes().setError("inv-id", "Invalid org id");
		}
		return service.getUserJson(callObj, id);
    }
	
	/**
	 * Update and Persist the list
	 * @param callObj
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("post")
    public JsonRes post(
    		@QueryParam ("co") CallObject callObj, 
    		List<EntUser> list)  throws Exception {
		
		if (list == null) {
			return new JsonRes().setError("Invalid post");
		}
		return service.putUsers(callObj, list);
    }

	/**
	 * Update the user configuration
	 * @param callObj
	 * @param config and value
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("put/config")
    public JsonRes putConfig(
    		@QueryParam ("co") CallObject callObj, 
    		JsonUserConfig config)  throws Exception {
		
System.out.println(config.config + "=" + config.value);		
		service.putConfig(callObj, config.config, config.value);
		return new JsonRes().setData("ok");
    }
	
	/**
	 * Return a new user object (uncommitted)
	 * 
	 * @param callObj
	 * @param org id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("new")
    public JsonRes newRole(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.newUserJson(callObj);
    }
	
	/**
	 * Export users to excel file
	 * @param callObj
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("excel")
    public Response excel(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.excelExport(callObj);
    }
	
}
