package com.sevenorcas.blue.system.login.ent;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.http.HttpSession;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

/**
 * Login cache for Development to work around CORS login restrictions
 * NOT TO BE USED IN PRODUCTION
 *  
 * [Licence]
 * @author John Stewart
 */

@Singleton
@Startup
public class CacheLogin_Dev {

	private EmbeddedCacheManager cacheManager;
	private Cache<String, HttpSession> cache;
	
	public CacheLogin_Dev() {}
	
	@PostConstruct
	public void initalise() {
		if (cacheManager == null) {
			cacheManager = new DefaultCacheManager();
			cacheManager.defineConfiguration("localdev", new ConfigurationBuilder().expiration().lifespan(-1).build());
			cacheManager.start();
			cache = cacheManager.getCache("localdev");			
		}
	}

	public void put(String key, HttpSession session) {
		cache.put(key, session);
	}

	public void removeKey(String key) {
		//Cache<String, Item> cache = cacheManager.getCache();   
		cache.remove(key);
	}

	public void clear() {
		cache.clear();
	}
	
	public HttpSession getSession(String key) {
		return cache.get(key);
	}
}

