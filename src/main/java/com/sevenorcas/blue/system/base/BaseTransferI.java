package com.sevenorcas.blue.system.base;

import javax.persistence.EntityManager;

import com.sevenorcas.blue.system.conf.ent.ConfigurationI;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* Base Transfer Module interface
* [Licence]
* Created Jul '22
* @author John Stewart
*/
public interface BaseTransferI extends ConfigurationI {
	public EntityManager getEntityManager(); 	
	public String prefix (String prefix, String fields);
	public String prefixAs (String prefix, String fields);
	public String prefix (String prefix, String fields, boolean as);
	public <T extends BaseEntity<T>> void detach (T ent) throws Exception;
	public <T extends BaseEntity<T>> T find (Class<T> clazz, Long id) throws Exception;
    public <T extends BaseEntity<T>> T find (T ent) throws Exception;
    public <T extends BaseEntity<T>> T persist (T ent, Long userId) throws Exception;
    public <T extends BaseEntity<T>> void updateTimestampUserid (T ent, Long userId) throws Exception;
    public <T extends BaseEntity<T>> T deleteEntity (T ent) throws Exception;
    public <T extends BaseEntity<T>> T put (T ent, EntityConfig config, CallObject callObj) throws Exception;
    public <T extends BaseEntity<T>> T merge(T ent, EntityConfig config, CallObject callObj) throws Exception;
    public <T extends BaseEntity<T>> void compareTimeStamp(T ent, EntityConfig config, ValidationErrors errors) throws Exception;
    public <T extends BaseEntity<T>> void canDelete(T ent, EntityConfig config, ValidationErrors errors) throws Exception;
	public Long nextTempIdNegative() throws Exception;
	public Long nextTempId() throws Exception;
}
