package com.sevenorcas.blue.system.lifecycle;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;
import com.sevenorcas.blue.system.login.ent.ClientSession;

/**
 * Inject the <code>ClientSession</code> object into a @RequestScoped object
 *  - can then be accessed by the RestAroundInvoke interceptor
 * 
 * [Licence]
 * Created July '22
 * @author John Stewart
 */

@PreMatching //indicates (with request filter) that such filter should be applied globally on all resources in the application before the actual resource matching occurs
@Provider
public class Filter3ClientCall implements ContainerRequestFilter, ContainerResponseFilter, ApplicationI, IntHardCodeLangKey {

	private static Logger log = Logger.getLogger(Filter3ClientCall.class.getName());
	
	@Context
	private HttpServletRequest httpRequest;
	
	@Inject
	private ClientCall clientCall;
	
	@Override
	public void filter(final ContainerRequestContext req) throws IOException {

		HttpSession ses = httpRequest.getSession(false);
		
		if (ses != null && httpRequest.getAttribute(CLIENT_SESSION_NR) != null){
			Integer nr = (Integer)httpRequest.getAttribute(CLIENT_SESSION_NR);
			String url = (String)httpRequest.getAttribute(CLIENT_URL);
			
			@SuppressWarnings("unchecked")
			Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(CLIENT_SESSIONS);
			ClientSession cs = clientSessions.get(nr);
			
			if (cs == null) {
				clientCall.setLoginRedirect();
			}
			else if (!cs.getUser().isLoggedIn() 
					&& url.equals(REST_LOGIN + "/" + REST_LOGIN_WEB)) {
				//re-login - do nothing
			}
			else if (!cs.getUser().isLoggedIn()) {
				clientCall.setLoginRedirect();
				log.debug("USER NOT LOGGED IN, client nr=" + (nr==null?"null":"" + nr.toString()) + ", session id=" + ses.getId());
			}
			else {
				cs.setLastActivity(Timestamp.valueOf(LocalDateTime.now()));
				clientCall.set(ses, cs)
				          .setClientUrl((String)httpRequest.getAttribute(CLIENT_URL));
				
				log.debug("CLIENT_SESSION_NR=" + (nr==null?"null":"" + nr.toString()) + ", session id=" + ses.getId());
			}
			
		} 
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {		
	}
	 
}