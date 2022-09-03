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
    public Object invocation(InvocationContext ctx) throws Exception {

		try {
			return ctx.proceed();

		} catch (Exception e) {
			
System.out.println("DaoAroundInvoke Exception:" + e.getMessage());
			throw e;
		}
    }
		
}
