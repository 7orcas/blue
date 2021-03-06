package com.sevenorcas.blue.system.lifecycle;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *  
 * [Licence]
 * @author John Stewart
 */
//@Interceptor
public class DaoAroundInvoke {
	
	public DaoAroundInvoke() {}
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		
System.out.println("DaoAroundInvoke called on " + ctx.getMethod().getName());
		try {
			return ctx.proceed();

		} catch (Exception e) {
System.out.println("DaoAroundInvoke Exception:" + e.getMessage());
			return null;
		
		}
    }
		
}
