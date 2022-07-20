package com.sevenorcas.blue.system.lang;

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
import com.sevenorcas.blue.system.lifecycle.CallObject;

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

	@EJB
	private LangSrv service;
	
	@GET
	@Path("pack")
    public List<LangJsonRes> langPackage(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("pack") String pack) throws Exception {
		
		return service.langPackageJson(callObj, pack, callObj.getLang());
    }
	
	/**
	 * This end-point is excluded from servlet filter check
	 * 
	 * @param callObj
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	@SkipAuthorisation
	@GET
	@Path("login-pack")
    public List<LangJsonRes> langPackageLogin(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("pack") String pack,
    		@QueryParam ("lang") String lang) throws Exception {
		
		return service.langPackageJson(callObj, "login", lang);
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
