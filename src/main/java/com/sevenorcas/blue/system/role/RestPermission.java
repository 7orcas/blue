package com.sevenorcas.blue.system.role;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* REST interface for Permissions
* 
* Created 05.10.22
* [Licence]
* @author John Stewart
*/

@Stateless
@Path("/permission")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RestPermission extends BaseRest {

	@EJB
	private SrvPermission service;

	
	/**
	 * Return permissions list
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
		return service.permissonListJson(callObj, new SqlParm());
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
    		List<EntPermission> list)  throws Exception {
		
		if (list == null) {
			return new JsonRes().setError("Invalid post");
		}
		return service.putPermissions(callObj, list);
    }
	
	/**
	 * Return a new permission object (uncommitted)
	 * 
	 * @param callObj
	 * @param org id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("new")
    public JsonRes newPermission(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.newPermissionJson(callObj);
    }
	
	/**
	 * Export permissions to excel file
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
