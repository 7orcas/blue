package com.sevenorcas.blue.system.lifecycle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Calls need to have a valid http session, otherwise they are not allowed into the system.
 * Exceptions are defined in web.xml in 'excludedUrls' parameters.
 *   
 * [Licence]
 * @author John Stewart
 */
public class Filter1SessionAuthentication implements Filter{

	private List <String> excludedUrls;

	//Load the excluded URLs
	public void init(FilterConfig conf) throws ServletException {
		String excludePattern = conf.getInitParameter("excludedUrls");
        excludedUrls = Arrays.asList(excludePattern.split(","));  
    }
	
	/**
	 * Check for valid sessions
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
System.out.println("Filter1 called");

		boolean proceed = false;

		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest)request;
			
			String url = req.getRequestURL().toString();
System.out.println("Filter1 url=" + url);			
			
			//Check if url is excluded from check
			for (String u : excludedUrls) {
				if (url.indexOf(u) != -1) {
					proceed = true;
					break;
				}
			}
			
			//Got a valid session?
			HttpSession ses = req.getSession(false);
			if (ses != null) {
				proceed = true;
				request.setAttribute("CallObject", ses);
			}
			
		}

		if (proceed) {
			chain.doFilter(request, response);
		}
		else if (response instanceof HttpServletResponse) {
System.out.println("Filter1 SC_UNAUTHORIZED");
			((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
	}

}
