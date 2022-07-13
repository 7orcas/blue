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

import com.sevenorcas.blue.system.cache.Item;
import com.sevenorcas.blue.system.cache.Manager;
import com.sevenorcas.blue.system.org.BaseOrg;

@Interceptor
public class RestInterceptor {

	@Context
    private HttpServletRequest httpRequest;
	
	@EJB
	private Manager m;
	
	public RestInterceptor() {}
	
	@AroundInvoke
    public Object invocation(InvocationContext ctx) {
		
System.out.println(">> RestInterceptor.invocation() <<");		
        //... log invocation data ...
        
		try {
			
//			if (true) throw new Exception("test");
			
			Map <String, Object> map = ctx.getContextData();
					
System.out.println("RestInterceptor.invocation() map is " + (map==null?"null":("sz=" + map.size())));
			

//Manager m = new Manager();
List l = m.getCacheList();
for (int i=0; i<l.size(); i++) {
	Item item = (Item)l.get(i);
	System.out.println("RestInterceptor cache k=" + item.getKey() + "  v=" + item.getValue());
}

			HttpServletRequest req = getHttpServletRequest(ctx);
			
System.out.println("HttpServletRequest is " + (req==null?"null!":"not null!"));			
			
			if (req != null) {
				HttpSession ses = req.getSession(false);
System.out.println("Session is " + (ses==null?"null":"not null"));
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
