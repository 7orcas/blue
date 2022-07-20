package com.sevenorcas.blue.system.lifecycle;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *  
 * [Licence]
 * @author John Stewart
 */
//@Interceptor
public class SrvAroundInvoke {
	
	public SrvAroundInvoke() {}
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		
System.out.println("ServiceAroundInvoke called on " + ctx.getMethod().getName());
		try {
			return ctx.proceed();

		} catch (Exception e) {
System.out.println("ServiceAroundInvoke Exception:" + e.getMessage());
			return null;
		
		}
    }
		
}
