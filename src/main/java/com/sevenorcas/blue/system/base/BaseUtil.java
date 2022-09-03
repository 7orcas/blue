package com.sevenorcas.blue.system.base;

import java.util.Hashtable;
import java.util.List;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.base.IdCodeI;

/**
* TODO Module Description
* 
* [Licence]
* Created 3/9/2022
* @author John Stewart
*/
public class BaseUtil implements ApplicationI {

	public AppProperties appProperties = AppProperties.getInstance();
	
		
	public boolean isSameNonNUll (Long o1, Long o2) {
		if (o1 == null && o2 == null) return false;
		if (o1 == null || o2 == null) return false;
		return o1.equals(o2);
	}
	
	/**
	 * Return true if both strings are non-null and equal
	 * @param o1 String 1
	 * @param o2 String 2
	 * @return
	 */
	public boolean isSameNonNull (String o1, String o2) {
		if (o1 == null && o2 == null) return false;
		if (o1 == null || o2 == null) return false;
		return o1.equals(o2);
	}
	
	public boolean isNotEmpty (String s) {
		return s != null && !s.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public <T>Hashtable<String, T> listToHashtableCode (List<? extends IdCodeI> list) throws Exception {
		Hashtable<String, T> t = new Hashtable<>();
        for (IdCodeI o: list){
            t.put(o.getCode(), (T)o);
        }
        return t;
	}
	
	@SuppressWarnings("unchecked")
	public <T>Hashtable<Long, T> listToHashtableId (List<? extends IdCodeI> list) throws Exception {
		Hashtable<Long, T> t = new Hashtable<>();
        for (IdCodeI o: list){
            t.put(o.getId(), (T)o);
        }
        return t;
	}
	
}

