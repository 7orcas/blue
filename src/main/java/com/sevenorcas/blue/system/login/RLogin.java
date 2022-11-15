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
import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.login.ent.JReqLogin;
import com.sevenorcas.blue.system.login.ent.JReqReset;
import com.sevenorcas.blue.system.login.ent.JResLogin;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * Part 1 of the login process 
 * If successful the client is sent the baseURL
 * The session id is used by the client to return to part 2, ie a valid key will create a new session 
 * 
 * Note this bean doesn't implement the BaseRest so the RestAuthorisation Interceptor is not called
 * 
 * Created July '22
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/login")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RLogin extends BaseRest{
	
	@EJB
	private SLoginI service;
	
	@SkipAuthorisation
	@POST
	@Path("web")
	public JsonRes loginWeb(@Context HttpServletRequest httpRequest, JReqLogin req) {
				
		String lang = isNotEmpty(req.l) ? req.l : appProperties.get("LanguageDefault");
		EntUser user = null;
		
		try {
			user = service.getUserAndValidate(req.u, req.p, req.o, lang);
		} catch (Exception x) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR);	
		}
		
		//Invalid user id
		if (user == null) {
			return new JsonRes().setError(LK_INVALID_USERID);			
		}
		//Login can't be validated
		if (!user.isValidUser()) {
			return new JsonRes().setError(user.getInvalidMessage());	
		}
			
		boolean changePw = user.isChangePassword(); //Temporary PW used, force a change
		try {
			user = service.persistAfterLogin(user);
			service.detach(user);
		} catch (Exception x) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR);	
		}
		
		//Success! Set parameters for client to open web gui
		HttpSession ses = httpRequest.getSession(true);
		
		//Return object
		JResLogin login = new JResLogin();
		login.sessionId = ses.getId();
		login.initialisationUrl = appProperties.get("WebLoginInitUrl");
		
		if (appProperties.is("DevelopmentMode")) {
			login.locationHref = appProperties.get("WebClientUrl-CORS");	
		}
		else {
			login.locationHref = appProperties.get("WebClientUrl");
		}
					
		//Get next client sessions
		@SuppressWarnings("unchecked")
		Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(CLIENT_SESSIONS);
		if (clientSessions == null) {
			clientSessions = new Hashtable<>();
			ses.setAttribute(CLIENT_SESSIONS, clientSessions);
		}

		ClientSession cs = new ClientSession(user, user.getOrgNrLogin(), lang, changePw);
		
		Integer nextSes = clientSessions.size();
		clientSessions.put(nextSes, cs.setSessionNr(nextSes));

		//Append client session to base url, client will use this to connect to this server
		login.baseUrl = appProperties.get("BaseUrl") + APPLICATION_PATH + "/" + cs.getUrlSegment();
		login.uploadUrl = appProperties.get("BaseUrl") + UPLOAD_PATH + "/" + cs.getUrlSegment();

		return new JsonRes().setData(login);
    }

	@SkipAuthorisation
	@POST
	@Path("forgotpw")
	public JsonRes forgotPw(@Context HttpServletRequest httpRequest, JReqReset req) {	
		try {
			return service.emailTempPassword(req.email, req.lang);
		} catch (Exception x) {
			return new JsonRes().setError(LK_UNKNOWN_ERROR);	
		}
	}
	
	
	
}
