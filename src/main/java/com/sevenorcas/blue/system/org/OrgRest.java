package com.sevenorcas.blue.system.org;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
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
	@Path("org-list")
    public JsonRes orgList(
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
	@Path("")
    public JsonRes getOrg(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("id") Long orgId) throws Exception {
		if (orgId == null) {
			return new JsonRes().setError("inv-id", "Invalid org id");
		}
		return service.getOrgJson(callObj, orgId);
    }
}
