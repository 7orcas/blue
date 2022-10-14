package com.sevenorcas.blue.system.org;

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

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.SrvConfig;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* REST interface for Organisation Module
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

@Stateless
@Path("/org")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RestOrg extends BaseRest {

	@EJB
	private SrvOrg service;

	
	/**
	 * Return active organisation list
	 * This end-point is excluded from servlet filter check
	 * 
	 * @param callObj
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	@SkipAuthorisation
	@GET
	@Path("list")
    public JsonRes list(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.orgListJson(callObj, new SqlParm());
    }
	
	
	/**
	 * Return an organisation entity
	 * 
	 * @param callObj
	 * @param org id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("get")
    public JsonRes getOrg(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("id") Long orgId) throws Exception {
		if (orgId == null) {
			return new JsonRes().setError("inv-id", "Invalid org id");
		}
		return service.getOrgJson(callObj, orgId);
    }
	
	/**
	 * Update and Persist the label list
	 * @param callObj
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("post")
    public JsonRes post(
    		@QueryParam ("co") CallObject callObj, 
    		List<EntOrg> list)  throws Exception {
		
		if (list == null) {
			return new JsonRes().setError("Invalid post");
		}
		return service.putOrgs(callObj, list);
    }
	
	/**
	 * Return a new organisation object (uncommitted)
	 * 
	 * @param callObj
	 * @param org id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("new")
    public JsonRes newOrg(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.newOrgJson(callObj);
    }
	
	/**
	 * Export orgs to excel file
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
