package com.sevenorcas.blue.system.lang;

import java.util.ArrayList;
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
 * Created July '22
 * TODO Module Description
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
    public List<LabelJsonRes> langPackage(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("pack") String pack) throws Exception {
		
System.out.println("LangRest lang=" + callObj.getLang());		
		
		return service.langPackageJson(callObj, pack, callObj.getLang());
    }
	
	/**
	 * This end-point is excluded from servlet filter check
	 * Return supported languages
	 * 
	 * @param callObj
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	@SkipAuthorisation
	@GET
	@Path("login-lang")
    public List<LangJsonRes> langLogin(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		
		List<LangJsonRes> l = new ArrayList<>();
		LangJsonRes r = new LangJsonRes();
		r._c = "en";
		r.l = "English";
		r.d = true;
		l.add(r);
		r = new LangJsonRes();
		r._c = "de";
		r.l = "Deutsch";
		l.add(r);
		return l;
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
    public List<LabelJsonRes> langPackageLogin(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("pack") String pack,
    		@QueryParam ("lang") String lang) throws Exception {
		
		return service.langPackageJson(callObj, "login", lang);
    }
	
	
	@POST
	@Path("update")
    public LabelJsonRes langUpdate(
    		@QueryParam ("co") CallObject callObj, 
    		LangJsonReq key) {
		
		LabelJsonRes r = new LabelJsonRes();
				
		
    	return r;
    }
	
	
}
