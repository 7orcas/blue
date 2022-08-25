package com.sevenorcas.blue.system.base;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.ApplicationI;

public class BaseUtil implements ApplicationI {

	public AppProperties appProperties = AppProperties.getInstance();
	
		
	public boolean isSameNonNUll (Long o1, Long o2) {
		if (o1 == null && o2 == null) return false;
		if (o1 == null || o2 == null) return false;
		return o1.equals(o2);
	}
	
	public boolean isSameNonNUll (String o1, String o2) {
		if (o1 == null && o2 == null) return false;
		if (o1 == null || o2 == null) return false;
		return o1.equals(o2);
	}
	
	public boolean isNotEmpty (String s) {
		return s != null && !s.isEmpty();
	}
}
