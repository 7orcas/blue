package com.sevenorcas.blue.system.java;

import java.util.Hashtable;

public class CallObject {

	Hashtable<String, Object> objects = new Hashtable<>();
	
	public void put (String key, Object obj) {
		objects.put(key, obj);
	}

	public Object get (String key) {
		return objects.get(key);
	}
}
