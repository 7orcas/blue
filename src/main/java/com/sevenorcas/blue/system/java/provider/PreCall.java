package com.sevenorcas.blue.system.java.provider;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

import com.sevenorcas.blue.system.org.BaseOrg;


@PreMatching //indicates (with request filter) that such filter should be applied globally on all resources in the application before the actual resource matching occurs

@Provider
public class PreCall implements ContainerRequestFilter, ContainerResponseFilter {

	@Context
    private HttpServletRequest httpRequest;
	
	
	@Override
	public void filter(final ContainerRequestContext req) throws IOException {

		HttpSession ses = httpRequest.getSession(false);
		if (ses != null){
			BaseOrg org = (BaseOrg)ses.getAttribute("blue.org");
System.out.println("PreCall filter Org is " + (org==null?"null":"" + org.getOrg()));
		} 

		
		//HttpServletRequest request = (HttpServletRequest) req;
//		Map<String, Cookie> cookies = req.getCookies();
//		for (Map.Entry<String,Cookie> entry : cookies.entrySet()) 
//System.out.println("PreCall filter Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());
	}

	
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
//System.out.println(">> PostCall filter <<");
		
	}
	 
}