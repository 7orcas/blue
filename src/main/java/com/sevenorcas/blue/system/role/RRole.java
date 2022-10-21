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
import com.sevenorcas.blue.system.conf.SConfig;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* REST interface for Roles
* 
* Created 06.10.22
* [Licence]
* @author John Stewart
*/

@Stateless
@Path("/role")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RRole extends BaseRest {

	@EJB private SRole service;
	@EJB private SConfig serviceConf;
	
	/**
	 * Return roles list
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
		return service.roleListJson(callObj, new SqlParm());
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
    		List<EntRole> list)  throws Exception {
		
		if (list == null) {
			return new JsonRes().setError("Invalid post");
		}
		return service.putRoles(callObj, list);
    }

	/**
	 * Return an entity's configuration
	 * 
	 * @param callObj
	 * @param org id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("config")
    public JsonRes getConfig(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("entity") String entity) throws Exception {
		if (entity == null) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR, "Invalid entity");
		}
		return serviceConf.getConfigJson(callObj, entity);
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
    public JsonRes newRole(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.newRoleJson(callObj);
    }
	
	/**
	 * Export roles to excel file
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
