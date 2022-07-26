package com.sevenorcas.blue.system.lifecycle;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sevenorcas.blue.JaxrsActivator;
import com.sevenorcas.blue.system.ApplicationI;

/**
 * Calls have a user session number within their URLs.
 * These need to be filter out and passed via a request attribute
 *   
 * [Licence]
 * @author John Stewart
 */
public class Filter2UrlRedirect implements Filter, ApplicationI {

	//Defined in JaxrsActivator
	static private String ApplicationPath;
	static private Integer CLIENT_SESSION_NRL;
	
	/**
	 * Get the ApplicationPath (used in URLs) 
	 */
	public void init(FilterConfig conf) throws ServletException {
	
		CLIENT_SESSION_NRL = CLIENT_SESSION_NR.length();
		
		for (Annotation annotation : JaxrsActivator.class.getAnnotations()) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (type.equals(javax.ws.rs.ApplicationPath.class)) {
	            for (Method method : type.getDeclaredMethods()) {
	            	try {
	            		ApplicationPath = method.invoke(annotation, (Object[])null).toString();
	            	} catch (Exception x) {
	            		//TODO log this
	            	}
	            }
            }
            
        }
		
    }
	
	/**
	 * Filter out user session number from URL
	 * Pass the user session number via a request attribute 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
System.out.println("Filter2 called");

		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest)request;
			
			String url = req.getRequestURL().toString();
			
			int index1 = url.indexOf(CLIENT_SESSION_NR);
			if (index1 != -1) {
				int index2 = url.indexOf("/", index1);
				Integer nr = Integer.parseInt(url.substring(index1 + CLIENT_SESSION_NRL, index2));
				request.setAttribute(CLIENT_SESSION_NR, nr);
				
				url = ApplicationPath + url.substring(index2);
				
System.out.println("Filter2 forward to url=" + url);
				RequestDispatcher rd = request.getRequestDispatcher(url);
				rd.forward(request, response);
			}
		}
System.out.println("Filter2 doFilter");
		chain.doFilter(request, response);
	}

}
