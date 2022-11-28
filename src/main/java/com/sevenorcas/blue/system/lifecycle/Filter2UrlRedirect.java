package com.sevenorcas.blue.system.lifecycle;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.ApplicationI;

/**
 * Calls have a client number within their URLs.
 * These need to be filter out and passed via a request attribute
 *   
 * [Licence]
 * @author John Stewart
 */
public class Filter2UrlRedirect implements Filter, ApplicationI {

	static private Integer CLIENT_SESSION_NRL;
	
	private static Logger log = Logger.getLogger(Filter2UrlRedirect.class.getName());
	
	/**
	 * Get the ApplicationPath (used in URLs) 
	 */
	public void init(FilterConfig conf) throws ServletException {
		CLIENT_SESSION_NRL = CLIENT_SESSION_NR.length();
    }
	
	/**
	 * Filter out client session number from URL
	 * Pass the client session number via a request attribute 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest)request;
			
			String url = req.getRequestURL().toString();
			Integer nr = getClientSessionNr(url);
			url = getURLAfterClientSessionNr(url);
			
			if (nr != -1) {
				request.setAttribute(CLIENT_SESSION_NR, nr);
				request.setAttribute(CLIENT_URL, cleanUrl(url));
				RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/" + APPLICATION_PATH + url);
				rd.forward(request, response);
				log.debug("forward: " + APPLICATION_PATH + url);
				return;
			}
		}

		chain.doFilter(request, response);
	}
	
	/**
	 * Return the client session number embedded in the URL
	 * If not found the <code>-1</code> is returned
	 * @param url
	 * @return
	 */
	static public Integer getClientSessionNr(String url) {
		try {
			int index1 = url.indexOf(CLIENT_SESSION_NR);
			int index2 = url.indexOf("/", index1);
			return Integer.parseInt(url.substring(index1 + CLIENT_SESSION_NRL, index2));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Return the URL segment after the embedded client session number 
	 * If not found then <code>NULL</code> is returned
	 * @param url
	 * @return
	 */
	static public String getURLAfterClientSessionNr(String url) {
		try {
			int index1 = url.indexOf(CLIENT_SESSION_NR);
			int index2 = url.indexOf("/", index1);
			return url.substring(index2);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	static public String cleanUrl(String url) {
		int index1 = url.indexOf("?");	
		if (index1 != -1) url = url.substring(0, index1);
		if (url.endsWith("/")) url = url.substring(0, url.length()-1);
		if (url.startsWith("/")) url = url.substring(1);
		return url;
	}
	
}
