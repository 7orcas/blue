package com.sevenorcas.blue.system.login;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.login.ent.JReqLogin;
import com.sevenorcas.blue.system.login.ent.JReqReset;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * Part 1 of the login process 
 * If successful the client is sent the baseURL
 * The session id is used by the client to return to part 2, ie a valid key will create a new session 
 * 
 * Note this bean doesn't implement the BaseRest so the RestAuthorisation Interceptor is not called
 * 
 * Created July '22
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/" + ApplicationI.REST_LOGIN)
@Produces({"application/json"})
@Consumes({"application/json"})
public class RLogin extends BaseRest{
	
	@EJB private SLoginI service;
	@EJB private CacheSession cache;
	
	@SkipAuthorisation
	@POST
	@Path(ApplicationI.REST_LOGIN_WEB)
	public JsonRes loginWeb(@Context HttpServletRequest httpRequest, JReqLogin req) {
				
		EntUser user = null;
		
		try {
			user = service.getUserAndValidate(req.u, req.p, req.a, req.o, req.l);
		} catch (Exception x) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR);	
		}
		
		//Invalid user id
		if (user == null) {
			return new JsonRes().setError(LK_INVALID_USERID);			
		}
		//Login can't be validated
		if (!user.isValidUser()) {
			return new JsonRes().setError(user.getInvalidMessage());	
		}
			
		return service.processWebLogin(httpRequest, user, req.cn);
    }

	@SkipAuthorisation
	@POST
	@Path("forgotpw")
	public JsonRes forgotPw(@Context HttpServletRequest httpRequest, JReqReset req) {	
		try {
			return service.emailTempPassword(req.email, req.lang);
		} catch (Exception x) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR);	
		}
	}
	
	/**
	 * Logout the current user
	 * @param callOb
	 * @return
	 */
	@SkipAuthorisation
	@POST
	@Path("logout")
	public JsonRes logout(@QueryParam ("co") CallObject callOb) {	
		try {
			return service.logout(callOb, callOb.getUserId());
		} catch (Exception x) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR);	
		}
	}
	
	/**
	 * Return session cache
	 * 
	 * @param callObj
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("listcache")
    public JsonRes listCache(
    		@QueryParam ("co") CallObject callObj) throws Exception {
		return service.listCacheJson(callObj);
    }
}
