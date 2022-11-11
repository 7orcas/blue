package com.sevenorcas.blue.system.org;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * Organisation Cache
 *  
 * 11.11.22
 * [Licence]
 * @author John Stewart
 */

@Singleton
@Startup
public class CacheOrg {

	private EmbeddedCacheManager cacheManager;
	private Cache<Integer, EntOrg> cache;
	
	public CacheOrg() {}
	
	@PostConstruct
	public void initalise() {
		if (cacheManager == null) {
			cacheManager = new DefaultCacheManager();
			cacheManager.defineConfiguration("local_org", new ConfigurationBuilder().expiration().lifespan(-1).build());
			cacheManager.start();
			cache = cacheManager.getCache("local_org");			
		}
	}

	public void put(Integer nr, EntOrg org) {
		cache.put(nr, org);
	}

	public void remove(Integer nr) {
		cache.remove(nr);
	}

	public void clear() {
		cache.clear();
	}
	
	public EntOrg get(Integer nr) {
		return cache.get(nr);
	}
}

