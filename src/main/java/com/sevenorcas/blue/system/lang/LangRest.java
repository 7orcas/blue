package com.sevenorcas.blue.system.lang;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
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

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB
	private LangSrv service;
	
	@GET
	@Path("pack")
    public JsonRes pack(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		log.info("pack for lang=" + callObj.getLang());	
		return service.langPackageJson(callObj, null, callObj.getLang());
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
	@Path("languages")
    public JsonRes languages(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		log.info("languages");
		return service.languagesJson(callObj);		
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
    public JsonRes langPack(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("lang") String lang) throws Exception {
		log.info("loging-pack for lang=" + lang);
		return service.langPackageJson(callObj, "login", lang);
    }
	
	/**
	 * Return the complete label entity(s)
	 * A language label may have multiple orgs
	 * 
	 * @param callObj
	 * @param label
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("label")
    public JsonRes getLabel(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("label") String label) throws Exception {
		return new JsonRes().setData(service.getLabelJson(callObj, label));
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
