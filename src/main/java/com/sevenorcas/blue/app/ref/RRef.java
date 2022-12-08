package com.sevenorcas.blue.app.ref;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.app.ref.ent.EntCountry;
import com.sevenorcas.blue.app.ref.ent.EntCurrency;
import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.ref.SRefI;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* REST interface for Reference data
* Simple reference data is managed like a generic  
* 
* Created 02.12.22
* [Licence]
* @author John Stewart
*/

@Stateless
@Path("/ref")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RRef extends BaseRest {

	@EJB private SRefI service;
			
	/**
	 * Return country list
	 * 
	 * @param callObj
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("country")
    public JsonRes country(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.listJson(callObj, new SqlParm(), EntCountry.class);
    }
	
	/**
	 * Return new country entity
	 * 
	 * @param callObj
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("country/new")
    public JsonRes countryNew(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.newJson(callObj, EntCountry.class);
    }
	
	/**
	 * Update and Persist the entity list
	 * @param callObj
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("country")
    public JsonRes countryPost(
    		@QueryParam ("co") CallObject callObj, 
    		List<EntCountry> list)  throws Exception {
		return service.putReference(callObj, list, EntCountry.class);
    }
	

	/**
	 * Return currency list
	 * 
	 * @param callObj
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("currency")
    public JsonRes currency(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.listJson(callObj, new SqlParm(), EntCurrency.class);
    }

	/**
	 * Return new currency entity
	 * 
	 * @param callObj
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("currency/new")
    public JsonRes currencyNew(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.newJson(callObj, EntCurrency.class);
    }
	
	/**
	 * Update and Persist the entity list
	 * @param callObj
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("currency")
    public JsonRes currencyPost(
    		@QueryParam ("co") CallObject callObj, 
    		List<EntCurrency> list)  throws Exception {
		return service.putReference(callObj, list, EntCurrency.class);
    }

	
}
