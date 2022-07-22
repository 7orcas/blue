package com.sevenorcas.blue.system.org;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
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
    public List<OrgJsonRes> orgList(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.orgListJson(callObj, new SqlParm().setActiveOnly());
    }
	
}
