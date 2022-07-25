package com.sevenorcas.blue.system.login;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

/**
 * Login cache for CORS login
 * For development, the system uses 2 reactjs projects, 1) Login 2) Main
 * The reason is for the 2 projects is not to expose Main URLs 
 *  
 * [Licence]
 * @author John Stewart
 */

@Singleton
@Startup
public class LoginCache {

	private EmbeddedCacheManager cacheManager;
	private Cache<String, Session> cache;
	
	public LoginCache() {}
	
	@PostConstruct
	public void initalise() {
		if (cacheManager == null) {
			cacheManager = new DefaultCacheManager();
			cacheManager.defineConfiguration("local", new ConfigurationBuilder().expiration().lifespan(10000L).build());
			cacheManager.start();
			cache = cacheManager.getCache("local");			
		}
	}

	public void put(String key, Session session) {
		cache.put(key, session);
	}

//DELETE
//	public List<String> getCacheList() {
//		List<String> list = new ArrayList();
//		cache.entrySet().forEach(entry -> list.add(entry.getKey() + "=" + entry.getValue()));
//		return list;
//
//	}

	public void removeKey(String key) {
		//Cache<String, Item> cache = cacheManager.getCache();   
		cache.remove(key);
	}

	public void clear() {
		cache.clear();
	}
	
	public Session getSessionAndRemove(String key) {
		Session s = cache.get(key);
		cache.remove(key);
		return s;
	}
}

