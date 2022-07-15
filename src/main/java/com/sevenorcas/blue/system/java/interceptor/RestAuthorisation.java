package com.sevenorcas.blue.system.java.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sevenorcas.blue.system.org.BaseOrg;

/**
 *  
 * [Licence]
 * @author John Stewart
 */
//@Provider
//@Interceptor
public class RestAuthorisation {

	public RestAuthorisation() {}
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		boolean proceed = false;
		try {

			//Initialising a login may not have a session (due to CORS) 
			if (ctx.getMethod() != null && ctx.getMethod().getName().equals("login2Web")) { 
				proceed = true;
			} 
			else {
				
				HttpServletRequest req = getHttpServletRequest(ctx);
				
				if (req != null) {
					HttpSession ses = req.getSession(false);

					if (ses != null){
						proceed = ses != null;
BaseOrg org = (BaseOrg)ses.getAttribute("blue.org");
System.out.println("RestAuthorisation Org is " + (org==null?"null":"" + org.getOrg()));
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
	
	/**
	 * Find the http request object 
	 * @param ctx
	 * @return
	 */
	private HttpServletRequest getHttpServletRequest(InvocationContext ctx) {
	    for (Object parameter : ctx.getParameters()) {
	        if (parameter instanceof HttpServletRequest) {
	            return (HttpServletRequest) parameter;
	        }
	    }
	    return null;
	}
	
	
}
