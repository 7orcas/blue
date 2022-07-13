package com.sevenorcas.blue.system.java.interceptor;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import com.sevenorcas.blue.system.login.LoginCache;
import com.sevenorcas.blue.system.org.BaseOrg;

/**
 *  
 * [Licence]
 * @author John Stewart
 */

@Interceptor
public class RestInterceptor {

	@Context
    private HttpServletRequest httpRequest;
	
	@EJB
	private LoginCache cache;
	
	public RestInterceptor() {}
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		
		try {
			Map <String, Object> map = ctx.getContextData();

//Testing
List<String> keyValues = cache.getCacheList();
for (int i=0; i<keyValues.size(); i++) {
	System.out.println("RestInterceptor cache k=" + keyValues.get(i));
}

			HttpServletRequest req = getHttpServletRequest(ctx);
			
			//cache.getValue(null)
			
			
			
			if (req != null) {
				HttpSession ses = req.getSession(false);

				if (ses != null){
					
					BaseOrg org = (BaseOrg)ses.getAttribute("blur.org");
System.out.println("Org is " + (org==null?"null":"" + org.getOrg()));
				} else {
System.out.println("Created Session");  //isRequestedSessionIdValid
					HttpSession s = req.getSession(true);
					BaseOrg org = new BaseOrg();
					Random rand = new Random();
					org.setOrg(rand.nextInt(5000));
					s.setAttribute("blur.org", org);
				}
				

			}



			
			return ctx.proceed();
		} catch (Exception e) {
System.out.println("RestInterceptor.invocation() Exception:" + e.getMessage());
			return null;
		}
    }
	
	private HttpServletRequest getHttpServletRequest(InvocationContext ctx) {
	    for (Object parameter : ctx.getParameters()) {
	        if (parameter instanceof HttpServletRequest) {
	            return (HttpServletRequest) parameter;
	        }
	    }
	    return null;
	}
	
	
}
