package com.sevenorcas.blue.system.login;

import java.util.Hashtable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.sevenorcas.blue.system.annotation.SkipAuthorisation;
import com.sevenorcas.blue.system.base.BaseRest;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.user.UserEnt;

/**
 * Part 1 of the login process 
 * If successful the client is sent the baseURL
 * The session id is used by the client to return to part 2, ie a valid key will create a new session 
 * 
 * Note this bean doesn't implement the BaseRest so the RestAuthorisation Interceptor is not called
 * 
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/login")
@Produces({"application/json"})
@Consumes({"application/json"})
public class LoginRest extends BaseRest{
	
//	@EJB
//	private LoginCache cache;
	
	@EJB
	private LoginSrv service;
	
	@SkipAuthorisation
	@POST
	@Path("web")
	public JsonRes loginWeb(@Context HttpServletRequest httpRequest, LoginJsonReq req) {
		
		UserEnt user = service.getUserAndValidate(req.u, req.p, req.o);
		
		//Invalid user id
		if (user == null) {
			return new JsonRes().setError("invuid");			
		}
		//Login can't be validated
		if (!user.isValid()) {
			return new JsonRes().setError(user.getInvalidMessage());	
		}
				
		//Success! Set parameters for client to open web gui
		HttpSession ses = httpRequest.getSession(true);
		
		//Return object
		LoginJsonRes login = new LoginJsonRes();
		login.sessionId = ses.getId();
		login.uploadUrl = appProperties.get("UploadUrl");
		login.initialisationUrl = appProperties.get("WebLoginInitUrl");
		
		if (appProperties.is("DevelopmentMode")) {
			login.locationHref = appProperties.get("WebClientMainUrl-CORS");	
		}
		else {
			login.locationHref = appProperties.get("WebClientMainUrl");
		}
				
		String lang = isNotEmpty(req.l) ? req.l : appProperties.get("LanguageDefault");
		
		//Get next client sessions
		@SuppressWarnings("unchecked")
		Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(CLIENT_SESSIONS);
		if (clientSessions == null) {
			clientSessions = new Hashtable<>();
			ses.setAttribute(CLIENT_SESSIONS, clientSessions);
		}
		ClientSession cs = new ClientSession(user.getId())
				.setOrgNr(user.getOrg())
				.setLang(lang);
		
		Integer nextSes = clientSessions.size();
		clientSessions.put(nextSes, cs.setSessionNr(nextSes));

		//Append client session to base url, client will use this to connect to this server
		login.baseUrl = appProperties.get("BaseUrl") + cs.getUrlSegment();

//		cache.put(ses.getId(), cs);
		return new JsonRes().setData(login);
    }

	
	
}
