package com.sevenorcas.blue.system.util;

import java.lang.invoke.MethodHandles;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* Return an error Json
* Called via ServletFileUpload
* * 
* [Licence]
* Created 08.09.2022
* @author John Stewart
*/

@Stateless
@Path("/" + ApplicationI.ERROR_PATH)
@Produces({"application/json"})
@Consumes({"application/json"})
public class RestError extends BaseRest {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	/**
	 * Return an error message
	 * 
	 * @param callObj
	 * @param langKey
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("message")
    public JsonRes errorMessageGet(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("message") String message,
    		@QueryParam ("detail") String detail,
    		@QueryParam ("code") Integer code) throws Exception {
		return new JsonRes()
				.setError(message, detail)
				.setReturnCode(code);
    }
	
	/**
	 * Return an error message from a bad upload
	 * 
	 * @param callObj
	 * @param langKey
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path(UPLOAD_PATH)
	@Consumes({"multipart/form-data"})
    public JsonRes errorUploadPost(
    		@QueryParam ("co") CallObject callObj,
    		@QueryParam ("message") String message,
    		@QueryParam ("append") String append,
    		@QueryParam ("detail") String detail,
    		@QueryParam ("code") Integer code) throws Exception {
		return new JsonRes()
				.setError(message + LK_APPEND + " (" + append + ")", detail)
				.setReturnCode(code);
    }
	
	
}
