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
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
 * TODO Module Description
 * 
 * [Licence]
 * Created July '22
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
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("load") String loadFlag) throws Exception {
		log.info("pack for lang=" + callObj.getLang());	
		
//if (loadFlag != null && loadFlag.equals("All")) return new JsonRes().setError("TEST ERROR");

		return service.langPackageJson(callObj.getOrg().getNr(), null, callObj.getLang(), loadFlag);
    }
	
	@GET
	@Path("pack/excel")
    public Response packExcel(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("load") String loadFlag) throws Exception {
		
		return service.langPackageExcel(callObj.getOrg().getNr(), null, callObj.getLang(), loadFlag);
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
		Integer org = 0;
		if (callObj != null && callObj.getOrg() != null) {
			org = callObj.getOrg().getNr();
		}
		return service.langPackageJson(org, "login", lang, null);
    }
	
	/**
	 * Return the complete label entity(s)
	 * A language label may have multiple orgs
	 * 
	 * @param callObj
	 * @param langKey
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("label")
    public JsonRes getLabel(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("langKey") String langKey) throws Exception {
		if (langKey == null) {
			return new JsonRes().setError("Invalid Label");
		}
		return new JsonRes().setData(service.getLabelJson(callObj, langKey));
    }
	
	
	/**
	 * Update and Persist the label list
	 * @param callObj
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("label")
    public JsonRes postLabel(
    		@QueryParam ("co") CallObject callObj, 
    		List<LangLabelJson> list)  throws Exception {
		
		if (list == null) {
			return new JsonRes().setError("Invalid Label");
		}
		service.updateLabel(callObj, list);
		return new JsonRes();
    }
	
	/**
	 * Update and Persist the uploaded label list
	 * @param callObj
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("upload")
	@Consumes({"multipart/form-data"})
	public JsonRes uploadLabels(
    		@QueryParam ("co") CallObject callObj, 
    		@QueryParam ("filename") String filename)  throws Exception {
System.out.println("filename=" + filename);
		
		return new JsonRes().setData(filename);
    }
	
	
}
