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
    public Object invocation(InvocationContext ctx) throws Exception {
		
		try {
			return ctx.proceed();

		} catch (Exception e) {
System.out.println("ServiceAroundInvoke Exception:" + e.getMessage());
			throw e;
		}
    }
		
}
