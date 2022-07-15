package com.sevenorcas.blue.system.java.interceptor;

import java.util.Map;

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
//@Interceptor
public class RestAuthorisation {

	public RestAuthorisation() {}
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
//System.out.println("RestAuthorisation called");
		
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
Integer orgNr = (Integer)ses.getAttribute("org_nr");
System.out.println("RestAuthorisation Org is " + (orgNr==null?"null":"" + orgNr.toString()) + ", session id=" + ses.getId());
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
	    
		Map<String,Object> map = ctx.getContextData();
		for(String k : map.keySet()) {
//System.out.println("RestAuthorisation map " + k + " = " + map.get(k).toString());
		}
		
		for (Object parameter : ctx.getParameters()) {
//System.out.println("RestAuthorisation parm " + parameter.getClass().getName() + " = " + parameter.toString());
	    	
	    	if (parameter instanceof HttpServletRequest) {
	            return (HttpServletRequest) parameter;
	        }
	    }
	    return null;
	}
	
	
}
