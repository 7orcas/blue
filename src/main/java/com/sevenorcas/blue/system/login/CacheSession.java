package com.sevenorcas.blue.system.login;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.infinispan.Cache;
import org.infinispan.CacheSet;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

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
	 * Key = User id
	 * Value = HttpSession id
	 */
	private Cache<Long, String> cache;
	
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

	public void put(Long userId, String sessionId) {
		cache.put(userId, sessionId);
	}

	public void remove(String sessionId) {
		CacheSet <Long> ids = cache.keySet();
		for (Long id : ids) {
			String s_id = cache.get(id);
			if (s_id.equals(sessionId)) {
				cache.remove(id);
			}
		}
	}

	public void clear() {
		cache.clear();
	}
	
	public String get(Long userId) {
		return cache.get(userId);
	}

	public boolean contains(Long userId) {
		return cache.containsKey(userId);
	}
	
	public void list() {
		System.out.println("CacheSession ids:");
		CacheSet <Long> ids = cache.keySet();
		for (Long id : ids) {
			String s_id = cache.get(id);
			System.out.println(s_id);
		}
	}

	
}

