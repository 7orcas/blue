package com.sevenorcas.blue.system.base;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.Table;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.exception.RedException;
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
	
	public <T extends BaseEntity<T>> int compareTo (BaseEntity<T> o1, BaseEntity<T> o2) {
		if (o1 == null && o2 == null) return 0;
		if (o1 == null) return 1;
		if (o2 == null) return -1;
		if (o1.getCode() == null && o2.getCode() == null) return 0;
		if (o1.getCode() == null) return 1;
		if (o2.getCode() == null) return -1;
		return o1.getCode().compareTo(o2.getCode());
	}
	
	public boolean isNotEmpty (String s) {
		return s != null && !s.isEmpty();
	}
	
	/**
	 * Is this a new ID?
	 * @param id
	 * @return
	 */
	public boolean isNewId (Long id) {
		return id == null || id < 0;
	}
	
	/**
	 * Return the entities database table name
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	static public String tableName (Class<?> clazz, String suffix) throws Exception {
		suffix = suffix == null? "" : suffix;
		if (suffix.length() > 0) {
			suffix = " " + suffix.trim() + " ";
		}
		if (clazz.isAnnotationPresent(Table.class)) {
			Table table = clazz.getAnnotation(Table.class);
			return table.schema() + "." + table.name() + suffix;
		}
		throw new RedException (LK_UNKNOWN_ERROR, "Invalid table annonation on class : " + clazz.getCanonicalName());
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
    public <T extends BaseEntity<T>> void nullBaseFields(T ent, EntityConfig config) throws Exception {
    	if (config.isUnused("orgNr")) ent.setOrgNr(null);
    	if (config.isUnused("code")) ent.setCode(null);
    	if (config.isUnused("descr")) ent.setDescr(null);
    	if (config.isUnused("active")) ent.setActive(null);
    }

    /**
     * Find the entity by it's code
     * @param code
     * @param list
     * @return
     */
    public  <T extends BaseEntity<T>>T findByCode (String code, List<T> list) {
    	for (int i=0;list!=null && i<list.size();i++) {
    		T e = list.get(i);
    		if (e.getCode().equals(code)) {
    			return e;
    		}
    	}
    	return null;
    }
    
    /**
     * Test if integer is null, if so return 0
     * ie guarantee non null integer
     * @param x
     * @return
     */
    static public Integer nonNull (Integer x) {
    	return x != null? x : 0;
    }
}

