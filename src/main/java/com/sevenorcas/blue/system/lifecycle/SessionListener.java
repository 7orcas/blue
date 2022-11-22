package com.sevenorcas.blue.system.lifecycle;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sevenorcas.blue.system.login.CacheSession;
import com.sevenorcas.blue.system.login.ent.JsonSessionCache;

/**
 * When a users session is invalidated (eg timed out) we need to send a message to the client to re-login
 * This Listener is part of that process
 * Note it requires an entry in web.xml
 * 
 * Created July '22
 * [Licence]
 * @author John Stewart
 */


public class SessionListener implements HttpSessionListener {

	@EJB private CacheSession cache;
	
	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {	   

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
System.out.println("SESSION DESTROYED " + sessionEvent.getSession().getId());         
		cache.removeSession(sessionEvent.getSession().getId());
List<JsonSessionCache> list = cache.listJson();
System.out.println("list size=" + list.size());
for (JsonSessionCache j : list) {
	System.out.println("name=" + j.username + " clientNr=" + j.clientNr + " session=" + j.sessionId); 
}
	}
} 
