package com.sevenorcas.blue.system.login;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.http.HttpSession;

import org.infinispan.Cache;
import org.infinispan.CacheSet;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;
import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.login.ent.JsonSessionCache;

/**
 * Session Cache
 *  
 * 21.11.22
 * [Licence]
 * @author John Stewart
 */

@Singleton
@Startup
public class CacheSession {

	private EmbeddedCacheManager cacheManager;
	/**
	 * Key = HttpSession id 
	 * Value = HttpSession
	 */
	private Cache<String, HttpSession> cache;
	
	public CacheSession() {}
	
	@PostConstruct
	public void initalise() {
		if (cacheManager == null) {
			cacheManager = new DefaultCacheManager();
			cacheManager.defineConfiguration("local_session", new ConfigurationBuilder().expiration().lifespan(-1).build());
			cacheManager.start();
			cache = cacheManager.getCache("local_session");			
		}
	}

	public void put(String sessionId, HttpSession session) {
		cache.put(sessionId, session);
	}

	public void removeSession(String sessionId) {
		cache.remove(sessionId);
	}

	@SuppressWarnings("unchecked")
	public boolean containsEntUser(Long userId) {
		CacheSet <String> ids = cache.keySet();
		
		for (String id : ids) {
			HttpSession ses = cache.get(id);
			Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(ApplicationI.CLIENT_SESSIONS);
			Enumeration<Integer> nrs = clientSessions.keys();
			while (nrs.hasMoreElements()) {
				Integer nr = nrs.nextElement();
				ClientSession cs = clientSessions.get(nr);
				if (cs.getUser().getId().equals(userId) && cs.getUser().isLoggedIn() ) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<JsonSessionCache> listJson() {
		CacheSet <String> ids = cache.keySet();
		List<JsonSessionCache> list = new ArrayList<>();
		
		for (String id : ids) {
			HttpSession ses = cache.get(id);
			Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(ApplicationI.CLIENT_SESSIONS);
			Enumeration<Integer> keys = clientSessions.keys();
			while (keys.hasMoreElements()) {
				ClientSession cs = clientSessions.get(keys.nextElement());
				JsonSessionCache j = new JsonSessionCache();
				list.add(j);
				j.userId = cs.getUserId();
				j.clientNr = cs.getSessionNr();
				j.username = cs.getUserName();
				j.sessionId = ses.getId();
				j.created = cs.getCreated();
				j.lastActivity = cs.getLastActivity();
			}			
		}
		return list;
	}

	/**
	 * Logout out the user
	 * If no remaining client sessions on user then invalidate the http session
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public void logout (Long userId) throws Exception {
		
System.out.println("logout userId=" + userId);		
		
		CacheSet <String> ids = cache.keySet();
		boolean found = false;
		for (String id : ids) {
			HttpSession ses = cache.get(id);
if (ses == null) continue;
			Hashtable<Integer, ClientSession> clientSessions = (Hashtable<Integer, ClientSession>)ses.getAttribute(ApplicationI.CLIENT_SESSIONS);
			
			Enumeration<Integer> keys = clientSessions.keys();
			while (keys.hasMoreElements()) {
				Integer nr = keys.nextElement();
				ClientSession cs = clientSessions.get(nr);
				if (cs.getUser().getId().equals(userId)) {
					cs.getUser().setLoggedIn(false);
					clientSessions.remove(nr);
System.out.println("logout remove client nr=" + nr);
					found = true;
				}
			}
				
			//This cache will be called back to remove the http session
			if (clientSessions.isEmpty()) {
				ses.invalidate();
			}
		}
		
		if (!found) throw new RedException(IntHardCodeLangKey.LK_UNKNOWN_ERROR, "No user found to logout session");
		
	}
	
	
}

