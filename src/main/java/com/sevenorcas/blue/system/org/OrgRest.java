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

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.OrgEnt;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Created July '22
* 
* REST interface for Organisation Module
* 
* [Licence]
* @author John Stewart
*/

@Stateless
@Path("/org")
@Produces({"application/json"})
@Consumes({"application/json"})
public class OrgRest extends BaseRest {

	@EJB
	private OrgSrv service;
	
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
    		List<OrgEnt> list)  throws Exception {
		
		if (list == null) {
			return new JsonRes().setError("Invalid post");
		}
		service.putOrgs(callObj, list);
		return list(callObj);
    }
	
}
