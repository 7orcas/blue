package com.sevenorcas.blue.system.lifecycle;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.log.AppLog;
import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.org.SOrgI;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.user.ent.EntUser;
import com.sevenorcas.blue.system.util.JsonResponseI;

/**
 * Prepare for a call to the Rest Interface.
 * 
 * Inject the <code>CallObject</code> into the target rest methods (if in the method signature).
 * The <code>CallObject</code> contains relevant objects required by a lot of methods within the call stack.
 * 
 * Check the url exists in the user permission. 
 * This is part 1, part 2 will check crud permissions (happens in the respective Transfer (DAO) object).
 *  
 * [Licence]
 * Created July '22
 * @author John Stewart
 */
//@Interceptor
public class RestAroundInvoke {

	private static Logger log = Logger.getLogger(RestAroundInvoke.class.getName());
	
	public RestAroundInvoke() {}
	
	@Inject	private ClientCall clientCall;
	@EJB private SOrgI orgService;
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		
		boolean proceed = false;
		CallObject callObj = null;
		try {

			SkipAuthorisation anno = ctx.getMethod().getAnnotation(SkipAuthorisation.class);
			if (anno != null) {
				proceed = true;
			}
			
			//Redirect client to a re-login
			else if (clientCall.isLoginRedirect()) {
				return new JsonRes().setReturnCode(JsonResponseI.JS_LOGIN_REDIRECT);
			}
			
			//Inject Call Object
			else if (clientCall.getClientSession() != null) {
				callObj = new CallObject("");
				proceed = true;
				
				ClientSession cs = clientCall.getClientSession();
				EntUser user = cs.getUser();
				EntPermission perm = null;
				if (clientCall.getClientUrl() != null) {
					perm = user.findPermission(clientCall.getClientUrl());
					if (perm == null && !user.isDevAdmin()) {
						log.error("User:" + user.getCode() + ", Url:" + clientCall.getClientUrl() + ", NO PERM");
						return new JsonRes().setReturnCode(JsonResponseI.JS_NO_PERMISSION);		
					}
				}
				
				callObj.setClientSession(cs)
					   .setHttpSession(clientCall.getHttpSession())
					   .setPermission(perm);
				
				//Get org entity from cache
				callObj.setOrg(orgService.getOrgCache(clientCall.getClientSession().getOrgNr()));
				
				for (int i=0;i<ctx.getMethod().getParameterTypes().length;i++) {
					if (ctx.getMethod().getParameterTypes()[i].getTypeName().equals(CallObject.class.getTypeName())) {
						ctx.getParameters()[i] = callObj;
						break;
					}
				}
			}
			
			
			if (proceed) {
				return ctx.proceed();
			}
			
			log.debug("returning null");			
			return null;
			
		} catch (Exception e) {
			Exception orgEx = e;
			if (!(e instanceof BaseException)) {
				e = new RedException("Rest", e, callObj);
			}
			else {
				orgEx = ((BaseException)e).getOriginalException();
			}
			AppLog.exception("Rest", e);
			
			
			String detail = orgEx.getMessage();
			if (detail == null) {
				detail = orgEx.getClass().getCanonicalName();
			}
			return new JsonRes().setError("errunk", detail);
		}
    }
		
}
