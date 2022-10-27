package com.sevenorcas.blue.system.lifecycle;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.log.AppLog;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * Inject the <code>CallObject</code> into the target rest methods (if in the method signature).
 *  
 * [Licence]
 * Created July '22
 * @author John Stewart
 */
//@Interceptor
public class RestAroundInvoke {

	private static Logger log = Logger.getLogger(RestAroundInvoke.class.getName());
	
	public RestAroundInvoke() {}
	
	@Inject
	private ClientCall clientCall;
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		
		boolean proceed = false;
		CallObject callObj = null;
		try {

			SkipAuthorisation anno = ctx.getMethod().getAnnotation(SkipAuthorisation.class);
			if (anno != null) {
				proceed = true;
			}
			
			//Inject Call Object
			else if (clientCall.getClientSession() != null) {
				callObj = new CallObject("");
				proceed = true;
				callObj.setClientSession(clientCall.getClientSession());
				
				//ToDo get org from cache
				callObj.setOrg(new EntOrg().setOrgNr(clientCall.getClientSession().getOrgNr()));
				
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
