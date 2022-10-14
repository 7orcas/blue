package com.sevenorcas.blue.system.base;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.conf.EntityConfig;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;

/**
* TODO Module Description
* 
* [Licence]
* Created 3/9/2022
* @author John Stewart
*/
public class BaseUtil implements ApplicationI, IntHardCodeLangKey {

	public AppProperties appProperties = AppProperties.getInstance();
	
	/**
	 * Return true if both objects are non-null and equal
	 * @param o1 
	 * @param o2 
	 * @return
	 */
	public boolean isSameNonNull (Object o1, Object o2) {
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
	
	public <T>List<T> hashtableToListId(Hashtable<Long,T> hashTable) throws Exception {
		Enumeration<Long>keys = hashTable.keys();
		List<T> list = new ArrayList<>();
		while(keys.hasMoreElements()) {
			Long id = keys.nextElement();
			list.add(hashTable.get(id));			
		}
		return list;
	}
	
	public <T>List<T> hashtableToListCode(Hashtable<String,T> hashTable) throws Exception {
		Enumeration<String>keys = hashTable.keys();
		List<T> list = new ArrayList<>();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			list.add(hashTable.get(key));			
		}
		return list;
	}
	
	/**
     * Null base fields using entity configuration (if required)
     * @param entity
     * @param entity configuration
     * @return
     */
    public <T extends BaseEnt<T>> void nullBaseFields(T ent, EntityConfig config) throws Exception {
    	if (config.isUnused("orgNr")) ent.setOrgNr(null);
    	if (config.isUnused("code")) ent.setCode(null);
    	if (config.isUnused("descr")) ent.setDescr(null);
    	if (config.isUnused("active")) ent.setActive(null);
    }

}

