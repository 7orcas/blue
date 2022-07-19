package com.sevenorcas.blue.system.java;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;


@PreMatching //indicates (with request filter) that such filter should be applied globally on all resources in the application before the actual resource matching occurs

@Provider
public class PreCallFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Context
	private HttpServletRequest httpRequest;
	
	@Inject
	private RequestUser requestUser;
	
	@Override
	public void filter(final ContainerRequestContext req) throws IOException {
System.out.println("PreCall filter called");

//		req.get

		HttpSession ses = httpRequest.getSession(false);
		if (ses != null){
			Integer orgNr = (Integer)ses.getAttribute("org_nr");
			requestUser.setOrgNr(orgNr);
			req.setProperty("org_nr", orgNr);
			req.getHeaders().add("org_nrX", "" + orgNr);
//			req.getRequest().
			httpRequest.setAttribute("org_nrXX", orgNr);
			
System.out.println("PreCall org_nr is " + (orgNr==null?"null":"" + orgNr.toString()) + ", session id=" + ses.getId());
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