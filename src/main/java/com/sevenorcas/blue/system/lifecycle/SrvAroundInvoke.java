package com.sevenorcas.blue.system.lifecycle;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.sevenorcas.blue.system.base.BaseAroundInvoke;
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.log.AppLog;

/**
 * Wrap service beans to handle exceptions
 *  
 * Created July '22
 * [Licence]
 * @author John Stewart
 */
//@Interceptor
public class SrvAroundInvoke extends BaseAroundInvoke {
	
	public SrvAroundInvoke() {}
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) throws Exception {
		
		try {
			return ctx.proceed();
		
		} catch (Exception e) {
			
			if (!(e instanceof BaseException)) {
				e = new RedException("Service", e, getCallObject(ctx));
			}
			AppLog.exception("Service", e);
			throw e;
		}
    }
		
}
