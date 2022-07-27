package com.sevenorcas.blue.system.lifecycle;

import java.io.IOException;
import java.util.Hashtable;

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

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.login.ClientSession;


@PreMatching //indicates (with request filter) that such filter should be applied globally on all resources in the application before the actual resource matching occurs

@Provider
public class Filter3ClientCall implements ContainerRequestFilter, ContainerResponseFilter, ApplicationI {

	@Context
	private HttpServletRequest httpRequest;
	
	@Inject
	private ClientCall clientCall;
	
	@Override
	public void filter(final ContainerRequestContext req) throws IOException {
System.out.println("Filter3 ClientCall called " + req.getUriInfo().getPath());

		HttpSession ses = httpRequest.getSession(false);
		if (ses != null && httpRequest.getAttribute(CLIENT_SESSION_NR) != null){
			Integer nr = (Integer)httpRequest.getAttribute(CLIENT_SESSION_NR);
			
			Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(CLIENT_SESSIONS);
			ClientSession cs = clientSessions.get(nr);
			clientCall.setClientSession(cs);
			
System.out.println("Filter3 ClientSessionNr is " + (nr==null?"null":"" + nr.toString()) + ", session id=" + ses.getId());
		} 

	}

	
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {		
	}
	 
}