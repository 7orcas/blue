package com.sevenorcas.blue.system.lifecycle;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseOrg;
import com.sevenorcas.blue.system.base.JsonRes;

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
		
		log.debug("called method=" + ctx.getMethod().getName());

		boolean proceed = false;
		try {

			SkipAuthorisation anno = ctx.getMethod().getAnnotation(SkipAuthorisation.class);
			if (anno != null) {
				log.debug("SkipAuthorisation annotation");
				proceed = true;
			}
			
			
			//Inject Call Object
			else if (clientCall.getClientSession() != null) {
				CallObject callObj = new CallObject("");
				proceed = true;
				callObj.setClientSession(clientCall.getClientSession());
				
				//ToDo get org from cache
				callObj.setOrg(new BaseOrg(clientCall.getClientSession().getOrgNr()));
				
				for (int i=0;i<ctx.getMethod().getParameterTypes().length;i++) {
					if (ctx.getMethod().getParameterTypes()[i].getTypeName().equals(CallObject.class.getTypeName())) {
						ctx.getParameters()[i] = callObj;
						break;
					}
				}
				log.debug("added CallObject");
			}
			
			
			if (proceed) {
				return ctx.proceed();
			}
			
			log.debug("returning null");			
			return null;
			
		} catch (Exception e) {
			String detail = e.getMessage();
			if (detail == null) {
				detail = e.getClass().getCanonicalName();
			}
			
			log.error("Exception:" + detail);
			return new JsonRes().setError("errunk", detail);
		}
    }
		
}
