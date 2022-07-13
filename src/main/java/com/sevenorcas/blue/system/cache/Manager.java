package com.sevenorcas.blue.system.cache;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

@Singleton
@Startup
public class Manager {

//	@Resource(lookup = "java:jboss/infinispan/container/DataCache")  
	EmbeddedCacheManager cacheManager;

	
//	@Resource(lookup = "java:jboss/infinispan/container/DataCache")
//	private CacheContainer Container;
//	
//	@Resource(name = "infinispan/DataCache")
	private Cache<String, Item> cache;
	
	private String key;
	private String value;

	public Manager() {
System.out.println("cache is " + (cache==null?"null":"not null"));		
	}
	
	@PostConstruct
	public void initalise() {
		if (cacheManager == null) {
			cacheManager = new DefaultCacheManager();
			cacheManager.defineConfiguration("local", new ConfigurationBuilder().build());
			cacheManager.start();
			cache = cacheManager.getCache("local");			
		}
		//
//		Container.start();
//	    Container.getCache("modelcache");
	}

	public String save() {
		//Cache<String, Item> cache = cacheManager.getCache();
		Item item = new Item();
		item.setKey(key);
		item.setValue(value);
		cache.put(item.getKey(), item);
		return "home";
	}

	public List getCacheList() {
		//Cache<String, Item> cache = cacheManager.getCache();
		List list = new ArrayList();
		cache.entrySet().forEach(entry -> list.add(entry.getValue()));
		return list;

	}

	public void removeItem(String key) {
		//Cache<String, Item> cache = cacheManager.getCache();   
		cache.remove(key);
	}

	public void clear() {
		//Cache<String, Item> cache = cacheManager.getCache();
		cache.clear();
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

