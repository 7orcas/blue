package com.sevenorcas.blue.system.lifecycle;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.log.AppLog;

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
			if (!(e instanceof BaseException)) {
				e = new RedException("Dao", e);
			}
			AppLog.exception("Dao", e);
			throw e;
		}
    }
		
}
