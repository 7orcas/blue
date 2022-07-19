package com.sevenorcas.blue.system.java;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.sevenorcas.blue.system.base.BaseOrg;

/**
 * Inject CallObject into the target method
 *  
 * [Licence]
 * @author John Stewart
 */
//@Interceptor
public class CallObjectInterceptor {

	
	public CallObjectInterceptor() {}
	
	@Inject
	private RequestUser requestUser;
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		
System.out.println("RestAuthorisation called");


		boolean proceed = false;
		try {

			//Initialising a login may not have a session (due to CORS) 
			if (ctx.getMethod() != null && ctx.getMethod().getName().equals("login2Web")) { 
				proceed = true;
			} 
			
			
			//Inject Call Object
			else if (requestUser.getOrgNr() != null) {
				CallObject callObj = new CallObject("");
				proceed = true;
				BaseOrg o = new BaseOrg("");
				o.setOrg(requestUser.getOrgNr());
				callObj.setOrg(o);
				
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
			
System.out.println("RestAuthorisation returning null");			
			return null;
		} catch (Exception e) {
System.out.println("RestAuthorisation.invocation() Exception:" + e.getMessage());
			return null;
		}
    }
		
}
